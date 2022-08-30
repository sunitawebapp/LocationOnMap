package com.sunitawebapp.locationonmap.ui.fragment


import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
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
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sunitawebapp.locationonmap.R
import com.sunitawebapp.locationonmap.databinding.FragmentCurrentLocationBinding
import java.util.*


class CurrentLocationFragment : Fragment() ,View.OnClickListener, OnMapReadyCallback {

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    lateinit var mMap: GoogleMap
    lateinit var binding : FragmentCurrentLocationBinding
     var  lat : Double =22.6131968
    var lng : Double = 88.4310016

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
     /*   var smf =requireActivity().getSupportFragmentManager().findFragmentById(R.id.map) as SupportMapFragment
        smf.getMapAsync(this);*/

        var smf =  getChildFragmentManager().findFragmentById(R.id.map) as SupportMapFragment
        smf.getMapAsync(this);


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

     /*   mFusedLocationClient!!.getLastLocation().addOnSuccessListener(requireActivity()) { location ->
            if (location != null) {
                lat = location.getLatitude()
                lng = location.getLongitude()
                //    mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 15f))
                Toast.makeText(requireContext(),"$lat , $lng" ,Toast.LENGTH_SHORT).show()
                Log.d("sunita", "onViewCreated: "+"$lat , $lng")
            }
        }*/


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
      /*  val mapFragment =requireActivity().supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)*/

      /*  val mapFragment =requireActivity(). supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)*/
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(view: View?) {
     when(view){
       binding.btnLoc -> {
           // Add a marker in Location and move the camera
           /*val loc = LatLng(22.6230272, 88.4441088)
           mMap.addMarker(
               MarkerOptions()
                   .position(loc)
                   .title("Marker in Location")
           )

           mMap.moveCamera(CameraUpdateFactory.newLatLng(loc))*/
           getCurrentloaction()
       }
     }
    }

    fun getCurrentloaction(){

      /*  mMap!!.addMarker(
            MarkerOptions()
                .position(LatLng(22.6131968, 88.4310016))
                .title("Check Out Location")
        )
        val cameraPosition = CameraPosition.Builder().target(LatLng(22.6131968, 88.4310016))
            .zoom(17f)
            .bearing(0f)
            .tilt(45f)
            .build()
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(LatLng(22.6131968, 88.4310016)))
        mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(12f), 2000, null);*/

    }

  /*  override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }*/

    override fun onMapReady(map: GoogleMap) {

        mMap = map;

     /*   // Add a marker in Location and move the camera
        val loc = LatLng(22.6230272, 88.4441088)
        mMap.addMarker(
            MarkerOptions()
                .position(loc)
                .title("Marker in Location")
        )

        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc))
      //  mMap.animateCamera(CameraUpdateFactory.zoomTo(15f), 2000, null);*/


          mMap!!.addMarker(
          MarkerOptions()
              .position(LatLng(22.6131968, 88.4310016))
              .title("Check Out Location")
      )
      val cameraPosition = CameraPosition.Builder().target(LatLng(22.6131968, 88.4310016))
          .zoom(17f)
          .bearing(0f)
          .tilt(45f)
          .build()
      mMap!!.moveCamera(CameraUpdateFactory.newLatLng(LatLng(22.6131968, 88.4310016)))
      mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
      mMap!!.animateCamera(CameraUpdateFactory.zoomTo(12f), 2000, null);

    }
    private fun stringToLatLong(latLongStr: String): LatLng{


        val geocoder = Geocoder(requireContext(), Locale.getDefault())


        val latlong = latLongStr.split(",").toTypedArray()
        val latitude = latlong[0].toDouble()
        val longitude = latlong[1].toDouble()

        val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
        val strReturnedAddress = StringBuilder("")
        var  returnedAdd = addresses[0]

        for (i in 0..returnedAdd.getMaxAddressLineIndex()) {
            strReturnedAddress.append(returnedAdd.getAddressLine(i)).append("\n")
        }
      var  returnedAddress = strReturnedAddress.toString()
        return LatLng(latitude, longitude)
    }
}
