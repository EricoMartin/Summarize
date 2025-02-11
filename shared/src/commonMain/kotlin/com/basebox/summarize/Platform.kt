package com.basebox.summarize

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform