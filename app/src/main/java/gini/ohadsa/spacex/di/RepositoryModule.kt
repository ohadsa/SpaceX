package gini.ohadsa.spacex.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gini.ohadsa.spacex.domain.repository.SpaceXRepository
import gini.ohadsa.spacex.domain.repository.SpaceXRepositoryImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    @Singleton
    abstract fun bindRepository(impl: SpaceXRepositoryImpl) : SpaceXRepository

}
