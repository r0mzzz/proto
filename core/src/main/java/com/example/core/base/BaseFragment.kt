package com.example.core.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.core.extensions.deeplinkNavigate
import com.example.core.tools.NavigationCommand
import kotlinx.coroutines.launch

abstract class BaseFragment<State, Effect, VB : ViewBinding, ViewModel : BaseViewModel<State, Effect>> :
    Fragment() {

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

    protected open fun observeState(state: State) {}

    protected fun observeEffect(effect: Effect) {}

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

    protected fun <T> LiveData<T>.observe(block: (T) -> Unit){
        observe(viewLifecycleOwner, block)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewmodel.state.observe(viewLifecycleOwner, ::observeState)
        viewmodel.effect.observe(viewLifecycleOwner, ::observeEffect)
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
