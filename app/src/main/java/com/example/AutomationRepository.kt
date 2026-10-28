package com.example

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

data class AutomationData(
    val session: WhatsAppSession = WhatsAppSession(
        status = ConnectionState.CONNECTED,
        phoneNumber = "+1 555-0199",
        pushName = "Urooj Choudhary 👑",
        deviceId = "dev_urooj_8f48dcc7",
        pairingCode = "8F48-DCC7",
        batteryLevel = 92,
        platform = "Android Local Engine",
        isSandboxIsolated = true
    ),
    val liveLocation: LiveLocationConfig = LiveLocationConfig(
        targetJid = "+1 (555) 392-8190",
        latitude = 37.7749,
        longitude = -122.4194,
        accuracy = 12.5f,
        durationMinutes = 60,
        caption = "📍 Meeting at HQ - Live position stream active",
        isStreaming = true
    ),
    val fakeQuote: FakeQuoteMessage = FakeQuoteMessage(
        recipient = "+1 (555) 839-2041",
        replyText = "Ledger updated! Transfer confirmed and credited to account.",
        quotedSender = "+1 (555) 120-9944",
        quotedSenderName = "Accounting Dept",
        quotedText = "Could you confirm receipt of batch wire transfer $2,450.00?"
    ),
    val scheduledMessages: List<ScheduledMessageItem> = listOf(
        ScheduledMessageItem(
            id = "sch_1",
            recipient = "+1 (555) 492-0192",
            content = "Daily automated project summary & status check-in",
            type = ScheduledMessageType.TEXT,
            scheduledTime = "Today, 18:00 (In 45 min)",
            status = ScheduleStatus.PENDING
        ),
        ScheduledMessageItem(
            id = "sch_2",
            recipient = "+1 (555) 782-9901",
            content = "Live Delivery Tracker (HQ to Warehouse 4)",
            type = ScheduledMessageType.LIVE_LOCATION,
            scheduledTime = "Tomorrow, 09:00",
            status = ScheduleStatus.PENDING
        ),
        ScheduledMessageItem(
            id = "sch_3",
            recipient = "+1 (555) 301-4455",
            content = "Interactive VIP Menu & Service Selection",
            type = ScheduledMessageType.BUTTON_MSG,
            scheduledTime = "Yesterday, 14:30",
            status = ScheduleStatus.SENT
        )
    ),
    val buttonConfig: ButtonMessageConfig = ButtonMessageConfig(),
    val autoReplyRules: List<AutoReplyRuleItem> = listOf(
        AutoReplyRuleItem(
            id = "rule_1",
            keyword = ".status",
            matchType = "exact",
            response = "👑 Urooj Suite v2.5 Online | Latency: 42ms | All systems optimal.",
            isEnabled = true
        ),
        AutoReplyRuleItem(
            id = "rule_2",
            keyword = "pricing",
            matchType = "contains",
            response = "Hello! Our VIP automation tier is currently active on device.",
            isEnabled = true
        ),
        AutoReplyRuleItem(
            id = "rule_3",
            keyword = "help",
            matchType = "contains",
            response = "Commands: .status, .ping, .quote, .location, .menu, .ai",
            isEnabled = true
        )
    ),
    val botSettings: BotSettings = BotSettings(),
    val activityLogs: List<ActivityLogItem> = listOf(
        ActivityLogItem("log_1", "10:14:02", "CORE", "Local device sandbox initialized (ID: dev_urooj_8f48dcc7)", true),
        ActivityLogItem("log_2", "10:14:03", "SESSION", "Baileys socket bound to WhatsApp Multi-Device session", true),
        ActivityLogItem("log_3", "10:15:30", "GPS", "Live location telemetry broadcasted (accuracy 12m)", true),
        ActivityLogItem("log_4", "10:16:12", "AUTOREPLY", "Triggered rule '.status' to +15550199", true)
    ),
    val viewOnceMedia: List<ViewOnceMediaItem> = listOf(
        ViewOnceMediaItem(
            id = "vo_1",
            senderName = "Sofia Martinez",
            senderNumber = "+1 (555) 829-1940",
            mediaType = ViewOnceMediaType.IMAGE,
            caption = "Confidential contract signing photo 📄",
            timestamp = "10:42 AM",
            fileSize = "2.4 MB",
            isSavedToGallery = false,
            previewGradientColor = 0xFF00A884
        ),
        ViewOnceMediaItem(
            id = "vo_2",
            senderName = "Tech Lead David",
            senderNumber = "+1 (555) 492-3311",
            mediaType = ViewOnceMediaType.VIDEO,
            caption = "Product architecture walkthrough demo",
            timestamp = "09:15 AM",
            fileSize = "14.8 MB",
            isSavedToGallery = true,
            previewGradientColor = 0xFF38BDF8
        ),
        ViewOnceMediaItem(
            id = "vo_3",
            senderName = "Elena Vance",
            senderNumber = "+1 (555) 901-7722",
            mediaType = ViewOnceMediaType.AUDIO_NOTE,
            caption = "Private executive voice briefing (0:45)",
            timestamp = "Yesterday, 22:10",
            fileSize = "890 KB",
            isSavedToGallery = false,
            previewGradientColor = 0xFFF59E0B
        )
    ),
    val deletedMessages: List<DeletedMessageItem> = listOf(
        DeletedMessageItem(
            id = "del_1",
            senderName = "Marcus Brody",
            senderNumber = "+1 (555) 234-9988",
            messageContent = "Hey team, don't ship build 204 yet, there's a minor regression in the auth gateway!",
            originalTimestamp = "10:30:14 AM",
            deletedTimestamp = "10:30:42 AM (Revoked 28s later)",
            chatName = "Core Engineering 🚀",
            hasMedia = false
        ),
        DeletedMessageItem(
            id = "del_2",
            senderName = "Alex Rivera",
            senderNumber = "+1 (555) 778-1029",
            messageContent = "The client sent the wrong invoice amount for wire #8491 ($14,500 instead of $11,500).",
            originalTimestamp = "09:48:02 AM",
            deletedTimestamp = "09:49:15 AM (Revoked 1m 13s later)",
            chatName = "Direct Chat",
            hasMedia = false
        ),
        DeletedMessageItem(
            id = "del_3",
            senderName = "Sarah Jenkins",
            senderNumber = "+1 (555) 602-4411",
            messageContent = "Meeting room shifted to Boardroom 3 on 4th floor.",
            originalTimestamp = "Yesterday, 16:20",
            deletedTimestamp = "Yesterday, 16:21 (Revoked 45s later)",
            chatName = "Product Strategy Group",
            hasMedia = false
        )
    )
)

class AutomationRepository {

    private val _data = MutableStateFlow(AutomationData())
    val data: StateFlow<AutomationData> = _data.asStateFlow()

    private fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
    }

    private fun log(tag: String, desc: String, success: Boolean): ActivityLogItem {
        return ActivityLogItem(
            id = "log_" + UUID.randomUUID().toString().take(6),
            timestamp = getCurrentTime(),
            tag = tag,
            description = desc,
            isSuccess = success
        )
    }

    fun toggleConnection() {
        val current = _data.value.session.status
        val isConn = current == ConnectionState.CONNECTED
        val newStatus = if (isConn) ConnectionState.DISCONNECTED else ConnectionState.CONNECTED
        val newLog = if (isConn) {
            log("SESSION", "WhatsApp session disconnected by user", false)
        } else {
            log("SESSION", "WhatsApp connected via local device sandbox", true)
        }
        _data.value = _data.value.copy(
            session = _data.value.session.copy(
                status = newStatus,
                batteryLevel = if (isConn) _data.value.session.batteryLevel else (85..99).random()
            ),
            activityLogs = listOf(newLog) + _data.value.activityLogs.take(40)
        )
    }

    fun requestPairingCode(phone: String) {
        val randomCode = "${('A'..'Z').random()}${('0'..'9').random()}${('A'..'Z').random()}${('0'..'9').random()}-${('A'..'Z').random()}${('0'..'9').random()}${('A'..'Z').random()}${('0'..'9').random()}"
        val newLog = log("PAIR", "Pairing code $randomCode generated for $phone", true)
        _data.value = _data.value.copy(
            session = _data.value.session.copy(
                status = ConnectionState.PAIRING,
                phoneNumber = phone,
                pairingCode = randomCode
            ),
            activityLogs = listOf(newLog) + _data.value.activityLogs.take(40)
        )
    }

    fun completePairing() {
        val newLog = log("PAIR", "Device pairing established successfully", true)
        _data.value = _data.value.copy(
            session = _data.value.session.copy(status = ConnectionState.CONNECTED),
            activityLogs = listOf(newLog) + _data.value.activityLogs.take(40)
        )
    }

    fun updateLiveLocation(target: String, lat: Double, lng: Double, duration: Int, caption: String) {
        val newLog = log("GPS", "Live location dispatched to $target ($lat, $lng)", true)
        _data.value = _data.value.copy(
            liveLocation = _data.value.liveLocation.copy(
                targetJid = target,
                latitude = lat,
                longitude = lng,
                durationMinutes = duration,
                caption = caption,
                isStreaming = true
            ),
            activityLogs = listOf(newLog) + _data.value.activityLogs.take(40)
        )
    }

    fun stopLiveLocation() {
        val newLog = log("GPS", "Live location stream stopped", true)
        _data.value = _data.value.copy(
            liveLocation = _data.value.liveLocation.copy(isStreaming = false),
            activityLogs = listOf(newLog) + _data.value.activityLogs.take(40)
        )
    }

    fun sendFakeQuote(recipient: String, reply: String, quotedSender: String, quotedSenderName: String, quotedText: String) {
        val newLog = log("QUOTE", "Dispatched fake quoted context to $recipient", true)
        _data.value = _data.value.copy(
            fakeQuote = FakeQuoteMessage(
                recipient = recipient,
                replyText = reply,
                quotedSender = quotedSender,
                quotedSenderName = quotedSenderName,
                quotedText = quotedText,
                timestamp = System.currentTimeMillis()
            ),
            activityLogs = listOf(newLog) + _data.value.activityLogs.take(40)
        )
    }

    fun addScheduledMessage(recipient: String, content: String, type: ScheduledMessageType, delay: String) {
        val newItem = ScheduledMessageItem(
            id = "sch_" + UUID.randomUUID().toString().take(6),
            recipient = recipient,
            content = content,
            type = type,
            scheduledTime = delay,
            status = ScheduleStatus.PENDING
        )
        val newLog = log("QUEUE", "Message scheduled for $recipient ($type)", true)
        _data.value = _data.value.copy(
            scheduledMessages = listOf(newItem) + _data.value.scheduledMessages,
            activityLogs = listOf(newLog) + _data.value.activityLogs.take(40)
        )
    }

    fun cancelScheduledMessage(id: String) {
        val newLog = log("QUEUE", "Scheduled task $id cancelled", false)
        _data.value = _data.value.copy(
            scheduledMessages = _data.value.scheduledMessages.map {
                if (it.id == id) it.copy(status = ScheduleStatus.CANCELLED) else it
            },
            activityLogs = listOf(newLog) + _data.value.activityLogs.take(40)
        )
    }

    fun dispatchScheduledMessageNow(id: String) {
        val newLog = log("QUEUE", "Scheduled message $id immediately dispatched", true)
        _data.value = _data.value.copy(
            scheduledMessages = _data.value.scheduledMessages.map {
                if (it.id == id) it.copy(status = ScheduleStatus.SENT) else it
            },
            activityLogs = listOf(newLog) + _data.value.activityLogs.take(40)
        )
    }

    fun updateButtonConfig(config: ButtonMessageConfig) {
        val newLog = log("BUTTONS", "Interactive button payload configured for ${config.recipient}", true)
        _data.value = _data.value.copy(
            buttonConfig = config,
            activityLogs = listOf(newLog) + _data.value.activityLogs.take(40)
        )
    }

    fun addAutoReplyRule(keyword: String, matchType: String, response: String) {
        val newRule = AutoReplyRuleItem(
            id = "rule_" + UUID.randomUUID().toString().take(6),
            keyword = keyword,
            matchType = matchType,
            response = response,
            isEnabled = true
        )
        val newLog = log("RULES", "Created auto-reply trigger '$keyword'", true)
        _data.value = _data.value.copy(
            autoReplyRules = _data.value.autoReplyRules + newRule,
            activityLogs = listOf(newLog) + _data.value.activityLogs.take(40)
        )
    }

    fun toggleAutoReplyRule(id: String) {
        _data.value = _data.value.copy(
            autoReplyRules = _data.value.autoReplyRules.map {
                if (it.id == id) it.copy(isEnabled = !it.isEnabled) else it
            }
        )
    }

    fun deleteAutoReplyRule(id: String) {
        val newLog = log("RULES", "Removed auto-reply rule $id", false)
        _data.value = _data.value.copy(
            autoReplyRules = _data.value.autoReplyRules.filterNot { it.id == id },
            activityLogs = listOf(newLog) + _data.value.activityLogs.take(40)
        )
    }

    fun updateBotSettings(settings: BotSettings) {
        val newLog = log("SETTINGS", "Automation engine preferences updated", true)
        _data.value = _data.value.copy(
            botSettings = settings,
            activityLogs = listOf(newLog) + _data.value.activityLogs.take(40)
        )
    }

    // =========================================================================
    // VIEW ONCE DOWNLOADER ACTIONS
    // =========================================================================

    fun saveViewOnceToGallery(id: String) {
        val item = _data.value.viewOnceMedia.find { it.id == id }
        val newLog = log("ONEVIEW", "Exported View Once media ${item?.id} to device gallery", true)
        _data.value = _data.value.copy(
            viewOnceMedia = _data.value.viewOnceMedia.map {
                if (it.id == id) it.copy(isSavedToGallery = true) else it
            },
            activityLogs = listOf(newLog) + _data.value.activityLogs.take(40)
        )
    }

    fun deleteViewOnceItem(id: String) {
        val newLog = log("ONEVIEW", "Purged View Once media $id from device sandbox", false)
        _data.value = _data.value.copy(
            viewOnceMedia = _data.value.viewOnceMedia.filterNot { it.id == id },
            activityLogs = listOf(newLog) + _data.value.activityLogs.take(40)
        )
    }

    fun simulateIncomingViewOnce(sender: String, type: ViewOnceMediaType, caption: String) {
        val newItem = ViewOnceMediaItem(
            id = "vo_" + UUID.randomUUID().toString().take(6),
            senderName = sender,
            senderNumber = "+1 (555) 77" + (1000..9999).random(),
            mediaType = type,
            caption = caption,
            timestamp = getCurrentTime(),
            fileSize = when (type) {
                ViewOnceMediaType.IMAGE -> "${(1.2 + Math.random() * 2).format(1)} MB"
                ViewOnceMediaType.VIDEO -> "${(8.5 + Math.random() * 12).format(1)} MB"
                ViewOnceMediaType.AUDIO_NOTE -> "${(400..950).random()} KB"
            },
            isSavedToGallery = false,
            previewGradientColor = when (type) {
                ViewOnceMediaType.IMAGE -> 0xFF00A884
                ViewOnceMediaType.VIDEO -> 0xFF38BDF8
                ViewOnceMediaType.AUDIO_NOTE -> 0xFFF59E0B
            }
        )
        val newLog = log("ONEVIEW", "Intercepted incoming View Once ($type) from $sender", true)
        _data.value = _data.value.copy(
            viewOnceMedia = listOf(newItem) + _data.value.viewOnceMedia,
            activityLogs = listOf(newLog) + _data.value.activityLogs.take(40)
        )
    }

    // =========================================================================
    // ANTI-DELETE LOGGER ACTIONS
    // =========================================================================

    fun clearAllDeletedMessages() {
        val newLog = log("ANTIDELETE", "Cleared all archived deleted messages", false)
        _data.value = _data.value.copy(
            deletedMessages = emptyList(),
            activityLogs = listOf(newLog) + _data.value.activityLogs.take(40)
        )
    }

    fun simulateIncomingDeletedMessage(sender: String, content: String, chat: String) {
        val timeNow = getCurrentTime()
        val newItem = DeletedMessageItem(
            id = "del_" + UUID.randomUUID().toString().take(6),
            senderName = sender,
            senderNumber = "+1 (555) 88" + (1000..9999).random(),
            messageContent = content,
            originalTimestamp = "$timeNow (Sent)",
            deletedTimestamp = "$timeNow (Revoked via Delete for Everyone)",
            chatName = chat,
            hasMedia = false
        )
        val newLog = log("ANTIDELETE", "Logged revoked message from $sender in $chat", true)
        _data.value = _data.value.copy(
            deletedMessages = listOf(newItem) + _data.value.deletedMessages,
            activityLogs = listOf(newLog) + _data.value.activityLogs.take(40)
        )
    }

    private fun Double.format(digits: Int) = String.format(Locale.US, "%.${digits}f", this)
}
