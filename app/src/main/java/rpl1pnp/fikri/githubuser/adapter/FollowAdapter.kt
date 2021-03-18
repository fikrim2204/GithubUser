package rpl1pnp.fikri.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import rpl1pnp.fikri.githubuser.databinding.ItemFollowBinding
import rpl1pnp.fikri.githubuser.model.DataUser

class FollowAdapter(
    private var item: List<DataUser>,
    private val listener: (DataUser) -> Unit
) : RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: DataUser, listener: (DataUser) -> Unit) {
            binding.civProfile.load(user.avatar_url)
            binding.tvNameProfile.text = user.name
            binding.description.text = user.bio.toString()
            binding.tvLocation.text = user.location
            itemView.setOnClickListener{
                listener(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFollowBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item[position], listener)
    }

    override fun getItemCount(): Int = item.size
}