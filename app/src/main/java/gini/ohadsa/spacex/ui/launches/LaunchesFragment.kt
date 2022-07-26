package gini.ohadsa.spacex.ui.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gini.ohadsa.spacex.databinding.FragmentLaunchesBinding
import gini.ohadsa.spacex.domain.models.Launch
import gini.ohadsa.spacex.ui.launches.adapter.AdapterLaunches
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LaunchesFragment : Fragment() {

    private var _binding: FragmentLaunchesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LaunchesViewModel by viewModels()

    @Inject
    lateinit var myAdapter: AdapterLaunches


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initAdapter()
        collectItemsToAdapter()
        return root
    }

    private fun collectItemsToAdapter() {
        lifecycleScope.launch {
            viewModel.launches.collect { launches ->
                launches.forEach {
                    myAdapter.addItem(it)
                }
            }
        }
    }

    private fun initAdapter() {
        myAdapter.itemClickedListener = AdapterLaunches.ItemClickedListener {
            findNavController().navigate(
                LaunchesFragmentDirections.actionNavLauncherToLaunchItemFragment(it)
            )

        }
        binding.apply {
            launchesRecycler.apply {
                setHasFixedSize(true)
                val grid = GridLayoutManager(requireContext(), 2)
                grid.orientation = GridLayoutManager.VERTICAL
                layoutManager = grid
                adapter = myAdapter
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}