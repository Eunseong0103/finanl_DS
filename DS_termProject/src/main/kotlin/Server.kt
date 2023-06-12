//import java.io.*
//import java.net.ServerSocket
//import java.net.Socket
//
//fun main() {
//    val server = Server(9999)
//    server.start()
//}
//class Server(private val port: Int) {
//    private val serverSocket = ServerSocket(port)
//    private val clients = mutableListOf<ClientHandler>()
//
//    fun start() {
//        println("Server started on port $port")
//        while (true) {
//            val socket = serverSocket.accept()
//            val clientHandler = ClientHandler(socket)
//            clients.add(clientHandler)
//            clientHandler.start()
//        }
//    }
//
//    inner class ClientHandler(private val socket: Socket) : Thread() {
//        private val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
//        private val writer = PrintWriter(socket.getOutputStream(), true)
//
//        override fun run() {
//            var username = ""
//            while (username.isEmpty()) {
//                writer.println("username을 입력하세요.")
//                username = reader.readLine()
//            }
//            println("$username has joined the server")
//
//            var choice = ""
//            while (choice != "2") {
//                writer.println("동작을 선택하세요 1.파일 전송 2.접속 종료")
//                choice = reader.readLine()
//                when (choice) {
//                    "1" -> {
//                        writer.println("업로드할 파일 경로를 입력하세요.")
//                        val filePaths = reader.readLine().split(",")
//                        for (filePath in filePaths) {
//                            val file = File(filePath)
//                            if (file.exists()) {
//                                val fileInputStream = FileInputStream(file)
//                                val buffer = ByteArray(1024)
//                                var bytesRead: Int
//                                val fileOutputStream = FileOutputStream(file.name)
//                                while (fileInputStream.read(buffer).also { bytesRead = it } > 0) {
//                                    fileOutputStream.write(buffer, 0, bytesRead)
//                                }
//                                fileInputStream.close()
//                                fileOutputStream.close()
//                                println("$username 에게서 ${file.name} 파일을 받았습니다.")
//                                writer.println("${file.name} 파일을 업로드 했습니다.")
//                            }
//                        }
//                    }
//
//                    "2" -> {
//                        writer.println("Goodbye")
//                        println("$username has left the server")
//                        socket.close()
//                        clients.remove(this)
//                    }
//                }
//            }
//        }
//    }
//}