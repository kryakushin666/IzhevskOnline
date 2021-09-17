package com.example.myapplication.modulesDTO

class FindPlaceDTO {
    var candidates = Candidates
    var status = ""
}

var Candidates = ArrayList<CandidatesRes>()

class CandidatesRes {
    var geometry = GeometryFind()
    var name = ""
}

class GeometryFind {
    var location = LocationsFind()
}

class LocationsFind {
    var lat = 0.0
    var lng = 0.0
}