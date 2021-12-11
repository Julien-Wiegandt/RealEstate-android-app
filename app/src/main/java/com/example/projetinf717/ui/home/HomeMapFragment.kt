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
import android.util.Log
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.CameraUpdate

import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener








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

        /*binding.floatingActionButton.setOnClickListener{
            getLastKnownLocation()
        }*/

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
                //mMap.clear()
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
                    val latLng : LatLng = LatLng(location.latitude, location.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12F))
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
                        mMap.addMarker(marker).tag = obj.get("id");

                    }
                }
                Action.NETWORK_ERROR -> {
                    Toast.makeText(context,"No houses found for this location", Toast.LENGTH_SHORT).show();
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

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true;
        getLastKnownLocation()
        mMap.setOnMyLocationButtonClickListener {
            mMap.clear()
            val ll = LatLng(mMap.myLocation.latitude, mMap.myLocation.longitude)
            val update = CameraUpdateFactory.newLatLngZoom(ll, 12F)
            mMap.animateCamera(update)
            homeMapViewModel.displayHomesByArea(mMap.myLocation.latitude, mMap.myLocation.longitude)
            false
        }
        mMap.setOnMarkerClickListener { marker ->
            val bundle = bundleOf("id" to marker.tag)
            findNavController().navigate(
                R.id.action_navigation_home_to_oneHomeFragment, bundle)
            true
        }
    }



}