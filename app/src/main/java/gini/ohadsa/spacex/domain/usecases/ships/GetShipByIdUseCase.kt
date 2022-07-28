package gini.ohadsa.spacex.domain.usecases.ships
import gini.ohadsa.spacex.domain.models.ShipWithLaunches

interface GetShipByIdUseCase {
    suspend fun get(id: String): ShipWithLaunches
}