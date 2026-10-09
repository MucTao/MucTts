package org.muc.tts.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.muc.tts.app.MainViewModel
import org.muc.tts.app.R
import org.muc.tts.app.gotoTtsSetting

@Composable
fun SpeechScreen(mainVM: MainViewModel) {
    val defText = stringResource(R.string.zho_sample)
    var speechText by remember { mutableStateOf(defText) }
    val ttsReady by mainVM.ttsReadyState //tts初始化状态
    val isSpeak by mainVM.speakState//是否正在朗读
    Column {
        OutlinedTextField(
            value = speechText,
            onValueChange = { speechText = it },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            maxLines = 3,
            enabled = ttsReady,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp, 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val context = LocalContext.current
            Button(onClick = { context.gotoTtsSetting() }) {
                Text(text = stringResource(R.string.goto_tts_setting))
            }
            Button(
                onClick = { if (isSpeak) mainVM.stop() else mainVM.sayText(speechText) },
                enabled = ttsReady
            ) {
                Text(text = if (isSpeak) stringResource(R.string.stop_speech) else stringResource(R.string.start_speech))
            }
        }
    }
}