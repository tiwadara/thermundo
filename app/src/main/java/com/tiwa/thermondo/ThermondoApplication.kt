package com.tiwa.thermondo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 *
 * This is the Application class with Hilt Annotator setting the Application scope
 *
 * **/

@HiltAndroidApp
class ThermondoApplication : Application()