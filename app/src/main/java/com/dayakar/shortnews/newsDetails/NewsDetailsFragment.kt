package com.dayakar.shortnews.newsDetails

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ShareCompat
import androidx.core.widget.NestedScrollView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.dayakar.shortnews.R
import com.dayakar.shortnews.databinding.FragmentNewsDetailsBinding
import com.dayakar.shortnews.newsData.Article
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_news_details.*
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class NewsDetailsFragment : Fragment() {
   var newArticle:Article?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        // Inflate the layout for this fragment
       val binding=FragmentNewsDetailsBinding.inflate(inflater)


        (activity as AppCompatActivity).supportActionBar?.hide()

        //val article=NewsDetailsFragmentArgs.fromBundle(arguments!!).selectedArticle

        val article:Article?= arguments?.getParcelable("article")
        newArticle=article
        binding.newsDetailHeader.text=article?.title

        Glide.with(binding.newsDetailImage.context).load(article?.urlToImage).into(binding.newsDetailImage)
        binding.newsDescription.text=article?.description
        binding.newsDetailsTiming.text=article?.publishedAt

        var isToolbarShown = false

        // scroll change listener begins at Y = 0 when image is fully collapsed
        binding.newsDetailScrollview.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->

                // User scrolled past image to height of toolbar and the title text is
                // underneath the toolbar, so the toolbar should be shown.
                val shouldShowToolbar = scrollY > binding.toolbar.height

                // The new state of the toolbar differs from the previous state; update
                // appbar and toolbar attributes.
                if (isToolbarShown != shouldShowToolbar) {
                    isToolbarShown = shouldShowToolbar

                    // Use shadow animator to add elevation if toolbar is shown
                    binding.appbar.isActivated = shouldShowToolbar

                    // Show the plant name if toolbar is shown
                    binding.toolbarLayout.isTitleEnabled = shouldShowToolbar


                }
            }
        )
        binding.fab.setOnClickListener {
            Snackbar.make(binding.root, "Added to favourite", Snackbar.LENGTH_LONG)
                .show()
        }

        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
       setHasOptionsMenu(true)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.action_share){
            try{
                shareArticle()
            }catch (e:Exception){
                Toast.makeText(context,"Please try again later",Toast.LENGTH_SHORT).show()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDetach() {
        super.onDetach()
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

    }

    private fun hideAppBarFab(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.hide()
    }

    private fun shareArticle(){

        val shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
            .setText("${newArticle?.title} /n for more details download the Short news app.")
            .setType("text/plain")
            .createChooserIntent()
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        startActivity(shareIntent)
    }

}
