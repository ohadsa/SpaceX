package gini.ohadsa.spacex.ui.shipitem

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gini.ohadsa.spacex.domain.models.Launch
import gini.ohadsa.spacex.domain.models.LaunchWithShips
import gini.ohadsa.spacex.domain.models.Ship
import gini.ohadsa.spacex.domain.models.ShipWithLaunches
import gini.ohadsa.spacex.domain.repository.SpaceXRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ShipItemViewModel @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel() {

    var shipFlow: MutableSharedFlow<Ship> = MutableSharedFlow(1, 1)
    var launchesFlow: MutableSharedFlow<LaunchWithShips> = MutableSharedFlow(1, 1)

    suspend fun setItem(item: ShipWithLaunches) {
        shipFlow.tryEmit(item.ship)
        item.launches.forEach { launch->
            launchesFlow.tryEmit(repository.getLaunchesById(launch.launchId))
        }
    }

}