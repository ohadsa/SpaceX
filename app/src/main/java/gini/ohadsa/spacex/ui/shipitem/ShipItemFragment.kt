package gini.ohadsa.spacex.ui.shipitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gini.ohadsa.spacex.databinding.FragmentShipItemBinding
import gini.ohadsa.spacex.ui.launches.LaunchesFragmentDirections
import gini.ohadsa.spacex.ui.launches.adapter.AdapterLaunches
import gini.ohadsa.spacex.utils.imageloader.ImageLoader
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ShipItemFragment : Fragment() {

    private var _binding: FragmentShipItemBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShipItemViewModel by viewModels()
    private val args: ShipItemFragmentArgs by navArgs()

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var launchesAdapter: AdapterLaunches

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShipItemBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            collectItemFlow()
            viewModel.setItem(args.shipWithLaunches)
            initLaunchAdapter()
        }


    }

    private fun initLaunchAdapter() {
        launchesAdapter.itemClickedListener = AdapterLaunches.ItemClickedListener {
            findNavController().navigate(
                ShipItemFragmentDirections.actionShipItemFragmentToLaunchItemFragment(it)
            )
        }
        binding.apply {
            launchesRecycler.apply {
                setHasFixedSize(true)
                val grid = GridLayoutManager(requireContext(), 1)
                grid.orientation = GridLayoutManager.HORIZONTAL
                layoutManager = grid
                adapter = launchesAdapter
            }

        }

    }

    private fun collectItemFlow() {
        collectShipFlow()
        collectLaunchesFlow()
    }

    private fun collectLaunchesFlow() {
        lifecycleScope.launch {
            viewModel.launchesFlow.collect{
                launchesAdapter.addItem(it)
            }
        }
    }

    private fun collectShipFlow() {
        lifecycleScope.launch {
            viewModel.shipFlow.collect { ship ->

                ship.image?.let {
                    imageLoader.load(it, binding.shipImage)
                }

                ship.name?.let {
                    binding.shipTitle.text = it
                }

                ship.homePort?.let {
                    binding.shipDetails.text = it
                }

                ship.link?.let {
                    val view = binding.webview
                    view.settings.javaScriptEnabled = true
                    view.loadUrl(it)
                }
            }
        }
    }

}