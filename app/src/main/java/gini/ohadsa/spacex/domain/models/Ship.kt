package gini.ohadsa.spacex.domain.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Ship")
data class Ship @JvmOverloads constructor(

    val image: String?,
    val name: String?,
    @SerializedName("home_port")
    val homePort: String?,
    val link: String?,
    @Ignore
    @SerializedName("launches")
    var launchIds: List<String>? = mutableListOf(),
    @PrimaryKey
    @SerializedName("id")
    val shipId: String,
) : Parcelable {

}


