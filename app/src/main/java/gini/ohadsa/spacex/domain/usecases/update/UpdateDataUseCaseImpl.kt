package gini.ohadsa.spacex.domain.usecases.update
import gini.ohadsa.spacex.domain.repository.SpaceXRepository
import javax.inject.Inject

class UpdateDataUseCaseImpl  @Inject constructor(
    private val repository : SpaceXRepository
) : UpdateDataUseCase {

    override suspend fun update() = repository.updateData()
}