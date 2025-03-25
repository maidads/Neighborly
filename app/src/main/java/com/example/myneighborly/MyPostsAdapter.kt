import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myneighborly.HelpRequest
import com.example.myneighborly.R

class MyPostsAdapter(
    private val posts: List<HelpRequest>,
    private val onDeleteClick: (HelpRequest) -> Unit
) : RecyclerView.Adapter<MyPostsAdapter.MyPostViewHolder>() {

    class MyPostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.helpTitle)
        val type: TextView = view.findViewById(R.id.helpType)
        val address: TextView = view.findViewById(R.id.helpAddress)
        val details: TextView = view.findViewById(R.id.helpDetails)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)
        val background: ImageView = view.findViewById(R.id.helpBackground)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_post_card, parent, false)
        return MyPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyPostViewHolder, position: Int) {
        val post = posts[position]
        holder.title.text = post.category
        holder.type.text = "Type: ${post.type}"
        holder.address.text = "Address: ${post.address}"
        holder.details.text = post.details

        when (post.type.lowercase()) {
            "moving assistance", "flytthjälp" -> holder.background.setImageResource(R.drawable.bg_moving)
            "grocery shopping", "handla mat" -> holder.background.setImageResource(R.drawable.bg_grocery)
            "homework", "math homework help", "läxhjälp" -> holder.background.setImageResource(R.drawable.bg_homework)
            "mail pickup" -> holder.background.setImageResource(R.drawable.bg_mail)
            "babysitting", "barnpassning" -> holder.background.setImageResource(R.drawable.bg_babysitting)
            else -> holder.background.setImageResource(R.drawable.bg_babysitting)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClick(post)
        }
    }

    override fun getItemCount(): Int = posts.size
}
