package gini.ohadsa.spacex.ui.ships.adapter

import androidx.recyclerview.widget.DiffUtil
import gini.ohadsa.spacex.domain.models.Ship
import gini.ohadsa.spacex.domain.models.ShipWithLaunches
import javax.inject.Inject


class ShipsDiffUtils @Inject constructor(): DiffUtil.Callback() {
    lateinit var oldList: List<ShipWithLaunches>
    lateinit var newList: List<ShipWithLaunches>

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].ship.shipId == newList[newItemPosition].ship.shipId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}