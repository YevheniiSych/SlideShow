package com.test.slideshow.data.playlist.model

import com.test.slideshow.BuildConfig

data class PlaylistItem(
    val creativeRefKey: String?,
    val duration: Int,
    val expireDate: String,
    val startDate: String,
    val creativeLabel: String,
    val slidePriority: Int,
    val creativeKey: String,
    val playlistKey: String,
    val orderKey: Int,
    val localUri: String?
) {
    private val mediaResourceUrl get() = "${BuildConfig.BASE_URL}/PlayerBackend/creative/get/$creativeKey"

    val mediaResourceLink: String get() {
        return if (localUri.isNullOrEmpty()) {
            mediaResourceUrl
        } else {
            localUri
        }
    }

    val mediaType
        get() = when {
            creativeKey.endsWith(".mp4", ignoreCase = true) -> MediaType.Video

            creativeKey.endsWith(".jpg", ignoreCase = true)
                    || creativeKey.endsWith(".jpeg", ignoreCase = true)
                    || creativeKey.endsWith(".png", ignoreCase = true) -> MediaType.Image

            else -> MediaType.Image
        }
}

enum class MediaType {
    Image, Video
}