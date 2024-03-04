package ru.smak.chat.view

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.concurrent.thread

class ConsoleView{

    private var stop = false

    fun start(){
        stop = false
        doMainRoutine()
    }

    private fun doMainRoutine(){
        thread {
            try {
                val br = BufferedReader(InputStreamReader(System.`in`))
                while (!stop) {
                    val data = br.readLine()
                    process(data)
                }
            } catch (_: Throwable){
                println("Ошибка чтения с клавиатуры")
            }
        }
    }

    fun showMessage(msg: String){
        println(msg)
    }
}