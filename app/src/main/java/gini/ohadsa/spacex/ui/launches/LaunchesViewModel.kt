package gini.ohadsa.spacex.ui.launches

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gini.ohadsa.spacex.domain.models.LaunchWithShips
import gini.ohadsa.spacex.domain.repository.SpaceXRepository
import gini.ohadsa.spacex.domain.usecases.launches.GetAllLaunchesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    getAllLaunchesUseCase: GetAllLaunchesUseCase
) : ViewModel() {

    private var currentQuery: MutableStateFlow<String> = MutableStateFlow("")
    private var currentSortType: MutableStateFlow<SortType> = MutableStateFlow(SortType.TITLE)

    @OptIn(ExperimentalCoroutinesApi::class)
    val launches: Flow<List<LaunchWithShips>> = combine(
        currentQuery,
        currentSortType,
    ) { q, s -> QueryLaunch(q, s) }.flatMapLatest { queryLaunch ->
        return@flatMapLatest getAllLaunchesUseCase.get(queryLaunch.sortType.type).map { lst ->
            lst.mapNotNull { item ->
                if (item.launch.name.lowercase()
                        .contains(queryLaunch.query, ignoreCase = true)
                ) item else null
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
)

enum class SortType(var type: String) {
    DATE("date"), TITLE("name")
}