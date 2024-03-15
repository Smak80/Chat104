package ru.smak.chat

import ru.smak.chat.net.Client
import ru.smak.chat.view.ConsoleView

fun main() {

    val cv = ConsoleView()
    cv.start()
    try {
        val cl = Client()
        cv.addGotUserDataListener(cl::send)
        cv.addStopListener(cl::stop)
        cl.addGotServerDataListener(cv::showMessage)
        cl.start()
    } catch (_: Throwable){
        println("Ошибка подключения к серверу :(")
    }

}