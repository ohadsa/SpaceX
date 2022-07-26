package gini.ohadsa.spacex.database.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import gini.ohadsa.spacex.domain.models.LaunchLinksResponse

@ProvidedTypeConverter
class LinksConverter {
    @TypeConverter
    fun fromSource(source: LaunchLinksResponse): String =
        Gson().toJson(source)

    @TypeConverter
    fun toSource(str: String): LaunchLinksResponse =
        Gson().fromJson(str, LaunchLinksResponse::class.java)
    
}