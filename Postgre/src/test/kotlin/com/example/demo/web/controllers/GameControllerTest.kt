package com.example.demo.web.controllers

import com.example.demo.domain.Game
import com.example.demo.domain.Player
import com.example.demo.repository.GameRepository
import com.example.demo.repository.model.dto.NewGameDTO
import com.fasterxml.jackson.databind.ObjectMapper
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import junit.framework.Assert.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureEmbeddedDatabase(refresh = AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD)
internal class GameControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var gameRepository: GameRepository

    @BeforeEach
    fun setUp() {
        gameRepository.save(
            Game(
                11, Player("Bob"), Player("Michel"),
                "2022.20.05", "11:5"
            )
        )

        println("Games: ${gameRepository.findAll()}")
    }

    @Test
    fun create() {
        var newGame = NewGameDTO("NameOTest", "NameTTest", 22)

        mockMvc.perform(
            post("/game/play")
                .content(ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(newGame))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(2))

        assertTrue(gameRepository.findById(2).isPresent)


    }

    @Test
    fun show() {
        val id: Long = 1

        mockMvc.perform(get("/game/{id}", 1))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
    }

    @Test
    fun showGameByPlayer() {
        val player = "Michel"

        val id = player.toLongOrNull()

        val uri = "/game/show/{player}"

        if (id !== null) {
            mockMvc.perform(get(uri, id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
        } else {
            mockMvc.perform(get(uri, player))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
        }

    }
}