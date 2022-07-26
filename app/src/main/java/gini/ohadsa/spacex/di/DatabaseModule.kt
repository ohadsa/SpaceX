package gini.ohadsa.spacex.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gini.ohadsa.spacex.database.DataSource
import gini.ohadsa.spacex.database.DataSourceImpl
import gini.ohadsa.spacex.database.converters.LinksConverter
import gini.ohadsa.spacex.database.SpaceXDatabase
import gini.ohadsa.spacex.database.daos.LaunchesDao
import gini.ohadsa.spacex.database.daos.ShipsDao
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val DBName = "SpaceXDb"


    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): SpaceXDatabase =
        Room.databaseBuilder(appContext, SpaceXDatabase::class.java, DBName)
            .fallbackToDestructiveMigration()
            .addTypeConverter(LinksConverter())
            .build()

    @Provides
    fun provideShipsDao(spaceXDatabase: SpaceXDatabase): ShipsDao =
        spaceXDatabase.shipsDao()

    @Provides
    fun provideGenreDao(spaceXDatabase: SpaceXDatabase):LaunchesDao =
        spaceXDatabase.launchesDao()

}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceProvider{

    @Binds
    @Singleton
    abstract fun bindDataSource(impl: DataSourceImpl) : DataSource

}

