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

import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager

import androidx.core.content.ContextCompat


class HomeMapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private var _binding: FragmentHomeMapBinding? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val binding get() = _binding!!
    lateinit var marker : MarkerOptions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeMapBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        checkAndRequestPermissions()

        binding.floatingActionButton.setOnClickListener{
            getLastKnownLocation()
        }

        val sherbrooke = LatLng(45.0, -31.0)
        marker = MarkerOptions().position(sherbrooke)

        val root: View = binding.root

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    fun getLastKnownLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location->
                if (location != null) {
                    Toast.makeText(context,"Votre position", Toast.LENGTH_SHORT).show();
                    var latLng : LatLng = LatLng(location.latitude, location.longitude)
                    marker = MarkerOptions()
                        .position(latLng )
                        .title("Ma position")
                    mMap.addMarker(marker)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 12F))
                }else{
                    Toast.makeText(context,"Marche pas", Toast.LENGTH_SHORT).show();
                }

            }

    }

    fun checkAndRequestPermissions(): Boolean {
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
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                (context as Activity?)!!,
                listPermissionsNeeded.toTypedArray(),
                1
            )
            return false
        }
        return true
    }






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