import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myneighborly.HelpRequest
import com.example.myneighborly.R
import com.example.myneighborly.adapter.MyPostsAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyPostsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val myPosts = mutableListOf<HelpRequest>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_posts, container, false)
        recyclerView = view.findViewById(R.id.myPostsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadMyPosts()
        return view
    }

    private fun loadMyPosts() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("users")
            .document(userId)
            .collection("helpRequests")
            .get()
            .addOnSuccessListener { result ->
                myPosts.clear()
                for (doc in result) {
                    val help = doc.toObject(HelpRequest::class.java)
                    myPosts.add(help)
                }

                recyclerView.adapter = MyPostsAdapter(myPosts) { postToDelete ->
                    deletePost(postToDelete, userId)
                }
            }
    }

    private fun deletePost(post: HelpRequest, userId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(userId)
            .collection("helpRequests")
            .document(post.id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Post deleted", Toast.LENGTH_SHORT).show()
                loadMyPosts()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to delete", Toast.LENGTH_SHORT).show()
            }
    }
}
