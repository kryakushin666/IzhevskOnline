package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
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
import android.widget.Toast

import com.google.android.gms.location.*
import com.google.android.gms.maps.model.*


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
        val contextCompats = requireContext().applicationContext
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    var latitude = location?.latitude
                    var longitude = location?.longitude
                    coordinate = "$latitude,$longitude"
                    //Toast.makeText(contextCompats, "$coordinate", Toast.LENGTH_LONG).show()
                }
    }
}




