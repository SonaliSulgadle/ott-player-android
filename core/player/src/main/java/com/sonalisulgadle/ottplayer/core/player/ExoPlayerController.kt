package com.sonalisulgadle.ottplayer.core.player

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Media3-backed [PlayerController].
 *
 * The [ExoPlayer] is constructor-injected rather than built here, so this class owns no
 * Android/Context wiring — Hilt (see PlayerModule) is responsible for producing the
 * player. All this class does is translate [Player.Listener] callbacks into
 * [PlayerUiState] and forward the control verbs.
 */
class ExoPlayerController @Inject constructor(
    private val player: ExoPlayer,
) : PlayerController {

    private val _state = MutableStateFlow(PlayerUiState())
    override val state: StateFlow<PlayerUiState> = _state.asStateFlow()

    private val listener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _state.update { it.copy(isPlaying = isPlaying) }
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            _state.update {
                it.copy(isBuffering = playbackState == Player.STATE_BUFFERING)
            }
        }
    }

    init {
        player.addListener(listener)
    }

    override fun play(url: String) {
        player.setMediaItem(MediaItem.fromUri(url))
        player.prepare()
        player.playWhenReady = true
    }

    override fun pause() {
        player.pause()
    }

    override fun release() {
        player.removeListener(listener)
        player.release()
    }
}
