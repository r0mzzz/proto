package com.example.core.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.core.extensions.deeplinkNavigate
import com.example.core.tools.NavigationCommand

abstract class BaseFragment<VB : ViewBinding, ViewModel: BaseViewModel> : Fragment() {

    private var _binding: VB? = null
    lateinit var binding: VB
    lateinit var viewmodel: ViewModel
    protected abstract fun getViewModelClass(): Class<ViewModel>
    protected open fun getViewModelScope(): ViewModelStoreOwner? = null
    protected abstract val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> VB
    protected open val bindViews: VB.() -> Unit = {}



    private fun init() {
        viewmodel = ViewModelProvider(getViewModelScope() ?: requireActivity())[getViewModelClass()]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingCallback.invoke(inflater, container, false)
        init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.let {
            it.bindViews()
        }
        binding.root.findViewWithTag<ImageView>("toolbarBackImage")?.let {
            it.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewmodel.navigationCommands.observe(viewLifecycleOwner) { command ->
            when (command) {
                is NavigationCommand.To -> {
                    command.extras?.let { extras ->
                        findNavController().navigate(
                            command.directions,
                            extras
                        )
                    } ?: run {
                        findNavController().navigate(
                            command.directions
                        )
                    }
                }
                is NavigationCommand.BackTo -> findNavController().getBackStackEntry(command.destinationId)
                is NavigationCommand.Back -> findNavController().popBackStack()
                is NavigationCommand.Deeplink -> findNavController().deeplinkNavigate(
                    direction = command.deeplink,
                    extras = command.extras,
                    isInclusive = command.isInclusive
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun onNewIntent(intent: Intent?) {}

}
