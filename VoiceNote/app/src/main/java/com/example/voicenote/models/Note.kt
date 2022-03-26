package com.example.voicenote.models

data class Note (
    var name: String,
    var path: String,
    var duration: String? = null,
    var date: String? = null,

)
