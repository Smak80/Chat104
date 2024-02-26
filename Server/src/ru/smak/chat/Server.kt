package ru.smak.chat

import java.net.ServerSocket

class Server(port: Int) {
    private val serverSocket = ServerSocket(port)
    private var stop: Boolean = false

    fun start(){
        stop = false
        try {
            while (!stop) {
                val socket = serverSocket.accept()
            }
        } catch (e: Throwable){
            println("Произошла непредвиденная ошибка: ${e.message}")
        }
        finally {
            serverSocket.close()
        }
    }

    fun stop(){
        stop = true
    }
}