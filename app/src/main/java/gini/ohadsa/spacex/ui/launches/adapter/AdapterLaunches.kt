package gini.ohadsa.spacex.ui.launches.adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import gini.ohadsa.spacex.R
import gini.ohadsa.spacex.domain.models.Launch
import gini.ohadsa.spacex.domain.models.LaunchWithShips
import gini.ohadsa.spacex.ui.launches.adapter.AdapterLaunches.ItemClickedListener
import gini.ohadsa.spacex.utils.imageloader.ImageLoader
import java.util.*
import javax.inject.Inject

class AdapterLaunches @Inject constructor(
    private val shipsDiffUtils: LaunchesDiffUtils,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: MutableList<LaunchWithShips> = mutableListOf()
    var itemClickedListener = ItemClickedListener { }

    fun interface ItemClickedListener {
        fun launchItemClicked(item: LaunchWithShips)
    }

    private fun getItem(position: Int): LaunchWithShips {
        return items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LaunchViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.launch_card, viewGroup, false)
        return LaunchViewHolder(view, itemClickedListener, this::getItem)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as LaunchViewHolder
        val launchWithShips = getItem(position)
        launchWithShips.launch.name?.let {
            vh.launchTitle.text = it
        }
        launchWithShips.launch.links.patches.large?.let {
            imageLoader.load(it , vh.launchImage)
        }
        vh.launchDate.text = "${DateFormat.format("MM/dd/yyyy", Date(launchWithShips.launch.date))}"
    }

    fun removeItem(item: LaunchWithShips) {
        val index = items.indexOf(item)
        notifyItemRemoved(index)
        items.remove(item)
    }

    fun updateList(updatedUserList: List<LaunchWithShips>) {
        shipsDiffUtils.oldList = items
        shipsDiffUtils.newList = updatedUserList
        val diffResult = DiffUtil.calculateDiff(shipsDiffUtils)
        items.clear()
        items.addAll(updatedUserList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun addItem(item: LaunchWithShips) {
        if (!items.contains(item)) {
            items.add(item)
            notifyItemInserted(items.indexOf(item))
        }
    }

    class LaunchViewHolder(
        itemView: View,
        private val itemClickListener: ItemClickedListener,
        val getItem: (Int) -> LaunchWithShips
    ) : RecyclerView.ViewHolder(itemView) {
        val launchDate: TextView
        val launchImage: ImageView
        val launchTitle: TextView


        init {
            launchImage = itemView.findViewById(R.id.launch_image)
            launchTitle = itemView.findViewById(R.id.launch_title)
            launchDate = itemView.findViewById(R.id.launch_date)
            itemView.setOnClickListener {
                val item = getItem(bindingAdapterPosition)
                itemClickListener.launchItemClicked(item)
            }
        }
    }

}




