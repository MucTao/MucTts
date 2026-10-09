package org.muc.tts.app

import android.app.Application
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.speech.tts.UtteranceProgressListener
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import java.util.Locale
import java.util.Random

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val onInitListener = OnInitListener { status -> ttsReady = status != TextToSpeech.ERROR }

    private val progressListener = object : UtteranceProgressListener() {
        override fun onStart(utteranceId: String?) {
            isSpeak = true
        }

        override fun onDone(utteranceId: String?) {
            isSpeak = false
        }

        override fun onError(utteranceId: String?) {
            isSpeak = false
        }

        override fun onStop(utteranceId: String?, interrupted: Boolean) {
            isSpeak = !interrupted
            super.onStop(utteranceId, interrupted)
        }

    }

    val tts = TextToSpeech(application, onInitListener, "org.muc.tts")

    val ttsReadyState: MutableState<Boolean> = mutableStateOf(false)
    var ttsReady: Boolean
        get() = ttsReadyState.value
        set(value) {
            ttsReadyState.value = value
        }

    val speakState: MutableState<Boolean> = mutableStateOf(false)
    private var isSpeak: Boolean
        get() = speakState.value
        set(value) {
            speakState.value = value
        }

    val appVer: String = try {
        application.packageManager.getPackageInfo(application.packageName, 0).versionName ?: ""
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }

    init {
        tts.setOnUtteranceProgressListener(progressListener)

    }

    fun sayText(text: String) {
        val result = tts.setLanguage(Locale.CHINESE)
        if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, Random().nextInt().toString())
        }
    }

    fun stop() {
        tts.stop()
    }

    override fun onCleared() {
        tts.shutdown()
    }
}