package com.sonalisulgadle.ottplayer.core.player

import kotlinx.coroutines.flow.StateFlow

/**
 * Playback abstraction the rest of the app depends on.
 *
 * The interface deliberately exposes only a state stream and a few verbs — it says
 * nothing about ExoPlayer or Media3. That's what lets a ViewModel depend on this type
 * and be tested against a trivial fake, with no real player and no Android runtime.
 */
interface PlayerController {

    /** Observable playback state, driven by the underlying player's callbacks. */
    val state: StateFlow<PlayerUiState>

    /** Load [url], prepare the pipeline, and start playing when ready. */
    fun play(url: String)

    /** Pause playback, keeping the loaded media and position. */
    fun pause()

    /** Release all underlying resources. The controller is unusable afterwards. */
    fun release()
}
