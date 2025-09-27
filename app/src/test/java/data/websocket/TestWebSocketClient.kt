package data.websocket

import com.google.gson.Gson
import data.models.BirthdayData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit

private const val HAPPY_BIRTHDAY = "HappyBirthday"

/**
 * Test-only WebSocketClient without Android Log dependencies
 */
class TestWebSocketClient {
    private val client = OkHttpClient.Builder()
        .readTimeout(3, TimeUnit.SECONDS)
        .writeTimeout(3, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()
    private var webSocket: WebSocket? = null
    private val _birthdayDataChannel = Channel<BirthdayData?>()
    val birthdayDataFlow: Flow<BirthdayData?> = _birthdayDataChannel.receiveAsFlow()

    private val _connectionStatusChannel = Channel<ConnectionStatus>()
    val connectionStatusFlow: Flow<ConnectionStatus> = _connectionStatusChannel.receiveAsFlow()

    fun connect(serverUrl: String) {
        val request = Request.Builder()
            .url("ws://$serverUrl/nanit")
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                println("WebSocket: Connection opened")
                _connectionStatusChannel.trySend(ConnectionStatus.CONNECTED)

                webSocket.send(HAPPY_BIRTHDAY)
                println("WebSocket: Sent: HappyBirthday")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                println("WebSocket: Received: $text")
                try {
                    val birthdayData = gson.fromJson(text, BirthdayData::class.java)
                    _birthdayDataChannel.trySend(birthdayData)
                } catch (e: Exception) {
                    println("WebSocket: Error parsing birthday data: ${e.message}")
                    _birthdayDataChannel.trySend(null)
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                println("WebSocket: Connection failed: ${t.message}")
                _connectionStatusChannel.trySend(ConnectionStatus.FAILED)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                println("WebSocket: Connection closing: $reason")
                _connectionStatusChannel.trySend(ConnectionStatus.DISCONNECTED)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                println("WebSocket: Connection closed: $reason")
                _connectionStatusChannel.trySend(ConnectionStatus.DISCONNECTED)
            }
        })
    }

    fun disconnect() {
        webSocket?.close(1000, "User disconnected")
        webSocket = null
    }
}