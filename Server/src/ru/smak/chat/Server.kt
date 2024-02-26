package ru.smak.chat

import java.net.ServerSocket
import kotlin.concurrent.thread

class Server(port: Int = 5104) {
    private val serverSocket = ServerSocket(port)
    private var stop: Boolean = false

    fun start(){
        stop = false
        thread {
            try {
                while (!stop) {
                    val socket = serverSocket.accept()
                    ConnectedClient(socket)
                }
            } catch (e: Throwable) {
                println("Произошла непредвиденная ошибка: ${e.message}")
            } finally {
                serverSocket.close()
            }
        }
    }

    fun stop(){
        stop = true
    }
}