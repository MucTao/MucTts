package org.muc.tts.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.muc.tts.app.ui.SpeechScreen
import org.muc.tts.app.ui.theme.MucTtsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MucTtsTheme {
                val mainVM: MainViewModel by viewModels<MainViewModel>()
                Surface(color = MaterialTheme.colorScheme.background) {
                    Scaffold(
                        topBar = { MainTopAppBar(mainVM.appVer) },
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding).fillMaxSize(), Alignment.Center) {
                            SpeechScreen(mainVM)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(appVer: String) {
    TopAppBar(
        title = {
            Text(text = "${stringResource(R.string.app_name)} $appVer", maxLines = 2)
        },
        modifier = Modifier.fillMaxWidth(),
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MucTtsTheme {
        MainTopAppBar("Android")
    }
}