package rpl1pnp.fikri.githubuser.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import rpl1pnp.fikri.githubuser.R
import rpl1pnp.fikri.githubuser.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentAboutBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvCredit1.setOnClickListener {
            val url = "https://blush.design/artists/ivan-mesaros"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        binding.cvCredit2.setOnClickListener {
            val url = "https://blush.design/artists/mariana-gonzalez"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        binding.cvCredit3.setOnClickListener {
            val url = "https://www.flaticon.com/authors/pixel-perfect"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_app_bar_setting, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                findNavController().navigate(R.id.action_aboutFragment_to_mainFragment)
                return true
            }
            R.id.setting -> {
                    findNavController().navigate(R.id.action_aboutFragment_to_settingFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}