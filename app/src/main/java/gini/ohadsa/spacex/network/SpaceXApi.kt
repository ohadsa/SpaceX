package gini.ohadsa.spacex.network

import gini.ohadsa.spacex.domain.models.Launch
import gini.ohadsa.spacex.domain.models.Ship
import retrofit2.http.GET


interface SpaceXApi {

    @GET("v4/ships")
    suspend fun allShips(): List<Ship>

    @GET("v5/launches")
    suspend fun allLaunches(): List<Launch>

    companion object{
        const val baseUrl = "https://api.spacexdata.com/"
    }


}


/***************************
    end points:
    https://api.spacexdata.com/v4/ships
    https://api.spacexdata.com/v5/launches
 **************************/
