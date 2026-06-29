package unikom.dimasnurfauzi.parsingapipublik.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import unikom.dimasnurfauzi.parsingapipublik.R
import unikom.dimasnurfauzi.parsingapipublik.model.DataItem

class UserAdapter(private val users: MutableList<DataItem>): RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    fun addUser(newUser: DataItem) {
        users.add(newUser)
        notifyItemInserted(users.lastIndex)
    }

    fun clear() {
        users.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_users, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = users[position]
        holder.tvName.text = "${user.firstName} ${user.lastName}"
        holder.tvEmail.text = user.email
        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .apply(RequestOptions().override(80, 80)
                .placeholder(R.drawable.ic_avatar))
            .transform(CircleCrop())
            .into(holder.ivAvatar)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.itemName)
        var tvEmail: TextView = itemView.findViewById(R.id.itemEmail)
        var ivAvatar: ImageView = itemView.findViewById(R.id.itemAvatar)
    }
}