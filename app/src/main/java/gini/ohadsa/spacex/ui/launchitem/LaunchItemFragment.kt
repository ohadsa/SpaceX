package gini.ohadsa.spacex.ui.launchitem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import gini.ohadsa.spacex.databinding.FragmentLaunchItemBinding
import gini.ohadsa.spacex.ui.ships.adapter.AdapterShips
import gini.ohadsa.spacex.utils.imageloader.ImageLoader
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class LaunchItemFragment : Fragment() {

    private var _binding: FragmentLaunchItemBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LaunchItemViewModel by viewModels()
    private val args: LaunchItemFragmentArgs by navArgs()

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var shipsAdapter: AdapterShips

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            collectItemFlow()
            viewModel.setItem(args.lauchWithShips)
            initShipsAdapter()
        }

    }

    private fun initShipsAdapter() {
        shipsAdapter.itemClickedListener = AdapterShips.ItemClickedListener {
            findNavController().navigate(
                LaunchItemFragmentDirections.actionLaunchItemFragmentToShipItemFragment(it)
            )
        }
        binding.apply {
            shipsRecycler.apply {
                setHasFixedSize(true)
                val grid = GridLayoutManager(requireContext(), 1)
                grid.orientation = GridLayoutManager.HORIZONTAL
                layoutManager = grid
                adapter = shipsAdapter
            }

        }

    }

    private fun collectItemFlow() {
        collectLaunchFlow()
        collectShipsFlow()


    }

    private fun collectShipsFlow() {
        lifecycleScope.launch {
            viewModel.shipFlow.collect{
                shipsAdapter.addItem(it)
            }
        }
    }

    private fun collectLaunchFlow() {
        lifecycleScope.launch {
            viewModel.launchFlow.collect { item ->
                item.links.patches.large?.let { img ->
                    imageLoader.load(img, binding.launchImage)
                }
                item.name.let { name ->
                    binding.launchTitle.text = name
                }
                item.details?.let { details ->
                    binding.launchDetails.text = details
                }
                item.links.youtubeMedia?.let { link ->
                    addYoutubeMedia(link)
                }
            }
        }    }

    private fun addYoutubeMedia(link: String) {
        binding.youtube.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(link, 0f)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}