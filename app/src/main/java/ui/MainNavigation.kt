package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import viewmodel.BabyBirthdayViewModel

@Composable
fun MainNavigation() {
    val viewModel: BabyBirthdayViewModel = viewModel()
    val birthdayData by viewModel.birthdayData.collectAsState()
    val connectionStatus by viewModel.connectionStatus.collectAsState()


    MainScreen(
        birthdayData = birthdayData,
        connectionStatus = connectionStatus,
        onConnect = { serverUrl ->
            viewModel.connectToServer(serverUrl)
        },
        onDisconnect = {
            viewModel.disconnect()
        }
    )
}
