package gini.ohadsa.spacex.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gini.ohadsa.spacex.domain.repository.SpaceXRepository
import gini.ohadsa.spacex.domain.repository.SpaceXRepositoryImpl
import gini.ohadsa.spacex.domain.usecases.launches.GetAllLaunchesUseCase
import gini.ohadsa.spacex.domain.usecases.launches.GetAllLaunchesUseCaseImpl
import gini.ohadsa.spacex.domain.usecases.launches.GetLaunchByIdUseCase
import gini.ohadsa.spacex.domain.usecases.launches.GetLaunchByIdUseCaseImpl
import gini.ohadsa.spacex.domain.usecases.ships.GetAllShipsUseCase
import gini.ohadsa.spacex.domain.usecases.ships.GetAllShipsUseCaseImpl
import gini.ohadsa.spacex.domain.usecases.ships.GetShipByIdUseCase
import gini.ohadsa.spacex.domain.usecases.ships.GetShipByIdUseCaseImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    @Singleton
    abstract fun bindRepository(impl: SpaceXRepositoryImpl) : SpaceXRepository


    @Binds
    @Singleton
    abstract fun bindGetAllLaunchesUseCase(impl : GetAllLaunchesUseCaseImpl) : GetAllLaunchesUseCase


    @Binds
    @Singleton
    abstract fun bindGetLaunchByIdUseCase(impl : GetLaunchByIdUseCaseImpl) : GetLaunchByIdUseCase

    @Binds
    @Singleton
    abstract fun bindGetAllShipsUseCase(impl : GetAllShipsUseCaseImpl) : GetAllShipsUseCase

    @Binds
    @Singleton
    abstract fun bindGetShipByIdUseCase(impl : GetShipByIdUseCaseImpl) : GetShipByIdUseCase

}
