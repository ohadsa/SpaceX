package gini.ohadsa.spacex.works

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import gini.ohadsa.spacex.domain.repository.SpaceXRepository
import kotlinx.coroutines.flow.take


@HiltWorker
class RefreshDataWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: SpaceXRepository,
) : CoroutineWorker(appContext, workerParams) {


    override suspend fun doWork(): Result {
        try {
            repository.updateData()
        } catch (e: Exception) {
            Result.retry()
        }
        return Result.success()
    }

    companion object {
        const val WORK_NAME = "gini.ohadsa.spacex.refreshDataWorker"

    }
}