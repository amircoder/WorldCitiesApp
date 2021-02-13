package com.aminography.worldcities.navigation

import com.aminography.worldcities.navigation.core.BaseNavDestination
import com.aminography.worldcities.navigation.core.argument.DeepLinkNavArgument
import com.aminography.worldcities.navigation.model.MapViewerNavArg
import com.aminography.worldcities.navigation.model.UserListNavArg

/**
 * @author aminography
 */
sealed class NavDestinations<T : DeepLinkNavArgument>(link: String) : BaseNavDestination<T>(link) {

    object TestDestinationWithNoArg : NavDestinations<Nothing>("myapp://test_no_arg")

    object MapViewer : NavDestinations<MapViewerNavArg>("myapp://map_viewer")

    object UserList : NavDestinations<UserListNavArg>("myapp://user_list")
}
