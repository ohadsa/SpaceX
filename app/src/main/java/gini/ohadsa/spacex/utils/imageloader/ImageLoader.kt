
package gini.ohadsa.spacex.utils.imageloader

import android.widget.ImageView

interface ImageLoader {
  fun load(imageResource: String, target: ImageView)
}
