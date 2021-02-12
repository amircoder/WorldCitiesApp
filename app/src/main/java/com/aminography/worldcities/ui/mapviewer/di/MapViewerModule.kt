package com.aminography.worldcities.ui.mapviewer.di

import com.aminography.scope.annotation.FeatureScope
import com.aminography.worldcities.ui.mapviewer.MapViewerFragment
import com.aminography.worldcities.ui.mapviewer.vm.MapViewerViewModel
import com.aminography.worldcities.ui.mapviewer.vm.MapViewerViewModelFactory
import com.aminography.worldcities.ui.util.createViewModel
import dagger.Module
import dagger.Provides

/**
 *  A dagger module class defining how to provide map-viewer related dependencies for injection.
 *
 * @author aminography
 */
@Module
class MapViewerModule {

    @FeatureScope
    @Provides
    fun providesCityMapViewModel(
        factoryViewer: MapViewerViewModelFactory,
        viewerFragment: MapViewerFragment
    ): MapViewerViewModel = viewerFragment.createViewModel(factoryViewer)
}