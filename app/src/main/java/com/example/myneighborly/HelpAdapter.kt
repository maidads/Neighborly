import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myneighborly.HelpRequest
import com.example.myneighborly.R

class HelpAdapter(
    private val helpList: List<HelpRequest>,
    private val onChatClick: (HelpRequest) -> Unit
) : RecyclerView.Adapter<HelpAdapter.HelpViewHolder>() {

    class HelpViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.helpTitle)
        val type: TextView = view.findViewById(R.id.helpType)
        val address: TextView = view.findViewById(R.id.helpAddress)
        val details: TextView = view.findViewById(R.id.helpDetails)
        val chatButton: Button = view.findViewById(R.id.chatButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.help_card, parent, false)
        return HelpViewHolder(view)
    }

    override fun onBindViewHolder(holder: HelpViewHolder, position: Int) {
        val help = helpList[position]
        holder.title.text = help.category
        holder.type.text = "Type: ${help.type}"
        holder.address.text = "Address: ${help.address}"
        holder.details.text = help.details

        holder.chatButton.setOnClickListener {
            onChatClick(help)
        }
    }

    override fun getItemCount(): Int = helpList.size
}
