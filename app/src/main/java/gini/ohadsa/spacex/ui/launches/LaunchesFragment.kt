package gini.ohadsa.spacex.ui.launches
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
import gini.ohadsa.spacex.databinding.FragmentLaunchesBinding
import gini.ohadsa.spacex.ui.launches.adapter.AdapterLaunches
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LaunchesFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentLaunchesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LaunchesViewModel by viewModels()
    lateinit var searchView: SearchView

    @Inject
    lateinit var myAdapter: AdapterLaunches


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initUpBarMenu()
        initAdapter()
        collectItemsToAdapter()
        return root
    }

    private fun collectItemsToAdapter() {
        lifecycleScope.launch {
            viewModel.launches.collect { launches ->
                myAdapter.updateList(launches)
                binding.launchesRecycler.scrollToPosition(0)
            }
        }
    }

    private fun initUpBarMenu() {
        setMenuVisibility(true)
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_launch, menu)
                searchView = menu.findItem(R.id.action_search).actionView as SearchView
                searchView.isSubmitButtonEnabled = true
                searchView.setOnQueryTextListener(this@LaunchesFragment)
                onQueryTextChange("")
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.title -> {
                        viewModel.updateSort(SortType.TITLE)

                        true
                    }
                    R.id.date -> {
                        viewModel.updateSort(SortType.DATE)
                        true
                    }
                    else -> false
                }

            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            viewModel.updateQuery(query)
            binding.launchesRecycler.scrollToPosition(0)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            viewModel.updateQuery(query)
            binding.launchesRecycler.scrollToPosition(0)
        }
        return true
    }
}