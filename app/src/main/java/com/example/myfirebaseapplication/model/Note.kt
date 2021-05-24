package com.example.myfirebaseapplication.model

import java.time.LocalDate
import java.util.*

data class Note(
    val userId: String,
    val noteId: String,
    val note: String){
    constructor(): this("", "", "")
}