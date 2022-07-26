package gini.ohadsa.spacex.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import gini.ohadsa.spacex.database.converters.LinksConverter
import gini.ohadsa.spacex.database.daos.LaunchesDao
import gini.ohadsa.spacex.database.daos.ShipsDao
import gini.ohadsa.spacex.domain.models.Launch
import gini.ohadsa.spacex.domain.models.Ship
import gini.ohadsa.spacex.domain.models.ShipLaunchCrossRef


@TypeConverters(LinksConverter::class)
@Database(entities = [ Ship::class, Launch::class , ShipLaunchCrossRef::class], version = 1)
abstract class SpaceXDatabase: RoomDatabase() {
    abstract fun shipsDao(): ShipsDao
    abstract fun launchesDao(): LaunchesDao

}

