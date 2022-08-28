package com.sunitawebapp.locationonmap.ui.fragment


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.sunitawebapp.locationonmap.R
import com.sunitawebapp.locationonmap.databinding.FragmentCurrentLocationBinding


class CurrentLocationFragment : Fragment() ,View.OnClickListener, OnMapReadyCallback {

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    var mMap: GoogleMap? = null
    lateinit var binding : FragmentCurrentLocationBinding
     var  lat : Double =0.0
    var lng : Double = 0.0

    var lastKnownLocation: Location? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentCurrentLocationBinding.inflate(inflater,container,false)
         binding.apply {
             btnLoc.setOnClickListener(this@CurrentLocationFragment)
             return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {




        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(view: View?) {
     when(view){
       binding.btnLoc -> {
           getCurrentloaction()
       }
     }
    }

    fun getCurrentloaction(){
        var smf =requireActivity().getSupportFragmentManager().findFragmentById(R.id.map) as SupportMapFragment
      //  smf.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1);
            }else{
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1);
            }
        }

        mFusedLocationClient!!.getLastLocation().addOnSuccessListener(requireActivity()) { location ->
            if (location != null) {
                lat = location.getLatitude()
                 lng = location.getLongitude()
            //    mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 15f))
                Toast.makeText(requireContext(),"$lat , $lng" ,Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(map: GoogleMap?) {

        mMap = map;


    }

}
