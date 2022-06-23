package com.example.bb

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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
import com.google.android.material.navigation.NavigationView


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


        fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.logout -> {
                    Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show()
                    Log.i("MainActivity", "Sign out clicked!")
                }
                R.id.arabic -> {
                    Toast.makeText(this, "arabic", Toast.LENGTH_SHORT).show()
                }
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
            return true
        }

        navController.addOnDestinationChangedListener{ nc: NavController, nd: NavDestination, args:Bundle? ->
             navMenu = binding.navView.menu

            if(nd.id == nc.graph.startDestinationId){
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                navMenu.findItem(R.id.myDonationsFragment).isVisible = false
                navMenu.findItem(R.id.logout).isVisible = false
            } else {
                navMenu.findItem(R.id.myDonationsFragment).isVisible = true
                navMenu.findItem(R.id.logout).isVisible = true
                //drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                //drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        //return NavigationUI.navigateUp(navController,drawerLayout)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}
