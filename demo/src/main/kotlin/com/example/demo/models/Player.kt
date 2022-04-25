package com.example.demo.domain

import javax.persistence.*

@Entity
class Player (var name: String){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    /*@OneToMany(mappedBy = "player", cascade = [(CascadeType.ALL)])
    private lateinit var game: Game*/


    constructor() : this("name")

}