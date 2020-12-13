package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsFragment : Fragment() {

    private lateinit var map: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->

        map = googleMap

        //These coordinates represent the latitude and longitude of the Googleplex.
        val latitude = 56.85970797942636
        val longitude = 53.196807013800594
        val zoomLevel = 11.5f

        val homeLatLng = LatLng(latitude, longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.84383886160861, 53.191198130527944))
                        .title("Главный корпус оружейного завода")
        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.844568122459364, 53.191131214863674))
                        .title("Памятник Дерябину")

        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.848892795955905, 53.19585937814813))
                        .title("Ижевский индустриальный техникум")

        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.84408400157632, 53.19771856787305))
                        .title("Памятник ижевским оружейникам")

        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.84398424917061, 53.198120889542295))
                        .title("Музей ИЖМАШ")

        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.83996208388173, 53.19589266822729))
                        .title("Долгий мост и завод «Ижсталь")

        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.85177628926549, 53.2002482478798))
                        .title("Здание из красного кирпич")

        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.85073186241447, 53.20672264326064))
                        .title("Музейно-выставочный комплекс стрелкового оружия имени Михаила Тимофеевича Калашникова")

        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.85285289473385, 53.215664171778975))
                        .title("Арсенал")

        )
        setPoiClick(map)

        map.setMinZoomPreference(11.5f)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                    MarkerOptions()
                            .position(poi.latLng)
                            .title(poi.name)
                            //.alpha(0.0f)
            )
            poiMarker.showInfoWindow()
        }
    }
}