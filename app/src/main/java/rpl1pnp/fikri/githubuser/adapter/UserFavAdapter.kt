package rpl1pnp.fikri.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import rpl1pnp.fikri.githubuser.adapter.UserFavAdapter.ViewHolder
import rpl1pnp.fikri.githubuser.databinding.ItemMainBinding
import rpl1pnp.fikri.githubuser.repository.local.entity.UserFavorite

class UserFavAdapter(private val listener: (UserFavorite) -> Unit) :
    RecyclerView.Adapter<ViewHolder>() {
    var listUserFav: List<UserFavorite> = mutableListOf()

    class ViewHolder(private val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(usersFav: UserFavorite, listener: (UserFavorite) -> Unit) {
            binding.civProfile.load(usersFav.image)
            binding.tvUsernameProfiles.text = usersFav.username
            itemView.setOnClickListener { listener(usersFav) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMainBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUserFav[position], listener)
    }

    override fun getItemCount(): Int = listUserFav.size
}