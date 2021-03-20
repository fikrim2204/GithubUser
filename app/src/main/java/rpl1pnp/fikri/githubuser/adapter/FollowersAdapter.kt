package rpl1pnp.fikri.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import rpl1pnp.fikri.githubuser.databinding.ItemMainBinding
import rpl1pnp.fikri.githubuser.model.DataFollow

class FollowersAdapter(
    private val listener: (DataFollow) -> Unit
) : RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {
    var followers: ArrayList<DataFollow> = arrayListOf()
    class ViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(follow: DataFollow, listener: (DataFollow) -> Unit) {
            binding.civProfile.load(follow.avatar_url)
            binding.tvUsernameProfile.text = follow.login
            itemView.setOnClickListener{
                listener(follow)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMainBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(followers[position], listener)
    }

    override fun getItemCount(): Int = followers.size
}