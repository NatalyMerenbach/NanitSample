package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.models.BirthdayData
import data.websocket.ConnectionStatus
import data.websocket.WebSocketClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BabyBirthdayViewModel : ViewModel() {
    private val webSocketClient = WebSocketClient()

    private val _birthdayData = MutableStateFlow<BirthdayData?>(null)
    val state: StateFlow<BirthdayData?> = _birthdayData.asStateFlow()

    private val _connectionStatus = MutableStateFlow(ConnectionStatus.DISCONNECTED)
    val connectionState: StateFlow<ConnectionStatus> = _connectionStatus.asStateFlow()

    init {
        viewModelScope.launch {
            webSocketClient.birthdayDataFlow.collect { data ->
                _birthdayData.value = data
            }
        }

        viewModelScope.launch {
            webSocketClient.connectionStatusFlow.collect { status ->
                _connectionStatus.value = status
            }
        }
    }

    fun connectToServer(serverUrl: String) {
        _connectionStatus.value = ConnectionStatus.CONNECTING
        _birthdayData.value = null
        webSocketClient.connect(serverUrl)
    }

    fun disconnect() {
        webSocketClient.disconnect()
        _birthdayData.value = null
        _connectionStatus.value = ConnectionStatus.DISCONNECTED
    }

    override fun onCleared() {
        super.onCleared()
        webSocketClient.disconnect()
    }
}