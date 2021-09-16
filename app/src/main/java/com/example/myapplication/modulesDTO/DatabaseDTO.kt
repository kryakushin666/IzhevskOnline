package com.example.myapplication.modulesDTO

class DatabaseDTO {
    val response = ArrayList<User>()
    val success: Boolean = false
}

class User {
    // User
    var id = 0
    var name = ""
    var email = ""
    var password = ""
    var admin = 0
    var vip = 0
    var authVK = 0
    // Object
    var logo_image = "tps://firebasestor.googlapis.com/v0/b/izhevskonline123.appspot.com/o/8"
    var latlng = ""
    var rating = ""
    // Object on map
    var galleryone = ""
    var gallerytwo = ""
    var gallerythree = ""

    var opisanie = ""
    var raspolozhenie = ""
    var historycreate = ""
    var interestplace = ""
    // excursion
    var type = ""
    var author = "Izhevsk Online"
    // Version App

    var fingerprint = ""

    var lastversion = 0.0F
    var betaversion = 0.0F
}
class DatabaseErrorDTO {
    val response = DataError()
    val success: Boolean = false
}
class DataError {
    var fieldCount = 0
    var affectedRows = 0
    var insertId = 0
    var serverStatus = 0
    var warningCount = 0
    var message = ""
    var protocol41 = false
    var changedRows = 0
}