package com.sunitawebapp.locationonmap.ui.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.GoogleDirection
import com.akexorcist.googledirection.constant.TransportMode
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.model.Route
import com.akexorcist.googledirection.util.DirectionConverter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.sunitawebapp.locationonmap.R
import com.sunitawebapp.locationonmap.moveCarmarkerAnimation.AnimationClass.HRMarkerAnimation
import com.sunitawebapp.locationonmap.moveCarmarkerAnimation.CallBacks.UpdateLocationCallBack


class DrawPolylineFragment : Fragment(),OnMapReadyCallback, DirectionCallback {
    private var mMap: GoogleMap? = null
   lateinit var pickup: LatLng
    lateinit var drop: LatLng
    private var mPolyline: Polyline? = null
    var markerPoints: ArrayList<LatLng> = ArrayList()
    private var polyLinePoints: ArrayList<LatLng>? = null
    private var marker: Marker? = null
    var currentPt = 0
 //  lateinit var mTicker :Runnable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_draw_polyline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        pickup = LatLng(24.9994173, 88.1388895)
       drop = LatLng(22.7225126, 88.4649025)

       var  smf= childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        smf.getMapAsync(this);
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap=p0
        markerPoints.add(pickup)
        markerPoints.add(drop)

        drawRoute(
           pickup,
           drop
        )
    }

    override fun onDirectionSuccess(direction: Direction?) {
        if (direction!!.isOK) {
            val route = direction.routeList[0]
            if (!route.legList.isEmpty()) {
                val endLeg = route.legList[0]
                val destination = LatLng(endLeg.endLocation.latitude, endLeg.endLocation.longitude)
                val bitmap = BitmapDescriptorFactory.fromBitmap(getDestinationNarker())
                mMap!!.addMarker(MarkerOptions().position(destination).icon(bitmap)).tag = endLeg

                mMap!!.addMarker(
                    MarkerOptions()
                        .position(pickup)
                        .title("Current Location")
                )
                polyLinePoints = route.legList[0].directionPoint
                mPolyline = mMap!!.addPolyline(
                    DirectionConverter.createPolyline(
                        requireContext(),
                        polyLinePoints,
                        5,
                        resources.getColor(R.color.purple_700)
                    )
                )
                setCameraWithCoordinationBounds(route)
                marker = mMap!!.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            polyLinePoints!!.get(currentPt).latitude,
                            polyLinePoints!!.get(currentPt).longitude
                        )
                    )
                        .icon(BitmapDescriptorFactory.fromBitmap(getCarBitmap()))
                )
                mMap!!.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            polyLinePoints!!.get(
                                currentPt
                            ).latitude, polyLinePoints!!.get(currentPt).longitude
                        ), 12f
                    )
                )
             /*   val handler = Handler()
                handler.postDelayed({ }, 3000)*/

                   //   mTicker =  Runnable() {}

                  val handler = Handler()
                  handler.postDelayed(object :Runnable {


                      override fun run() {
                          if (currentPt < polyLinePoints!!.size) {
                              //post again
                              Log.d("tess", "inside run ")
                              val targetLocation = Location(LocationManager.GPS_PROVIDER)
                              targetLocation.latitude = polyLinePoints!![currentPt].latitude
                              targetLocation.longitude = polyLinePoints!![currentPt].longitude
                              // animateMarkerNew(targetLocation, mMarker);
                              HRMarkerAnimation(mMap, 1000, object : UpdateLocationCallBack {
                                  override  fun onUpdatedLocation(updatedLocation: Location?) {}
                              }).animateMarker(targetLocation, targetLocation, marker)
                              handler.postDelayed(this, 3000)
                              currentPt++
                          } else {
                              Log.d("tess", "call back removed")
                              //removed callbacks
                              handler.removeCallbacks(this)
                          }
                      }
                  }, 3000)
              }
           //   mTicker!!.run();



        } else
            Toast.makeText(requireActivity(), direction.status, Toast.LENGTH_SHORT).show()
    }

    override fun onDirectionFailure(t: Throwable) {
        TODO("Not yet implemented")
    }

    fun drawRoute(source: LatLng, destination: LatLng) {
        GoogleDirection.withServerKey(getString(R.string.MAP_KEY))
            .from(source!!)
            .to(destination)
            .transportMode(TransportMode.DRIVING)
            .execute(this)
    }
    fun getCarBitmap(): Bitmap? {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_car)
        return Bitmap.createScaledBitmap(bitmap, 50, 100, false)
    }

    fun getDestinationNarker(): Bitmap? {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.des_icon)
        return Bitmap.createScaledBitmap(bitmap, 100, 100, false)
    }

    private fun setCameraWithCoordinationBounds(route: Route) {
        val southwest = route.bound.southwestCoordination.coordination
        val northeast = route.bound.northeastCoordination.coordination
        val bounds = LatLngBounds(southwest, northeast)
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
    }

}
