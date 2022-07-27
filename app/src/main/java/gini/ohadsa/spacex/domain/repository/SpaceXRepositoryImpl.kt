package gini.ohadsa.spacex.domain.repository

import gini.ohadsa.spacex.database.DataSource
import gini.ohadsa.spacex.domain.models.LaunchWithShips
import gini.ohadsa.spacex.domain.models.ShipWithLaunches
import gini.ohadsa.spacex.network.SpaceXApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SpaceXRepositoryImpl @Inject constructor(
    private val spaceXApi: SpaceXApi,
    private val dataSource: DataSource,
) : SpaceXRepository {


    override fun getAllShips(): Flow<List<ShipWithLaunches>> =
        flow {
            emit(
                dataSource.getShipWithLaunches().ifEmpty {
                    dataSource.insertShipsWithLaunches(spaceXApi.allShips())
                    return@ifEmpty dataSource.getShipWithLaunches()
                }
            )
        }

    override fun getAllLaunches(type: String): Flow<List<LaunchWithShips>> =
        flow {
            emit(
                dataSource.getLaunchWithShips(type).ifEmpty {
                    dataSource.insertLaunchesWithShips(spaceXApi.allLaunches())
                    return@ifEmpty dataSource.getLaunchWithShips(type)
                }
            )
        }

    override suspend fun getShipById(id: String): ShipWithLaunches =
        dataSource.getShipWithLaunchesById(id)

    override suspend fun getLaunchesById(id: String): LaunchWithShips =
        dataSource.getLaunchWithShipsById(id)


    override suspend fun updateData() {
        updateShipsData()
        updateLaunchesData()
    }

    private suspend fun updateShipsData() {
        dataSource.insertShipsWithLaunches(spaceXApi.allShips())
    }

    private suspend fun updateLaunchesData() {
        dataSource.insertLaunchesWithShips(spaceXApi.allLaunches())
    }
}