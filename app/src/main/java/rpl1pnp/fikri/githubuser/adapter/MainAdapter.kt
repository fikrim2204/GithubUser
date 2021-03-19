package rpl1pnp.fikri.githubuser.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import rpl1pnp.fikri.githubuser.adapter.MainAdapter.ViewHolder
import rpl1pnp.fikri.githubuser.databinding.ItemMainBinding
import rpl1pnp.fikri.githubuser.model.DataUser

class MainAdapter(
    private val listener: (DataUser) -> Unit
) :
    RecyclerView.Adapter<ViewHolder>() {
    var user: List<DataUser> = mutableListOf()
    class ViewHolder(private val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(users: DataUser, listener: (DataUser) -> Unit) {
            binding.civProfile.load(users.avatar_url)
            Log.i("MainAdapter", users.login.toString())
            binding.tvUsernameProfile.text = users.login
            itemView.setOnClickListener {
                listener(users)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMainBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(user[position], listener)
    }

    override fun getItemCount(): Int = user.size
}