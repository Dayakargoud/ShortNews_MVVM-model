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
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.dayakar.shortnews.BuildConfig
import com.dayakar.shortnews.R
import com.dayakar.shortnews.databinding.ActivityMainBinding
import com.dayakar.shortnews.viewpager.FragmentViewPagerDirections
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding=DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        )

        val navController = this.findNavController(R.id.myNavHostFragment)
       // navController.setGraph(R.navigation.navgraph)
        setSupportActionBar(binding.toolbar)

         NavigationUI.setupActionBarWithNavController(this,navController)
        NavigationUI.setupWithNavController(binding.toolbar,navController,binding.drawerLayout).also {
            NavigationUI.setupWithNavController(binding.navView,navController)

        }

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
                    val navController = findNavController(R.id.myNavHostFragment)
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

            R.id.share_app->{
                shareApp()
                true
            }
            R.id.addCategoryFragment -> {
                val navController = this.findNavController(R.id.myNavHostFragment)
                navController.navigate(FragmentViewPagerDirections.actionFragmentViewPager2ToAddCategoryFragment())
                true
            }
            R.id.about_app->{
                val version=BuildConfig.VERSION_NAME
                Toast.makeText(this,"Short News\ncurrent version-$version",Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


//    override fun onSupportNavigateUp(): Boolean {
//        val navController = this.findNavController(R.id.myNavHostFragment)
//        return navController.navigateUp()
//    }

    private fun shareApp(){
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText("I am exploring the latest news in single app i.e Short news app.")
            .setType("text/plain")
            .createChooserIntent()
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        startActivity(shareIntent)
    }

}
