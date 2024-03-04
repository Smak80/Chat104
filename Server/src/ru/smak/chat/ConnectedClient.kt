package ru.smak.chat

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlin.concurrent.thread

class ConnectedClient(private val socket: Socket) {

    companion object{
        val clients = mutableListOf<ConnectedClient>()
    }

    private var stop = false
    private val br = BufferedReader(InputStreamReader(socket.getInputStream()))
    private val pw = PrintWriter(socket.getOutputStream())

    val isAlive: Boolean
        get() = !stop && socket.isConnected

    init{
        clients.add(this)
        doMainRoutine()
    }

    private fun doMainRoutine(){
        thread {
            try {
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

    private fun process(data: String?) = data?.let{ sendToAll(it) }

    fun send(data: String) = pw.apply {
        println(data)
        flush()
    }

    private fun sendToAll(data: String) = clients.apply {
        removeIf { !it.isAlive }
        forEach {
            it.send(data)
        }
    }

    fun stop(){
        stop = true
    }

}