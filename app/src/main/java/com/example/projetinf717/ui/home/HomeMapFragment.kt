package com.example.projetinf717.ui.home

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projetinf717.Application
import com.example.projetinf717.R
import com.example.projetinf717.databinding.FragmentHomeMapBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*


class HomeMapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private var _binding: FragmentHomeMapBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //val mapFragment : SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        /*val mapFragment : SupportMapFragment = activity?.supportFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)*/
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)




        _binding = FragmentHomeMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }






    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        /*val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

        var geocoder: Geocoder = Geocoder(context, Locale.getDefault())
        try {
            var addresses: List<Address> =
                geocoder.getFromLocationName("209 passage Garibaldi, Aix les Bains, france", 1)
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
        }
    }



}