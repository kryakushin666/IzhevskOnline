@file:Suppress("DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.myapplication.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.VectorDrawable
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.`interface`.respObjDatabase
import com.example.myapplication.activities.bottomNavigationView
import com.example.myapplication.database.DatabaseHelper
import com.example.myapplication.directions.DirectionsHelper
import com.example.myapplication.fragments.GunFragment.Companion.EXCURSION_ID
import com.example.myapplication.fragments.UserMuseumFragment.Companion.USERNAME_COORDINATES
import com.example.myapplication.notification.NotificationHelper
import com.example.myapplication.place.PlaceHelper
import com.example.myapplication.utilits.*
import com.example.myapplication.weather.WeatherHelper
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.internal.wait


var maintext = "This my work"
var mainid = "This my work"
var dirtext = "This my work"
var disttext = "This my work"
var coordtext = "This my work"

//lateinit var mBottomSheetRoute: BottomSheetBehavior<*>
//lateinit var mBottomSheetTimeRoute: ConstraintLayout

//lateinit var textdistance: TextView

/*lateinit var minut1: TextView
lateinit var minut2: TextView
lateinit var minut3: TextView
lateinit var km1: TextView
lateinit var km2: TextView
lateinit var km3: TextView*/

var polylineFinal: Polyline? = null

lateinit var map: GoogleMap

var locationRequest: LocationRequest? = null
var userLocationMarker: Marker? = null
var userLocationAccuracyCircle: Circle? = null

@Suppress("CAST_NEVER_SUCCEEDS")
class MapsFragment : Fragment(), GoogleMap.OnMarkerClickListener {

    // Разрешения для использования и получения геолокации
    //private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val requestLocationPermission = 1
    private lateinit var fab: ImageView
    private lateinit var fabinfo: FloatingActionButton
    private lateinit var fabcamera: FloatingActionButton
    private lateinit var fabcheckloc: FloatingActionButton
    private lateinit var fabexc: FloatingActionButton
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<*>

    private val callback = OnMapReadyCallback { googleMap ->

        map = googleMap

        val contextCompats = requireContext().applicationContext

        //Наводят камеру на Ижевск и устанавливают уровень приближения
        val homeLatLng = LatLng(latitudeStartMap, longitudeStartMap)
        // Перемещает камеру к этим координа  там
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        // Создание меток на карте
        map.setOnMarkerClickListener(this)
        map.uiSettings.isMapToolbarEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false
        //map.setOnMyLocationChangeListener(this)
        // Включение геолокации и запрос разрешения
        enableMyLocation()
        //map.isTrafficEnabled = true
        // Устанавливаем максимальное приближение
        map.setMinZoomPreference(zoomLevel)
        var guided = arguments?.getInt(EXCURSION_ID, -1)
        val coord = editData(contextCompats, "USERNAME", "USERNAME_COORDINATES", "HELLO S", "getString").toString()
        val nameofobject = editData(contextCompats, "USERNAME", "USERNAME_NAME", "0", "getString").toString()
        editData(contextCompats, "USERNAME", "USERNAME_COORDINATES", "HELLO S", "putString")
        editData(contextCompats, "USERNAME", "USERNAME_NAME", "0", "putString")

        if (coord != "HELLO S") {
            map.clear()
            map.addMarker(
                MarkerOptions()
                    .position(convertertoLatLng(coord))
                    .title(nameofobject)
                    .icon(getBitmapDescriptor(R.drawable.icon_on_map))
            )
            /*DirectionsHelper(requireFragmentManager()).getTwoDirection(
                getLastKnownLocation(
                    contextCompats
                ), coord
            )*/
        }
        if(guided != -1 && guided != null) {
            val allCounterLatLng: ArrayList<String> = ArrayList()
            val allCounterName: ArrayList<String> = ArrayList()
            var size: Int? = null
            allCounterLatLng.clear()
            allCounterName.clear()
            map.clear()
            DatabaseHelper(requireFragmentManager()) {
                Log.d("dada", respObjDatabase.response.size.toString())
                size = respObjDatabase.response.size
                for(i in 0 until size!!) {
                    allCounterLatLng.add(respObjDatabase.response[i].latlng)
                    allCounterName.add(respObjDatabase.response[i].name)
                }
                activity?.runOnUiThread {
                    map.clear()
                    for (i in 0 until size!!) {
                        map.addMarker(
                            MarkerOptions()
                                .position(convertertoLatLng(allCounterLatLng[i % allCounterLatLng.size]))
                                .title(allCounterName[i % allCounterName.size])
                                .icon(getBitmapDescriptor(R.drawable.icon_on_map))
                        )
                    }
                }
            }.getTwoData("SELECT * FROM `excursionsObjects` WHERE `id` = '$guided'")
            guided = -1
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_maps, container, false)
        val contextCompats = requireContext().applicationContext
        fab = fragmentLayout.findViewById(R.id.fablocation)
        fabinfo = fragmentLayout.findViewById(R.id.fabinfo)
        fabcamera = fragmentLayout.findViewById(R.id.fabcamera)
        fabcheckloc = fragmentLayout.findViewById(R.id.fabcheckloc)
        fabexc = fragmentLayout.findViewById(R.id.fabexc)
        mBottomSheetBehavior =
            BottomSheetBehavior.from(fragmentLayout.findViewById(R.id.bottom_sheet))
        //mBottomSheetRoute = BottomSheetBehavior.from(fragmentLayout.findViewById(R.id.bottom_sheet_route))
        //textdistance = fragmentLayout.findViewById(R.id.time_route)
        //mBottomSheetTimeRoute = fragmentLayout.findViewById(R.id.bottom_time_route)
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        //mBottomSheetRoute.state = BottomSheetBehavior.STATE_HIDDEN
        bottomNavigationView.visibility = View.VISIBLE
        //mBottomSheetTimeRoute.visibility = View.INVISIBLE
        /*minut1 = fragmentLayout.findViewById(R.id.minut1)
        minut2 = fragmentLayout.findViewById(R.id.minut2)
        minut3 = fragmentLayout.findViewById(R.id.minut3)*/

        /*val imageRoute1 = fragmentLayout.findViewById<ImageView>(R.id.imageRoute1)
        val imageRoute2 = fragmentLayout.findViewById<ImageView>(R.id.imageRoute2)
        val imageRoute3 = fragmentLayout.findViewById<ImageView>(R.id.imageRoute3)
        val route1 = fragmentLayout.findViewById<CardView>(R.id.route1)
        val route2 = fragmentLayout.findViewById<CardView>(R.id.route2)
        val route3 = fragmentLayout.findViewById<CardView>(R.id.route3)
        km1 = fragmentLayout.findViewById(R.id.km1)
        km2 = fragmentLayout.findViewById(R.id.km2)
        km3 = fragmentLayout.findViewById(R.id.km3)
        textdistance.text = minut1.text
        textdistance.setTextColor(Color.parseColor("#5D8EEF"))

        km1.setTextColor(Color.parseColor("#5D8EEF"))
        minut1.setTextColor(Color.parseColor("#5D8EEF"))
        km2.setTextColor(Color.parseColor("#5D8EEF"))
        minut2.setTextColor(Color.parseColor("#5D8EEF"))
        km3.setTextColor(Color.parseColor("#5D8EEF"))
        minut3.setTextColor(Color.parseColor("#5D8EEF"))
        imageRoute1.visibility = View.VISIBLE
        imageRoute2.visibility = View.INVISIBLE
        imageRoute3.visibility = View.INVISIBLE
        route1.setOnClickListener {
            textdistance.text = minut1.text
            textdistance.setTextColor(Color.parseColor("#5D8EEF"))

            km1.setTextColor(Color.parseColor("#5D8EEF"))
            minut1.setTextColor(Color.parseColor("#5D8EEF"))
            km2.setTextColor(Color.parseColor("#5D8EEF"))
            minut2.setTextColor(Color.parseColor("#5D8EEF"))
            km3.setTextColor(Color.parseColor("#5D8EEF"))
            minut3.setTextColor(Color.parseColor("#5D8EEF"))
            imageRoute1.visibility = View.VISIBLE
            imageRoute2.visibility = View.INVISIBLE
            imageRoute3.visibility = View.INVISIBLE

        }
        route2.setOnClickListener {
            textdistance.text = minut2.text
            textdistance.setTextColor(Color.parseColor("#5D8EEF"))
            km1.setTextColor(Color.parseColor("#5D8EEF"))
            minut1.setTextColor(Color.parseColor("#5D8EEF"))
            km2.setTextColor(Color.parseColor("#5D8EEF"))
            minut2.setTextColor(Color.parseColor("#5D8EEF"))
            km3.setTextColor(Color.parseColor("#5D8EEF"))
            minut3.setTextColor(Color.parseColor("#5D8EEF"))
            imageRoute2.visibility = View.VISIBLE
            imageRoute1.visibility = View.INVISIBLE
            imageRoute3.visibility = View.INVISIBLE
        }
        route3.setOnClickListener {
            textdistance.text = minut3.text
            textdistance.setTextColor(Color.parseColor("#5D8EEF"))
            km1.setTextColor(Color.parseColor("#5D8EEF"))
            minut1.setTextColor(Color.parseColor("#5D8EEF"))
            km2.setTextColor(Color.parseColor("#5D8EEF"))
            minut2.setTextColor(Color.parseColor("#5D8EEF"))
            km3.setTextColor(Color.parseColor("#5D8EEF"))
            minut3.setTextColor(Color.parseColor("#5D8EEF"))
            imageRoute3.visibility = View.VISIBLE
            imageRoute1.visibility = View.INVISIBLE
            imageRoute2.visibility = View.INVISIBLE
        }
        lotties = fragmentLayout.findViewById(R.id.lotties) as LottieAnimationView*/
        /*fragmentLayout.findViewById<ImageView>(R.id.backbutton).setOnClickListener {
            mBottomSheetRoute.state = BottomSheetBehavior.STATE_HIDDEN
            mBottomSheetTimeRoute.visibility = View.INVISIBLE
            bottomNavigationView.visibility = View.VISIBLE
            polylineFinal?.remove()
        }*/
        fab.setOnClickListener {
            map.animateCamera(
                CameraUpdateFactory.newLatLng(
                    convertertoLatLng(
                        getLastKnownLocation(
                            contextCompats
                        )
                    )
                )
            )
        }
        var fabIsOpen = false
        val fabOpen = AnimationUtils.loadAnimation(contextCompats, R.anim.fade_in)
        val fabClose = AnimationUtils.loadAnimation(contextCompats, R.anim.fade_out)
        fabinfo.setOnClickListener {
            /*//displayMessage("hewl")
            findNavController().navigate(R.id.action_maps_screen_to_guided_screen)*/
            if (fabIsOpen) {
                fabcamera.startAnimation(fabClose)
                fabcheckloc.startAnimation(fabClose)
                fabexc.startAnimation(fabClose)
                fabcheckloc.hide()
                fabcamera.hide()
                fabexc.hide()
                fabIsOpen = false
            } else {
                fabcamera.startAnimation(fabOpen)
                fabcheckloc.startAnimation(fabOpen)
                fabexc.startAnimation(fabOpen)
                fabcheckloc.show()
                fabcamera.show()
                fabexc.show()
                fabIsOpen = true
            }
        }
        fabcamera.setOnClickListener {
            findNavController().navigate(R.id.action_maps_screen_to_checklandmark_screen)
        }
        fabcheckloc.setOnClickListener {
            val bundle = bundleOf("UserLocation" to getLastKnownLocation(contextCompats))
            findNavController().navigate(R.id.action_maps_screen_to_checkloc_screen, bundle)
        }
        fabexc.setOnClickListener {
            findNavController().navigate(R.id.action_maps_screen_to_guided_screen)
        }
        val mapFragment = childFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment?
        val coord = editData(contextCompats, "USERNAME", "USERNAME_COORDINATES", "HELLO S", "getString").toString()
        if (coord != "HELLO S") {
            mapFragment?.getMapAsync(callback)
            Log.d("MapsFragment", coord)
        }
        val guided = arguments?.getInt(EXCURSION_ID, -1)
        Log.d("tt", "$guided loading")
        if(guided != -1 && guided != null) {
            mapFragment?.getMapAsync(callback)
        }
        if (coord != "HELLO S") {
            DirectionsHelper(requireFragmentManager(), requireActivity()).getTwoDirection(
                getLastKnownLocation(
                    contextCompats
                ), coord
            )
        }
        val routeStart = fragmentLayout.findViewById<ImageView>(R.id.routeStart)
        routeStart.setOnClickListener {
            DirectionsHelper(requireFragmentManager(), requireActivity()).getTwoDirection(
                getLastKnownLocation(
                    contextCompats
                ), coordtext
            )
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
        fragmentLayout.findViewById<TextView>(R.id.detailed).setOnClickListener {
            val objectId = arguments?.getInt(EXCURSION_ID, -1)
            val bundle = bundleOf(OBJECT_NAME to maintext, OBJECT_ID to objectId)
            findNavController().navigate(R.id.allinfos_screen, bundle)
        }
        fun changeText(ids: String) {
            when (ids) {
                "m0" -> {
                    fragmentLayout.findViewById<ImageView>(R.id.mainimg)
                        .setImageResource(R.drawable.ic_eyeforad)
                    fragmentLayout.findViewById<ImageView>(R.id.galaryone)
                        .setImageResource(R.drawable.ic_eyeforad)
                    fragmentLayout.findViewById<ImageView>(R.id.galarytwo)
                        .setImageResource(R.drawable.ic_eyeforad)
                }
                "m1" -> {
                    fragmentLayout.findViewById<ImageView>(R.id.mainimg)
                        .setImageResource(R.drawable.ic_eyeforad)
                    fragmentLayout.findViewById<ImageView>(R.id.galaryone)
                        .setImageResource(R.drawable.ic_eyeforad)
                    fragmentLayout.findViewById<ImageView>(R.id.galarytwo)
                        .setImageResource(R.drawable.ic_eyeforad)
                }
            }
        }

        changeText(mainid)
        fragmentLayout.findViewById<TextView>(R.id.mainName).text = maintext
        val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if(slideOffset == 0.0.toFloat()){
                    bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
        mBottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)
        return fragmentLayout

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val contextCompats = requireContext().applicationContext
        val mapFragment = childFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    // Функция проверки есть ли разрешение
    private fun isPermissionGranted(): Boolean {
        val contextCompats = requireContext().applicationContext
        return ContextCompat.checkSelfPermission(
            contextCompats,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Функция включения геолокации
    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            val contextCompats = requireContext().applicationContext
            if (ActivityCompat.checkSelfPermission(
                    contextCompats,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    contextCompats,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            map.isMyLocationEnabled = true
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                requestLocationPermission
            )
        }
    }

    // Функция получает значение разрешения и вызывает функцию включения геолокации
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        if (requestCode == requestLocationPermission) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

    // Функция переводит xml картинку в Bit для отображения на карте
    private fun getBitmapDescriptor(id: Int): BitmapDescriptor? {
        return if (Build.VERSION.SDK_INT >= 23) {
            val contextCompats = requireContext().applicationContext
            val vectorDrawable = getDrawable(contextCompats, id) as VectorDrawable
            val h = vectorDrawable.intrinsicHeight
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

    // Функция получения координат человека
    private fun getLastKnownLocation(context: Context): String {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers: List<String> = locationManager.getProviders(true)
        var location: Location? = null
        val contextCompats = requireContext().applicationContext
        for (i in providers.size - 1 downTo 0) {
            if (ActivityCompat.checkSelfPermission(
                    contextCompats,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    contextCompats,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
            }
            location = locationManager.getLastKnownLocation(providers[i])
            if (location != null)
                break
        }
        val gps = DoubleArray(2)
        var coordinate = ""
        if (location != null) {
            gps[0] = location.latitude
            gps[1] = location.longitude
            coordinate = "${gps[0]}, ${gps[1]}"
            Log.e("gpsLat", gps[0].toString())
            Log.e("gpsLong", gps[1].toString())
        }
        return coordinate

    }

    private fun converterLatLng(coordinate: LatLng): String {
        val lat = coordinate.latitude
        val lng = coordinate.longitude
        return "$lat, $lng"
    }

    private fun convertertoLatLng(coordinate: String): LatLng {
        val latlong = coordinate.split(",")
        val latitude = latlong[0].toDouble()
        val longitude = latlong[1].toDouble()
        return LatLng(latitude, longitude)
    }

    private fun showBottom(p0: Marker?, fragmentLayout: View) {
        val contextCompats = requireContext().applicationContext
        maintext = "${p0?.title}"
        mainid = "${p0?.id}"
        Log.d("mainid", mainid)
        val coord: LatLng = p0?.position!!
        coordtext = converterLatLng(coord)
        bottomNavigationView.visibility = View.INVISIBLE
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        val routeStart: ImageView = fragmentLayout.findViewById(R.id.routeStart)
        routeStart.setOnClickListener {
            DirectionsHelper(requireFragmentManager(), requireActivity()).getTwoDirection(
                getLastKnownLocation(
                    contextCompats
                ), coordtext
            )
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
        fragmentLayout.findViewById<TextView>(R.id.detailed).setOnClickListener {
            val bundle = bundleOf(OBJECT_NAME to maintext, OBJECT_ID to mainid)
            findNavController().navigate(R.id.allinfos_screen, bundle)
        }
        fun changeText(name: String) {
            DatabaseHelper(requireFragmentManager()) {
                activity?.runOnUiThread {
                    fragmentLayout.findViewById<ImageView>(R.id.mainimg).downloadAndInto(
                        respObjDatabase.response[0].galleryone)
                    fragmentLayout.findViewById<ImageView>(R.id.galaryone).downloadAndInto(
                        respObjDatabase.response[0].gallerytwo)
                    fragmentLayout.findViewById<ImageView>(R.id.galarytwo).downloadAndInto(
                        respObjDatabase.response[0].gallerythree)
                }
            }.getTwoData("SELECT * FROM `excursionsObjects` WHERE `name` = '$name'")
        }
        changeText(maintext)
        fragmentLayout.findViewById<TextView>(R.id.mainName).text = maintext
        //fragmentManager?.let { it1 -> sheet.show(it1, "DemoBottomSheetFragment") }

    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        if (p0 != null) {
            showBottom(p0, requireView())
        }

        return true
    }

    companion object {
        const val OBJECT_NAME = "objectName"
        const val OBJECT_ID = "objectId"
    }

}
