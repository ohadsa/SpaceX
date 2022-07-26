package gini.ohadsa.spacex.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gini.ohadsa.spacex.utils.imageloader.ImageLoader
import gini.ohadsa.spacex.utils.imageloader.ImageLoaderImpl
import gini.ohadsa.weather.network.NetworkStatusChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    @Provides
    @Singleton
    fun provideNetworkStatusChecker(@ApplicationContext context: Context) : NetworkStatusChecker =
        NetworkStatusChecker(
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        )



    @Provides
    @Singleton
    fun providePicasso(): Picasso = Picasso.get()

    @Provides
    @Singleton
    fun provideImageLoader(picasso: Picasso): ImageLoader = ImageLoaderImpl(picasso)


    @Provides
    fun provideGridLayoutManager(@ApplicationContext context: Context): GridLayoutManager =
        GridLayoutManager(context , 1 )
}