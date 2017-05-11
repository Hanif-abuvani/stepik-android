package org.stepic.droid.core.presenters

import org.stepic.droid.concurrency.MainHandler
import org.stepic.droid.core.presenters.contracts.VideoWithTimestampView
import org.stepic.droid.di.video.VideoScope
import org.stepic.droid.model.VideoTimestamp
import org.stepic.droid.storage.operations.DatabaseFacade
import java.util.concurrent.ThreadPoolExecutor
import javax.inject.Inject

@VideoScope
class VideoWithTimestampPresenter
@Inject constructor(
        private val databaseFacade: DatabaseFacade,
        private val mainHandler: MainHandler,
        private val threadPoolExecutor: ThreadPoolExecutor) : PresenterBase<VideoWithTimestampView>() {

    private var cachedTimestamp: Long? = null

    fun showVideoWithPredefinedTimestamp(videoId: Long?) {
        if (videoId == null) {
            view?.onNeedShowVideoWithTimestamp(null ?: 0L)
            return
        }

        if (cachedTimestamp != null) {
            view?.onNeedShowVideoWithTimestamp(cachedTimestamp ?: 0L)
            return
        }

        threadPoolExecutor.execute {
            val timestamp: Long? = databaseFacade.getVideoTimestamp(videoId)?.timestamp
            mainHandler.post {
                cachedTimestamp = timestamp
                view?.onNeedShowVideoWithTimestamp(timestamp ?: 0L)
            }
        }
    }

    fun saveMillis(currentTimeInMillis: Long, videoId: Long?) {
        if (videoId == null || currentTimeInMillis <= 0) return
        cachedTimestamp = currentTimeInMillis
        threadPoolExecutor.execute {
            databaseFacade.addTimestamp(VideoTimestamp(videoId, currentTimeInMillis))
        }
    }

}
