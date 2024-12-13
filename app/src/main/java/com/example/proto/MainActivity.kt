package com.example.proto

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.core.base.BaseActivity
import com.example.proto.databinding.FragmentMainActivityBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : BaseActivity() {
    private lateinit var mainBinding: FragmentMainActivityBinding
    private lateinit var navHost: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var graph: NavGraph
    private lateinit var bottomNav: BottomNavigationView

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
            supportFragmentManager.findFragmentById(R.id.main_nav_fragment) as NavHostFragment? ?: return
        navController = navHost.navController
        bottomNav = findViewById(R.id.bottomNav)
        setupWithNavController(navigationBarView = bottomNav, navController = navController)
        if (savedInstanceState == null) {
            graph = navHost.navController.navInflater.inflate(R.navigation.main_graph)
            val (startGraphId, startGraphArgs) = findStartGraph()
            graph.setStartDestination(startGraphId)
            navController.setGraph(graph, startGraphArgs)
        }
    }

    data class FindStartGraphResult(
        val graphId: Int,
        val args: Bundle? = null,
    )

    private fun findStartGraph(): FindStartGraphResult {
        return FindStartGraphResult(
            graphId = R.id.homePageFragment,
            args = null,
        )
    }
}
