package gini.ohadsa.spacex.ui.launchitem

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gini.ohadsa.spacex.domain.models.Launch
import gini.ohadsa.spacex.domain.models.LaunchWithShips
import gini.ohadsa.spacex.domain.models.ShipWithLaunches
import gini.ohadsa.spacex.domain.usecases.ships.GetShipByIdUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class LaunchItemViewModel @Inject constructor(
    private val getShipByIdUseCase: GetShipByIdUseCase
) : ViewModel() {

    var launchFlow: MutableSharedFlow<Launch> = MutableSharedFlow(1,1)
    var shipFlow: MutableSharedFlow<ShipWithLaunches> = MutableSharedFlow(1,1)

    suspend fun setItem(item: LaunchWithShips) {
        launchFlow.tryEmit(item.launch)
        item.ships.forEach {
            shipFlow.tryEmit(getShipByIdUseCase.get(it.shipId))
        }
    }

}