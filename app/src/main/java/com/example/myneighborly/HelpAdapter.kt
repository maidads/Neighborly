import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myneighborly.HelpRequest
import com.example.myneighborly.R
import com.example.myneighborly.generateChatId
import com.google.firebase.auth.FirebaseAuth
import com.example.myneighborly.HelpNeededFragmentDirections

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
        val background: ImageView = view.findViewById(R.id.helpBackground)
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

        when (help.type.lowercase()) {
            "moving assistance", "flytthjälp" -> holder.background.setImageResource(R.drawable.bg_moving)
            "grocery shopping", "handla mat" -> holder.background.setImageResource(R.drawable.bg_grocery)
            "homework", "math homework help", "läxhjälp" -> holder.background.setImageResource(R.drawable.bg_homework)
            "mail pickup" -> holder.background.setImageResource(R.drawable.bg_mail)
            "babysitting", "barnpassning" -> holder.background.setImageResource(R.drawable.bg_babysitting)
            else -> holder.background.setImageResource(R.drawable.bg_babysitting)
        }

        holder.chatButton.setOnClickListener {
            onChatClick(help)
        }
    }

    override fun getItemCount(): Int = helpList.size
}
