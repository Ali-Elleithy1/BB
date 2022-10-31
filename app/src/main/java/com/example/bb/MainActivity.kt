package com.example.bb

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.bb.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navMenu: Menu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        drawerLayout = binding.drawerLayout
        val navController = this.findNavController(R.id.myNavHostFragment)
        //NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
        //appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.loginFragment, R.id.patientHomeFragment,
            R.id.donorHomeFragment),drawerLayout)
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration)
        NavigationUI.setupWithNavController(binding.navView,navController)

        val lang = LanguageManager(this)

        val navHost = R.id.myNavHostFragment

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.aboutFragment -> {
                    this.findNavController(navHost).navigate(R.id.aboutFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.myDonationsFragment -> {
                    Log.i("MainActivity", "My Donations clicked!")
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.logout -> {
                    this.findNavController(navHost).navigate(R.id.loginFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.arabic -> {
                    if(it.title.equals(R.string.english_label))
                    {
                        lang.updateResource("en")
                        recreate()
                        it.setTitle(R.string.arabic_label)
                    }
                    else
                    {
                        lang.updateResource("ar")
                        recreate()
                        it.setTitle(R.string.english_label)
                    }
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
            drawerLayout.refreshDrawableState()
            drawerLayout.invalidate()
            true
        }

        navController.addOnDestinationChangedListener{ nc: NavController, nd: NavDestination, args:Bundle? ->
            navMenu = binding.navView.menu

            if(nd.id == nc.graph.startDestinationId){
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                navMenu.findItem(R.id.myDonationsFragment).isEnabled = false
                navMenu.findItem(R.id.logout).isEnabled = false
                //navMenu.findItem(R.id.myDonationsFragment).isVisible = false
                //navMenu.findItem(R.id.logout).isVisible = false
            } else {
                navMenu.findItem(R.id.myDonationsFragment).isEnabled = true
                navMenu.findItem(R.id.logout).isEnabled = true
//                navMenu.findItem(R.id.myDonationsFragment).isVisible = true
//                navMenu.findItem(R.id.logout).isVisible = true
                //drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                //drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
            }
            drawerLayout.refreshDrawableState()
            drawerLayout.invalidate()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        //return NavigationUI.navigateUp(navController,drawerLayout)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}
