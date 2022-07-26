package gini.ohadsa.spacex.database

import gini.ohadsa.spacex.domain.models.Launch
import gini.ohadsa.spacex.domain.models.LaunchWithShips
import gini.ohadsa.spacex.domain.models.Ship
import gini.ohadsa.spacex.domain.models.ShipWithLaunches
import kotlinx.coroutines.flow.Flow


interface DataSource {

    //ships:
    suspend fun insertShip(ship: Ship)
    suspend fun deleteAllShips()
    suspend fun getShipById(id: String): Ship?
    suspend fun getAllShips(): List<Ship>
    suspend fun insertShipsWithLaunches(ships: List<Ship>)

    //launches:
    suspend fun insertLaunch(launch: Launch)
    suspend fun getAllLaunches(): List<Launch>
    suspend fun getLaunchById(id: String): Launch?
    suspend fun deleteAllLaunches()
    suspend fun insertLaunchesWithShips(launches: List<Launch>)

    //cross
    suspend fun getShipWithLaunches(): List<ShipWithLaunches>
    suspend fun getLaunchWithShips(): List<LaunchWithShips>
    suspend fun getLaunchWithShipsById(id:String): LaunchWithShips
    suspend fun getShipWithLaunchesById(id:String): ShipWithLaunches



}