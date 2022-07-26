package gini.ohadsa.spacex.ui.ships

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
import gini.ohadsa.spacex.databinding.FragmentShipsBinding
import gini.ohadsa.spacex.ui.ships.adapter.AdapterShips
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ShipsFragment : Fragment() {

    private var _binding: FragmentShipsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShipsViewModel by viewModels()
    @Inject
    lateinit var myAdapter: AdapterShips

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShipsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initAdapter()
        collectItemsToAdapter()
        return root
    }

    private fun collectItemsToAdapter() {
        lifecycleScope.launch {
            viewModel.ships.collect { ships ->
                ships.forEach { item ->
                    myAdapter.addItem(item)
                }
            }
        }
    }

    private fun initAdapter() {
        myAdapter.itemClickedListener = AdapterShips.ItemClickedListener {
            findNavController().navigate(
                ShipsFragmentDirections.actionNavShipsToShipItemFragment(it)
            )
        }
        binding.apply {
            shipsRecycler.apply {
                setHasFixedSize(true)
                val grid = GridLayoutManager(requireContext(), 1)
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