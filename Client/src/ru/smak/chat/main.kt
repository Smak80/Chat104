package ru.smak.chat

fun main() {
    try {
        Client().start()
    } catch (_: Throwable){
        println("Ошибка подключения к серверу :(")
    }
}