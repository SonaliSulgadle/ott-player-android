package com.sonalisulgadle.ottplayer.player

import com.sonalisulgadle.ottplayer.core.player.PlayerController
import com.sonalisulgadle.ottplayer.core.player.PlayerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pure JVM test — no Robolectric, no emulator, no ExoPlayer. Possible only because
 * [PlayerViewModel] depends on the [PlayerController] interface, which we can fake here.
 */
class PlayerViewModelTest {

    private class FakePlayerController : PlayerController {
        private val _state = MutableStateFlow(PlayerUiState())
        override val state: StateFlow<PlayerUiState> = _state.asStateFlow()

        var lastPlayedUrl: String? = null
        var pauseCount = 0
        var released = false

        override fun play(url: String) {
            lastPlayedUrl = url
            _state.value = PlayerUiState(isPlaying = true, isBuffering = false)
        }

        override fun pause() {
            pauseCount++
            _state.value = _state.value.copy(isPlaying = false)
        }

        override fun release() {
            released = true
        }
    }

    @Test
    fun `play forwards url and surfaces playing state`() {
        val fake = FakePlayerController()
        val viewModel = PlayerViewModel(fake)

        viewModel.play("https://example.com/stream.m3u8")

        assertEquals("https://example.com/stream.m3u8", fake.lastPlayedUrl)
        assertTrue(viewModel.uiState.value.isPlaying)
    }

    @Test
    fun `onCleared releases the controller`() {
        val fake = FakePlayerController()
        val viewModel = PlayerViewModel(fake)

        // onCleared is protected; expose it via a tiny reflection-free subclass helper.
        viewModel.callOnClearedForTest()

        assertTrue(fake.released)
    }
}

/** onCleared() is protected in ViewModel; this keeps the test in pure JVM land. */
private fun PlayerViewModel.callOnClearedForTest() {
    val method = androidx.lifecycle.ViewModel::class.java.getDeclaredMethod("onCleared")
    method.isAccessible = true
    method.invoke(this)
}
