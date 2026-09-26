package com.sonalisulgadle.ottplayer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Hilt's entry point. @HiltAndroidApp triggers generation of the application-level
 * dependency container that every @AndroidEntryPoint / @HiltViewModel draws from.
 */
@HiltAndroidApp
class OTTPlayerApplication : Application()
