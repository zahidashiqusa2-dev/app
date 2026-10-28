package com.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

enum class AppScreen(val title: String) {
    DASHBOARD("Dashboard"),
    ONE_VIEW("One View"),
    DELETED_LOGS("Anti-Delete"),
    PAIRING("Pair Device"),
    LIVE_LOCATION("Live GPS"),
    FAKE_QUOTE("Fake Quote"),
    SCHEDULER("Scheduler"),
    BUTTONS("Buttons"),
    AUTO_REPLY("Auto Rules")
}

data class DashboardMetrics(
    val messagesSent: Int = 184,
    val messagesReceived: Int = 312,
    val activeRules: Int = 3,
    val scheduledPending: Int = 2,
    val viewOnceIntercepted: Int = 3,
    val revokedMessagesLogged: Int = 3,
    val engineUptime: String = "99.98%"
)

data class AutomationUiState(
    val currentScreen: AppScreen = AppScreen.DASHBOARD,
    val session: WhatsAppSession = WhatsAppSession(),
    val liveLocation: LiveLocationConfig = LiveLocationConfig(),
    val fakeQuote: FakeQuoteMessage = FakeQuoteMessage(),
    val scheduledMessages: List<ScheduledMessageItem> = emptyList(),
    val buttonConfig: ButtonMessageConfig = ButtonMessageConfig(),
    val autoReplyRules: List<AutoReplyRuleItem> = emptyList(),
    val botSettings: BotSettings = BotSettings(),
    val activityLogs: List<ActivityLogItem> = emptyList(),
    val viewOnceMedia: List<ViewOnceMediaItem> = emptyList(),
    val deletedMessages: List<DeletedMessageItem> = emptyList(),
    val metrics: DashboardMetrics = DashboardMetrics()
)

class AutomationViewModel(
    private val repository: AutomationRepository = AutomationRepository()
) : ViewModel() {

    private val _currentScreen = MutableStateFlow(AppScreen.DASHBOARD)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    val uiState: StateFlow<AutomationUiState> = combine(
        _currentScreen,
        repository.data
    ) { screen, data ->
        val pendingCount = data.scheduledMessages.count { it.status == ScheduleStatus.PENDING }
        val sentCount = data.scheduledMessages.count { it.status == ScheduleStatus.SENT }
        val activeRulesCount = data.autoReplyRules.count { it.isEnabled }

        AutomationUiState(
            currentScreen = screen,
            session = data.session,
            liveLocation = data.liveLocation,
            fakeQuote = data.fakeQuote,
            scheduledMessages = data.scheduledMessages,
            buttonConfig = data.buttonConfig,
            autoReplyRules = data.autoReplyRules,
            botSettings = data.botSettings,
            activityLogs = data.activityLogs,
            viewOnceMedia = data.viewOnceMedia,
            deletedMessages = data.deletedMessages,
            metrics = DashboardMetrics(
                messagesSent = 184 + sentCount,
                messagesReceived = 312,
                activeRules = activeRulesCount,
                scheduledPending = pendingCount,
                viewOnceIntercepted = data.viewOnceMedia.size,
                revokedMessagesLogged = data.deletedMessages.size,
                engineUptime = "99.98%"
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AutomationUiState()
    )

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun toggleConnection() {
        repository.toggleConnection()
    }

    fun requestPairingCode(phone: String) {
        repository.requestPairingCode(phone)
    }

    fun completePairing() {
        repository.completePairing()
    }

    fun updateLiveLocation(target: String, lat: Double, lng: Double, duration: Int, caption: String) {
        repository.updateLiveLocation(target, lat, lng, duration, caption)
    }

    fun stopLiveLocation() {
        repository.stopLiveLocation()
    }

    fun sendFakeQuote(recipient: String, reply: String, quotedSender: String, quotedSenderName: String, quotedText: String) {
        repository.sendFakeQuote(recipient, reply, quotedSender, quotedSenderName, quotedText)
    }

    fun scheduleMessage(recipient: String, content: String, type: ScheduledMessageType, delay: String) {
        repository.addScheduledMessage(recipient, content, type, delay)
    }

    fun cancelScheduledMessage(id: String) {
        repository.cancelScheduledMessage(id)
    }

    fun dispatchMessageNow(id: String) {
        repository.dispatchScheduledMessageNow(id)
    }

    fun updateButtonConfig(config: ButtonMessageConfig) {
        repository.updateButtonConfig(config)
    }

    fun addAutoReplyRule(keyword: String, matchType: String, response: String) {
        repository.addAutoReplyRule(keyword, matchType, response)
    }

    fun toggleAutoReplyRule(id: String) {
        repository.toggleAutoReplyRule(id)
    }

    fun deleteAutoReplyRule(id: String) {
        repository.deleteAutoReplyRule(id)
    }

    fun updateBotSettings(settings: BotSettings) {
        repository.updateBotSettings(settings)
    }

    // =========================================================================
    // VIEW ONCE & ANTI-DELETE EXPOSURES
    // =========================================================================

    fun saveViewOnceToGallery(id: String) {
        repository.saveViewOnceToGallery(id)
    }

    fun deleteViewOnceItem(id: String) {
        repository.deleteViewOnceItem(id)
    }

    fun simulateIncomingViewOnce(sender: String, type: ViewOnceMediaType, caption: String) {
        repository.simulateIncomingViewOnce(sender, type, caption)
    }

    fun clearAllDeletedMessages() {
        repository.clearAllDeletedMessages()
    }

    fun simulateIncomingDeletedMessage(sender: String, content: String, chat: String) {
        repository.simulateIncomingDeletedMessage(sender, content, chat)
    }
}
