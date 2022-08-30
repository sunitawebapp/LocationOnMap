package com.sunitawebapp.locationonmap.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunitawebapp.locationonmap.R


class CarMoveFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_move, container, false)
    }


    /*      mTicker =  Runnable() {
              fun run() {
                  val handler = Handler()
                  handler.postDelayed({
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
                          handler.postDelayed(mTicker!!, 3000)
                          currentPt++
                      } else {
                          Log.d("tess", "call back removed")
                          //removed callbacks
                          handler.removeCallbacks(mTicker!!)
                      }
                  }, 3000)
              }
              mTicker!!.run();

          }*/
}
