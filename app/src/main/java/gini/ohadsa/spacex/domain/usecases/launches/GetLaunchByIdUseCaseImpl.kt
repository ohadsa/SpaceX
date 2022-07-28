package gini.ohadsa.spacex.domain.usecases.launches
import gini.ohadsa.spacex.domain.models.LaunchWithShips
import gini.ohadsa.spacex.domain.repository.SpaceXRepository
import javax.inject.Inject

class GetLaunchByIdUseCaseImpl @Inject constructor(
    private val repository: SpaceXRepository
): GetLaunchByIdUseCase {
    override suspend fun get(id: String): LaunchWithShips = repository.getLaunchesById(id)
}