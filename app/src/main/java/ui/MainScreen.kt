package ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.models.BirthdayData
import data.websocket.ConnectionStatus

@Composable
fun MainScreen(
    birthdayData: BirthdayData?,
    connectionStatus: ConnectionStatus,
    onConnect: (String) -> Unit,
    onDisconnect: () -> Unit,
) {
    //server uri state flow string for connect to websocket
    var serverUrl by remember { mutableStateOf("10.25.50.190:8080") }

    //selected photo uri state flow
    var selectedPhotoUri by remember { mutableStateOf<Uri?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedPhotoUri = uri
    }

    if (birthdayData != null) {
        //show BirthdayScreen
        BirthdayScreen(
            birthdayData = birthdayData,
            selectedPhotoUri = selectedPhotoUri,
            onPhotoClick = { photoPickerLauncher.launch("image/*") },
            onBackPress = { onDisconnect() }
        )
    } else {
        //show ConnectionScreen
        ConnectionScreen(
            serverUrl = serverUrl,
            onServerUrlChange = { serverUrl = it },
            connectionStatus = connectionStatus,
            onConnect = { onConnect(serverUrl) }
        )
    }
}


