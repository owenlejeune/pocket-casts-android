package au.com.shiftyjelly.pocketcasts.profile.cloud

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import au.com.shiftyjelly.pocketcasts.analytics.AnalyticsEvent
import au.com.shiftyjelly.pocketcasts.analytics.AnalyticsTracker
import au.com.shiftyjelly.pocketcasts.analytics.SourceView
import au.com.shiftyjelly.pocketcasts.models.type.SignInState
import au.com.shiftyjelly.pocketcasts.preferences.Settings
import au.com.shiftyjelly.pocketcasts.repositories.user.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CloudSettingsViewModel @Inject constructor(
    private val analyticsTracker: AnalyticsTracker,
    private val settings: Settings,
    userManager: UserManager,
) : ViewModel() {

    val signInState: LiveData<SignInState> = userManager.getSignInState().toLiveData()

    private var isFragmentChangingConfigurations: Boolean = false

    fun onShown() {
        if (!isFragmentChangingConfigurations) {
            analyticsTracker.track(AnalyticsEvent.SETTINGS_FILES_SHOWN)
        }
    }

    fun onFragmentPause(isChangingConfigurations: Boolean?) {
        isFragmentChangingConfigurations = isChangingConfigurations ?: false
    }

    fun setAddToUpNext(enabled: Boolean) {
        settings.cloudAddToUpNext.set(enabled, updateModifiedAt = true)
        analyticsTracker.track(
            AnalyticsEvent.SETTINGS_FILES_AUTO_ADD_UP_NEXT_TOGGLED,
            mapOf("enabled" to enabled),
        )
    }

    fun setDeleteLocalFileAfterPlaying(enabled: Boolean) {
        settings.deleteLocalFileAfterPlaying.set(enabled, updateModifiedAt = true)
        analyticsTracker.track(
            AnalyticsEvent.SETTINGS_FILES_DELETE_LOCAL_FILE_AFTER_PLAYING_TOGGLED,
            mapOf("enabled" to enabled),
        )
    }

    fun setDeleteCloudFileAfterPlaying(enabled: Boolean) {
        settings.deleteCloudFileAfterPlaying.set(enabled, updateModifiedAt = true)
        analyticsTracker.track(
            AnalyticsEvent.SETTINGS_FILES_DELETE_CLOUD_FILE_AFTER_PLAYING_TOGGLED,
            mapOf("enabled" to enabled),
        )
    }

    fun setCloudAutoUpload(enabled: Boolean) {
        settings.cloudAutoUpload.set(enabled, updateModifiedAt = true)
        analyticsTracker.track(
            AnalyticsEvent.SETTINGS_FILES_AUTO_UPLOAD_TO_CLOUD_TOGGLED,
            mapOf("enabled" to enabled),
        )
    }

    fun setCloudAutoDownload(enabled: Boolean) {
        settings.cloudAutoDownload.set(enabled, updateModifiedAt = true)
        analyticsTracker.track(
            AnalyticsEvent.SETTINGS_FILES_AUTO_DOWNLOAD_FROM_CLOUD_TOGGLED,
            mapOf("enabled" to enabled),
        )
    }

    fun setCloudOnlyWifi(enabled: Boolean) {
        settings.cloudDownloadOnlyOnWifi.set(enabled, updateModifiedAt = true)
        analyticsTracker.track(
            AnalyticsEvent.SETTINGS_FILES_ONLY_ON_WIFI_TOGGLED,
            mapOf("enabled" to enabled),
        )
    }

    fun onUpgradeBannerDismissed(source: SourceView) {
        analyticsTracker.track(AnalyticsEvent.UPGRADE_BANNER_DISMISSED, mapOf("source" to source.analyticsValue))
    }
}
