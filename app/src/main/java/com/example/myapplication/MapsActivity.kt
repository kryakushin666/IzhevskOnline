package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
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
                        .icon(getBitmapDescriptor(R.drawable.ic_bar))
        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.844568122459364, 53.191131214863674))
                        .title("Памятник Дерябину")
                        .icon(getBitmapDescriptor(R.drawable.ic_bar))
        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.848892795955905, 53.19585937814813))
                        .title("Ижевский индустриальный техникум")
                        .icon(getBitmapDescriptor(R.drawable.ic_bar))
        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.84408400157632, 53.19771856787305))
                        .title("Памятник ижевским оружейникам")
                        .icon(getBitmapDescriptor(R.drawable.ic_bar))
        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.84398424917061, 53.198120889542295))
                        .title("Музей ИЖМАШ")
                        .icon(getBitmapDescriptor(R.drawable.ic_bar))
        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.83996208388173, 53.19589266822729))
                        .title("Долгий мост и завод «Ижсталь")
                        .icon(getBitmapDescriptor(R.drawable.ic_bar))
        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.85177628926549, 53.2002482478798))
                        .title("Здание из красного кирпич")
                        .icon(getBitmapDescriptor(R.drawable.ic_bar))
        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.85073186241447, 53.20672264326064))
                        .title("Музейно-выставочный комплекс стрелкового оружия имени Михаила Тимофеевича Калашникова")
                        .icon(getBitmapDescriptor(R.drawable.ic_bar))
        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.85285289473385, 53.215664171778975))
                        .title("Арсенал")
                        .icon(getBitmapDescriptor(R.drawable.ic_bar))
        )
        enableMyLocation()
        setPoiClick(map)
        map.setMinZoomPreference(11.5f)
    }

    private fun getBitmapDescriptor(id: Int): BitmapDescriptor? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val vectorDrawable = getDrawable(id) as VectorDrawable?
            val h = vectorDrawable!!.intrinsicHeight
            val w = vectorDrawable.intrinsicWidth
            vectorDrawable.setBounds(0, 0, w, h)
            val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bm)
            vectorDrawable.draw(canvas)
            BitmapDescriptorFactory.fromBitmap(bm)
        } else {
            BitmapDescriptorFactory.fromResource(id)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // Change the map type based on the user's selection.
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
    private val REQUEST_LOCATION_PERMISSION = 1
    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //   int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            map.isMyLocationEnabled = true
        }
        else {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION
            )
        }
    }
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }
    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                    MarkerOptions()
                            .position(poi.latLng)
                            .title(poi.name)
                            //.alpha(0.0f)
                            .icon(getBitmapDescriptor(R.drawable.ic_icon))
            )
            poiMarker.showInfoWindow()
        }
    }
}
