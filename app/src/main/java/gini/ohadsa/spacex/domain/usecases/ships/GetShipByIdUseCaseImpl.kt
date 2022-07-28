package gini.ohadsa.spacex.domain.usecases.ships
import gini.ohadsa.spacex.domain.models.ShipWithLaunches
import gini.ohadsa.spacex.domain.repository.SpaceXRepository
import javax.inject.Inject

class GetShipByIdUseCaseImpl @Inject constructor(
    private val repository: SpaceXRepository
): GetShipByIdUseCase {
    override suspend fun get(id: String): ShipWithLaunches = repository.getShipById(id)
}