import java.io.*
import java.net.Socket
class Client(private val host: String, private val port: Int) {
    private lateinit var socket: Socket
    private lateinit var reader: BufferedReader
    private lateinit var writer: PrintWriter

    fun start() {
        socket = Socket(host, port)
        reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        writer = PrintWriter(socket.getOutputStream(), true)

        var message = reader.readLine()
        while (message != null) {
            println(message)
            when (message) {
                "업로드할 파일 경로를 입력하세요." -> {
                    val filePaths = readlnOrNull() ?: ""
                    writer.println(filePaths)
                    val filePathList = filePaths.split(",")
                    var fileEx = true
                    for (filePath in filePathList) {
                        val file = File(filePath)
                        if (file.exists()) {
                            fileEx = true
                            writer.println(fileEx)
                            val fileInputStream = FileInputStream(file)
                            val buffer = ByteArray(1024)
                            var bytesRead: Int
                            while (fileInputStream.read(buffer).also { bytesRead = it } > 0) {
                                socket.getOutputStream().write(buffer, 0, bytesRead)
                            }
                            fileInputStream.close()
                        } else {
                            fileEx = false
                            writer.println(fileEx)
                            println("$filePath 파일이 존재하지 않습니다.")
                        }
                        if(fileEx){
                            val fileUploaded = reader.readLine()
                            println(fileUploaded)
                        }
                    }
                }
                "Goodbye" ->{
                    break
                }
                else ->{
                    val answer = readlnOrNull()
                    writer.println(answer)
                }
            }
            message = reader.readLine()
        }
    }
}