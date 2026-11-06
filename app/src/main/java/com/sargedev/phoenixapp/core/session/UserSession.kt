package com.sargedev.phoenixapp.core.session

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSession @Inject constructor() {
    var userId: String? = null
}
