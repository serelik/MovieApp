package com.serelik.movieapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.serelik.movieapp.databinding.ActivityMainBinding
import com.serelik.movieapp.extensions.doOnApplyWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
            false

        setupInsets()

        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navView: BottomNavigationView = viewBinding.bottomNavigation
        navView.setupWithNavController(navController)
        navView.setOnApplyWindowInsetsListener(null)
    }

    fun changeBottomNavigationVisibility(isVisible: Boolean) {
        viewBinding.bottomNavigation.isVisible = isVisible
    }

    private fun setupInsets() {
        this.window.decorView.doOnApplyWindowInsets { view, insets, rect ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())

            val isKeyboardVisible = imeInsets.bottom > 0

            this.changeBottomNavigationVisibility(!isKeyboardVisible)

            val bottomPadding = if (isKeyboardVisible) 0 else systemBarsInsets.bottom

            viewBinding.root.updatePadding(
                bottom = bottomPadding,
                right = systemBarsInsets.right,
                left = systemBarsInsets.left
            )

            WindowInsetsCompat.Builder()
                .setInsets(
                    WindowInsetsCompat.Type.systemBars(),
                    Insets.of(
                        systemBarsInsets.left,
                        systemBarsInsets.top,
                        systemBarsInsets.right,
                        0
                    )
                ).setInsets(
                    WindowInsetsCompat.Type.ime(),
                    Insets.of(
                        imeInsets.left,
                        imeInsets.top,
                        imeInsets.right,
                        0
                    )
                ).build()
        }
    }
}
