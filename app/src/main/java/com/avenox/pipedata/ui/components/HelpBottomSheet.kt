package com.avenox.pipedata.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenox.pipedata.data.model.AppLanguage
import com.avenox.pipedata.data.model.HelpTopic
import com.avenox.pipedata.ui.theme.LocalCadColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpBottomSheet(
    topic: HelpTopic,
    language: AppLanguage,
    fontScale: Float,
    onDismiss: () -> Unit
) {
    val colors = LocalCadColors.current
    val title = if (language == AppLanguage.TR) topic.titleTr else topic.titleEn
    val body = if (language == AppLanguage.TR) topic.bodyTr else topic.bodyEn
    val formula = if (language == AppLanguage.TR) topic.formulaTr else topic.formulaEn

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = colors.surface,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .width(40.dp)
                    .height(4.dp)
                    .background(colors.border, RoundedCornerShape(2.dp))
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .navigationBarsPadding()
        ) {
            // ASME Badge
            Box(
                modifier = Modifier
                    .background(colors.chipBg, RoundedCornerShape(6.dp))
                    .border(1.dp, colors.border, RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = topic.badge,
                    color = colors.accent,
                    fontSize = (11 * fontScale).sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title and Close
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = colors.textPrimary,
                    fontSize = (18 * fontScale).sp,
                    fontWeight = FontWeight.ExtraBold
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = colors.textSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Body text
            Text(
                text = body,
                color = colors.textSecondary,
                fontSize = (14 * fontScale).sp,
                lineHeight = (20 * fontScale).sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Formula box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.background, RoundedCornerShape(8.dp))
                    .border(1.dp, colors.border, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = formula,
                    color = colors.accent,
                    fontSize = (12.5 * fontScale).sp,
                    fontFamily = FontFamily.Monospace,
                    lineHeight = (18 * fontScale).sp
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Got it button
            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.accent,
                    contentColor = colors.accentText
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (language == AppLanguage.TR) "Anladım" else "Got it",
                    fontWeight = FontWeight.Bold,
                    fontSize = (14 * fontScale).sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
