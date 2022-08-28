package com.sunitawebapp.locationonmap.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.sunitawebapp.locationonmap.R
import com.sunitawebapp.locationonmap.adapter.TopicAdapter
import com.sunitawebapp.locationonmap.databinding.FragmentHomeBinding


class HomeFragment : Fragment()  {
    lateinit var binding : FragmentHomeBinding
  lateinit  var topicAdapter : TopicAdapter
    var topiclist : ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getTopicList()

         topicAdapter = TopicAdapter(topiclist)

        binding.rvTopicList.adapter=topicAdapter

        topicAdapter.setOnItemClickListener(object :TopicAdapter.OnClickRecyclerView{
            override fun setOnClickRecyclerView(position: Int) {
                if (position==0){
                    findNavController().navigate(R.id.currentLocationFragment)
                }else if (position==1){
                    findNavController().navigate(R.id.drawPolylineFragment)
                }else if (position==2){
                    findNavController().navigate(R.id.carMoveFragment)
                }


            }

        })

        super.onViewCreated(view, savedInstanceState)
    }

    fun getTopicList(){
        topiclist.add("Current Location")
        topiclist.add("Draw Polyline")
        topiclist.add("Polyline draw with move Car")
    }




}
