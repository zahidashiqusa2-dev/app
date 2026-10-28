package com.example

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun DeviceSandboxBadge(
    deviceId: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, Color(0xFF10B981).copy(alpha = 0.3f), RoundedCornerShape(20.dp)),
        color = Color(0xFF064E3B).copy(alpha = 0.35f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = "Device Sandbox",
                tint = Color(0xFF34D399),
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = "SANDBOX: ${deviceId.takeLast(8).uppercase()}",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF6EE7B7),
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MetricStatCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier,
    testTagId: String = ""
) {
    Card(
        modifier = modifier
            .then(if (testTagId.isNotEmpty()) Modifier.testTag(testTagId) else Modifier),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkSurface
        ),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.linearGradient(
                colors = listOf(DarkBorder, accentColor.copy(alpha = 0.3f))
            )
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    color = DarkTextSecondary
                )
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(accentColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = accentColor,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = DarkTextPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = DarkTextMuted,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun WhatsAppQuotedBubblePreview(
    quotedSender: String,
    quotedText: String,
    replyText: String,
    timestamp: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 4.dp))
            .background(Color(0xFF005C4B)) // WhatsApp outbound chat bubble green
            .padding(10.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            // Quoted container inside the bubble
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF025144))
                    .padding(8.dp)
            ) {
                // Vertical green accent bar
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(36.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFF25D366))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = quotedSender,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF25D366),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = quotedText,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFE2E8F0),
                        maxLines = 2
                    )
                }
            }

            // The main reply text
            Text(
                text = replyText,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            // Timestamp and read receipt ticks
            Row(
                modifier = Modifier.align(Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF86EFAC),
                    fontSize = 10.sp
                )
                Row {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Read",
                        tint = Color(0xFF38BDF8),
                        modifier = Modifier.size(12.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Read",
                        tint = Color(0xFF38BDF8),
                        modifier = Modifier
                            .size(12.dp)
                            .padding(start = (-4).dp)
                    )
                }
            }
        }
    }
}

@Composable
fun RadarLiveLocationView(
    lat: Double,
    lng: Double,
    isStreaming: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "RadarTransition")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "RadarSweep"
    )

    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = 8f,
        targetValue = 28f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "BeaconPulse"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF0F1E19))
            .border(1.dp, Color(0xFF00A884).copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxWidth().height(200.dp)) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val maxR = minOf(size.width, size.height) * 0.44f

            // Concentric range circles
            drawCircle(
                color = Color(0xFF00A884).copy(alpha = 0.15f),
                radius = maxR * 0.33f,
                center = center,
                style = Stroke(width = 1.dp.toPx())
            )
            drawCircle(
                color = Color(0xFF00A884).copy(alpha = 0.2f),
                radius = maxR * 0.66f,
                center = center,
                style = Stroke(width = 1.dp.toPx())
            )
            drawCircle(
                color = Color(0xFF00A884).copy(alpha = 0.3f),
                radius = maxR,
                center = center,
                style = Stroke(width = 1.5.dp.toPx())
            )

            // Crosshairs
            drawLine(
                color = Color(0xFF00A884).copy(alpha = 0.2f),
                start = Offset(center.x - maxR, center.y),
                end = Offset(center.x + maxR, center.y),
                strokeWidth = 1.dp.toPx()
            )
            drawLine(
                color = Color(0xFF00A884).copy(alpha = 0.2f),
                start = Offset(center.x, center.y - maxR),
                end = Offset(center.x, center.y + maxR),
                strokeWidth = 1.dp.toPx()
            )

            if (isStreaming) {
                // Radar sweep line
                val rad = Math.toRadians(angle.toDouble())
                val sweepEnd = Offset(
                    (center.x + maxR * cos(rad)).toFloat(),
                    (center.y + maxR * sin(rad)).toFloat()
                )
                drawLine(
                    color = Color(0xFF34D399),
                    start = center,
                    end = sweepEnd,
                    strokeWidth = 2.dp.toPx()
                )

                // Beacon pulse circle
                drawCircle(
                    color = Color(0xFF10B981).copy(alpha = 0.4f),
                    radius = pulseRadius,
                    center = center
                )
            }

            // Center location pin
            drawCircle(
                color = Color(0xFF25D366),
                radius = 7.dp.toPx(),
                center = center
            )
            drawCircle(
                color = Color.White,
                radius = 3.dp.toPx(),
                center = center
            )
        }

        // Overlay status info
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            Text(
                text = "GPS: ${String.format("%.4f", lat)}, ${String.format("%.4f", lng)}",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF6EE7B7),
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (isStreaming) "📡 Real-time GPS stream active" else "⏸️ GPS Standby",
                style = MaterialTheme.typography.bodySmall,
                color = if (isStreaming) Color(0xFF34D399) else DarkTextMuted,
                fontSize = 11.sp
            )
        }
    }
}
