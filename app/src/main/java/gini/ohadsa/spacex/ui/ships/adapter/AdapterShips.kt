package gini.ohadsa.spacex.ui.ships.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gini.ohadsa.spacex.R
import gini.ohadsa.spacex.domain.models.LaunchWithShips
import gini.ohadsa.spacex.domain.models.Ship
import gini.ohadsa.spacex.domain.models.ShipWithLaunches
import gini.ohadsa.spacex.ui.launches.adapter.AdapterLaunches
import gini.ohadsa.spacex.utils.imageloader.ImageLoader
import javax.inject.Inject

class AdapterShips @Inject constructor(
    private val shipsDiffUtils: ShipsDiffUtils,
    private val imageLoader: ImageLoader,
    private val gridLayoutManager: GridLayoutManager,
    private val adapterLaunches: AdapterLaunches
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: MutableList<ShipWithLaunches> = mutableListOf()
    var itemClickedListener = ItemClickedListener { }

    fun interface ItemClickedListener {
        fun shipItemClicked(item: ShipWithLaunches)
    }

    private fun getItem(position: Int): ShipWithLaunches {
        return items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ShipViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.ship_card, viewGroup, false)
        return ShipViewHolder(view, itemClickedListener, this::getItem)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ShipViewHolder
        val shipWithLaunches = getItem(position)
        shipWithLaunches.ship.name?.let {
            vh.shipTitle.text = it
        }

        shipWithLaunches.ship.image?.let {
            imageLoader.load(it, vh.shipImage)
        } ?: run {
            vh.shipImage.setImageResource(R.drawable.notfound)
        }

    }

    fun removeItem(item: ShipWithLaunches) {
        val index = items.indexOf(item)
        notifyItemRemoved(index)
        items.remove(item)
    }

    fun updateList(updatedUserList: List<ShipWithLaunches>) {
        shipsDiffUtils.oldList = items
        shipsDiffUtils.newList = updatedUserList
        val diffResult = DiffUtil.calculateDiff(shipsDiffUtils)
        items.clear()
        items.addAll(updatedUserList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun addItem(item: ShipWithLaunches) {
        if (!items.contains(item)) {
            items.add(item)
            notifyItemInserted(items.indexOf(item))
        }
    }

    class ShipViewHolder(
        itemView: View,
        private val itemClickListener: ItemClickedListener,
        val getItem: (Int) -> ShipWithLaunches
    ) : RecyclerView.ViewHolder(itemView) {
        val shipImage: ImageView
        val shipTitle: TextView

        init {
            shipImage = itemView.findViewById(R.id.ship_image)
            shipTitle = itemView.findViewById(R.id.ship_title)
            itemView.setOnClickListener {
                val item = getItem(bindingAdapterPosition)
                itemClickListener.shipItemClicked(item)
            }
        }
    }

}




