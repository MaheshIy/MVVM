package com.mvvm.kotlin.model

data class Result(val gender: String,
                  var name: Name,
                  var location: Location,
                  val email: String,
                  val login: Login,
                  var dob: DateOfBirth,
                  val registered: Registered,
                  val phone: String,
                  val cell: String,
                  val id: Id,
                  var status: Int = 0,
                  var picture: Picture,
                  val nat: String)

data class MatchesResponse(
    var results : List<Result>
)

data class Name(
    val title: String,
    var first: String,
    val last: String
)

data class Location(
    val street: Street,
    var city: String,
    val state: String,
    val country: String,
    val postcode: String,
    val coordinates: Coordinates,
    val timezone: Timezone
)

data class Street(
    val number: Int,
    val name: String
)

data class Coordinates(
    val latitude: String,
    val longitude: String
)

data class Timezone(
    val offset: String,
    val description: String
)

data class Login(
    val uuid: String,
    val username: String,
    val password: String,
    val salt: String,
    val md5: String,
    val sha1: String,
    val sha256: String
)

data class DateOfBirth (
    val date: String,
    var age: Int
)

data class Registered (
    val date: String,
    val age: Int
)

data class Id (
    val name: String,
    val value: String
)

data class Picture (
    var large: String,
    val medium: String,
    val thumbnail: String
)



















