package com.sonalisulgadle.ottplayer.player

import androidx.lifecycle.ViewModel
import com.sonalisulgadle.ottplayer.core.player.PlayerController
import com.sonalisulgadle.ottplayer.core.player.PlayerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Plain [ViewModel] — no Application, no Context. It holds a [PlayerController] handed to
 * it by Hilt and does nothing but expose its state and forward commands. All the Android
 * machinery (ExoPlayer, Context) lives behind the interface, in :core:player.
 */
@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val controller: PlayerController,
) : ViewModel() {

    val uiState: StateFlow<PlayerUiState> = controller.state

    fun play(url: String) = controller.play(url)

    fun pause() = controller.pause()

    override fun onCleared() {
        // Same guarantee as before: fires when the ViewModel is permanently destroyed
        // (not on config change). We delegate the actual resource teardown to the
        // controller, which owns the ExoPlayer.
        controller.release()
        super.onCleared()
    }
}
