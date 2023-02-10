package com.example.chattingtest

class MessageModel {
    var messageId: String?=null
    var senderId: String?=null
    var name: String? = null
    var message: String? = null
    var date: String? = null

    constructor() {}
    constructor(messageId: String, senderId: String?, name: String?, message: String?, date: String?) {
        this.messageId=messageId
        this.senderId=senderId
        this.name = name
        this.message = message
        this.date = date
    }
}