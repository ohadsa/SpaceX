package gini.ohadsa.spacex.domain.usecases.launches

import gini.ohadsa.spacex.domain.models.LaunchWithShips
import kotlinx.coroutines.flow.Flow

interface GetAllLaunchesUseCase  {
    fun get(sortType: String): Flow<List<LaunchWithShips>>
}