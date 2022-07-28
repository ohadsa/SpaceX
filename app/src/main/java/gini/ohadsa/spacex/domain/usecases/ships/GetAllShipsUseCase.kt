package gini.ohadsa.spacex.domain.usecases.ships
import gini.ohadsa.spacex.domain.models.ShipWithLaunches
import kotlinx.coroutines.flow.Flow

interface GetAllShipsUseCase  {
    fun get(): Flow<List<ShipWithLaunches>>
}