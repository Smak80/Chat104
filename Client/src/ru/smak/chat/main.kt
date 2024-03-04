package ru.smak.chat

import ru.smak.chat.net.Client

fun main() {
    try {
        Client().start()
    } catch (_: Throwable){
        println("Ошибка подключения к серверу :(")
    }
}