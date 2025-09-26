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
    modifier: Modifier = Modifier
) {
    var serverUrl by remember { mutableStateOf("10.0.0.6:8080") }
    var selectedPhotoUri by remember { mutableStateOf<Uri?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedPhotoUri = uri
    }

    if (birthdayData != null) {
        BirthdayScreen(
            birthdayData = birthdayData,
            selectedPhotoUri = selectedPhotoUri,
            onPhotoClick = { photoPickerLauncher.launch("image/*") }
        )
    } else {
        ConnectionScreen(
            serverUrl = serverUrl,
            onServerUrlChange = { serverUrl = it },
            connectionStatus = connectionStatus,
            onConnect = { onConnect(serverUrl) }
        )
    }
}


