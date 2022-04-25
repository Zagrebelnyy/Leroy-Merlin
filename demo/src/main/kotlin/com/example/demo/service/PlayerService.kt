package com.example.demo.service

import com.example.demo.domain.TablePoint
import org.springframework.stereotype.Service


interface PlayerService {
    fun hit(): TablePoint
}