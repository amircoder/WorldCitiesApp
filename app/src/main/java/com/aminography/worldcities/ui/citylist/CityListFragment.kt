package com.aminography.worldcities.ui.citylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.aminography.worldcities.databinding.FragmentCityListBinding
import com.aminography.worldcities.ui.base.BaseFragment
import com.aminography.worldcities.ui.base.adapter.BaseDataHolder
import com.aminography.worldcities.ui.base.adapter.OnListItemClickListener
import com.aminography.worldcities.ui.citylist.adapter.CityListAdapter
import com.aminography.worldcities.ui.citylist.di.injectComponent
import com.aminography.worldcities.ui.citylist.model.MapViewerArg
import com.aminography.worldcities.ui.citylist.vm.CityListViewModel
import com.aminography.worldcities.ui.util.hideKeyboard
import com.aminography.worldcities.ui.util.toast
import javax.inject.Inject

/**
 * @author aminography
 */
class CityListFragment : BaseFragment<FragmentCityListBinding>(), OnListItemClickListener {

    @Inject
    lateinit var viewModel: CityListViewModel

    @Inject
    lateinit var adapter: CityListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectComponent()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCityListBinding = FragmentCityListBinding.inflate(inflater, container, false)

    override fun onInitViews(rootView: View, savedInstanceState: Bundle?) {
        adapter.setOnListItemClickListener(this)
        adapter.addLoadStateListener { loadState ->
            loadState.decide(
                showEmptyState = { binding.emptyState.isVisible = it },
                showError = { context?.toast(it) }
            )
        }

        binding.recyclerView.adapter = adapter
        binding.searchEditText.addTextChangedListener { viewModel.setQuery(it.toString()) }

        initViewModel()
    }

    private fun initViewModel() {
        val owner = viewLifecycleOwner
        viewModel.searchResult.observe(owner) { adapter.submitData(lifecycle, it) }
        viewModel.errorMessage.observe(owner) { context?.toast(it) }
        viewModel.loading.observe(owner) { binding.progressBar.run { isVisible = it } }
        viewModel.navigateToMap.observe(owner) { navigateToMap(it) }
    }

    private fun navigateToMap(arg: MapViewerArg) {
        binding.searchEditText.hideKeyboard()
        findNavController().navigate(
            CityListFragmentDirections.actionCityListFragmentToMapViewerFragment(arg)
        )
    }

    override fun onItemClicked(dataHolder: BaseDataHolder?) {
        dataHolder?.let { viewModel.onCityClicked(it) }
    }

    private fun CombinedLoadStates.decide(
        showEmptyState: (Boolean) -> Unit,
        showError: (String) -> Unit
    ) {
        showEmptyState(
            source.append is LoadState.NotLoading
                    && source.append.endOfPaginationReached
                    && adapter.itemCount == 0
        )
        val errorState = source.append as? LoadState.Error
            ?: source.prepend as? LoadState.Error
            ?: append as? LoadState.Error
            ?: prepend as? LoadState.Error
        errorState?.let { showError(it.error.toString()) }
    }
}