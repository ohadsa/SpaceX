package gini.ohadsa.spacex.domain.usecases.ships
import gini.ohadsa.spacex.domain.models.ShipWithLaunches
import gini.ohadsa.spacex.domain.repository.SpaceXRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllShipsUseCaseImpl @Inject constructor(
    private val repository : SpaceXRepository
) : GetAllShipsUseCase {
    override fun get(): Flow<List<ShipWithLaunches>> = repository.getAllShips()
}