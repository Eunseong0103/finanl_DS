import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class Server(port: Int) {
    private val serverSocket = ServerSocket(port)
    private val userList = mutableListOf<User>()

    fun start() {
        println("서버 시작...")
        while (true) {
            val clientSocket = serverSocket.accept()
            println("클라이언트 접속: ${clientSocket.inetAddress.hostAddress}")
            val user = User(clientSocket)
            userList.add(user)
            user.start()
        }
    }

    inner class User(private val socket: Socket) : Thread() {
        private val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        private val writer = PrintWriter(socket.getOutputStream(), true)
         var username = ""

        override fun run() {
            requestUsername()
            while (true) {
                val message = reader.readLine()
                if (message == null) {
                    removeUser(this)
                    break
                }
                broadcastMessage("$username: $message")
            }
        }

        private fun requestUsername() {
            writer.println("사용자 이름을 입력해주세요.")
            username = reader.readLine()
//            while (isUsernameTaken(username)) {
//                writer.println("이미 사용 중인 사용자 이름입니다. 다른 사용자 이름을 입력해주세요.")
//                username = reader.readLine()
//            }
            writer.println("환영합니다, $username!")
            broadcastMessage("$username 님이 접속하셨습니다.", this)
        }

        private fun isUsernameTaken(username: String): Boolean {
            return userList.any { it.username == username }
        }

        fun sendMessage(message: String) {
            writer.println(message)
        }
    }

    private fun broadcastMessage(message: String, excludeUser: User? = null) {
        for (user in userList) {
            if (user != excludeUser) {
                user.sendMessage(message)
            }
        }
    }

    private fun removeUser(user: User) {
        userList.remove(user)
        broadcastMessage("${user.username} 님이 접속을 종료하셨습니다.")
    }
}

fun main() {
    val server = Server(9999)
    server.start()
}