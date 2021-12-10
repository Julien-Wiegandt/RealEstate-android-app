package com.example.projetinf717.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.projetinf717.R
import com.example.projetinf717.databinding.FragmentHomeMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*
import android.app.Activity
import android.content.Context
import androidx.lifecycle.Observer


import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas

import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptorFactory

import android.graphics.drawable.Drawable

import com.google.android.gms.maps.model.BitmapDescriptor



import androidx.lifecycle.ViewModelProvider
import org.json.JSONObject
import android.content.Intent
import android.net.Uri

import com.google.android.gms.maps.model.Marker





class HomeMapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var homeMapViewModel: HomeMapViewModel

    private var _binding: FragmentHomeMapBinding? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val binding get() = _binding!!
    lateinit var marker : MarkerOptions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeMapViewModel = ViewModelProvider(this).get(HomeMapViewModel::class.java)
        // Inflate the layout for this fragment
        _binding = FragmentHomeMapBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        homeMapViewModel.getAction().observe(viewLifecycleOwner, Observer<Action> { action -> action?.let { handleAction(it) } })
        checkAndRequestPermissions()

        binding.floatingActionButton.setOnClickListener{
            getLastKnownLocation()

        }

        binding.button.setOnClickListener {
            loadHousesAroundCity(binding.editTextTextPersonName.text.toString())
        }

        val root: View = binding.root

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun loadHousesAroundCity(name: String){
        homeMapViewModel.displayHomesByCity(name)
        val geocoder: Geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses: List<Address> =
                geocoder.getFromLocationName(name, 1)
            if (addresses.isNotEmpty()) {
                val latLong: LatLng = LatLng(addresses[0].latitude, addresses[0].longitude)
                val markerOptions: MarkerOptions = MarkerOptions()
                markerOptions.title(name)
                markerOptions.position(latLong)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerOptions.position, 12F))

            }
        }catch (e: IOException) {
            e.printStackTrace()
        }
    }
    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        mMap.clear()
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location->
                if (location != null) {
                    homeMapViewModel.displayHomesByArea(location.latitude, location.longitude)
                    var latLng : LatLng = LatLng(location.latitude, location.longitude)
                    marker = MarkerOptions()
                        .position(latLng )
                        .title("Ma position")
                        .icon(context?.let {
                            BitmapFromVector(
                                it,
                                R.drawable.ic_baseline_my_location_24
                            )
                        })
                    mMap.addMarker(marker)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 12F))
                }

            }

    }

    private fun handleAction(action: Action) {
        if(this::mMap.isInitialized){
            when (action.value) {
                Action.HOMES_LOADED -> {
                    val homeArray = homeMapViewModel.homesArray
                    for(i in 0 until homeArray.length()){
                        val obj: JSONObject = homeArray[i] as JSONObject
                        val latLong = JSONObject(obj.get("latlong").toString())
                        val position =  LatLng(latLong.getDouble("latitude"), latLong.getDouble("longitude"))
                        marker = MarkerOptions()
                            .position(position)
                            .title(obj.get("title").toString())
                        mMap.addMarker(marker)

                    }
                }
                Action.NETWORK_ERROR -> {
                    Toast.makeText(context,"An error occurred while requesting for houses", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
    private fun BitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        // below line is use to generate a drawable.
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

        // below line is use to set bounds to our vector drawable.
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        // below line is use to create a bitmap for our
        // drawable which we have added.
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        // below line is use to add bitmap in our canvas.
        val canvas = Canvas(bitmap)

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas)

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun checkAndRequestPermissions(): Boolean {
        val internet = context?.let {
            ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.INTERNET
            )
        }
        val loc = context?.let {
            ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
        val loc2 = context?.let {
            ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (internet != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET)
        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                (context as Activity?)!!,
                listPermissionsNeeded.toTypedArray(),
                1
            )
            return false
        }
        return true
    }


    /*fun onMarkerClick(marker: Marker): Boolean {
        if (marker == myMarker) {
            val uriUrl: Uri = Uri.parse(hashmap.get(myMarker))
            val launchBrowser = Intent(Intent.ACTION_VIEW, id)
            startActivity(launchBrowser)
        }
    }*/


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        getLastKnownLocation()
        /*val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

        /*var geocoder: Geocoder = Geocoder(context, Locale.getDefault())
        try {
            var addresses: List<Address> =
                geocoder.getFromLocationName("209 passage Garibaldi, Aix les Bains, france", 1)
            var addresses2: List<Address> =
                geocoder.getFromLocationName("50 route de st innocent, Aix les Bains, france", 1)
            if(addresses2.isNotEmpty()){
                var latLong: LatLng = LatLng(addresses2[0].latitude, addresses2[0].longitude)
                var markerOptions: MarkerOptions = MarkerOptions()
                markerOptions.title("Chateau Misser")
                markerOptions.position(latLong)
                mMap?.addMarker(markerOptions)
            }

            var addresses3: List<Address> =
                geocoder.getFromLocationName("12 rue vaissiere, Montpellier, france", 1)
            if(addresses3.isNotEmpty()){
                var latLong: LatLng = LatLng(addresses3[0].latitude, addresses3[0].longitude)
                var markerOptions: MarkerOptions = MarkerOptions()
                markerOptions.title("Chateau Misser")
                markerOptions.position(latLong)
                mMap?.addMarker(markerOptions)
            }

            var addresses4: List<Address> =
                geocoder.getFromLocationName("955 rue kitchener, Canada, quebec, sherbrooke", 1)
            if(addresses4.isNotEmpty()){
                var latLong: LatLng = LatLng(addresses4[0].latitude, addresses4[0].longitude)
                var markerOptions: MarkerOptions = MarkerOptions()
                markerOptions.title("Chateau Misser")
                markerOptions.position(latLong)
                mMap?.addMarker(markerOptions)
            }

            var addresses5: List<Address> =
                geocoder.getFromLocationName("tour eiffel, paris", 1)
            if(addresses5.isNotEmpty()){
                var latLong: LatLng = LatLng(addresses5[0].latitude, addresses5[0].longitude)
                var markerOptions: MarkerOptions = MarkerOptions()
                markerOptions.title("Chateau Misser")
                markerOptions.position(latLong)
                mMap?.addMarker(markerOptions)
            }

            if (addresses.isNotEmpty()) {
                var latLong: LatLng = LatLng(addresses[0].latitude, addresses[0].longitude)
                var markerOptions: MarkerOptions = MarkerOptions()
                markerOptions.title("Chateau Misser")
                markerOptions.position(latLong)
                mMap?.addMarker(markerOptions)
                var cameraUpdate: CameraUpdate = CameraUpdateFactory.newLatLngZoom(
                    latLong,
                    5F
                )
                mMap?.animateCamera(cameraUpdate)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }*/
    }



}