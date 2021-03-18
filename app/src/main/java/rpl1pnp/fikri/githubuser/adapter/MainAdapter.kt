package rpl1pnp.fikri.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import rpl1pnp.fikri.githubuser.adapter.MainAdapter.ViewHolder
import rpl1pnp.fikri.githubuser.databinding.ItemMainBinding
import rpl1pnp.fikri.githubuser.utils.getId

class MainAdapter(private var item: List<Users>, private val listener: (Users) -> Unit) :
    RecyclerView.Adapter<ViewHolder>() {
    class ViewHolder(private val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(users: Users, listener: (Users) -> Unit) {
            binding.civProfile.load(getId(binding.root.context, users.avatar))
            binding.tvNameProfile.text = users.name
            binding.tvUsernameProfile.text = users.username
            val follower = "${users.follower} Followers"
            val following = "${users.following} Following"
            binding.tvFollower.text = follower
            binding.tvFollowing.text = following
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
        holder.bind(item[position], listener)
    }

    override fun getItemCount(): Int = item.size
}