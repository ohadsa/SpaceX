package gini.ohadsa.spacex.ui.ships

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import gini.ohadsa.spacex.domain.models.Launch
import gini.ohadsa.spacex.domain.models.Ship
import gini.ohadsa.spacex.domain.models.ShipWithLaunches
import gini.ohadsa.spacex.domain.repository.SpaceXRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ShipsViewModel @Inject constructor(
    repository: SpaceXRepository,

    ) : ViewModel() {

    private var currentQuery: MutableStateFlow<String> = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val ships: Flow<List<ShipWithLaunches>> = combine(
        currentQuery,
        repository.getAllShips()
    ) { q, m -> QueryShip(q, m) }.flatMapLatest {queryShip->
        flow{
            emit(queryShip.Ship.mapNotNull {
                if(it.ship.name?.lowercase()?.contains(queryShip.query , ignoreCase = true) == true ) it else null
            })
        }
    }


    fun updateQuery(query: String) {
        currentQuery.value = query
    }



}

data class QueryShip(
    val query: String,
    val Ship: List<ShipWithLaunches> //true-Movie , false - TV Show
)
