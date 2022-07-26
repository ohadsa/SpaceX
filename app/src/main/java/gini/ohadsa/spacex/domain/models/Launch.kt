package gini.ohadsa.spacex.domain.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
data class Launch @JvmOverloads constructor(
    @SerializedName("links")
    @TypeConverters
    val links: LaunchLinksResponse,
    val details: String?,
    val name: String?,
    @SerializedName("static_fire_date_unix")
    val date: Long,
    @Ignore
    @SerializedName("ships")
    var shipIds: List<String>? = mutableListOf(),
    @PrimaryKey
    @SerializedName("id")
    val launchId: String,
) : Parcelable

@Parcelize
data class LaunchLinksResponse(
    @SerializedName("patch")
    val patches: PatchResponse,
    @SerializedName("youtube_id")
    val youtubeMedia: String?,
    val wikipedia: String?,
    val article: String?,
) : Parcelable

@Parcelize
data class PatchResponse(
    val small: String?,
    val large: String?,
) : Parcelable

