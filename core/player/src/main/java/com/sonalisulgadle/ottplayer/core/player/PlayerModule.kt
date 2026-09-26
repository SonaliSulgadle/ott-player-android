package com.sonalisulgadle.ottplayer.core.player

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Supplies the concrete ExoPlayer and binds it behind [PlayerController].
 *
 * Both are intentionally *unscoped* (no @Singleton): Hilt creates a fresh ExoPlayer and
 * a fresh [ExoPlayerController] for each PlayerViewModel that asks for one. That matches
 * the player's lifetime to the ViewModel — the ViewModel releases it in onCleared() and
 * the next screen gets a clean instance, instead of reusing an already-released singleton.
 */
@Module
@InstallIn(SingletonComponent::class)
object PlayerModule {

    @Provides
    fun provideExoPlayer(@ApplicationContext context: Context): ExoPlayer =
        ExoPlayer.Builder(context).build()
}

/**
 * Binds the interface to its implementation. Kept separate from [PlayerModule] because
 * @Binds lives in an abstract class while @Provides lives in an object.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class PlayerBindsModule {

    @Binds
    abstract fun bindPlayerController(impl: ExoPlayerController): PlayerController
}
