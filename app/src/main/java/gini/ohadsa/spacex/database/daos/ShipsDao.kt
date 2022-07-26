package gini.ohadsa.spacex.database.daos

import androidx.room.*
import gini.ohadsa.spacex.domain.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


@Dao
interface ShipsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShip(ship: Ship)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShips(ships: List<Ship>)

    @Query("SELECT * FROM Ship WHERE shipId LIKE :id ")
    suspend fun getShipById(id: String): Ship?

    @Query("DELETE FROM Ship")
    suspend fun deleteAllShips()

    @Query("SELECT * FROM Ship")
    suspend fun getAllShips(): List<Ship>

    @Transaction
    suspend fun insertShipsWithLaunches(ships: List<Ship>){
        ships.forEach{ ship ->
            ship.launchIds?.forEach { launchId->
                insertShipLaunchCrossRef(ShipLaunchCrossRef(ship.shipId , launchId ))
            }
        }
        insertShips(ships)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShipLaunchCrossRef(shipLaunchCrossRef: ShipLaunchCrossRef)


    @Transaction
    @Query("SELECT * FROM Ship")
    suspend fun getShipWithLaunches(): List<ShipWithLaunches>


    @Transaction
    @Query("SELECT * FROM Ship WHERE shipId LIKE :id ")
    suspend fun getShipWithLaunchesById(id: String): ShipWithLaunches
}



