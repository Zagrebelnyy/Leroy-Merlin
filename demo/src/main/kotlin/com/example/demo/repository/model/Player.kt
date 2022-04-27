package com.example.demo.domain

import javax.persistence.*

@Entity
data class Player (var name: String){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    constructor() : this("name")
}