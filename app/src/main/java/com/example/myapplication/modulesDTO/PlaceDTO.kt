package com.example.myapplication.modulesDTO

class PlaceDTO {
    var results = Results
    var status = ""
}

var Results = ArrayList<ResultsRes>()

class ResultsRes {
    var geometry = Geometry()
    var icon = ""
    var name = ""
    var rating = 0.0
    var opening_hours = OpenHours()
}

class Geometry {
    var location = Locations()
}

class Locations {
    var lat = 0.0
    var lng = 0.0
}

class OpenHours {
    var open_now = false
}