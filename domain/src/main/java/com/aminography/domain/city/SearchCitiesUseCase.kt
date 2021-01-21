package com.aminography.domain.city

import androidx.paging.PagingData
import com.aminography.domain.base.BaseFlowUseCase
import com.aminography.domain.di.DefaultDispatcher
import com.aminography.model.city.City
import com.aminography.scope.CityListScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

/**
 * The use-case of searching cities.
 *
 * @param cityRepository an instance of [CityRepository].
 * @param dispatcher the [CoroutineDispatcher] that the flow should produce values on.
 *
 * @author aminography
 */
@CityListScope
class SearchCitiesUseCase @Inject constructor(
    private val cityRepository: CityRepository,
    @DefaultDispatcher dispatcher: CoroutineDispatcher
) : BaseFlowUseCase<String, PagingData<City>>(dispatcher) {

    override fun execute(parameter: String): Flow<PagingData<City>> =
        parameter.toLowerCase(Locale.getDefault()).let { query ->
            cityRepository.searchCities(query)
        }
}