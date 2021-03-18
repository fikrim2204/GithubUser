package rpl1pnp.fikri.githubuser.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import rpl1pnp.fikri.githubuser.adapter.MainAdapter
import rpl1pnp.fikri.githubuser.databinding.FragmentMainBinding
import rpl1pnp.fikri.githubuser.model.User

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: MainAdapter
    private var user: List<User> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler()
    }

    private fun recycler() {
        binding.rvUserGithub.layoutManager = LinearLayoutManager(activity)
        adapter = MainAdapter(user) {
        }
        binding.rvUserGithub.adapter = adapter
    }
}