package rpl1pnp.fikri.githubuser.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import rpl1pnp.fikri.githubuser.adapter.MainAdapter.ViewHolder
import rpl1pnp.fikri.githubuser.databinding.ItemMainBinding
import rpl1pnp.fikri.githubuser.model.User
import rpl1pnp.fikri.githubuser.utils.getId

class MainAdapter(private var item: List<User>, private val listener: (User) -> Unit) :
    RecyclerView.Adapter<ViewHolder>() {
    class ViewHolder(private val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, listener: (User) -> Unit) {
            binding.civProfile.load(getId(binding.root.context, user.avatar))
            Log.i(TAG, user.avatar)
            binding.tvNameProfile.text = user.name
            binding.tvUsernameProfile.text = user.username
            val follower = "${user.follower} Followers"
            val following = "${user.following} Following"
            binding.tvFollower.text = follower
            binding.tvFollowing.text = following
            itemView.setOnClickListener {
                listener(user)
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