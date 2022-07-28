package gini.ohadsa.spacex.domain.usecases.launches

import gini.ohadsa.spacex.domain.models.LaunchWithShips
import gini.ohadsa.spacex.domain.repository.SpaceXRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllLaunchesUseCaseImpl @Inject constructor(
    private val repository : SpaceXRepository
) : GetAllLaunchesUseCase {
    override fun get(sortType: String): Flow<List<LaunchWithShips>> = repository.getAllLaunches(sortType)
}