package gini.ohadsa.spacex.domain.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShipWithLaunches(
    @Embedded
    val ship: Ship,

    @Relation(
        parentColumn = "shipId",
        entityColumn = "launchId",
        associateBy = Junction(ShipLaunchCrossRef::class)
    )
    val launches: List<Launch>
): Parcelable


@Parcelize
data class LaunchWithShips(
    @Embedded
    val launch: Launch,
    @Relation(
        parentColumn = "launchId",
        entityColumn = "shipId",
        associateBy = Junction(ShipLaunchCrossRef::class)
    )
    val ships: List<Ship>
): Parcelable

@Entity(primaryKeys = ["shipId", "launchId"])
data class ShipLaunchCrossRef(
    val shipId: String,
    val launchId: String
)