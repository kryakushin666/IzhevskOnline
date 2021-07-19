package com.example.myapplication.modulesDTO

class DatabaseDTO: ArrayList<User>() {
}

class User {
    // User
    var id = 0
    var name = ""
    var email = ""
    var password = ""
    // Object
    var logo_image = "tps://firebasestor.googlapis.com/v0/b/izhevskonline123.appspot.com/o/8"
    var latlng = ""
    var rating = ""
    // Version App
    var lastversion = 0.0F
    var betaversion = 0.0F
}
class DatabaseErrorDTO {
    var errno = -1
    var message = "std"
}