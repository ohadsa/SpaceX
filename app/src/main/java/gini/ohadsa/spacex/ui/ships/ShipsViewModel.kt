package gini.ohadsa.spacex.ui.ships

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gini.ohadsa.spacex.domain.models.Launch
import gini.ohadsa.spacex.domain.models.Ship
import gini.ohadsa.spacex.domain.models.ShipWithLaunches
import gini.ohadsa.spacex.domain.repository.SpaceXRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ShipsViewModel @Inject constructor(
    private val repository : SpaceXRepository
) : ViewModel() {

    val ships : Flow<List<ShipWithLaunches>> = repository.getAllShips()

}