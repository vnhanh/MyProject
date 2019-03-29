package com.tma.movemind.base.models

class Instructor (val id:String, val firstName:String, val lastName:String, var avatarUrl:String?=null){
    val fullName:String
    get() = String.format("%s %s", firstName, lastName)
}