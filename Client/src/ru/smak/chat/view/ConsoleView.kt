package ru.smak.chat.view

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.concurrent.thread

class ConsoleView{

    private var stop = false

    private val gotUserDataListeners = mutableListOf<(String)->Unit>()
    fun addGotUserDataListener(l: (String)->Unit) = gotUserDataListeners.add(l)
    fun removeGotUserDataListener(l: (String)->Unit) = gotUserDataListeners.remove(l)

    private val stopListeners = mutableListOf<()->Unit>()
    fun addStopListener(l: ()->Unit) = stopListeners.add(l)
    fun removeStopListener(l: ()->Unit) = stopListeners.remove(l)

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
                    gotUserDataListeners.forEach { it(data) }
                    if ( data.trim().lowercase() == "bye" ){
                        stop=true
                        stopListeners.forEach { it() }
                    }
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