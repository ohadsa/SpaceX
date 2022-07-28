package gini.ohadsa.spacex.ui.launches.adapter

import androidx.recyclerview.widget.DiffUtil
import gini.ohadsa.spacex.domain.models.LaunchWithShips
import javax.inject.Inject


class LaunchesDiffUtils @Inject constructor(): DiffUtil.Callback() {
    lateinit var oldList: List<LaunchWithShips>
    lateinit var newList: List<LaunchWithShips>

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].launch.launchId == newList[newItemPosition].launch.launchId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}