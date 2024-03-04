package ru.smak.chat

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlin.concurrent.thread
import kotlin.random.Random

class Client(host: String = "localhost", port: Int = 5104) {

    private var stop = false
    private val socket = Socket(host, port)

    fun start() {
        doMainRoutine()
        Thread.sleep(Random.nextLong(2000, 5000))
        send("Привет, всем!")
        Thread.sleep(Random.nextLong(2000, 5000))
        send("Как у вас дела, Все?")
        Thread.sleep(Random.nextLong(2000, 5000))
        send("У меня, вот, хорошо!")
    }

    private fun doMainRoutine(){
        thread {
            try {
                val br = BufferedReader(InputStreamReader(socket.getInputStream()))
                while (!stop) {
                    val data = br.readLine()
                    process(data)
                }
            } catch (_: Throwable){
                println("Ошибка взаимодействия")
            } finally {
                socket.close()
            }
        }
    }

    private fun process(data: String?) = data?.let{ println(it) }

    fun send(data: String) = PrintWriter(socket.getOutputStream()).apply {
        println(data)
        flush()
    }

}