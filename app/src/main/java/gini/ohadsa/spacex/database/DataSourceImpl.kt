package gini.ohadsa.spacex.database

import gini.ohadsa.spacex.database.daos.LaunchesDao
import gini.ohadsa.spacex.database.daos.ShipsDao
import gini.ohadsa.spacex.domain.models.Launch
import gini.ohadsa.spacex.domain.models.LaunchWithShips
import gini.ohadsa.spacex.domain.models.Ship
import gini.ohadsa.spacex.domain.models.ShipWithLaunches
import javax.inject.Inject

//facade
class DataSourceImpl @Inject constructor(
    private val shipsDao: ShipsDao,
    private val launchesDao: LaunchesDao
) : DataSource {

    override suspend fun deleteAllShips() = shipsDao.deleteAllShips()

    override suspend fun insertShip(ship: Ship) = shipsDao.insertShip(ship)

    override suspend fun getShipById(id: String): Ship? = shipsDao.getShipById(id)

    override suspend fun getAllShips(): List<Ship> = shipsDao.getAllShips()

    override suspend fun insertShipsWithLaunches(ships: List<Ship>) =
        shipsDao.insertShipsWithLaunches(ships)

    override suspend fun insertLaunch(launch: Launch) = launchesDao.insertLaunch(launch)

    override suspend fun getAllLaunches(): List<Launch> = launchesDao.getAllLaunches()

    override suspend fun getLaunchById(id: String): Launch? = launchesDao.getLaunchById(id)

    override suspend fun deleteAllLaunches() = launchesDao.deleteAllLaunches()

    override suspend fun insertLaunchesWithShips(launches: List<Launch>) =
        launchesDao.insertLaunchesWithShips(launches)

    override suspend fun getShipWithLaunches(): List<ShipWithLaunches> =
        shipsDao.getShipWithLaunches()

    override suspend fun getLaunchWithShips(): List<LaunchWithShips> =
        launchesDao.getLaunchWithShips()

    override suspend fun getLaunchWithShipsById(id: String): LaunchWithShips = launchesDao.getLaunchWithShipsById(id)

    override suspend fun getShipWithLaunchesById(id: String): ShipWithLaunches = shipsDao.getShipWithLaunchesById(id)

}