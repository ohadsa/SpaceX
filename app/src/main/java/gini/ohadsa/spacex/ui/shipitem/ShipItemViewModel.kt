package gini.ohadsa.spacex.ui.shipitem

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gini.ohadsa.spacex.domain.models.LaunchWithShips
import gini.ohadsa.spacex.domain.models.Ship
import gini.ohadsa.spacex.domain.models.ShipWithLaunches
import gini.ohadsa.spacex.domain.usecases.launches.GetLaunchByIdUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class ShipItemViewModel @Inject constructor(
    private val getLaunchById: GetLaunchByIdUseCase
) : ViewModel() {

    var shipFlow: MutableSharedFlow<Ship> = MutableSharedFlow(1, 1)
    var launchesFlow: MutableSharedFlow<LaunchWithShips> = MutableSharedFlow(1, 1)

    suspend fun setItem(item: ShipWithLaunches) {
        shipFlow.tryEmit(item.ship)
        item.launches.forEach { launch->
            launchesFlow.tryEmit(getLaunchById.get(launch.launchId))
        }
    }

}