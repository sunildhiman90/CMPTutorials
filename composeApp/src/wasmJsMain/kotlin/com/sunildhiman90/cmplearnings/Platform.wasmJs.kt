package com.sunildhiman90.cmplearnings

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"


}

actual fun getPlatform(): Platform = WasmPlatform()

