package com.example

enum class ConnectionState {
    DISCONNECTED,
    PAIRING,
    CONNECTED
}

data class WhatsAppSession(
    val status: ConnectionState = ConnectionState.DISCONNECTED,
    val phoneNumber: String = "+1 555-0199",
    val pushName: String = "Urooj Choudhary",
    val deviceId: String = "dev_urooj_8f48dcc7",
    val pairingCode: String = "7K92-B48M",
    val batteryLevel: Int = 94,
    val platform: String = "Android Local Engine",
    val isSandboxIsolated: Boolean = true
)

data class LiveLocationConfig(
    val targetJid: String = "+1234567890",
    val latitude: Double = 37.7749,
    val longitude: Double = -122.4194,
    val accuracy: Float = 15.0f,
    val durationMinutes: Int = 60,
    val caption: String = "📍 Live on-site meeting at headquarters",
    val isStreaming: Boolean = false
)

data class FakeQuoteMessage(
    val recipient: String = "+1 987 654 3210",
    val replyText: String = "Yes, payment has been successfully recorded in the ledger! 🎉",
    val quotedSender: String = "+1 555 123 4567",
    val quotedSenderName: String = "Finance Team",
    val quotedText: String = "Did you receive the wire transfer of $1,250 for invoice #4928?",
    val timestamp: Long = System.currentTimeMillis()
)

enum class ScheduledMessageType {
    TEXT,
    LIVE_LOCATION,
    FAKE_QUOTE,
    BUTTON_MSG,
    VOICE_NOTE,
    PTV_VIDEO
}

enum class ScheduleStatus {
    PENDING,
    SENT,
    CANCELLED
}

data class ScheduledMessageItem(
    val id: String,
    val recipient: String,
    val content: String,
    val type: ScheduledMessageType,
    val scheduledTime: String,
    val status: ScheduleStatus = ScheduleStatus.PENDING
)

enum class ButtonType {
    QUICK_REPLY,
    URL,
    CALL
}

data class InteractiveButtonItem(
    val id: String,
    val label: String,
    val type: ButtonType,
    val actionValue: String
)

data class ButtonMessageConfig(
    val recipient: String = "+1 555-8888",
    val headerText: String = "VIP Concierge Alert 👑",
    val bodyText: String = "Welcome to the Urooj Automated Service. Please select an option below:",
    val footerText: String = "Powered by Urooj Choudhary Suite",
    val buttons: List<InteractiveButtonItem> = listOf(
        InteractiveButtonItem("b1", "✅ Confirm Appointment", ButtonType.QUICK_REPLY, "CONFIRM_APPT"),
        InteractiveButtonItem("b2", "🌐 Visit Dashboard", ButtonType.URL, "https://urooj-suite.local"),
        InteractiveButtonItem("b3", "📞 Call Direct Support", ButtonType.CALL, "+18005550199")
    )
)

data class AutoReplyRuleItem(
    val id: String,
    val keyword: String,
    val matchType: String, // "exact", "contains", "regex"
    val response: String,
    val isEnabled: Boolean = true
)

data class ActivityLogItem(
    val id: String,
    val timestamp: String,
    val tag: String,
    val description: String,
    val isSuccess: Boolean = true
)

data class BotSettings(
    val autoRead: Boolean = true,
    val autoType: Boolean = true,
    val autoRecord: Boolean = false,
    val autoStatusReact: Boolean = true,
    val autoStatusReply: Boolean = false,
    val aiAssistantEnabled: Boolean = true,
    val aiPersonality: String = "Executive Concierge",
    val antiDeleteActive: Boolean = true,
    val viewOnceInterceptionActive: Boolean = true,
    val deviceSandboxLocked: Boolean = true
)

// =========================================================================
// ONE VIEW (VIEW ONCE) & ANTI-DELETE MODELS
// =========================================================================

enum class ViewOnceMediaType {
    IMAGE,
    VIDEO,
    AUDIO_NOTE
}

data class ViewOnceMediaItem(
    val id: String,
    val senderName: String,
    val senderNumber: String,
    val mediaType: ViewOnceMediaType,
    val caption: String,
    val timestamp: String,
    val fileSize: String,
    val isSavedToGallery: Boolean = false,
    val previewGradientColor: Long = 0xFF00A884
)

data class DeletedMessageItem(
    val id: String,
    val senderName: String,
    val senderNumber: String,
    val messageContent: String,
    val originalTimestamp: String,
    val deletedTimestamp: String,
    val chatName: String,
    val hasMedia: Boolean = false,
    val mediaTypeHint: String = ""
)
