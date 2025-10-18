package com.sunildhiman90.cmplearnings

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform