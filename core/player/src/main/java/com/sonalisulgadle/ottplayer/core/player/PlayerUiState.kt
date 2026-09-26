package com.sonalisulgadle.ottplayer.core.player

/**
 * Immutable snapshot of everything the playback UI needs to render itself.
 *
 * This is a plain Kotlin data class with no Android dependencies, so it can be
 * asserted against in a pure JVM unit test.
 */
data class PlayerUiState(
    /** True while media is actively rendering frames (not paused, not buffering). */
    val isPlaying: Boolean = false,
    /** True while the player is stalled loading media (Player.STATE_BUFFERING). */
    val isBuffering: Boolean = false,
)
