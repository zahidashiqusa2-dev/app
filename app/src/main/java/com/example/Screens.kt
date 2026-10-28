package com.example

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// =========================================================================
// 1. DASHBOARD SCREEN
// =========================================================================
@Composable
fun DashboardScreen(
    state: AutomationUiState,
    onNavigate: (AppScreen) -> Unit,
    onToggleConnection: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "👑 Urooj Choudhary",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "WhatsApp Automation Engine",
                        style = MaterialTheme.typography.bodySmall,
                        color = BrandAmberLight
                    )
                }
                DeviceSandboxBadge(deviceId = state.session.deviceId)
            }
        }

        // Connection Status Banner Card
        item {
            val isConnected = state.session.status == ConnectionState.CONNECTED
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isConnected) Color(0xFF063326) else Color(0xFF2E1515)
                ),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(
                        if (isConnected) Color(0xFF00A884) else Color(0xFFEF4444)
                    )
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("connection_banner_card")
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(if (isConnected) Color(0xFF25D366) else Color(0xFFEF4444))
                            )
                            Text(
                                text = if (isConnected) "WhatsApp Connected" else "WhatsApp Disconnected",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.Black.copy(alpha = 0.3f)
                        ) {
                            Text(
                                text = "⚡ ${state.session.batteryLevel}%",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Account: ${state.session.pushName}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFE2E8F0)
                            )
                            Text(
                                text = "Number: ${state.session.phoneNumber}",
                                style = MaterialTheme.typography.bodySmall,
                                color = DarkTextMuted,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Button(
                            onClick = onToggleConnection,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isConnected) Color(0xFFB91C1C) else WhatsAppGreen
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("toggle_connect_button")
                        ) {
                            Text(
                                text = if (isConnected) "Disconnect" else "Connect Now",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Privacy & Capture Quick Banner
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1E28)),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF38BDF8).copy(alpha = 0.4f))
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("🛡️ PRIVACY VAULT SHIELD", style = MaterialTheme.typography.labelSmall, color = Color(0xFF38BDF8), fontWeight = FontWeight.Bold)
                            Surface(shape = RoundedCornerShape(6.dp), color = Color(0xFF0369A1).copy(alpha = 0.3f)) {
                                Text("ACTIVE", style = MaterialTheme.typography.labelSmall, color = Color(0xFF7DD3FC), modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), fontSize = 9.sp)
                            }
                        }
                        Text(
                            text = "${state.metrics.viewOnceIntercepted} View Once media captured • ${state.metrics.revokedMessagesLogged} Deleted messages logged",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFE2E8F0)
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        IconButton(onClick = { onNavigate(AppScreen.ONE_VIEW) }) {
                            Icon(Icons.Default.RemoveRedEye, contentDescription = "View Once", tint = Color(0xFF38BDF8))
                        }
                        IconButton(onClick = { onNavigate(AppScreen.DELETED_LOGS) }) {
                            Icon(Icons.Default.Shield, contentDescription = "Anti-Delete", tint = Color(0xFFF87171))
                        }
                    }
                }
            }
        }

        // Metrics Grid (2x2)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "ENGINE TELEMETRY",
                    style = MaterialTheme.typography.labelSmall,
                    color = DarkTextSecondary,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricStatCard(
                        title = "ONE VIEW VAULT",
                        value = "${state.metrics.viewOnceIntercepted}",
                        subtitle = "Saved media",
                        icon = Icons.Default.RemoveRedEye,
                        accentColor = Color(0xFF38BDF8),
                        modifier = Modifier.weight(1f),
                        testTagId = "metric_one_view"
                    )
                    MetricStatCard(
                        title = "REVOKED LOGGED",
                        value = "${state.metrics.revokedMessagesLogged}",
                        subtitle = "Anti-delete active",
                        icon = Icons.Default.Shield,
                        accentColor = Color(0xFFF87171),
                        modifier = Modifier.weight(1f),
                        testTagId = "metric_deleted_logs"
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricStatCard(
                        title = "ACTIVE RULES",
                        value = "${state.metrics.activeRules}",
                        subtitle = "Keyword & AI",
                        icon = Icons.Default.SmartToy,
                        accentColor = BrandAmber,
                        modifier = Modifier.weight(1f),
                        testTagId = "metric_rules"
                    )
                    MetricStatCard(
                        title = "SCHEDULED QUEUE",
                        value = "${state.metrics.scheduledPending}",
                        subtitle = "Pending dispatch",
                        icon = Icons.Default.Schedule,
                        accentColor = Color(0xFFA78BFA),
                        modifier = Modifier.weight(1f),
                        testTagId = "metric_queue"
                    )
                }
            }
        }

        // Feature Quick Access Bar
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "QUICK SHORTCUTS",
                    style = MaterialTheme.typography.labelSmall,
                    color = DarkTextSecondary,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FeatureShortcutChip(
                        label = "👁️ One View",
                        onClick = { onNavigate(AppScreen.ONE_VIEW) },
                        modifier = Modifier.weight(1f),
                        testTagId = "shortcut_one_view"
                    )
                    FeatureShortcutChip(
                        label = "🗑️ Anti-Delete",
                        onClick = { onNavigate(AppScreen.DELETED_LOGS) },
                        modifier = Modifier.weight(1f),
                        testTagId = "shortcut_anti_delete"
                    )
                    FeatureShortcutChip(
                        label = "📍 Live GPS",
                        onClick = { onNavigate(AppScreen.LIVE_LOCATION) },
                        modifier = Modifier.weight(1f),
                        testTagId = "shortcut_gps"
                    )
                    FeatureShortcutChip(
                        label = "💬 Fake Quote",
                        onClick = { onNavigate(AppScreen.FAKE_QUOTE) },
                        modifier = Modifier.weight(1f),
                        testTagId = "shortcut_quote"
                    )
                }
            }
        }

        // Live Activity Logs
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "LIVE ACTIVITY STREAM",
                        style = MaterialTheme.typography.labelSmall,
                        color = DarkTextSecondary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "SANDBOX LOCAL",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF10B981),
                        fontFamily = FontFamily.Monospace
                    )
                }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurface),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        state.activityLogs.take(5).forEach { log ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = log.timestamp,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = DarkTextMuted,
                                    fontFamily = FontFamily.Monospace
                                )
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = Color(0xFF1E293B)
                                ) {
                                    Text(
                                        text = log.tag,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = BrandAmberLight,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        fontSize = 9.sp
                                    )
                                }
                                Text(
                                    text = log.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = DarkTextPrimary,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun FeatureShortcutChip(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTagId: String = ""
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .then(if (testTagId.isNotEmpty()) Modifier.testTag(testTagId) else Modifier),
        color = DarkSurface,
        border = CardDefaults.outlinedCardBorder()
    ) {
        Box(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// =========================================================================
// 2. ONE VIEW DOWNLOADER SCREEN
// =========================================================================
@Composable
fun OneViewDownloaderScreen(
    state: AutomationUiState,
    onSaveToGallery: (String) -> Unit,
    onDeleteItem: (String) -> Unit,
    onSimulateCapture: (String, ViewOnceMediaType, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showSimulateDialog by remember { mutableStateOf(false) }
    var inspectingItem by remember { mutableStateOf<ViewOnceMediaItem?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "View Once Vault 👁️",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Auto-intercept & preserve ephemeral media without expiration",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF38BDF8)
                    )
                }

                Button(
                    onClick = { showSimulateDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("simulate_view_once_btn")
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Simulate", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Capture Demo", fontSize = 12.sp)
                }
            }
        }

        // Summary Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1E28)),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF0284C7).copy(alpha = 0.5f))
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("VAULT STATUS: ACTIVE", style = MaterialTheme.typography.labelSmall, color = Color(0xFF38BDF8), fontWeight = FontWeight.Bold)
                        Text(
                            text = "${state.viewOnceMedia.size} Intercepted Files • Encrypted Sandbox",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Sender is never alerted; media never expires",
                            style = MaterialTheme.typography.bodySmall,
                            color = DarkTextMuted,
                            fontSize = 11.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF0369A1).copy(alpha = 0.25f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.RemoveRedEye, contentDescription = "Vault", tint = Color(0xFF38BDF8))
                    }
                }
            }
        }

        // Media Item Cards
        items(state.viewOnceMedia, key = { it.id }) { item ->
            ViewOnceMediaCard(
                item = item,
                onSave = { onSaveToGallery(item.id) },
                onDelete = { onDeleteItem(item.id) },
                onInspect = { inspectingItem = item }
            )
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }

    if (showSimulateDialog) {
        SimulateViewOnceDialog(
            onDismiss = { showSimulateDialog = false },
            onConfirm = { sender, type, caption ->
                onSimulateCapture(sender, type, caption)
                showSimulateDialog = false
            }
        )
    }

    inspectingItem?.let { item ->
        ViewOnceInspectorDialog(
            item = item,
            onDismiss = { inspectingItem = null },
            onSave = {
                onSaveToGallery(item.id)
                inspectingItem = null
            }
        )
    }
}

@Composable
fun ViewOnceMediaCard(
    item: ViewOnceMediaItem,
    onSave: () -> Unit,
    onDelete: () -> Unit,
    onInspect: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = CardDefaults.outlinedCardBorder(),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("view_once_card_${item.id}")
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(item.previewGradientColor).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (item.mediaType) {
                                ViewOnceMediaType.IMAGE -> Icons.Default.Image
                                ViewOnceMediaType.VIDEO -> Icons.Default.Videocam
                                ViewOnceMediaType.AUDIO_NOTE -> Icons.Default.Audiotrack
                            },
                            contentDescription = item.mediaType.name,
                            tint = Color(item.previewGradientColor),
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Column {
                        Text(
                            text = item.senderName,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${item.senderNumber} • ${item.timestamp}",
                            style = MaterialTheme.typography.bodySmall,
                            color = DarkTextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF1E293B)
                ) {
                    Text(
                        text = item.fileSize,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF38BDF8),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp
                    )
                }
            }

            // Media Preview Canvas Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF0F172A))
                    .clickable(onClick = onInspect)
                    .border(1.dp, Color(item.previewGradientColor).copy(alpha = 0.25f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "Inspect",
                        tint = Color(item.previewGradientColor),
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        text = "Tap to Inspect & Play",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF94A3B8),
                        fontSize = 11.sp
                    )
                }
            }

            if (item.caption.isNotEmpty()) {
                Text(
                    text = item.caption,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFE2E8F0)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (item.isSavedToGallery) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF065F46).copy(alpha = 0.5f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = "Saved", tint = Color(0xFF34D399), modifier = Modifier.size(14.dp))
                            Text("Saved to Gallery", style = MaterialTheme.typography.labelSmall, color = Color(0xFF6EE7B7), fontSize = 11.sp)
                        }
                    }
                } else {
                    Button(
                        onClick = onSave,
                        colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("save_gallery_btn_${item.id}")
                    ) {
                        Icon(Icons.Default.Download, contentDescription = "Save", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Save to Gallery", color = Color.White, fontSize = 12.sp)
                    }
                }

                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFF87171))
                }
            }
        }
    }
}

@Composable
fun SimulateViewOnceDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, ViewOnceMediaType, String) -> Unit
) {
    var sender by remember { mutableStateOf("Elena Rostova") }
    var selectedType by remember { mutableStateOf(ViewOnceMediaType.IMAGE) }
    var caption by remember { mutableStateOf("Confidential client wire breakdown 📄") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Simulate Incoming View Once", color = Color.White) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = sender,
                    onValueChange = { sender = it },
                    label = { Text("Sender Name") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = WhatsAppGreen,
                        unfocusedBorderColor = DarkBorder
                    )
                )

                Text("MEDIA TYPE", style = MaterialTheme.typography.labelSmall, color = DarkTextSecondary)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    ViewOnceMediaType.values().forEach { type ->
                        FilterChip(
                            selected = selectedType == type,
                            onClick = { selectedType = type },
                            label = { Text(type.name, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF0284C7),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                OutlinedTextField(
                    value = caption,
                    onValueChange = { caption = it },
                    label = { Text("Caption") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = WhatsAppGreen,
                        unfocusedBorderColor = DarkBorder
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(sender, selectedType, caption) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
            ) {
                Text("Intercept Media", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = DarkTextSecondary)
            }
        },
        containerColor = DarkSurface
    )
}

@Composable
fun ViewOnceInspectorDialog(
    item: ViewOnceMediaItem,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Media Vault Preview", color = Color.White, fontWeight = FontWeight.Bold)
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = DarkTextMuted)
                }
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // High-resolution preview area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF080E18))
                        .border(1.dp, Color(item.previewGradientColor).copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = when (item.mediaType) {
                                ViewOnceMediaType.IMAGE -> Icons.Default.Image
                                ViewOnceMediaType.VIDEO -> Icons.Default.Videocam
                                ViewOnceMediaType.AUDIO_NOTE -> Icons.Default.Audiotrack
                            },
                            contentDescription = item.mediaType.name,
                            tint = Color(item.previewGradientColor),
                            modifier = Modifier.size(52.dp)
                        )
                        Text(
                            text = "${item.mediaType.name} DECRYPTED",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(item.previewGradientColor),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = item.fileSize,
                            style = MaterialTheme.typography.bodySmall,
                            color = DarkTextMuted,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("FROM: ${item.senderName} (${item.senderNumber})", style = MaterialTheme.typography.labelSmall, color = DarkTextSecondary)
                    Text("TIME: ${item.timestamp}", style = MaterialTheme.typography.labelSmall, color = DarkTextSecondary)
                    if (item.caption.isNotEmpty()) {
                        Text(item.caption, style = MaterialTheme.typography.bodyMedium, color = Color.White)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onSave,
                colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen)
            ) {
                Icon(Icons.Default.Download, contentDescription = "Save", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Export to Device Gallery", color = Color.White)
            }
        },
        containerColor = DarkSurface
    )
}

// =========================================================================
// 3. DELETED MESSAGE LOGGER SCREEN
// =========================================================================
@Composable
fun DeletedMessageLoggerScreen(
    state: AutomationUiState,
    onClearAll: () -> Unit,
    onSimulateRevoke: (String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var showRevokeDialog by remember { mutableStateOf(false) }

    val filteredList = state.deletedMessages.filter {
        it.senderName.contains(searchQuery, ignoreCase = true) ||
        it.messageContent.contains(searchQuery, ignoreCase = true) ||
        it.chatName.contains(searchQuery, ignoreCase = true)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Anti-Delete Archive 🗑️",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Preserves messages erased by 'Delete for Everyone'",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFF87171)
                    )
                }

                Button(
                    onClick = { showRevokeDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("simulate_revoke_btn")
                ) {
                    Icon(Icons.Default.Warning, contentDescription = "Revoke Demo", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Revoke Demo", fontSize = 12.sp)
                }
            }
        }

        // Status Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF261214)),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFEF4444).copy(alpha = 0.5f))
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFFEF4444)))
                            Text("ANTI-REVOKE ENGINE ACTIVE", style = MaterialTheme.typography.labelSmall, color = Color(0xFFF87171), fontWeight = FontWeight.Bold)
                        }
                        Text(
                            text = "${state.deletedMessages.size} Revoked Messages Intercepted",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Logs original text, sender ID, and revocation diff",
                            style = MaterialTheme.typography.bodySmall,
                            color = DarkTextMuted,
                            fontSize = 11.sp
                        )
                    }

                    if (state.deletedMessages.isNotEmpty()) {
                        IconButton(onClick = onClearAll) {
                            Icon(Icons.Default.Delete, contentDescription = "Clear All", tint = Color(0xFFF87171))
                        }
                    }
                }
            }
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search deleted messages or sender...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = DarkTextMuted) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFEF4444),
                    unfocusedBorderColor = DarkBorder
                )
            )
        }

        // Revoked Message List
        items(filteredList, key = { it.id }) { item ->
            DeletedMessageCard(item = item)
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }

    if (showRevokeDialog) {
        SimulateRevokeDialog(
            onDismiss = { showRevokeDialog = false },
            onConfirm = { sender, text, chat ->
                onSimulateRevoke(sender, text, chat)
                showRevokeDialog = false
            }
        )
    }
}

@Composable
fun DeletedMessageCard(item: DeletedMessageItem) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFEF4444).copy(alpha = 0.35f))
        ),
        modifier = Modifier.fillMaxWidth().testTag("deleted_msg_card_${item.id}")
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEF4444).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.senderName.take(1).uppercase(),
                            style = MaterialTheme.typography.labelMedium,
                            color = Color(0xFFF87171),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Column {
                        Text(
                            text = item.senderName,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "in ${item.chatName} • ${item.senderNumber}",
                            style = MaterialTheme.typography.bodySmall,
                            color = DarkTextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF7F1D1D).copy(alpha = 0.5f)
                ) {
                    Text(
                        text = "REVOKED",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFFCA5A5),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 9.sp
                    )
                }
            }

            // Message Bubble with WhatsApp Styling and Revocation Indicator
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF1E293B))
                    .padding(12.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = item.messageContent,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Original: ${item.originalTimestamp}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF94A3B8),
                            fontSize = 10.sp
                        )
                        Text(
                            text = item.deletedTimestamp,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFF87171),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SimulateRevokeDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    var sender by remember { mutableStateOf("Jordan Vance") }
    var chat by remember { mutableStateOf("Executive VIP Group") }
    var content by remember { mutableStateOf("Hold the wire transfer! The bank routing code changed to 021000021.") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Simulate Message Revocation", color = Color.White) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = sender,
                    onValueChange = { sender = it },
                    label = { Text("Sender Name") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFEF4444),
                        unfocusedBorderColor = DarkBorder
                    )
                )
                OutlinedTextField(
                    value = chat,
                    onValueChange = { chat = it },
                    label = { Text("Chat / Group Name") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFEF4444),
                        unfocusedBorderColor = DarkBorder
                    )
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Message Content Erased by Sender") },
                    minLines = 2,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFEF4444),
                        unfocusedBorderColor = DarkBorder
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(sender, content, chat) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626))
            ) {
                Text("Log Revoked Message", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = DarkTextSecondary)
            }
        },
        containerColor = DarkSurface
    )
}

// =========================================================================
// 4. PAIRING SCREEN (Phone Pairing Code & QR Scanner)
// =========================================================================
@Composable
fun PairingScreen(
    state: AutomationUiState,
    onRequestCode: (String) -> Unit,
    onCompletePairing: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var inputPhone by remember { mutableStateOf(state.session.phoneNumber) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Device Pairing Studio",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = DarkSurface,
            contentColor = WhatsAppGreen
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Phone Pairing Code") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("QR Code Scanner") }
            )
        }

        if (selectedTab == 0) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = CardDefaults.outlinedCardBorder(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Link Device via 8-Digit Code",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Enter your WhatsApp phone number with country code. A native pairing code will be generated to enter in WhatsApp > Linked Devices > Link with Phone Number.",
                        style = MaterialTheme.typography.bodySmall,
                        color = DarkTextSecondary
                    )

                    OutlinedTextField(
                        value = inputPhone,
                        onValueChange = { inputPhone = it },
                        label = { Text("WhatsApp Phone Number") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("pairing_phone_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WhatsAppGreen,
                            unfocusedBorderColor = DarkBorder
                        )
                    )

                    Button(
                        onClick = { onRequestCode(inputPhone) },
                        colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("request_pairing_code_btn")
                    ) {
                        Text("Generate 8-Digit Pairing Code", color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    // Display Generated Code Box
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF0F172A),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = androidx.compose.ui.graphics.SolidColor(BrandAmber)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "ENTER THIS CODE IN WHATSAPP:",
                                style = MaterialTheme.typography.labelSmall,
                                color = BrandAmberLight
                            )
                            Text(
                                text = state.session.pairingCode,
                                style = MaterialTheme.typography.headlineLarge,
                                color = Color.White,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 4.sp
                            )
                            Text(
                                text = "Valid for 60 seconds • Local secure exchange",
                                style = MaterialTheme.typography.bodySmall,
                                color = DarkTextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = onCompletePairing,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Confirm Linked State", color = WhatsAppLightGreen)
                    }
                }
            }
        } else {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = CardDefaults.outlinedCardBorder(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.QrCode,
                        contentDescription = "QR Code",
                        tint = WhatsAppGreen,
                        modifier = Modifier.size(140.dp)
                    )
                    Text(
                        text = "Scan QR with WhatsApp",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Open WhatsApp on your primary phone > Linked Devices > Link a Device > Scan this QR Code.",
                        style = MaterialTheme.typography.bodySmall,
                        color = DarkTextSecondary
                    )
                    Button(
                        onClick = onCompletePairing,
                        colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Simulate QR Scanned", color = Color.White)
                    }
                }
            }
        }

        // Device Sandbox Info Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1E19)),
            border = CardDefaults.outlinedCardBorder().copy(
                brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF00A884).copy(alpha = 0.4f))
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "🔒 100% DEVICE-BOUND ISOLATION",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF34D399),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Your WhatsApp cryptographic keys (`creds.json`) and session tokens are strictly bound to this Android device instance: ${state.session.deviceId}.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFE2E8F0)
                )
            }
        }
    }
}

// =========================================================================
// 5. LIVE LOCATION SCREEN
// =========================================================================
@Composable
fun LiveLocationScreen(
    state: AutomationUiState,
    onUpdateLocation: (String, Double, Double, Int, String) -> Unit,
    onStopLocation: () -> Unit,
    modifier: Modifier = Modifier
) {
    var target by remember { mutableStateOf(state.liveLocation.targetJid) }
    var lat by remember { mutableDoubleStateOf(state.liveLocation.latitude) }
    var lng by remember { mutableDoubleStateOf(state.liveLocation.longitude) }
    var duration by remember { mutableIntStateOf(state.liveLocation.durationMinutes) }
    var caption by remember { mutableStateOf(state.liveLocation.caption) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Native Live Location Studio",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Stream real-time GPS telemetry into WhatsApp chats or groups with dynamic duration.",
                style = MaterialTheme.typography.bodySmall,
                color = DarkTextSecondary
            )
        }

        item {
            RadarLiveLocationView(
                lat = lat,
                lng = lng,
                isStreaming = state.liveLocation.isStreaming
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = CardDefaults.outlinedCardBorder(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = target,
                        onValueChange = { target = it },
                        label = { Text("Target Phone / Group JID") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("location_target_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WhatsAppGreen,
                            unfocusedBorderColor = DarkBorder
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = lat.toString(),
                            onValueChange = { lat = it.toDoubleOrNull() ?: lat },
                            label = { Text("Latitude") },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = WhatsAppGreen,
                                unfocusedBorderColor = DarkBorder
                            )
                        )
                        OutlinedTextField(
                            value = lng.toString(),
                            onValueChange = { lng = it.toDoubleOrNull() ?: lng },
                            label = { Text("Longitude") },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = WhatsAppGreen,
                                unfocusedBorderColor = DarkBorder
                            )
                        )
                    }

                    Text(
                        text = "STREAM DURATION",
                        style = MaterialTheme.typography.labelSmall,
                        color = DarkTextSecondary
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(15 to "15 Mins", 60 to "1 Hour", 480 to "8 Hours").forEach { (mins, label) ->
                            FilterChip(
                                selected = duration == mins,
                                onClick = { duration = mins },
                                label = { Text(label) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = WhatsAppGreen,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    OutlinedTextField(
                        value = caption,
                        onValueChange = { caption = it },
                        label = { Text("Location Caption") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WhatsAppGreen,
                            unfocusedBorderColor = DarkBorder
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { onUpdateLocation(target, lat, lng, duration, caption) },
                            colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("broadcast_location_btn")
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Start")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Broadcast Live", color = Color.White, fontWeight = FontWeight.Bold)
                        }

                        if (state.liveLocation.isStreaming) {
                            Button(
                                onClick = onStopLocation,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB91C1C)),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.testTag("stop_location_btn")
                            ) {
                                Icon(Icons.Default.Stop, contentDescription = "Stop")
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// =========================================================================
// 6. FAKE QUOTE SCREEN (Quoted Message Generator)
// =========================================================================
@Composable
fun FakeQuoteScreen(
    state: AutomationUiState,
    onSendQuote: (String, String, String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var recipient by remember { mutableStateOf(state.fakeQuote.recipient) }
    var quotedSender by remember { mutableStateOf(state.fakeQuote.quotedSender) }
    var quotedSenderName by remember { mutableStateOf(state.fakeQuote.quotedSenderName) }
    var quotedText by remember { mutableStateOf(state.fakeQuote.quotedText) }
    var replyText by remember { mutableStateOf(state.fakeQuote.replyText) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Fake Quoted Message Studio",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Dispatch replies with synthetic quoted message context in native WhatsApp protocol.",
                style = MaterialTheme.typography.bodySmall,
                color = DarkTextSecondary
            )
        }

        // WhatsApp Bubble Live Preview
        item {
            Text(
                text = "WHATSAPP CHAT PREVIEW",
                style = MaterialTheme.typography.labelSmall,
                color = BrandAmberLight,
                fontWeight = FontWeight.Bold
            )
            WhatsAppQuotedBubblePreview(
                quotedSender = quotedSenderName.ifEmpty { quotedSender },
                quotedText = quotedText,
                replyText = replyText,
                timestamp = "Just now"
            )
        }

        // Input Form
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = CardDefaults.outlinedCardBorder(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = recipient,
                        onValueChange = { recipient = it },
                        label = { Text("Recipient Phone Number") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("quote_recipient_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WhatsAppGreen,
                            unfocusedBorderColor = DarkBorder
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = quotedSenderName,
                            onValueChange = { quotedSenderName = it },
                            label = { Text("Quoted Sender Name") },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = WhatsAppGreen,
                                unfocusedBorderColor = DarkBorder
                            )
                        )
                        OutlinedTextField(
                            value = quotedSender,
                            onValueChange = { quotedSender = it },
                            label = { Text("Quoted Number") },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = WhatsAppGreen,
                                unfocusedBorderColor = DarkBorder
                            )
                        )
                    }

                    OutlinedTextField(
                        value = quotedText,
                        onValueChange = { quotedText = it },
                        label = { Text("Quoted Context / Message Text") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("quote_text_input"),
                        minLines = 2,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WhatsAppGreen,
                            unfocusedBorderColor = DarkBorder
                        )
                    )

                    OutlinedTextField(
                        value = replyText,
                        onValueChange = { replyText = it },
                        label = { Text("Your Reply Content") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("quote_reply_input"),
                        minLines = 2,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WhatsAppGreen,
                            unfocusedBorderColor = DarkBorder
                        )
                    )

                    Button(
                        onClick = {
                            onSendQuote(recipient, replyText, quotedSender, quotedSenderName, quotedText)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("dispatch_quote_btn")
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Dispatch Quoted Reply", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// =========================================================================
// 7. SCHEDULER SCREEN (Scheduled Message Queue)
// =========================================================================
@Composable
fun SchedulerScreen(
    state: AutomationUiState,
    onScheduleMessage: (String, String, ScheduledMessageType, String) -> Unit,
    onCancelMessage: (String) -> Unit,
    onDispatchNow: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Scheduled Message Queue",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${state.scheduledMessages.count { it.status == ScheduleStatus.PENDING }} messages pending dispatch",
                            style = MaterialTheme.typography.bodySmall,
                            color = DarkTextSecondary
                        )
                    }
                }
            }

            items(state.scheduledMessages, key = { it.id }) { item ->
                ScheduledItemCard(
                    item = item,
                    onCancel = { onCancelMessage(item.id) },
                    onDispatchNow = { onDispatchNow(item.id) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        FloatingActionButton(
            onClick = { showDialog = true },
            containerColor = WhatsAppGreen,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 80.dp, end = 20.dp)
                .testTag("add_schedule_fab")
        ) {
            Icon(Icons.Default.Add, contentDescription = "Schedule New Message")
        }
    }

    if (showDialog) {
        CreateScheduleDialog(
            onDismiss = { showDialog = false },
            onConfirm = { recipient, content, type, delay ->
                onScheduleMessage(recipient, content, type, delay)
                showDialog = false
            }
        )
    }
}

@Composable
fun ScheduledItemCard(
    item: ScheduledMessageItem,
    onCancel: () -> Unit,
    onDispatchNow: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = CardDefaults.outlinedCardBorder(),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = when (item.status) {
                        ScheduleStatus.PENDING -> BrandAmber.copy(alpha = 0.2f)
                        ScheduleStatus.SENT -> WhatsAppGreen.copy(alpha = 0.2f)
                        ScheduleStatus.CANCELLED -> Color(0xFFEF4444).copy(alpha = 0.2f)
                    }
                ) {
                    Text(
                        text = item.status.name,
                        style = MaterialTheme.typography.labelSmall,
                        color = when (item.status) {
                            ScheduleStatus.PENDING -> BrandAmberLight
                            ScheduleStatus.SENT -> Color(0xFF34D399)
                            ScheduleStatus.CANCELLED -> Color(0xFFF87171)
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = item.scheduledTime,
                    style = MaterialTheme.typography.labelSmall,
                    color = DarkTextMuted,
                    fontFamily = FontFamily.Monospace
                )
            }

            Text(
                text = "To: ${item.recipient}",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = item.content,
                style = MaterialTheme.typography.bodySmall,
                color = DarkTextSecondary
            )

            if (item.status == ScheduleStatus.PENDING) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onCancel) {
                        Text("Cancel", color = Color(0xFFF87171))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onDispatchNow,
                        colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Dispatch Now", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun CreateScheduleDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, ScheduledMessageType, String) -> Unit
) {
    var recipient by remember { mutableStateOf("+1 555-4019") }
    var content by remember { mutableStateOf("") }
    var delay by remember { mutableStateOf("In 15 minutes") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Schedule Automated Message", color = Color.White) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = recipient,
                    onValueChange = { recipient = it },
                    label = { Text("Recipient") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = WhatsAppGreen,
                        unfocusedBorderColor = DarkBorder
                    )
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Message Content") },
                    minLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = WhatsAppGreen,
                        unfocusedBorderColor = DarkBorder
                    )
                )
                OutlinedTextField(
                    value = delay,
                    onValueChange = { delay = it },
                    label = { Text("Execution Time / Delay") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = WhatsAppGreen,
                        unfocusedBorderColor = DarkBorder
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(recipient, content, ScheduledMessageType.TEXT, delay) },
                colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen)
            ) {
                Text("Schedule Message", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = DarkTextSecondary)
            }
        },
        containerColor = DarkSurface
    )
}

// =========================================================================
// 8. BUTTONS SCREEN (Interactive Button Messenger)
// =========================================================================
@Composable
fun ButtonsScreen(
    state: AutomationUiState,
    onUpdateConfig: (ButtonMessageConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    var recipient by remember { mutableStateOf(state.buttonConfig.recipient) }
    var header by remember { mutableStateOf(state.buttonConfig.headerText) }
    var body by remember { mutableStateOf(state.buttonConfig.bodyText) }
    var footer by remember { mutableStateOf(state.buttonConfig.footerText) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Interactive Button Messenger",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Compose rich interactive quick-reply and call-to-action buttons for WhatsApp.",
                style = MaterialTheme.typography.bodySmall,
                color = DarkTextSecondary
            )
        }

        // WhatsApp Interactive Card Live Preview
        item {
            Text(
                text = "WHATSAPP CARD PREVIEW",
                style = MaterialTheme.typography.labelSmall,
                color = BrandAmberLight,
                fontWeight = FontWeight.Bold
            )

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF005C4B)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (header.isNotEmpty()) {
                        Text(
                            text = header,
                            style = MaterialTheme.typography.titleMedium,
                            color = BrandAmberLight,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = body,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                    if (footer.isNotEmpty()) {
                        Text(
                            text = footer,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF86EFAC)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    state.buttonConfig.buttons.forEach { btn ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF025144),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier.padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = btn.label,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color(0xFF38BDF8),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Form Builder
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = CardDefaults.outlinedCardBorder(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = recipient,
                        onValueChange = { recipient = it },
                        label = { Text("Recipient Number") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WhatsAppGreen,
                            unfocusedBorderColor = DarkBorder
                        )
                    )
                    OutlinedTextField(
                        value = header,
                        onValueChange = { header = it },
                        label = { Text("Header Text") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WhatsAppGreen,
                            unfocusedBorderColor = DarkBorder
                        )
                    )
                    OutlinedTextField(
                        value = body,
                        onValueChange = { body = it },
                        label = { Text("Body Text") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WhatsAppGreen,
                            unfocusedBorderColor = DarkBorder
                        )
                    )
                    OutlinedTextField(
                        value = footer,
                        onValueChange = { footer = it },
                        label = { Text("Footer Text") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WhatsAppGreen,
                            unfocusedBorderColor = DarkBorder
                        )
                    )

                    Button(
                        onClick = {
                            onUpdateConfig(
                                state.buttonConfig.copy(
                                    recipient = recipient,
                                    headerText = header,
                                    bodyText = body,
                                    footerText = footer
                                )
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("send_button_msg_btn")
                    ) {
                        Text("Send Interactive Buttons", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// =========================================================================
// 9. AUTO-REPLY & BOT RULES SCREEN
// =========================================================================
@Composable
fun AutoReplyScreen(
    state: AutomationUiState,
    onAddRule: (String, String, String) -> Unit,
    onToggleRule: (String) -> Unit,
    onDeleteRule: (String) -> Unit,
    onUpdateBotSettings: (BotSettings) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Automation & AI Rules",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Configure keyword triggers and simulated presence indicators.",
                        style = MaterialTheme.typography.bodySmall,
                        color = DarkTextSecondary
                    )
                }
                IconButton(
                    onClick = { showAddDialog = true },
                    modifier = Modifier.testTag("add_rule_btn")
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Rule", tint = WhatsAppGreen)
                }
            }
        }

        // Presence & Bot Features Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = CardDefaults.outlinedCardBorder(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "PRESENCE & AI ASSISTANT",
                        style = MaterialTheme.typography.labelSmall,
                        color = BrandAmberLight,
                        fontWeight = FontWeight.Bold
                    )

                    ToggleSettingRow(
                        title = "Auto-Read Messages",
                        subtitle = "Send blue double-tick read receipt automatically",
                        checked = state.botSettings.autoRead,
                        onCheckedChange = { onUpdateBotSettings(state.botSettings.copy(autoRead = it)) }
                    )
                    ToggleSettingRow(
                        title = "Auto-Type Presence",
                        subtitle = "Display 'typing...' status prior to dispatch",
                        checked = state.botSettings.autoType,
                        onCheckedChange = { onUpdateBotSettings(state.botSettings.copy(autoType = it)) }
                    )
                    ToggleSettingRow(
                        title = "AI Chatbot Mode",
                        subtitle = "Generate smart replies with Gemini AI logic",
                        checked = state.botSettings.aiAssistantEnabled,
                        onCheckedChange = { onUpdateBotSettings(state.botSettings.copy(aiAssistantEnabled = it)) }
                    )
                    ToggleSettingRow(
                        title = "Anti-Delete Message Logger",
                        subtitle = "Retain messages revoked by contacts",
                        checked = state.botSettings.antiDeleteActive,
                        onCheckedChange = { onUpdateBotSettings(state.botSettings.copy(antiDeleteActive = it)) }
                    )
                    ToggleSettingRow(
                        title = "One View Auto-Interceptor",
                        subtitle = "Save incoming View Once media to vault immediately",
                        checked = state.botSettings.viewOnceInterceptionActive,
                        onCheckedChange = { onUpdateBotSettings(state.botSettings.copy(viewOnceInterceptionActive = it)) }
                    )
                }
            }
        }

        // Trigger Rules List
        item {
            Text(
                text = "ACTIVE KEYWORD RULES (${state.autoReplyRules.size})",
                style = MaterialTheme.typography.labelSmall,
                color = DarkTextSecondary,
                fontWeight = FontWeight.Bold
            )
        }

        items(state.autoReplyRules, key = { it.id }) { rule ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = CardDefaults.outlinedCardBorder(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = rule.keyword,
                                style = MaterialTheme.typography.titleMedium,
                                color = BrandAmberLight,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFF1E293B)
                            ) {
                                Text(
                                    text = rule.matchType.uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFF38BDF8),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    fontSize = 9.sp
                                )
                            }
                        }
                        Text(
                            text = rule.response,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFE2E8F0)
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Switch(
                            checked = rule.isEnabled,
                            onCheckedChange = { onToggleRule(rule.id) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = WhatsAppGreen
                            )
                        )
                        IconButton(onClick = { onDeleteRule(rule.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Rule", tint = Color(0xFFF87171))
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }

    if (showAddDialog) {
        var keyword by remember { mutableStateOf("") }
        var matchType by remember { mutableStateOf("contains") }
        var response by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("New Auto-Reply Trigger", color = Color.White) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = keyword,
                        onValueChange = { keyword = it },
                        label = { Text("Trigger Keyword (e.g. .menu)") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WhatsAppGreen,
                            unfocusedBorderColor = DarkBorder
                        )
                    )
                    OutlinedTextField(
                        value = response,
                        onValueChange = { response = it },
                        label = { Text("Reply Message Text") },
                        minLines = 2,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WhatsAppGreen,
                            unfocusedBorderColor = DarkBorder
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (keyword.isNotEmpty() && response.isNotEmpty()) {
                            onAddRule(keyword, matchType, response)
                            showAddDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen)
                ) {
                    Text("Add Trigger", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel", color = DarkTextSecondary)
                }
            },
            containerColor = DarkSurface
        )
    }
}

@Composable
fun ToggleSettingRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = DarkTextMuted,
                fontSize = 11.sp
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = WhatsAppGreen
            )
        )
    }
}
