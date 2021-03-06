package com.aminography.data.city.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aminography.model.city.City
import com.aminography.radixtree.RadixTree
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * A factory class responsible for creating a [Flow] of [PagingData], according to a specific
 * [PagingConfig].
 *
 * @param pagingConfig the configuration parameters for pagination.
 *
 * @author aminography
 */
internal class CityPagingFactory @Inject constructor(
    private val pagingConfig: PagingConfig
) {

    /**
     * The factory method that creates a [Flow] of [PagingData] in searching the [query] on the [tree].
     *
     * @param tree the tree of data.
     * @param query the query that should be used for the prefix search on the tree.
     */
    fun createPagingDataFlow(tree: RadixTree<City>, query: String): Flow<PagingData<City>> =
        Pager(pagingConfig) { SearchCityPagingSource(tree, query) }.flow
}