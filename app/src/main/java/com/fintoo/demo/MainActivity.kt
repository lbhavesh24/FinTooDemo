package com.fintoo.demo

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.NavHostFragment
import com.fintoo.demo.databinding.ActivityMainBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.luseen.spacenavigation.SpaceItem


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var binding:ActivityMainBinding? = null
    private lateinit var navController: NavController

    @ExperimentalBadgeUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        binding!!.msgFab.viewTreeObserver.addOnGlobalLayoutListener {
            val badgeDrawable = BadgeDrawable.create(this@MainActivity)
            badgeDrawable.number = 2
            //Important to change the position of the Badge
            badgeDrawable.horizontalOffset = 30
            badgeDrawable.verticalOffset = 20

            BadgeUtils.attachBadgeDrawable(badgeDrawable, binding!!.msgFab, null)
        }
        setupNavigationHost()
        binding!!.yourInfoCard.setOnClickListener(this)
        binding!!.knowYouBetterCard.setOnClickListener(this)
        binding!!.KnowYourRiskCard.setOnClickListener(this)
        binding!!.yourFamilyCard.setOnClickListener(this)

        binding!!.bottomNavigation.addSpaceItem(SpaceItem("DASHBOARD", R.drawable.ic_dashboard))
        binding!!.bottomNavigation.addSpaceItem(SpaceItem("PROFILE", R.drawable.ic_baseline_person_24))
        binding!!.bottomNavigation.setCentreButtonSelectable(true)
        binding!!.bottomNavigation.setCentreButtonSelected()
        binding!!.bottomNavigation.showIconOnly()
    }

    private fun setupNavigationHost() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onClick(v: View?) {
        when {
            v === binding!!.yourInfoCard -> {
                when (navController.currentDestination?.id) {
                    R.id.knowYouBetter -> {
                        navController.navigateSafe(R.id.action_knowYouBetter_to_userInfo)
                    }
                    R.id.knowYourRisk -> {
                        navController.navigateSafe(R.id.action_knowYourRisk_to_userInfo)
                    }
                    R.id.yourFamily -> {
                        navController.navigateSafe(R.id.action_yourFamily_to_userInfo)
                    }
                }
            }
            v === binding!!.knowYouBetterCard -> {
                when (navController.currentDestination?.id) {
                    R.id.userInfo -> {
                        navController.navigateSafe(R.id.action_userInfo_to_knowYouBetter)
                    }
                    R.id.knowYourRisk -> {
                        navController.navigateSafe(R.id.action_knowYourRisk_to_knowYouBetter)
                    }
                    R.id.yourFamily -> {
                        navController.navigateSafe(R.id.action_yourFamily_to_knowYouBetter)
                    }
                }
            }
            v === binding!!.KnowYourRiskCard -> {
                when (navController.currentDestination?.id) {
                    R.id.userInfo -> {
                        navController.navigateSafe(R.id.action_userInfo_to_knowYourRisk)
                    }
                    R.id.knowYouBetter -> {
                        navController.navigateSafe(R.id.action_knowYouBetter_to_knowYourRisk)
                    }
                    R.id.yourFamily -> {
                        navController.navigateSafe(R.id.action_yourFamily_to_knowYourRisk)
                    }
                }
            }
            v === binding!!.yourFamilyCard -> {
                when (navController.currentDestination?.id) {
                    R.id.userInfo -> {
                        navController.navigateSafe(R.id.action_userInfo_to_yourFamily)
                    }
                    R.id.knowYouBetter -> {
                        navController.navigateSafe(R.id.action_knowYouBetter_to_yourFamily)
                    }
                    R.id.knowYourRisk -> {
                        navController.navigateSafe(R.id.action_knowYourRisk_to_yourFamily)
                    }
                }
            }
        }
    }

    private fun NavController.navigateSafe(
        @IdRes resId: Int,
        args: Bundle? = null,
        navOptions: NavOptions? = null,
        navExtras: Navigator.Extras? = null
    ) {
        val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)
        if (action != null && currentDestination?.id != action.destinationId) {
            navigate(resId, args, navOptions, navExtras)
        }
    }

    override fun onBackPressed() {
        if (!navController.popBackStack()) {
            // Call finish() on your Activity
            finish()
        }
    }
}