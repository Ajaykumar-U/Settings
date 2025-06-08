package com.android.car.settings.presentation.settings

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.android.car.settings.R
import com.android.car.settings.databinding.ActivitySettingsBinding
import com.google.android.flexbox.FlexboxLayout
import androidx.core.view.isVisible

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var navController: NavController
    private val breadcrumbPath = mutableListOf<Pair<String, Int>>() // Label, destinationId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = (supportFragmentManager
            .findFragmentById(R.id.settings_nav_host_fragment) as NavHostFragment).navController

        binding.backIcon.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val label = destination.label?.toString() ?: return@addOnDestinationChangedListener
            val destId = destination.id

            val existingIndex = breadcrumbPath.indexOfFirst { it.second == destId }
            if (existingIndex != -1) {
                breadcrumbPath.subList(existingIndex + 1, breadcrumbPath.size).clear()
            } else {
                breadcrumbPath.add(label to destId)
            }
            // Toggle UI based on destination
            if (breadcrumbPath.size == 1) {
                // Root screen (SettingsLandingFragment)
                binding.backIcon.visibility = ImageView.GONE
                binding.searchIcon.visibility = ImageView.VISIBLE
                binding.breadcrumbContainer.justifyContent = com.google.android.flexbox.JustifyContent.CENTER
            } else {
                // Any sub-screen (General, FactoryReset, etc.)
                binding.backIcon.visibility = ImageView.VISIBLE
                binding.searchIcon.visibility = ImageView.GONE
                binding.breadcrumbContainer.justifyContent = com.google.android.flexbox.JustifyContent.FLEX_START
            }
            updateBreadcrumbUI()
        }
    }

    private fun updateBreadcrumbUI() {
        binding.breadcrumbContainer.removeAllViews()

        breadcrumbPath.forEachIndexed { index, (label, destId) ->
            val labelView = TextView(this).apply {
                text = label
                setTextColor(android.graphics.Color.parseColor("#e1e1e1"))
                textSize = 32f
                gravity = android.view.Gravity.CENTER_VERTICAL
                val params = FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, 27.dp, 0, 0)
                layoutParams = params

                setOnClickListener {
                    navController.popBackStack(destId, false)
                    breadcrumbPath.subList(index + 1, breadcrumbPath.size).clear()
                    updateBreadcrumbUI()
                }
            }
            binding.breadcrumbContainer.addView(labelView)

            if (index < breadcrumbPath.size - 1) {
                val arrow = ImageView(this).apply {
                    setImageResource(R.drawable.ico_right)
                    val params = FlexboxLayout.LayoutParams(
                        FlexboxLayout.LayoutParams.WRAP_CONTENT,
                        FlexboxLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(0, 20.dp, 0, 0)
                    layoutParams = params
                }
                binding.breadcrumbContainer.addView(arrow)
            }
        }

        // Show back icon only if more than 1 breadcrumb entry
        binding.backIcon.isVisible = breadcrumbPath.size > 1
    }

    // Extension to convert dp to pixels
    private val Int.dp: Int
        get() = (this * resources.displayMetrics.density).toInt()
}