package com.dayakar.shortnews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.dayakar.shortnews.databinding.FragmentAddCategoryBinding
import com.dayakar.shortnews.viewpager.ViewPagerViewModel

/**
 * A simple [AddCategoryFragment] subclass. below this class [CategoryFragment] is there
 */
class AddCategoryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentAddCategoryBinding.inflate(inflater)

        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment_container, CategoryPrefFragment())
            ?.commit()
        return binding.root
    }
}
class CategoryPrefFragment : PreferenceFragmentCompat() {
    lateinit var model: ViewPagerViewModel


        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.add_categoies_pref, rootKey)

            model = activity?.let { ViewModelProvider(it).get(ViewPagerViewModel::class.java) }!!


        }

        override fun onPreferenceTreeClick(preference: Preference?): Boolean {

            val title = preference?.title.toString()
            val key = preference?.key.toString()
            val selectedPref = findPreference<CheckBoxPreference>(key)
            if (selectedPref != null) {
                val isPrefSelected: Boolean = selectedPref.sharedPreferences.getBoolean(key, false)
                if (isPrefSelected) {
                        model.addCategory(key)
                } else {
                    model.removeCategory(key)
                        Toast.makeText(context, "$key is removed", Toast.LENGTH_SHORT).show()

                    }

                }

            return false
        }


    }

