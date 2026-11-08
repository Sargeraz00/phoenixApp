package com.sargedev.phoenixapp.core.session

import com.sargedev.phoenixapp.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSession @Inject constructor() {
    var userId: String? = null
    var gymId: String? = BuildConfig.DEFAULT_GYM_ID.ifEmpty { "gym-demo-001" }
}
