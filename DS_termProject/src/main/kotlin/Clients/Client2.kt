package Clients

import Client


fun main() {
    val client = Client("localhost", 9999)
    client.start()
}