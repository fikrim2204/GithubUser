package rpl1pnp.fikri.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import rpl1pnp.fikri.githubuser.databinding.ItemMainBinding
import rpl1pnp.fikri.githubuser.model.DataFollow

class FollowAdapter(
    private val listener: (DataFollow) -> Unit
) : RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    var follow: ArrayList<DataFollow> = arrayListOf()

    class ViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(follow: DataFollow, listener: (DataFollow) -> Unit) {
            binding.civProfile.load(follow.avatar_url)
            binding.tvUsernameProfiles.text = follow.login
            itemView.setOnClickListener {
                listener(follow)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMainBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(follow[position], listener)
    }

    override fun getItemCount(): Int = follow.size
}