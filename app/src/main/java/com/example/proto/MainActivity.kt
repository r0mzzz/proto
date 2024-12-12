package com.example.proto

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.example.proto.databinding.FragmentMainActivityBinding
import com.example.proto.viewmodel.MainViewModel
import com.example.core.base.BaseActivity


class MainActivity : BaseActivity() {
    private lateinit var mainBinding: FragmentMainActivityBinding
    private lateinit var navHost: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var graph: NavGraph
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        mainBinding = FragmentMainActivityBinding.inflate(LayoutInflater.from(this)).also {
            setContentView(it.root)
        }
        setStartGraph(savedInstanceState)
    }

    private fun setStartGraph(savedInstanceState: Bundle?) {
        navHost =
            supportFragmentManager.findFragmentById(R.id.main_layout) as NavHostFragment?
                ?: return
        navController = navHost.navController
        if (savedInstanceState == null) {
            graph = navHost.navController.navInflater.inflate(R.navigation.main_graph)
            val (startGraphId, startGraphArgs) = findStartGraph()
            graph.setStartDestination(startGraphId)
            navController.setGraph(
                graph = graph,
                startDestinationArgs = startGraphArgs,
            )
        }
    }

    data class FindStartGraphResult(
        val graphId: Int,
        val args: Bundle? = null,
    )

    private fun findStartGraph(): FindStartGraphResult {
        return FindStartGraphResult(
            graphId = R.id.main_layout,
            args = null,
        )
    }
}
