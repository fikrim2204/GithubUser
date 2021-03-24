package rpl1pnp.fikri.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import rpl1pnp.fikri.githubuser.adapter.MainAdapter.ViewHolder
import rpl1pnp.fikri.githubuser.databinding.ItemMainBinding
import rpl1pnp.fikri.githubuser.model.UserSingleResponse

class MainAdapter(
    private val listener: (UserSingleResponse) -> Unit
) :
    RecyclerView.Adapter<ViewHolder>() {
    var userSingleResponse: List<UserSingleResponse> = mutableListOf()

    class ViewHolder(private val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(users: UserSingleResponse, listener: (UserSingleResponse) -> Unit) {
            binding.civProfile.load(users.avatar_url)
            binding.tvUsernameProfiles.text = users.login
            itemView.setOnClickListener {
                listener(users)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMainBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userSingleResponse[position], listener)
    }

    override fun getItemCount(): Int = userSingleResponse.size
}