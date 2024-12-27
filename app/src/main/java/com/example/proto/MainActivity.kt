package com.example.proto

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.core.base.BaseActivity
import com.example.proto.databinding.FragmentMainActivityBinding
import com.example.proto.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.elevation.SurfaceColors
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var mainBinding: FragmentMainActivityBinding
    private lateinit var navHost: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var graph: NavGraph
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        changeSystemBottomBarColor()
        super.onCreate(savedInstanceState)
        mainBinding = FragmentMainActivityBinding.inflate(LayoutInflater.from(this)).also {
            setContentView(it.root)
        }
        setStartGraph(savedInstanceState)
    }

    private fun changeSystemBottomBarColor(){
        window.setNavigationBarColor(SurfaceColors.SURFACE_2.getColor(this));

    }

    private fun setStartGraph(savedInstanceState: Bundle?) {
        navHost =
            supportFragmentManager.findFragmentById(R.id.main_nav_fragment) as NavHostFragment?
                ?: return
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
            graphId = R.id.home_graph,
            args = null,
        )
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.home_graph -> {
//                navController.popBackStack(
//                    R.id.home_graph,
//                    false
//                )
//                return true
//            }
//
//            else -> return super.onOptionsItemSelected(item)
//        }
//    }
}
