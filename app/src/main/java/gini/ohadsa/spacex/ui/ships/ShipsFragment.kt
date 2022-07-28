package gini.ohadsa.spacex.ui.ships

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gini.ohadsa.spacex.R
import gini.ohadsa.spacex.databinding.FragmentShipsBinding
import gini.ohadsa.spacex.ui.ships.adapter.AdapterShips
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ShipsFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentShipsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShipsViewModel by viewModels()
    lateinit var searchView: SearchView

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
        initUpBarMenu()
        collectItemsToAdapter()


        return root
    }

    private fun initUpBarMenu() {
        setMenuVisibility(true)
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_ships, menu)
                searchView = menu.findItem(R.id.action_search).actionView as SearchView
                searchView.isSubmitButtonEnabled = true
                searchView.setOnQueryTextListener(this@ShipsFragment)
                onQueryTextChange("")
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun collectItemsToAdapter() {
        lifecycleScope.launch {
            viewModel.ships.collect { ships ->
                myAdapter.updateList(ships)
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            viewModel.updateQuery(query)
            binding.shipsRecycler.scrollToPosition(0)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            viewModel.updateQuery(query)
            binding.shipsRecycler.scrollToPosition(0)
        }
        return true
    }

}