package au.com.shiftyjelly.pocketcasts.wear.ui

import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import au.com.shiftyjelly.pocketcasts.compose.preview.ThemePreviewParameterProvider
import au.com.shiftyjelly.pocketcasts.repositories.playback.UpNextQueue
import au.com.shiftyjelly.pocketcasts.ui.theme.Theme
import au.com.shiftyjelly.pocketcasts.wear.theme.WearAppTheme
import au.com.shiftyjelly.pocketcasts.wear.ui.component.WatchListChip
import au.com.shiftyjelly.pocketcasts.wear.ui.downloads.DownloadsScreen
import au.com.shiftyjelly.pocketcasts.wear.ui.podcasts.PodcastsScreen
import au.com.shiftyjelly.pocketcasts.images.R as IR
import au.com.shiftyjelly.pocketcasts.localization.R as LR
import au.com.shiftyjelly.pocketcasts.profile.R as PR

object WatchListScreen {
    const val route = "watch_list_screen"
}

@Composable
fun WatchListScreen(
    scrollState: ScalingLazyListState,
    navigateToRoute: (String) -> Unit,
    toNowPlaying: () -> Unit,
    upNextViewModel: UpNextViewModel = hiltViewModel(),
) {

    val upNextState by upNextViewModel.upNextQueue.subscribeAsState(null)

    ScalingLazyColumn(
        state = scrollState,
        flingBehavior = ScrollableDefaults.flingBehavior(),
        modifier = Modifier.fillMaxWidth(),
    ) {

        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                text = stringResource(LR.string.app_name)
            )
        }

        item {
            WatchListChip(
                titleRes = LR.string.player_tab_playing_wide,
                iconRes = IR.drawable.ic_play_all,
                secondaryLabel = (upNextState as? UpNextQueue.State.Loaded)?.episode?.title,
                onClick = toNowPlaying,
            )
        }

        item {
            WatchListChip(
                titleRes = LR.string.podcasts,
                iconRes = IR.drawable.ic_podcasts,
                onClick = { navigateToRoute(PodcastsScreen.route) }
            )
        }

        item {
            WatchListChip(
                titleRes = LR.string.filters,
                iconRes = IR.drawable.ic_filters,
                onClick = { navigateToRoute(FiltersScreen.route) }
            )
        }

        item {
            WatchListChip(
                titleRes = LR.string.downloads,
                iconRes = IR.drawable.ic_download,
                onClick = { navigateToRoute(DownloadsScreen.route) }
            )
        }

        item {
            WatchListChip(
                titleRes = LR.string.profile_navigation_files,
                iconRes = PR.drawable.ic_file,
                onClick = { navigateToRoute(FilesScreen.route) }
            )
        }

        item {
            WatchListChip(
                titleRes = LR.string.settings,
                iconRes = IR.drawable.ic_profile_settings,
                onClick = { navigateToRoute(SettingsScreen.route) }
            )
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
private fun WatchListPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) themeType: Theme.ThemeType,
) {
    WearAppTheme(themeType) {
        WatchListScreen(
            toNowPlaying = {},
            navigateToRoute = {},
            scrollState = ScalingLazyListState()
        )
    }
}
