package gini.ohadsa.spacex.domain.usecases.launches
import gini.ohadsa.spacex.domain.models.LaunchWithShips

interface GetLaunchByIdUseCase {
    suspend fun get(id: String): LaunchWithShips
}