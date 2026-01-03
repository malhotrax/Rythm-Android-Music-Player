package com.player.presentation.player.repository.impl

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import com.player.domain.model.Music
import com.player.presentation.player.repository.PlaybackController
import com.player.presentation.player.service.MusicService
import com.player.presentation.util.Util
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PlaybackControllerImpl @Inject constructor(
    @ApplicationContext context: Context
) : PlaybackController {

    private val sessionToken =
        SessionToken(context, ComponentName(context, MusicService::class.java))
    private val controllerFuture = MediaController.Builder(
        context,
        sessionToken
    ).buildAsync()

    private val controller: MediaController
        get() = controllerFuture.get()

    override fun setPlaylist(songs: List<Music>) {
        controller.stop()
        controller.clearMediaItems()
        val mediaItems = songs.map { song ->
            MediaItem.Builder()
                .setMediaId(song.id.toString())
                .setUri(song.path)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(Util.truncateName(song.name))
                        .build()
                )
                .build()
        }
        controller.setMediaItems(mediaItems)
        controller.prepare()
    }

    override fun play() = controller.play()

    override fun pause() = controller.pause()

    override fun playAt(index: Int, position: Long) {
        if (index in 0 until controller.mediaItemCount) {
            controller.seekTo(index, position)
            controller.prepare()
            controller.play()
        }
    }

    override fun seekTo(position: Long) = controller.seekTo(position)

    override fun skipToNext() {
        if (controller.hasNextMediaItem()) {
            controller.seekToNext()
        } else {
            controller.seekTo(0, 0L)
            controller.play()
        }
    }

    override fun skipToPrevious() {
        if (controller.hasPreviousMediaItem()) {
            controller.seekToPrevious()
        } else {
            val lastIndex = controller.mediaItemCount - 1
            controller.seekTo(lastIndex, 0L)
            controller.play()
        }
    }

    override fun addListener(listener: Player.Listener) {
        controllerFuture.addListener({
            controller.addListener(listener)
        }, MoreExecutors.directExecutor())
    }

    override fun removeListener(listener: Player.Listener) {
        if (controllerFuture.isDone) {
            controller.removeListener(listener)
        }
    }


    override fun getMediaCount(): Int {
        return controller.mediaItemCount
    }

    override fun isPlaying() = controller.isPlaying

    override fun getCurrentPosition() = controller.currentPosition

    override fun getDuration() = controller.duration
}