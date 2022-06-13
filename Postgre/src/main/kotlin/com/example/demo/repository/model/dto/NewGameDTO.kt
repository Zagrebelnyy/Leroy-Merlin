package com.example.demo.repository.model.dto

data class NewGameDTO(
    val nameOne: String,
    val nameTwo: String,
    val points: Int = -1,
    val ageOne: Int = -1,
    val ageTwo: Int = -1
)