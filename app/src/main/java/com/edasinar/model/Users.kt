package com.edasinar.model

class Users {
    var name: String? = null
    var surname: String? = null
    var username: String? = null
    var email: String? = null
    var status: String? = null

    constructor() {}
    constructor(name: String?, surname: String?, username: String?, email: String?, status: String?) {
        this.name = name
        this.surname = surname
        this.username = username
        this.email = email
        this.status = status
    }
}
