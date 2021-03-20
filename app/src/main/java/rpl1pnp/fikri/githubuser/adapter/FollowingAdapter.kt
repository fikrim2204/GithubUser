package rpl1pnp.fikri.githubuser.adapter

//class FollowingAdapter(
//    private val listener: (DataFollowing) -> Unit
//) : RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {
//    var following: ArrayList<DataFollowing> = arrayListOf()
//    class ViewHolder(private val binding: ItemFollowBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(following: DataFollowing, listener: (DataFollowing) -> Unit) {
//            binding.civProfile.load(following.avatar_url)
//            binding.tvNameProfile.text = following.login
//            itemView.setOnClickListener{
//                listener(following)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val binding = ItemFollowBinding.inflate(layoutInflater)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(following[position], listener)
//    }
//
//    override fun getItemCount(): Int = following.size
//}