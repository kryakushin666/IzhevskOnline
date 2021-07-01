package com.example.myapplication.models

class User {
    private var uid: String = ""
    private var email: String = ""
    private var name: String = ""
    private var lastname: String = ""
    private var image: String = ""

    constructor()

    constructor(uid: String, email: String, name: String, lastname: String, password: String) {
        this.uid = uid
        this.email = email
        this.name = name
        this.lastname = lastname
        this.image = password
    }

    fun getUid(): String {
        return uid
    }

    fun setUid(setuid: String) {
        this.uid = setuid
    }

    fun getEmail(): String {
        return email
    }

    fun setEmail(emailset: String) {
        this.email = emailset
    }

    fun getName(): String {
        return name
    }

    fun setName(nameset: String) {
        this.name = nameset
    }

    fun getLastName(): String {
        return lastname
    }

    fun setLastName(lastnameset: String) {
        this.lastname = lastnameset
    }

    fun getPassword(): String {
        return image
    }

    fun setimage(passwordset: String) {
        this.image = passwordset
    }
}

