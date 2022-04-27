package com.example.demo.domain

import java.text.SimpleDateFormat
import java.util.*
import javax.persistence.*

@Entity
class Game(
    val MAXIMUM_GAME_POINT: Int,

    @ManyToOne(cascade = [(CascadeType.ALL)])
    @JoinColumn(name = "first_player_id", referencedColumnName = "id")
    var playerOne: Player,

    @ManyToOne(cascade = [(CascadeType.ALL)])
    @JoinColumn(name = "second_player_id", referencedColumnName = "id")
    var playerTwo: Player,

    var date: String,

    var score: String
){
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    constructor() : this(0, Player(""), Player(""),
        SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date()), "0:0")
}
