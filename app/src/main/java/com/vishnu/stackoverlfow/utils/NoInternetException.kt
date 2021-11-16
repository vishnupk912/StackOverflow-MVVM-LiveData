package com.vishnu.stackoverlfow.utils

import java.io.IOException

class NoInternetException :IOException(){
    override  val message:String?
    get() = "Check your network"

}