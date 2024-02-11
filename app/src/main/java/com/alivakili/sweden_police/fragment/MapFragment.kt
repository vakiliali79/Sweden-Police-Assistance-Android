package com.alivakili.sweden_police.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alivakili.sweden_police.R
import com.alivakili.sweden_police.databinding.FragmentMapBinding
import com.alivakili.sweden_police.model.StationsModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private var data:StationsModel.StationsModelItem? = null
    private lateinit var mapFragment: SupportMapFragment
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //val view = inflater.inflate(R.layout.fragment_map, container, false)
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        data=properData()
        properLayout()
        return binding.root

    }

    private fun properData(): StationsModel.StationsModelItem? {
        return arguments?.getParcelable<StationsModel.StationsModelItem>("KEY_MAP")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment.getMapAsync { map ->
            googleMap = map
            addMarker()
        // Call methods to add markers and zoom the map here
        }
        properLayout()
    }

    private fun properLayout() {
        binding.cityName.text=data?.name
        binding.services.text=data?.services?.mapNotNull { it?.name }?.joinToString(", ")
        binding.address.text=data?.location?.name
        binding.cardView.setOnClickListener(View.OnClickListener {
            openBrowser(data?.url)
        })
    }

    private fun openBrowser(url: String?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }



    private fun addMarker(){
        val markerLocation:LatLng = getLocation(data?.location?.gps)!!
        googleMap?.addMarker(MarkerOptions().position(markerLocation).title(""))
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLocation, 16f))
    }


    override fun onMapReady(map: GoogleMap) {
        googleMap = map

    }



    private fun getLocation(string:String?): LatLng? {
        return convertStringToLatLng(string)


    }

    fun convertStringToLatLng(string: String?): LatLng? {
        val coordinates = string?.split(",")
        if (coordinates?.size == 2) {
            val latitude = coordinates[0].toDoubleOrNull()
            val longitude = coordinates[1].toDoubleOrNull()
            if (latitude != null && longitude != null) {
                return LatLng(latitude, longitude)
            }
        }
        return null
    }

    companion object {
        fun newInstance(data: StationsModel.StationsModelItem?): MapFragment {
            val fragment = MapFragment()
            val args = Bundle()
            args.putParcelable("KEY_MAP", data)
            fragment.arguments = args
            return fragment
        }
    }

}