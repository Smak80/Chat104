package ru.smak.chat.net

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlin.concurrent.thread
import kotlin.random.Random

class Client(host: String = "localhost", port: Int = 5104) {

    private var stop = false
    private val socket = Socket(host, port)

    private val gotServerDataListeners = mutableListOf<(String)->Unit>()
    fun addGotServerDataListener(l: (String)->Unit) = gotServerDataListeners.add(l)
    fun removeGotServerDataListener(l: (String)->Unit) = gotServerDataListeners.remove(l)

    fun start() {
        doMainRoutine()
    }
    fun stop() {
        stop = true
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

    private fun process(data: String?) = data?.let{ gotServerDataListeners.forEach { it(data) } }

    fun send(data: String) = PrintWriter(socket.getOutputStream()).apply {
        println(data)
        flush()
    }

}