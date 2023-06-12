//import java.io.BufferedReader
//import java.io.InputStreamReader
//import java.io.PrintWriter
//import java.net.Socket
//
//class Client(serverAddress: String, port: Int) {
//    private val socket = Socket(serverAddress, port)
//    private val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
//    private val writer = PrintWriter(socket.getOutputStream(), true)
//
//    fun start() {
//        val messageListener = MessageListener()
//        messageListener.start()
//
//        while (true) {
//            val input = readLine()
//            if (input != null) {
//                writer.println(input)
//            }
//        }
//    }
//
//    inner class MessageListener : Thread() {
//        override fun run() {
//            while (true) {
//                val message = reader.readLine()
//                if (message == null) {
//                    break
//                }
//                println(message)
//            }
//        }
//    }
//}
//
//fun main() {
//    val client = Client("localhost", 9999)
//    client.start()
//}