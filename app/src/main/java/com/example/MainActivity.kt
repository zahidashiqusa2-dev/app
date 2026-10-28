package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UroojTheme {
                MainAppContent()
            }
        }
    }
}

@Composable
fun MainAppContent(
    viewModel: AutomationViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    // Handle back button when on any screen other than Dashboard
    if (state.currentScreen != AppScreen.DASHBOARD) {
        BackHandler {
            viewModel.navigateTo(AppScreen.DASHBOARD)
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = DarkSurface,
                contentColor = WhatsAppGreen,
                modifier = Modifier.testTag("bottom_nav_bar")
            ) {
                val navItems = listOf(
                    Triple(AppScreen.DASHBOARD, Icons.Default.Dashboard, "Home"),
                    Triple(AppScreen.ONE_VIEW, Icons.Default.RemoveRedEye, "One View"),
                    Triple(AppScreen.DELETED_LOGS, Icons.Default.Shield, "Anti-Delete"),
                    Triple(AppScreen.LIVE_LOCATION, Icons.Default.LocationOn, "Live GPS"),
                    Triple(AppScreen.FAKE_QUOTE, Icons.Default.FormatQuote, "Quote"),
                    Triple(AppScreen.SCHEDULER, Icons.Default.Schedule, "Queue"),
                    Triple(AppScreen.AUTO_REPLY, Icons.Default.SmartToy, "Rules")
                )

                navItems.forEach { (screen, icon, label) ->
                    val isSelected = state.currentScreen == screen
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { viewModel.navigateTo(screen) },
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = label,
                                fontSize = 10.sp,
                                maxLines = 1
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = WhatsAppLightGreen,
                            indicatorColor = when (screen) {
                                AppScreen.ONE_VIEW -> Color(0xFF0284C7).copy(alpha = 0.4f)
                                AppScreen.DELETED_LOGS -> Color(0xFFEF4444).copy(alpha = 0.4f)
                                else -> WhatsAppGreen.copy(alpha = 0.35f)
                            },
                            unselectedIconColor = DarkTextMuted,
                            unselectedTextColor = DarkTextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_${screen.name.lowercase()}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBg)
                .padding(innerPadding)
        ) {
            when (state.currentScreen) {
                AppScreen.DASHBOARD -> DashboardScreen(
                    state = state,
                    onNavigate = { viewModel.navigateTo(it) },
                    onToggleConnection = { viewModel.toggleConnection() }
                )
                AppScreen.ONE_VIEW -> OneViewDownloaderScreen(
                    state = state,
                    onSaveToGallery = { viewModel.saveViewOnceToGallery(it) },
                    onDeleteItem = { viewModel.deleteViewOnceItem(it) },
                    onSimulateCapture = { sender, type, caption ->
                        viewModel.simulateIncomingViewOnce(sender, type, caption)
                    }
                )
                AppScreen.DELETED_LOGS -> DeletedMessageLoggerScreen(
                    state = state,
                    onClearAll = { viewModel.clearAllDeletedMessages() },
                    onSimulateRevoke = { sender, text, chat ->
                        viewModel.simulateIncomingDeletedMessage(sender, text, chat)
                    }
                )
                AppScreen.PAIRING -> PairingScreen(
                    state = state,
                    onRequestCode = { viewModel.requestPairingCode(it) },
                    onCompletePairing = { viewModel.completePairing() }
                )
                AppScreen.LIVE_LOCATION -> LiveLocationScreen(
                    state = state,
                    onUpdateLocation = { target, lat, lng, duration, caption ->
                        viewModel.updateLiveLocation(target, lat, lng, duration, caption)
                    },
                    onStopLocation = { viewModel.stopLiveLocation() }
                )
                AppScreen.FAKE_QUOTE -> FakeQuoteScreen(
                    state = state,
                    onSendQuote = { recipient, reply, quotedSender, quotedName, quotedText ->
                        viewModel.sendFakeQuote(recipient, reply, quotedSender, quotedName, quotedText)
                    }
                )
                AppScreen.SCHEDULER -> SchedulerScreen(
                    state = state,
                    onScheduleMessage = { recipient, content, type, delay ->
                        viewModel.scheduleMessage(recipient, content, type, delay)
                    },
                    onCancelMessage = { viewModel.cancelScheduledMessage(it) },
                    onDispatchNow = { viewModel.dispatchMessageNow(it) }
                )
                AppScreen.BUTTONS -> ButtonsScreen(
                    state = state,
                    onUpdateConfig = { viewModel.updateButtonConfig(it) }
                )
                AppScreen.AUTO_REPLY -> AutoReplyScreen(
                    state = state,
                    onAddRule = { keyword, matchType, response ->
                        viewModel.addAutoReplyRule(keyword, matchType, response)
                    },
                    onToggleRule = { viewModel.toggleAutoReplyRule(it) },
                    onDeleteRule = { viewModel.deleteAutoReplyRule(it) },
                    onUpdateBotSettings = { viewModel.updateBotSettings(it) }
                )
            }
        }
    }
}
