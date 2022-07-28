package gini.ohadsa.spacex.domain.repository

import gini.ohadsa.spacex.domain.models.LaunchWithShips
import gini.ohadsa.spacex.domain.models.ShipWithLaunches
import kotlinx.coroutines.flow.Flow

interface SpaceXRepository {

    fun getAllShips(): Flow<List<ShipWithLaunches>>
    fun getAllLaunches(type: String): Flow<List<LaunchWithShips>>
    suspend fun getShipById(id:String): ShipWithLaunches
    suspend fun getLaunchesById(id:String): LaunchWithShips
    suspend fun updateData()

}