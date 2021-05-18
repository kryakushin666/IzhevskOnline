@file:Suppress("DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.myapplication

import ModulesDTO.GoogleMapDTO
import ModulesDTO.WeatherDTO
import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.VectorDrawable
import android.location.Location
import android.location.LocationManager
import android.media.RingtoneManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.example.myapplication.GunFragment.Companion.OBJECT_GUIDED
import com.example.myapplication.UserProfileMuseum.Companion.USERNAME_COORDINATES
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.custom_toast_layout.*
import okhttp3.OkHttpClient
import okhttp3.Request

var maintext = "This my work"
var mainid = "This my work"
var dirtext = "This my work"
var disttext = "This my work"
var coordtext = "This my work"
var maintemp = 0.0

var polylineFinal: Polyline? = null

private lateinit var map: GoogleMap

var fusedLocationProviderClient: FusedLocationProviderClient? = null
var locationRequest: LocationRequest? = null
var userLocationMarker: Marker? = null
var userLocationAccuracyCircle: Circle? = null

private lateinit var bottomSheetBehavior: BottomSheetBehavior<RelativeLayout>

@Suppress("CAST_NEVER_SUCCEEDS")
class MapsFragment : Fragment(), GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationChangeListener {

    // Разрешения для использования и получения геолокации
    //private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val requestLocationPermission = 1
    private lateinit var buttonss: TextView
    private lateinit var buttons: TextView
    private lateinit var fab: ImageView
    private lateinit var fabinfo: ImageView


    val API: String = "01c75bbdb01acb6bbf0b75e71d13a680" // Use API key
    private val CHANNEL_ID = "IzhevskOnline"
    //private var mPositionMarker: Marker? = null
    //private lateinit var lotties: LottieAnimationView

    private val callback = OnMapReadyCallback { googleMap ->

        map = googleMap

        //val contextCompats = requireContext().applicationContext

        //Наводят камеру на Ижевск и устанавливают уровень приближения
        val latitude = 56.85970797942636
        val longitude = 53.196807013800594
        val zoomLevel = 11.5f
        val homeLatLng = LatLng(latitude, longitude)
        // Перемещает камеру к этим координа  там
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        // Создание меток на карте
        map.setOnMarkerClickListener(this)
        map.setOnMyLocationChangeListener(this)
        map.uiSettings.isMapToolbarEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false
        //map.setOnMyLocationChangeListener(this)
        // Включение геолокации и запрос разрешения
        enableMyLocation()
        // Устанавливаем максимальное приближение
        map.setMinZoomPreference(11.5f)
        val guided = arguments?.getString(OBJECT_GUIDED) ?: "HELLO WORLD"
        if(guided == "GunCenter")
        {
            map.addMarker(
                    MarkerOptions()
                            .position(LatLng(56.84383886160861, 53.191198130527944))
                            .title("Главный корпус\n оружейного завода")
                            .icon(getBitmapDescriptor(R.drawable.icon_on_map))
            )
            map.addMarker(
                    MarkerOptions()
                            .position(LatLng(56.844568122459364, 53.191131214863674))
                            .title("Памятник Дерябину")
                            .icon(getBitmapDescriptor(R.drawable.icon_on_map))
            )
            map.addMarker(
                    MarkerOptions()
                            .position(LatLng(56.848892795955905, 53.19585937814813))
                            .title("Ижевский\n индустриальный техникум")
                            .icon(getBitmapDescriptor(R.drawable.icon_on_map))
            )
            map.addMarker(
                    MarkerOptions()
                            .position(LatLng(56.84408400157632, 53.19771856787305))
                            .title("Памятник ижевским\n оружейникам")
                            .icon(getBitmapDescriptor(R.drawable.icon_on_map))
            )
            map.addMarker(
                    MarkerOptions()
                            .position(LatLng(56.84398424917061, 53.198120889542295))
                            .title("Музей ИЖМАШ")
                            .icon(getBitmapDescriptor(R.drawable.icon_on_map))
            )
            map.addMarker(
                    MarkerOptions()
                            .position(LatLng(56.83996208388173, 53.19589266822729))
                            .title("Долгий мост\n и завод «Ижсталь")
                            .icon(getBitmapDescriptor(R.drawable.icon_on_map))
            )
            map.addMarker(
                    MarkerOptions()
                            .position(LatLng(56.85177628926549, 53.2002482478798))
                            .title("Здание из красного\n кирпича")
                            .icon(getBitmapDescriptor(R.drawable.icon_on_map))
            )
            map.addMarker(
                    MarkerOptions()
                            .position(LatLng(56.85073186241447, 53.20672264326064))
                            .title("Музейно-выставочный\n комплекс стрелкового\n оружия имени Михаила\n Тимофеевича Калашникова")
                            .icon(getBitmapDescriptor(R.drawable.icon_on_map))
            )
            map.addMarker(
                    MarkerOptions()
                            .position(LatLng(56.85285289473385, 53.215664171778975))
                            .title("Арсенал")
                            .icon(getBitmapDescriptor(R.drawable.icon_on_map))
            )
        }
        // Получаем координаты и выводим как уведомление
        //Toast.makeText(contextCompats, getLastKnownLocation(contextCompats), Toast.LENGTH_LONG).show()
    }
    private fun displayError(message: String) {
        val snackBar = Snackbar.make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
        snackBar.view.setBackgroundResource(R.drawable.curved_bg_error)
        snackBar.show()
    }
    private fun displayMessage(message: String) {
        Snackbar.make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_maps, container, false)
        val contextCompats = requireContext().applicationContext
        buttonss = fragmentLayout.findViewById(R.id.buttonss)
        buttons = fragmentLayout.findViewById(R.id.buttons)
        fab = fragmentLayout.findViewById(R.id.fablocation)
        fabinfo = fragmentLayout.findViewById(R.id.fabinfo)
        //lotties = fragmentLayout.findViewById(R.id.lotties) as LottieAnimationView
        buttonss.setOnClickListener {
            val customLayout = layoutInflater.inflate(
                R.layout.custom_toast_layout,
                constraintlayout
            )
            val animationView1: LottieAnimationView = customLayout.findViewById(R.id.imageSlideIcon)
            val toast = Toast(contextCompats)
            toast.duration = Toast.LENGTH_SHORT
            toast.view = customLayout
            toast.show()
            animationView1.setAnimation(R.raw.animation)
            animationView1.playAnimation()

        }
        buttons.setOnClickListener {
            /*createNotificationChannel()
            getWeather()*/
            getMoreDirection("56.84383886160861, 53.191198130527944","56.85285289473385, 53.215664171778975", "56.844568122459364, 53.191131214863674|56.848892795955905, 53.19585937814813|56.84408400157632, 53.19771856787305|56.84398424917061, 53.198120889542295|56.83996208388173, 53.19589266822729|56.85177628926549, 53.2002482478798|56.85073186241447, 53.20672264326064")
        }
        fab.setOnClickListener {
            map.animateCamera(CameraUpdateFactory.newLatLng(convertertoLatLng(getLastKnownLocation(contextCompats))))
        }
        fabinfo.setOnClickListener {
            //displayMessage("hewl")
            findNavController().navigate(R.id.action_maps_screen_to_guided_screen)
        }
        val coord = arguments?.getString(USERNAME_COORDINATES) ?: "HELLO S"
        val guided = arguments?.getString(OBJECT_GUIDED) ?: "HELLO WORLD"
        val mapFragment = childFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment?
        Log.d("tt","$guided loading")
        if(guided == "GunCenter")
        {
            //findNavController().navigateUp()
            //fragmentManager?.beginTransaction().
            /*val selectedFragment = fragmentManager?.findFragmentByTag(R.id.maps_screen)
                    as NavHostFragment
            fragmentManager?.beginTransaction()
                    ?.setCustomAnimations(
                            R.anim.fade_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.fade_out)
                    ?.attach(selectedFragment)
                    ?.setPrimaryNavigationFragment(selectedFragment)
                    .apply {
                        // Detach all other Fragments
                        graphIdToTagMap.forEach { _, fragmentTagIter ->
                            if (fragmentTagIter != newlySelectedItemTag) {
                                detach(fragmentManager?.findFragmentByTag(firstFragmentTag)!!)
                            }
                        }
                    }
                    ?.addToBackStack(firstFragmentTag)
                    ?.setReorderingAllowed(true)
                    ?.commit()*/
                    mapFragment?.getMapAsync(callback)
        }
        //val coordinate = converterLatLng(coord)
        Log.d("coord", "oord: $coord")

        if(coord != "HELLO S")
        {
            getTwoDirection(getLastKnownLocation(contextCompats), coord)
        }
        return fragmentLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val contextCompats = requireContext().applicationContext
        val mapFragment = childFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
    /*override fun onMyLocationChange(p0: Location?) {
        if (p0 != null) {
            setUserLocationMarker(p0)
        }
    }*/
    private fun sendNotification(body: String?) {
        val contextCompats = requireContext().applicationContext
        val intent = Intent(contextCompats, MapsActivity::class.java)
        //If set, and the activity being launched is already running in the current task,
        //then instead of launching a new instance of that activity, all of the other activities
        // on top of it will be closed and this Intent will be delivered to the (now on top)
        // old activity as a new Intent.
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.putExtra("Notification",body)

        val pendingIntent = PendingIntent.getActivity(contextCompats,0,intent, PendingIntent.FLAG_ONE_SHOT/*Flag indicating that this PendingIntent can be used only once.*/)
        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationManager: NotificationManager = contextCompats.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(contextCompats, "IzhevskOnline")
        } else {
            Notification.Builder(contextCompats)
        }.apply {
            setSmallIcon(R.drawable.icon_on_map)
            setContentTitle("Отличная погода")
            setContentText(body)
            setAutoCancel(true)
            setSound(notificationSound)
            setContentIntent(pendingIntent)
        }.build()
        notificationManager.notify(0, notificationBuilder)
    }
    private fun createNotificationChannel() {
        val contextCompats = requireContext().applicationContext
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "IzhevskOnline"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("IzhevskOnline", name, importance)
            val notificationManager: NotificationManager? = ContextCompat.getSystemService(contextCompats, NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }
    }
    inner class weatherTask(private val url: String) : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String?): String {
            val client = OkHttpClient()
            //val contextCompats = requireContext().applicationContext
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()
            Log.d("Weather", " data : $data")
            try {
                val respObj = Gson().fromJson(data, WeatherDTO::class.java)
                maintemp = respObj.current.temp_c
                Log.d("temp", "$maintemp")
                sendNotification("$maintemp °C")

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return "true"
        }

        // Отрисовка линий маршрута на карте
        override fun onPostExecute(result: String?) {
        }
    }
    fun converterToLatLng(coordinate: LatLng): String {
        val lat = coordinate.latitude
        val lng = coordinate.longitude
        return "$lat, $lng"
    }
    private fun getTwoDirection(origin: String, dest: String) {
        GetDirection(getDirectionURL(origin, dest)).execute()
        return
    }
    private fun getMoreDirection(origin: String, dest: String, waypoints: String) {
        GetDirection(getDirectionURLs(origin, dest, waypoints)).execute()
        return
    }
    private fun getWeather() {
        weatherTask(getWeatherURL()).execute()
        return
    }
    // Функция получения запроса на пострение маршрута
    private fun getDirectionURL(origin: String, dest: String): String {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin}&destination=${dest}&key=AIzaSyDVGH9AfUwk5CLr76_QGmoLhDNWwuj6yps"
    }
    private fun getDirectionURLs(origin: String, dest: String, waypoints: String): String {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin}&destination=${dest}&waypoints=via:${waypoints}&key=AIzaSyDVGH9AfUwk5CLr76_QGmoLhDNWwuj6yps"
    }
    private fun getWeatherURL(): String {
        return "https://api.weatherapi.com/v1/current.json?key=7837c70818904b4eb94100007211104&q=Izhevsk&aqi=no"
    }
    // Функция дешифровки URL
    inner class GetDirection(private val url: String) : AsyncTask<Void, Void, List<List<LatLng>>>() {
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            //val contextCompats = requireContext().applicationContext
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()
            Log.d("GoogleMap", " data : $data")
            val result = ArrayList<List<LatLng>>()
            try {
                val respObj = Gson().fromJson(data, GoogleMapDTO::class.java)
                val path = ArrayList<LatLng>()
                val duration = respObj.routes[0].legs[0].duration.text
                val distance = respObj.routes[0].legs[0].distance.text

                dirtext = duration
                disttext = distance

                val sheet = DemoBottomSheetFragments()
                fragmentManager?.let { it1 -> sheet.show(it1, "DemoBottomSheetFragments") }

                for (i in 0 until respObj.routes[0].legs[0].steps.size) {
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        // Отрисовка линий маршрута на карте
        override fun onPostExecute(result: List<List<LatLng>>) {
            polylineFinal?.remove()
            val lineoption = PolylineOptions()
            for (i in result.indices) {
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.RED)
                lineoption.geodesic(true)
            }
            polylineFinal = map.addPolyline(lineoption)
            /*lotties.playAnimation()
            val invis = lotties.isInvisible
            Log.d("dd", "invi: $invis")
            IsCheckedDone = true*/
        }
    }

    fun decodePolyline(encoded: String): List<LatLng> {


        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val latLng = LatLng((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
            poly.add(latLng)
        }

        return poly
    }
    // Функция проверки есть ли разрешение
    private fun isPermissionGranted(): Boolean {
        val contextCompats = requireContext().applicationContext
        return ContextCompat.checkSelfPermission(
            contextCompats,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }


    override fun onMyLocationChange(location: Location) {
        val target = Location("target")
        var name = "какой-то"
        val contextCompats = requireContext().applicationContext
        for (point in arrayOf(
            LatLng(56.85285289473385, 53.215664171778975),
            LatLng(56.85073186241447, 53.20672264326064)
        )) {
            target.latitude = point.latitude
            target.longitude = point.longitude
            if(point == LatLng(56.85285289473385, 53.215664171778975))
            {
                name = "арсенал"
            }
            if (location.distanceTo(target) < 50f) {
                Toast.makeText(contextCompats, "Вы попали в зону локации $name", Toast.LENGTH_LONG).show()
            }

        }
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
                ) != PackageManager.PERMISSION_GRANTED) {
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
        val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
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
                ) != PackageManager.PERMISSION_GRANTED) {
            }
            location= locationManager.getLastKnownLocation(providers[i])
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
    private fun showBottom(p0: Marker?) {
        val sheet = DemoBottomSheetFragment()
        maintext = "${p0?.title}"
        mainid = "${p0?.id}"
        Log.d("mainid", mainid)
        val coord: LatLng = p0?.position!!
        coordtext = converterLatLng(coord)
        fragmentManager?.let { it1 -> sheet.show(it1, "DemoBottomSheetFragment") }

    }
    override fun onMarkerClick(p0: Marker?): Boolean {
        if (p0 != null) {
            showBottom(p0)
        }

        return true
    }
    class DemoBottomSheetFragment : SuperBottomSheetFragment() {

        fun converterToLatLng(coordinate: LatLng): String {
            val lat = coordinate.latitude
            val lng = coordinate.longitude
            return "$lat, $lng"
        }
        fun getTwoDirection(origin: String, dest: String) {
            GetDirection(getDirectionURL(origin, dest)).execute()
            return
        }
        // Функция получения запроса на пострение маршрута
        fun getDirectionURL(origin: String, dest: String): String {
            return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin}&destination=${dest}&key=AIzaSyDVGH9AfUwk5CLr76_QGmoLhDNWwuj6yps"
        }
        // Функция дешифровки URL
        inner class GetDirection(private val url: String) : AsyncTask<Void, Void, List<List<LatLng>>>() {
            override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
                val client = OkHttpClient()
                //val contextCompats = requireContext().applicationContext
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()
                val data = response.body!!.string()
                Log.d("GoogleMap", " data : $data")
                val result = ArrayList<List<LatLng>>()
                try {
                    val respObj = Gson().fromJson(data, GoogleMapDTO::class.java)
                    val path = ArrayList<LatLng>()
                    val duration = respObj.routes[0].legs[0].duration.text
                    val distance = respObj.routes[0].legs[0].distance.text

                    dirtext = duration
                    disttext = distance

                    val sheet = DemoBottomSheetFragments()
                    fragmentManager?.let { it1 -> sheet.show(it1, "DemoBottomSheetFragments") }

                    for (i in 0 until respObj.routes[0].legs[0].steps.size) {
                        path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                    }
                    result.add(path)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return result
            }

            // Отрисовка линий маршрута на карте
            override fun onPostExecute(result: List<List<LatLng>>) {
                polylineFinal?.remove()
                val lineoption = PolylineOptions()
                for (i in result.indices) {
                    lineoption.addAll(result[i])
                    lineoption.width(10f)
                    lineoption.color(Color.BLUE)
                    lineoption.geodesic(true)
                }
                polylineFinal = map.addPolyline(lineoption)
                /*lotties.playAnimation()
                val invis = lotties.isInvisible
                Log.d("dd", "invi: $invis")
                IsCheckedDone = true*/
            }
        }

        fun decodePolyline(encoded: String): List<LatLng> {


            val poly = ArrayList<LatLng>()
            var index = 0
            val len = encoded.length
            var lat = 0
            var lng = 0

            while (index < len) {
                var b: Int
                var shift = 0
                var result = 0
                do {
                    b = encoded[index++].toInt() - 63
                    result = result or (b and 0x1f shl shift)
                    shift += 5
                } while (b >= 0x20)
                val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
                lat += dlat

                shift = 0
                result = 0
                do {
                    b = encoded[index++].toInt() - 63
                    result = result or (b and 0x1f shl shift)
                    shift += 5
                } while (b >= 0x20)
                val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
                lng += dlng

                val latLng = LatLng((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
                poly.add(latLng)
            }

            return poly
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            lateinit var rectangle: ImageView
            val contextCompats = requireContext().applicationContext
            val fragmentLayout = inflater.inflate(R.layout.fragment_demo_sheet, container, false)
            rectangle = fragmentLayout.findViewById(R.id.rectangle_3)
            rectangle.setOnClickListener {
                getTwoDirection(getLastKnownLocation(contextCompats), coordtext)
                dialog?.cancel()
            }
            fragmentLayout.findViewById<TextView>(R.id.some_idsss).setOnClickListener {
                val bundle = bundleOf(OBJECT_NAME to maintext, OBJECT_ID to mainid)
                findNavController().navigate(R.id.allinfos_screen, bundle)
                dialog?.cancel()
            }
            /*fun changeText(ids: String)
            {
                when(ids) {
                    "m0" -> {
                        fragmentLayout.findViewById<ImageView>(R.id.mainimg).setImageResource(R.drawable.m0ia)
                        fragmentLayout.findViewById<ImageView>(R.id.galaryone).setImageResource(R.drawable.m0is)
                        fragmentLayout.findViewById<ImageView>(R.id.galarytwo).setImageResource(R.drawable.m0id)
                    }
                    "m1" -> {
                        fragmentLayout.findViewById<ImageView>(R.id.mainimg).setImageResource(R.drawable.m1ia)
                        fragmentLayout.findViewById<ImageView>(R.id.galaryone).setImageResource(R.drawable.m1is)
                        fragmentLayout.findViewById<ImageView>(R.id.galarytwo).setImageResource(R.drawable.m1id)
                    }
                }
            }

            changeText(mainid)*/
            fragmentLayout.findViewById<TextView>(R.id.some_id).text = maintext


            return fragmentLayout
        }
        private fun getLastKnownLocation(context: Context): String {
            val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
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
                    ) != PackageManager.PERMISSION_GRANTED) {

                }
                location= locationManager.getLastKnownLocation(providers[i])
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

        override fun getCornerRadius() = requireContext().resources.getDimension(R.dimen.demo_sheet_rounded_corner)

        override fun getStatusBarColor() = Color.RED

        companion object {
            const val OBJECT_NAME = "objectName"
            const val OBJECT_ID = "objectId"
        }
    }

    class DemoBottomSheetFragments : SuperBottomSheetFragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            val fragmentLayout = inflater.inflate(
                R.layout.fragment_demo_sheet_distance,
                container,
                false
            )

            fragmentLayout.findViewById<TextView>(R.id.some_id).text = dirtext
            fragmentLayout.findViewById<TextView>(R.id.some_idss).text = disttext

            return fragmentLayout
        }

        override fun getCornerRadius() = requireContext().resources.getDimension(R.dimen.demo_sheet_rounded_corner)

        override fun getStatusBarColor() = Color.RED
    }


}
