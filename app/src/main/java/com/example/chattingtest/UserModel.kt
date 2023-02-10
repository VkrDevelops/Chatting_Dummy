package com.example.chattingtest

class UserModel {
    var id: String?=null
    var name: String? = null
    var email: String? = null
    var password: String? = null

    constructor() {}
    constructor(id: String, name: String?, email: String?, password: String?) {
        this.id=id
        this.name = name
        this.email = email
        this.password = password
    }
}