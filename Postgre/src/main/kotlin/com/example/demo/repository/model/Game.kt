package com.example.demo.domain

import javax.persistence.*

@Entity
data class Game(
    val POINTS: Int,

    @ManyToOne(cascade = [(CascadeType.ALL)])
    @JoinColumn(name = "player_one", referencedColumnName = "id")
    var playerOne: Player,

    @ManyToOne(cascade = [(CascadeType.ALL)])
    @JoinColumn(name = "player_two", referencedColumnName = "id")
    var playerTwo: Player,

    var date: String,

    var score: String,


    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

}
