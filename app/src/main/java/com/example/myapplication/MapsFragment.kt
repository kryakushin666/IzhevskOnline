@file:Suppress("DEPRECATION")

package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.VectorDrawable
import android.location.Location
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.airbnb.lottie.LottieAnimationView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.example.myapplication.UserProfileMuseum.Companion.USERNAME_COORDINATES
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import androidx.navigation.fragment.findNavController




var maintext = "This my work"
var dirtext = "This my work"
var disttext = "This my work"
var coordtext = "This my work"
//var IsCheckedDone: Boolean = false
var polylineFinal: Polyline? = null


private lateinit var map: GoogleMap
private lateinit var fusedLocationClient: FusedLocationProviderClient

@Suppress("CAST_NEVER_SUCCEEDS")
public class MapsFragment : Fragment(), GoogleMap.OnMarkerClickListener {


    // Разрешения для использования и получения геолокации
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val request_location_permission = 1
    private lateinit var buttonss: TextView
    private lateinit var buttons: TextView
    //private lateinit var lotties: LottieAnimationView

    private val callback = OnMapReadyCallback { googleMap ->

        map = googleMap

        val contextCompats = requireContext().applicationContext

        //Наводят камеру на Ижевск и устанавливают уровень приближения
        val latitude = 56.85970797942636
        val longitude = 53.196807013800594
        val zoomLevel = 11.5f

        val homeLatLng = LatLng(latitude, longitude)
        // Перемещает камеру к этим координатам
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        // Создание меток на карте
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
        map.setOnMarkerClickListener(this)

        // Включение геолокации и запрос разрешения
        enableMyLocation()
        // Устанавливаем максимальное приближение
        map.setMinZoomPreference(11.5f)
        // Получаем координаты и выводим как уведомление
        //giveMeLocation()
        //getLastKnownLocation(contextCompats)
        Toast.makeText(contextCompats, "${getLastKnownLocation(contextCompats)}", Toast.LENGTH_LONG).show()
        //getLastKnownLocation()
        //Toast.makeText(contextCompats, "$coordinate", Toast.LENGTH_LONG).show()
        // Отрисовка маршрута
        val location4 = "56.85285289473385, 53.215664171778975"
        val location5 = "56.83996208388173, 53.19589266822729"
        //getTwoDirection(location4, location5)
        val location1 = "56.84383886160861, 53.191198130527944"
        val location2 = "56.85073186241447, 53.20672264326064"
        val location3 = "56.85285289473385, 53.215664171778975"
        //getThreeDirection(location1, location2, location3)
        //Log.d("GoogleMap", "before URL")
        //val URL = getDirectionURL(location1,location2)
        //Log.d("GoogleMap", "URL : $URL")
        //GetDirection(URL).execute()
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_maps, container, false)
        val contextCompats = requireContext().applicationContext
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(contextCompats)
        buttonss = fragmentLayout.findViewById(R.id.buttonss)
        buttons = fragmentLayout.findViewById(R.id.buttons)
        //lotties = fragmentLayout.findViewById(R.id.lotties) as LottieAnimationView
        buttonss.setOnClickListener {
            val coordinate = "56.85285289473385, 53.215664171778975"
            getTwoDirection(getLastKnownLocation(contextCompats), coordinate)
        }
        buttons.setOnClickListener {
            if(dirtext != "This my work") {
                val sheet = DemoBottomSheetFragments()
                fragmentManager?.let { it1 -> sheet.show(it1, "DemoBottomSheetFragments") }
            }
        }
        /*val animidet = lotties.isAnimating
        if(IsCheckedDone) {
            if (animidet == true) {
                lotties.visibility
                Log.d("dd", "invis done!")
                IsCheckedDone = false
            }
        }*/
        val coord = arguments?.getString(USERNAME_COORDINATES) ?: "HELLO S"
        //val coordinate = converterLatLng(coord)
        Log.d("coord", "oord: $coord")

        if(coord != "HELLO S")
        {
            /*val bottomNavigationView = fragmentLayout.findViewById<BottomNavigationView>(R.id.bottom_nav)
            val navGraphIds = listOf(R.navigation.navigation_home, R.navigation.navigation_notifications, R.navigation.navigation_profile)
            intent = requireActivity().intent!!
            val controller = fragmentManager?.let {
                bottomNavigationView.setupWithNavController(
                        navGraphIds = navGraphIds,
                        fragmentManager = it,
                        containerId = R.id.nav_host_container,
                        intent = intent
                )
            }*/

            // Whenever the selected controller changes, setup the action bar.
            /*controller?.observe(contextCompats, Observer { navController ->
                setupActionBarWithNavController(navController)
            })*/
            //currentNavController = controller
            getTwoDirection(getLastKnownLocation(contextCompats), coord)
        }
        return fragmentLayout
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                    ) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            map.isMyLocationEnabled = true
        } else {
            requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    request_location_permission
            )
        }
    }

    // Функция получает значение разрешения и вызывает функцию включения геолокации
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray,
    ) {
        if (requestCode == request_location_permission) {
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
    fun getLastKnownLocation(context: Context): String {
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
            gps[0] = location.getLatitude()
            gps[1] = location.getLongitude()
            coordinate = "${gps[0]}, ${gps[1]}"
            Log.e("gpsLat", gps[0].toString())
            Log.e("gpsLong", gps[1].toString())
        }
        return coordinate

    }
    fun getThreeDirection(origin: String, dest: String, desttwo: String) {
        val URL = getDirectionURL(origin, dest)
        GetDirection(URL).execute()
        val URLtwo = getDirectionURL(dest, desttwo)
        GetDirection(URLtwo).execute()
        return
    }
    public fun getTwoDirection(origin: String, dest: String) {
        val URL = getDirectionURL(origin, dest)
        GetDirection(URL).execute()
        return
    }

    // Функция получения запроса на пострение маршрута
    fun getDirectionURL(origin: String, dest: String): String {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin}&destination=${dest}&key=AIzaSyDVGH9AfUwk5CLr76_QGmoLhDNWwuj6yps"
    }

    // Функция дешифровки URL
    inner class GetDirection(val url: String) : AsyncTask<Void, Void, List<List<LatLng>>>() {
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            //val contextCompats = requireContext().applicationContext
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body()!!.string()
            Log.d("GoogleMap", " data : $data")
            val result = ArrayList<List<LatLng>>()
            try {
                val respObj = Gson().fromJson(data, GoogleMapDTO::class.java)
                val path = ArrayList<LatLng>()
                var duration = respObj.routes[0].legs[0].duration.text
                var distance = respObj.routes[0].legs[0].distance.text
                val sheet = DemoBottomSheetFragments()
                dirtext = "$duration"
                disttext = "$distance"
                fragmentManager?.let { it1 -> sheet.show(it1, "DemoBottomSheetFragments") }

                for (i in 0..(respObj.routes[0].legs[0].steps.size - 1)) {
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

    private fun converterLatLng(coordinate: LatLng): String {
        val lat = coordinate.latitude
        val lng = coordinate.longitude
        var coordinates = "$lat, $lng"
        return coordinates
    }
    private fun showBottom(p0: Marker?) {
        val sheet = DemoBottomSheetFragment()
        maintext = "${p0?.title}"
        val coord: LatLng = p0?.position!!
        coordtext = converterLatLng(coord)
        fragmentManager?.let { it1 -> sheet.show(it1, "DemoBottomSheetFragment") }
    }
    override fun onMarkerClick(p0: Marker?): Boolean {
        if (p0 != null) {
            showBottom(p0)
            /*if(marker == "m8") {
                context?.let {
                    InfoSheet().show(it) {
                        title("${p0.title}")
                        var coordinate = converterLatLng(p0.position)
                        content("$coordinate")
                        onNegative("He's good") {
                            getTwoDirection(getLastKnownLocation(contextCompats), "56.84408400157632, 53.19771856787305")
                        }
                        onPositive("Тест") {
                            Toast.makeText(contextCompats, "${p0.id}", Toast.LENGTH_LONG).show()
                        }
                    }
                    /*val sheet = DemoBottomSheetFragment()
                    maintext = "${p0.title}"
                    fragmentManager?.let { it1 -> sheet.show(it1, "DemoBottomSheetFragment") }*/
                }
            }*/
        }
        return true
    }
    class DemoBottomSheetFragment : SuperBottomSheetFragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            lateinit var rectangle_3: ImageView
            val contextCompats = requireContext().applicationContext
            val fragmentLayout = inflater.inflate(R.layout.fragment_demo_sheet, container, false)
            rectangle_3 = fragmentLayout.findViewById(R.id.rectangle_3)
            rectangle_3.setOnClickListener {
                getTwoDirection(getLastKnownLocation(contextCompats), coordtext)
            }
            fragmentLayout.findViewById<TextView>(R.id.some_idsss).setOnClickListener {
                val bundle = bundleOf(OBJECT_NAME to maintext)
                findNavController().navigate(R.id.allinfos_screen, bundle)
            }
            fragmentLayout.findViewById<TextView>(R.id.some_id).text = maintext

            return fragmentLayout
        }
        private fun converterLatLng(coordinate: LatLng): String {
            val lat = coordinate.latitude
            val lng = coordinate.longitude
            val coordinates = "$lat, $lng"
            return coordinates
        }
        fun getLastKnownLocation(context: Context): String {
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
                gps[0] = location.getLatitude()
                gps[1] = location.getLongitude()
                coordinate = "${gps[0]}, ${gps[1]}"
                Log.e("gpsLat", gps[0].toString())
                Log.e("gpsLong", gps[1].toString())
            }
            return coordinate

        }
        fun getTwoDirection(origin: String, dest: String) {
            val URL = getDirectionURL(origin, dest)
            GetDirection(URL).execute()
            return
        }

        // Функция получения запроса на пострение маршрута
        fun getDirectionURL(origin: String, dest: String): String {
            return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin}&destination=${dest}&key=AIzaSyDVGH9AfUwk5CLr76_QGmoLhDNWwuj6yps"
        }

        // Функция дешифровки URL
        inner class GetDirection(val url: String) : AsyncTask<Void, Void, List<List<LatLng>>>() {
            override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
                val client = OkHttpClient()
                //val contextCompats = requireContext().applicationContext
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()
                val data = response.body()!!.string()
                Log.d("GoogleMap", " data : $data")
                val result = ArrayList<List<LatLng>>()
                try {
                    val respObj = Gson().fromJson(data, GoogleMapDTO::class.java)
                    val path = ArrayList<LatLng>()
                    var duration = respObj.routes[0].legs[0].duration.text
                    var distance = respObj.routes[0].legs[0].distance.text
                    val sheet = DemoBottomSheetFragments()
                    dirtext = "$duration"
                    disttext = "$distance"
                    fragmentManager?.let { it1 -> sheet.show(it1, "DemoBottomSheetFragments") }

                    for (i in 0..(respObj.routes[0].legs[0].steps.size - 1)) {
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
                //lotties.playAnimation()
                //val invis = lotties.isInvisible
                //Log.d("dd", "invi: $invis")
                //IsCheckedDone = true
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
        override fun getCornerRadius() = requireContext().resources.getDimension(R.dimen.demo_sheet_rounded_corner)

        override fun getStatusBarColor() = Color.RED

        companion object {
            const val OBJECT_NAME = "objectName"
        }
    }

    class DemoBottomSheetFragments : SuperBottomSheetFragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            val fragmentLayout = inflater.inflate(R.layout.fragment_demo_sheet_distance, container, false)

            fragmentLayout.findViewById<TextView>(R.id.some_id).text = dirtext
            fragmentLayout.findViewById<TextView>(R.id.some_idss).text = disttext

            return fragmentLayout
        }

        override fun getCornerRadius() = requireContext().resources.getDimension(R.dimen.demo_sheet_rounded_corner)

        override fun getStatusBarColor() = Color.RED
    }
}
