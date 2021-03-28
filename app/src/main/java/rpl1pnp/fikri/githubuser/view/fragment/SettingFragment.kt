package rpl1pnp.fikri.githubuser.view.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import rpl1pnp.fikri.githubuser.R
import rpl1pnp.fikri.githubuser.databinding.FragmentSettingBinding
import rpl1pnp.fikri.githubuser.utils.Prefs
import rpl1pnp.fikri.githubuser.view.activity.MainActivity

class SettingFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentSettingBinding? = null
    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = "Setting"
        prefs = Prefs(requireActivity())
        binding.scDarkMode.isChecked = prefs.darkModePref
        darkMode()
        changeLanguage()
    }

    private fun darkMode() {
        binding.scDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                prefs.darkModePref = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                prefs.darkModePref = false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_app_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorites -> {
                activity?.supportFragmentManager?.commit {
                    replace(
                        R.id.fragment_container,
                        FavoritesFragment()
                    ).addToBackStack(FavoritesFragment::class.java.simpleName)
                }
                return true
            }
            R.id.about -> {
                activity?.supportFragmentManager?.commit {
                    replace(
                        R.id.fragment_container,
                        AboutFragment()
                    ).addToBackStack(AboutFragment::class.java.simpleName)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeLanguage() {
        binding.cvChangeLanguage.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}