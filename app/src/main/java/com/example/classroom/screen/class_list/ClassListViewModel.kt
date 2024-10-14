package com.example.classroom.screen.class_list

data class ClassDetail(val id: String, val className: String)

var ClassMap: MutableMap<String, ClassDetail> = mutableMapOf(
    "MA006.O111" to ClassDetail(id = "MA006.O111", className = "Toan dai cuong"),
    "SS006.P19" to ClassDetail(id = "SS006.P19", className = "Phap luat dai cuong"),
    "SS004.P14" to ClassDetail(id = "SS004.P14", className = "Ky nang nghe nghiep")
)