package com.dayakar.shortnews.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.dayakar.shortnews.BuildConfig
import com.dayakar.shortnews.R
import com.dayakar.shortnews.databinding.ActivityMainBinding
import com.dayakar.shortnews.viewpager.FragmentViewPagerDirections
import com.google.android.material.internal.NavigationMenuItemView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
      lateinit var navController: NavController
      lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         binding=DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

         navController = this.findNavController(R.id.myNavHostFragment)
       // navController.setGraph(R.navigation.navgraph)
        setSupportActionBar(binding.toolbar)

         NavigationUI.setupActionBarWithNavController(this,navController)
        NavigationUI.setupWithNavController(binding.toolbar,navController,binding.drawerLayout)
        binding.navView.setNavigationItemSelectedListener(this)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        val searchManager= getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.search_action)?.actionView as android.widget.SearchView).apply {
            queryHint="Enter keywords"
            setOnQueryTextListener(object: android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // Hide the keyboard.
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
                    //send query to searchFragment
                  //  val navController = findNavController(R.id.myNavHostFragment)
                    navController.navigate(
                        FragmentViewPagerDirections.actionFragmentViewPager2ToSearchFragment(
                            query!!
                        )
                    )
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {

                    }
                    return false
                }

            })
        }
       return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.addCategoryFragment -> {
                  navController.navigate(R.id.action_fragment_viewPager2_to_addCategoryFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()
    }

    private fun shareApp(){
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText("I am exploring the latest news in single app i.e Short news app.")
            .setType("text/plain")
            .createChooserIntent()
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        startActivity(shareIntent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

          when(item.itemId) {
             R.id.share_app -> {
                 shareApp()
             }
             R.id.addCategoryFragment -> {
                 navController.navigate(R.id.action_fragment_viewPager2_to_addCategoryFragment)
             }
             R.id.about_app->{
                 val version=BuildConfig.VERSION_NAME
                 Toast.makeText(this,"Short News\ncurrent version-$version",Toast.LENGTH_LONG).show()
             }
         }
        binding.drawerLayout.closeDrawer(GravityCompat.START)

         return true
    }

    override fun onBackPressed() {


        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else
          super.onBackPressed()
    }

}
