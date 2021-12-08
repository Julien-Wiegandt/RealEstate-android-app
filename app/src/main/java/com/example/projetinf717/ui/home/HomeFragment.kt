package com.example.projetinf717.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.projetinf717.Application
import com.example.projetinf717.R
import com.example.projetinf717.databinding.FragmentHomeBinding
import org.json.JSONArray
import org.json.JSONObject

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private var housesArray = JSONArray()
    private val viewAdapter = MyAdapter(housesArray)


    private lateinit var swipeContainer: SwipeRefreshLayout


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.listHouses.run {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

        homeViewModel.getAction()?.observe(viewLifecycleOwner,
            Observer<Action> { action -> action?.let { handleAction(it) } })

        swipeContainer = binding.swipeContainerAllHouses

        swipeContainer.setOnRefreshListener {
            homeViewModel.displayHomes()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleAction(action: Action) {
//        when (action.value) {
//            Action.HOMES_LOADED -> {
        // TEMP DATA
        val tempHouseArray = JSONArray()
        val house = JSONObject()
        house.put("title", "Offer over ")
        house.put("price", 240000)
        house.put("city", "Sherbrooke")
        house.put("street", "2500 Boulevard de l'Université")
        tempHouseArray.put(house)
        viewAdapter.swapDataSet(tempHouseArray)
        Toast.makeText(context,tempHouseArray.toString(),Toast.LENGTH_SHORT).show();
//                viewAdapter.swapDataSet(homeViewModel.homesArray)
                swipeContainer.isRefreshing = true
//            }
//            Action.NETWORK_ERROR ->{
//                if(Application.isActivityVisible()){
//                    Toast.makeText(context,"Network error", Toast.LENGTH_SHORT).show();
//                }
//                swipeContainer.isRefreshing = false
//            }
//        }
    }



}

class MyAdapter(private var myDataset: JSONArray) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(val item: View) : RecyclerView.ViewHolder(item)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        // create a new view
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_house, parent, false)


        return ViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val house = myDataset[position] as JSONObject

        val title = house.getString("title")
        val price = house.getInt("price")
        val city = house.getString("city")
        val street = house.getString("street")

        holder.item.findViewById<TextView>(R.id.title).text = title

        holder.item.setOnClickListener {
            val bundle = bundleOf("id" to position)
            /*holder.item.findNavController().navigate(
                R.id.action_title_to_house, bundle)*/
        }
    }

    fun swapDataSet(newData: JSONArray) {
        myDataset = newData

        notifyDataSetChanged()
    }

    // Return the size of the dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.length()

}