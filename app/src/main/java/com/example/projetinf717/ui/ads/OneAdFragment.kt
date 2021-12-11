package com.example.projetinf717.ui.ads

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projetinf717.Application
import com.example.projetinf717.R
import com.example.projetinf717.databinding.FragmentMainHomeBinding
import com.example.projetinf717.databinding.FragmentOneHomeBinding
import org.json.JSONArray
import org.json.JSONObject
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.example.projetinf717.databinding.FragmentOneAdBinding

import java.io.InputStream
import java.lang.Exception
import java.net.URL


class OneAdFragment : Fragment() {
    private lateinit var oneAdViewModel: OneAdViewModel
    private var _binding: FragmentOneAdBinding? = null

    private var ad = JSONObject()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        oneAdViewModel =
            ViewModelProvider(this).get(OneAdViewModel::class.java)

        _binding = FragmentOneAdBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val id : Int = arguments?.getInt("id")!!

        oneAdViewModel.getAction()?.observe(viewLifecycleOwner,
            Observer<OneAdAction> { action -> action?.let { handleAction(it) } })

        oneAdViewModel.displayHome(id)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleAction(action: OneAdAction) {
        when (action.value) {
            OneAdAction.HOME_LOADED -> {
                ad = oneAdViewModel.ad
                binding.adDetailsTitle.setText(ad.getString("title"))
                binding.adDetailsAddress.setText(ad.getString("street") + ", " + ad.getString("city") + ", " + ad.getString("country"))
                binding.adDetailsPrice.setText("$"+ad.getInt("estateprice"))
                binding.adDetailsEstateType.setText(ad.getString("estatetype") + " for " + if (ad.getBoolean("rent")) "rent" else "sell")
                binding.adDetailsBedNumber.setText(ad.getInt("numberbed").toString())
                binding.adDetailsBathNumber.setText(ad.getInt("numberbath").toString())
//                binding.adDetailsCarNumber.setText(home.getInt("numbercar").toString())
                binding.adDetailsEmail.setText(ad.getString("email"))
                binding.adDetailsPhone.setText(ad.getString("phone"))
                binding.adDetailsDescription.setText(ad.getString("description"))

                val img = binding.adDetailsImage
                Toast.makeText(context, "Please wait for the image, it may take a few seconds...",     Toast.LENGTH_SHORT).show()
                DownloadImageFromInternet(img).execute(ad.getString("imgpath"))
            }
            OneAdAction.NETWORK_ERROR -> {
                if (Application.isActivityVisible()) {
                    Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @SuppressLint("StaticFieldLeak")
    @Suppress("DEPRECATION")
    class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            }
            catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
            return image
        }
        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }
}

