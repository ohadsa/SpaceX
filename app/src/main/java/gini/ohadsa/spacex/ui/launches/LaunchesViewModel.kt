package gini.ohadsa.spacex.ui.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gini.ohadsa.spacex.domain.models.Launch
import gini.ohadsa.spacex.domain.models.LaunchWithShips
import gini.ohadsa.spacex.domain.models.ShipWithLaunches
import gini.ohadsa.spacex.domain.repository.SpaceXRepository
import gini.ohadsa.spacex.ui.ships.QueryShip
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel() {

    private var currentQuery: MutableStateFlow<String> = MutableStateFlow("")
    private var currentSortType: MutableStateFlow<SortType> = MutableStateFlow(SortType.TITLE)

    @OptIn(ExperimentalCoroutinesApi::class)
    val launches: Flow<List<LaunchWithShips>> = combine(
        currentQuery,
        currentSortType,
        repository.getAllLaunches()
    ) { q, s, m -> QueryLaunch(q, s, m) }.flatMapLatest { queryLaunch ->
        flow {
            if (queryLaunch.sortType == SortType.TITLE)
                emit(queryLaunch.Ship.sortedBy {
                    it.launch.name
                }.mapNotNull {
                    if (it.launch.name.lowercase().contains(queryLaunch.query, ignoreCase = true)
                    ) it else null
                })
            else {
                emit(queryLaunch.Ship.sortedBy {
                    it.launch.date
                }.mapNotNull {
                    if (it.launch.name.lowercase().contains(queryLaunch.query, ignoreCase = true)
                    ) it else null
                })
            }
        }
    }


    fun updateQuery(query: String) {
        currentQuery.value = query
    }

    fun updateSort(type: SortType) {
        currentSortType.value = type
    }


}

data class QueryLaunch(
    val query: String,
    val sortType: SortType,
    val Ship: List<LaunchWithShips> //true-Movie , false - TV Show
)

enum class SortType {
    DATE, TITLE
}