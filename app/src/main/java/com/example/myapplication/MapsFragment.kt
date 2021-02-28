package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import android.location.Location
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast

import com.google.android.gms.location.*
import com.google.android.gms.maps.model.*

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

var coordinate = ""

class MapsFragment : Fragment() {

    private lateinit var map: GoogleMap

    // Разрешения для использования и получения геолокации
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val request_location_permission = 1

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
                        .title("Главный корпус оружейного завода")
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
                        .title("Ижевский индустриальный техникум")
                        .icon(getBitmapDescriptor(R.drawable.icon_on_map))
        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.84408400157632, 53.19771856787305))
                        .title("Памятник ижевским оружейникам")
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
                        .title("Долгий мост и завод «Ижсталь")
                        .icon(getBitmapDescriptor(R.drawable.icon_on_map))
        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.85177628926549, 53.2002482478798))
                        .title("Здание из красного кирпич")
                        .icon(getBitmapDescriptor(R.drawable.icon_on_map))
        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.85073186241447, 53.20672264326064))
                        .title("Музейно-выставочный комплекс стрелкового оружия имени Михаила Тимофеевича Калашникова")
                        .icon(getBitmapDescriptor(R.drawable.icon_on_map))
        )
        map.addMarker(
                MarkerOptions()
                        .position(LatLng(56.85285289473385, 53.215664171778975))
                        .title("Арсенал")
                        .icon(getBitmapDescriptor(R.drawable.icon_on_map))
        )
        // Включение геолокации и запрос разрешения
        enableMyLocation()
        // Устанавливаем максимальное приближение
        map.setMinZoomPreference(11.5f)
        // Получаем координаты и выводим как уведомление
        giveMeLocation()
        Toast.makeText(contextCompats, "$coordinate", Toast.LENGTH_LONG).show()
        // Отрисовка маршрута
        val location4 = "56.85285289473385, 53.215664171778975"
        val location5 = "56.83996208388173, 53.19589266822729"
        getTwoDirection(location4, location5)
        val location1 = "56.84383886160861, 53.191198130527944"
        val location2 = "56.85073186241447, 53.20672264326064"
        val location3 = "56.85285289473385, 53.215664171778975"
        getThreeDirection(location1,location2,location3)
        //Log.d("GoogleMap", "before URL")
        //val URL = getDirectionURL(location1,location2)
        //Log.d("GoogleMap", "URL : $URL")
       //GetDirection(URL).execute()

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val contextCompats = requireContext().applicationContext
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(contextCompats)
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
    // Функция проверки есть ли разрешение
    private fun isPermissionGranted() : Boolean {
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
        }
        else {
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
            grantResults: IntArray
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
            val vectorDrawable = getDrawable(contextCompats,id) as VectorDrawable
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
    @SuppressLint("MissingPermission")
    private fun giveMeLocation() {
        //val contextCompats = requireContext().applicationContext
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    var latitude = location?.latitude
                    var longitude = location?.longitude
                    coordinate = "$latitude,$longitude"
                    //Toast.makeText(contextCompats, "$coordinate", Toast.LENGTH_LONG).show()
                }
    }
    fun getThreeDirection(origin: String, dest: String, desttwo: String) {
        val URL = getDirectionURL(origin,dest)
        GetDirection(URL).execute()
        val URLtwo = getDirectionURL(dest,desttwo)
        GetDirection(URLtwo).execute()
        return
    }
    fun getTwoDirection(origin: String, dest: String) {
        val URL = getDirectionURL(origin,dest)
        GetDirection(URL).execute()
        return
    }
    // Функция получения запроса на пострение маршрута
    fun getDirectionURL(origin: String, dest: String) : String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin}&destination=${dest}&key=AIzaSyDVGH9AfUwk5CLr76_QGmoLhDNWwuj6yps"
    }
    // Функция дешифровки URL
    private inner class GetDirection(val url : String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val contextCompats = requireContext().applicationContext
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body()!!.string()
            Log.d("GoogleMap" , " data : $data")
            val result =  ArrayList<List<LatLng>>()
            try{
                val respObj = Gson().fromJson(data,GoogleMapDTO::class.java)
                val path =  ArrayList<LatLng>()

                for (i in 0..(respObj.routes[0].legs[0].steps.size-1)){
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return result
        }
        // Отрисовка линий маршрута на карте
        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.BLUE)
                lineoption.geodesic(true)
            }
            map.addPolyline(lineoption)
        }
    }
    public fun decodePolyline(encoded: String): List<LatLng> {

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

            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }

        return poly
    }
}


