package com.example.projetinf717.ui.home

import android.os.Bundle
import android.util.Log
import android.util.Log.DEBUG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.VolleyLog.DEBUG
import com.example.projetinf717.Application
import com.example.projetinf717.BuildConfig.DEBUG
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
        when (action.value) {
            Action.HOMES_LOADED -> {
                viewAdapter.swapDataSet(homeViewModel.homesArray)
                swipeContainer.isRefreshing = false
            }
            Action.NETWORK_ERROR ->{
                if(Application.isActivityVisible()){
                    Toast.makeText(context,"Network error", Toast.LENGTH_SHORT).show();
                }
                // Temp
                var jsonObject = JSONObject()
                var jsonArray = JSONArray()
                // Temp house 1
                jsonObject.put("id", 0)
                jsonObject.put("title", "Apartment to sell")
                jsonObject.put("street", "2500 Boulevard de l'Université")
                jsonObject.put("city", "Sherbrooke")
                jsonObject.put("postalCode", "J1K2R1")
                jsonObject.put("country", "Canada")
                jsonObject.put("estatePrice", 210000)
                jsonObject.put("estateType", "Apartment")
                jsonObject.put("rent", false)
                jsonObject.put("numberBath", 1)
                jsonObject.put("numberBed", 2)
                jsonObject.put("numberCar", 1)
                jsonObject.put("email", "jerome@gmail.com")
                jsonObject.put("phone", "+1 438 276 1283")
                jsonObject.put("description", "Appartement lumineux en plein centre de Sherbrooke. Emplacement proche des transports en communs. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.")
                jsonArray.put(jsonObject)

                // Temp house 2
                jsonObject = JSONObject()
                jsonObject.put("id", 1)
                jsonObject.put("title", "Beautiful house to rent")
                jsonObject.put("street", "238 Rue de la Lauze")
                jsonObject.put("city", "Valflaunès")
                jsonObject.put("postalCode", "34270")
                jsonObject.put("country", "France")
                jsonObject.put("estatePrice", 1600)
                jsonObject.put("estateType", "House")
                jsonObject.put("rent", true)
                jsonObject.put("numberBath", 3)
                jsonObject.put("numberBed", 4)
                jsonObject.put("numberCar", 2)
                jsonObject.put("email", "paul@gmail.com")
                jsonObject.put("phone", "+33 6 73 82 02 82")
                jsonObject.put("description", "Magnifique maison à la campagne! Emplacement proche des transports en communs. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.")
                jsonArray.put(jsonObject)

                housesArray = jsonArray
                viewAdapter.swapDataSet(housesArray)

                swipeContainer.isRefreshing = false
            }
        }
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

        // Extract data from JSONObject

        val price = house.getInt("estatePrice")
        val street = house.getString("street")
        val city = house.getString("city")
        val country = house.getString("country")
        val estateType = house.getString("estateType")
        val bedNumber = house.getInt("numberBed")
        val bathNumber = house.getInt("numberBath")
        val carNumber = house.getInt("numberCar")

        holder.item.findViewById<TextView>(R.id.adItemPrice).text = "$"+price.toString()
        holder.item.findViewById<TextView>(R.id.adItemAddress).text = street + ", " + city + ", " + country
        holder.item.findViewById<TextView>(R.id.adItemBedNumber).text = bedNumber.toString()
        holder.item.findViewById<TextView>(R.id.adItemBathNumber).text = bathNumber.toString()
        holder.item.findViewById<TextView>(R.id.adItemCarNumber).text = carNumber.toString()
        holder.item.findViewById<TextView>(R.id.adItemEstateType).text = estateType

        holder.item.setOnClickListener {
            val bundle = bundleOf("id" to position)
            holder.item.findNavController().navigate(
                R.id.action_title_to_home, bundle)
        }
    }

    fun swapDataSet(newData: JSONArray) {
        myDataset = newData

        notifyDataSetChanged()
    }

    // Return the size of the dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.length()

}