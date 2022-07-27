package gini.ohadsa.spacex.database.daos

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import gini.ohadsa.spacex.domain.models.*
import kotlinx.coroutines.flow.Flow


@Dao
interface LaunchesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunch(launch: Launch)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunches(launches: List<Launch>)

    @Query("SELECT * FROM Launch")
    suspend fun getAllLaunches(): List<Launch>

    @Query("SELECT * FROM Launch WHERE launchId LIKE :id ")
    suspend fun getLaunchById(id:String): Launch?

    @Query("DELETE FROM Launch")
    suspend fun deleteAllLaunches()

    @Transaction
    suspend fun insertLaunchesWithShips(launches: List<Launch>){
        launches.forEach{ launch ->
            launch.shipIds?.forEach { shipId->
                insertShipLaunchCrossRef(ShipLaunchCrossRef(shipId, launch.launchId))
            }
        }
        insertLaunches(launches)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShipLaunchCrossRef(shipLaunchCrossRef: ShipLaunchCrossRef)

    @Transaction
    @RawQuery
    suspend fun getLaunchWithShips(sortType : SimpleSQLiteQuery): List<LaunchWithShips>


    @Transaction
    @Query("SELECT * FROM Launch WHERE launchId LIKE :id ")
    suspend fun getLaunchWithShipsById(id:String): LaunchWithShips



}



