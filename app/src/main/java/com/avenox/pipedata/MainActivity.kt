package com.avenox.pipedata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale
import kotlin.math.*

private val Background = Color(0xFF052824)
private val Deep = Color(0xFF031D1B)
private val Panel = Color(0xFF0C3934)
private val StrokeColor = Color(0xFF4B716B)
private val Gold = Color(0xFFFFC23C)
private val White = Color(0xFFF5F7F4)
private val Muted = Color(0xFFBED0CC)

private fun decimal(value: Double, digits: Int = 2): String =
    String.format(Locale.forLanguageTag("tr-TR"), "%." + digits + "f", value)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val catalog = runCatching {
            assets.open("pipe_schedules.csv").bufferedReader().use { PipeCatalog.load(it) }
        }.getOrNull()
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Gold, onPrimary = Deep,
                    background = Background, onBackground = White,
                    surface = Panel, onSurface = White
                )
            ) {
                if (catalog == null) {
                    Box(Modifier.fillMaxSize().background(Background).padding(24.dp), contentAlignment = Alignment.Center) {
                        Text("Boru standart tablosu yüklenemedi. Uygulamayı güncelleyin.", color = White, fontSize = 20.sp)
                    }
                } else {
                    PipeApp(catalog)
                }
            }
        }
    }
}

@Composable
private fun PipeApp(catalog: PipeCatalog) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("pipe_preferences_v2", 0) }
    var unitImperial by rememberSaveable { mutableStateOf(prefs.getBoolean("imperial", false)) }
    var largeText by rememberSaveable { mutableStateOf(prefs.getBoolean("large_text", true)) }
    var page by rememberSaveable { mutableIntStateOf(0) }
    var standard by rememberSaveable { mutableStateOf("B36.10") }
    var nps by rememberSaveable { mutableStateOf("6") }
    var schedule by rememberSaveable { mutableStateOf("40") }
    // B36.10 and B36.19 are different sources, but the user sees one union list.
    // The actual standard always comes from the selected catalog record.
    val sizes = remember(catalog) { catalog.rows.map { it.nps }.distinct() }
    val activeNps = nps.takeIf { it in sizes } ?: sizes.first()
    val schedules = remember(activeNps) {
        catalog.rows.filter { it.nps == activeNps }.map { it.schedule }
            .distinct().sortedWith(compareBy<String> {
                it.filter(Char::isDigit).toIntOrNull() ?: Int.MAX_VALUE
            }.thenBy { it })
    }
    val activeSch = schedule.takeIf { it in schedules }
        ?: ("40".takeIf { it in schedules } ?: schedules.first())
    val pipe = catalog.rows.firstOrNull {
        it.nps == activeNps && it.schedule == activeSch && it.standard == standard
    } ?: catalog.rows.firstOrNull { it.nps == activeNps && it.schedule == activeSch }
    val normalSp = if (largeText) 18 else 16

    LaunchedEffect(activeNps, activeSch, pipe?.standard) {
        if (nps != activeNps) nps = activeNps
        if (schedule != activeSch) schedule = activeSch
        if (pipe != null && standard != pipe.standard) standard = pipe.standard
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Background).systemBarsPadding()
    ) {
        Box(Modifier.fillMaxWidth().heightIn(min = 62.dp).padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center) {
            Text("Boru Hesaplayıcı", color = White, fontWeight = FontWeight.SemiBold,
                fontSize = if (largeText) 25.sp else 22.sp, textAlign = TextAlign.Center)
        }
        Box(Modifier.weight(1f)) {
            when (page) {
                0 -> CalculatorPage(
                    standard, { standard = it }, activeNps, sizes,
                    onNps = { nps = it }, activeSch, schedules,
                    onSch = { selectedSch ->
                        catalog.rows.firstOrNull { it.nps == activeNps &&
                            it.schedule == selectedSch && it.standard == standard }
                            ?.let { standard = it.standard }
                            ?: catalog.rows.firstOrNull { it.nps == activeNps &&
                                it.schedule == selectedSch }?.let { standard = it.standard }
                        schedule = selectedSch
                    }, pipe, unitImperial, normalSp,
                    catalog = catalog, onChoose = { chosen ->
                        standard = chosen.standard; nps = chosen.nps; schedule = chosen.schedule
                    }
                )
                1 -> LookupPage(catalog, standard, normalSp, unitImperial)
                2 -> CombinedTableStandardsPage(catalog, activeNps, sizes,
                    { nps = it }, normalSp, onSelect = { chosen ->
                        standard = chosen.standard; nps = chosen.nps
                        schedule = chosen.schedule; page = 0
                    })
                else -> SettingsPage(
                    largeText, { largeText = it; prefs.edit().putBoolean("large_text", it).apply() },
                    unitImperial, { unitImperial = it; prefs.edit().putBoolean("imperial", it).apply() },
                    normalSp
                )
            }
        }
        Row(
            Modifier.fillMaxWidth().background(Deep).navigationBarsPadding().padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val items = listOf(
                Triple("Boru", Icons.Default.Tune, 0),
                Triple("SCH Bul", Icons.Default.Search, 1),
                Triple("Tablo/Standart", Icons.Default.MenuBook, 2),
                Triple("Ayarlar", Icons.Default.Settings, 3)
            )
            items.forEach { (label, icon, index) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(8.dp))
                        .clickable { page = index }.padding(vertical = 8.dp)
                        .semantics { contentDescription = label }
                ) {
                    Icon(icon, contentDescription = null, tint = if (page == index) Gold else Muted, modifier = Modifier.size(26.dp))
                    Spacer(Modifier.height(2.dp))
                    Text(label, color = if (page == index) Gold else Muted, fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
private fun Picker(label: String, value: String, options: List<String>, onChange: (String) -> Unit, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Column(modifier) {
        Text(label, color = Muted, fontSize = 14.sp, modifier = Modifier.padding(start = 4.dp, bottom = 4.dp))
        Box {
            Row(
                modifier = Modifier.fillMaxWidth().heightIn(min = 55.dp)
                    .clip(RoundedCornerShape(18.dp)).border(1.5.dp, StrokeColor, RoundedCornerShape(18.dp))
                    .clickable { expanded = true }.padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(value, color = White, fontSize = 19.sp, fontWeight = FontWeight.SemiBold, maxLines = 1)
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Seç", tint = Gold)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, fontSize = 20.sp) },
                        modifier = Modifier.heightIn(min = 49.dp),
                        onClick = { onChange(option); expanded = false }
                    )
                }
            }
        }
    }
}

@Composable
private fun GoldButton(label: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth().heightIn(min = 62.dp).clickable(onClick = onClick),
        shape = RoundedCornerShape(28.dp), color = Gold
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Icon(Icons.Default.Calculate, contentDescription = null, tint = Deep, modifier = Modifier.size(30.dp))
            Spacer(Modifier.width(12.dp))
            Text(label, color = Deep, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        }
    }
}

@Composable
private fun CalculatorPage(
    standard: String, setStandard: (String) -> Unit,
    nps: String, sizes: List<String>, onNps: (String) -> Unit,
    schedule: String, schedules: List<String>, onSch: (String) -> Unit,
    pipe: PipeSpec?, imperial: Boolean, textSp: Int,
    catalog: PipeCatalog, onChoose: (PipeSpec) -> Unit
) {
    var editing by remember { mutableStateOf<String?>(null) }
    var showLength by remember { mutableStateOf(false) }
    val index = sizes.indexOf(nps).coerceAtLeast(0)
    val step: (Int) -> Unit = { delta ->
        onNps(sizes[(index + delta + sizes.size) % sizes.size])
    }
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth().heightIn(min = 62.dp)
                .clip(RoundedCornerShape(18.dp))
                .border(1.5.dp, StrokeColor, RoundedCornerShape(18.dp))
                .clickable { editing = "NPS" }.padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("NPS (inç)", color = Muted, fontSize = 19.sp)
            Text(nps + "″", color = White, fontSize = 27.sp, fontWeight = FontWeight.Bold)
            Icon(Icons.Default.ArrowDropDown, contentDescription = "NPS seç", tint = Gold)
        }
        Spacer(Modifier.height(10.dp))
        Text("Schedule (SCH)", color = White, fontSize = 20.sp,
            fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start)
                .clickable { editing = "SCH" }.padding(vertical = 5.dp))
        // Mixed numeric and S schedules: only physically available catalog rows.
        schedules.chunked(4).forEach { rowValues ->
            Row(Modifier.fillMaxWidth().padding(bottom = 7.dp),
                horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                rowValues.forEach { sch ->
                    Surface(
                        color = if (sch == schedule) Gold else Panel,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f).heightIn(min = 53.dp)
                            .border(1.dp, if (sch == schedule) Gold else StrokeColor,
                                RoundedCornerShape(16.dp)).clickable { onSch(sch) }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(sch, color = if (sch == schedule) Deep else White,
                                fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }
                    }
                }
                repeat(4 - rowValues.size) { Spacer(Modifier.weight(1f)) }
            }
        }
        Spacer(Modifier.height(8.dp))
        PipeDial(nps, step, pipe, imperial) { editing = it }
        Spacer(Modifier.height(18.dp))
        if (pipe == null) {
            Text("Bu NPS ve Schedule için doğrulanmış ölçü yok.", color = Gold, fontSize = textSp.sp)
        } else {
            ResultRows(pipe, imperial, textSp) { editing = it }
        }
        Spacer(Modifier.height(18.dp))
        GoldButton("Hesapla", { showLength = true }, Modifier.fillMaxWidth())
        Spacer(Modifier.height(20.dp))
    }
    if (showLength && pipe != null) LengthDialog(pipe, imperial) { showLength = false }
    editing?.let { field ->
        NumericPipeDialog(field, standard, nps, schedule, pipe, imperial, catalog,
            onDismiss = { editing = null },
            onChoose = { chosen -> onChoose(chosen); editing = null })
    }
}

/** Entirely procedural dial; no image assets or bitmap transformations. */
@Composable
private fun PipeDial(nps: String, onStep: (Int) -> Unit, pipe: PipeSpec?,
    imperial: Boolean, onEdit: (String) -> Unit) {
    val currentOnStep by rememberUpdatedState(onStep)
    var wheelRotation by remember { mutableFloatStateOf(0f) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        BoxWithConstraints(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            val dialSide = minOf(maxWidth, 285.dp)
            Box(
                modifier = Modifier.size(dialSide)
                    .semantics {
                        contentDescription = "Döner NPS sayacı, seçili çap " + nps + " inç"
                        onClick(label = "Sonraki boru çapı") {
                            currentOnStep(1)
                            true
                        }
                    }
                    .pointerInput(Unit) {
                        var previousAngle = 0f
                        var remainder = 0f
                        var draggingRing = false
                        val stepAngle = 0.22f
                        detectDragGestures(
                            onDragStart = { point ->
                                val dx = point.x - size.width / 2f
                                val dy = point.y - size.height / 2f
                                val outer = minOf(size.width, size.height) / 2f
                                draggingRing = hypot(dx, dy) in (outer * 0.42f)..outer
                                previousAngle = atan2(dy, dx)
                                remainder = 0f
                            },
                            onDragCancel = { draggingRing = false; remainder = 0f },
                            onDragEnd = { draggingRing = false; remainder = 0f }
                        ) { change, _ ->
                            if (draggingRing) {
                                change.consume()
                                val dx = change.position.x - size.width / 2f
                                val dy = change.position.y - size.height / 2f
                                val angle = atan2(dy, dx)
                                val delta = atan2(sin(angle - previousAngle), cos(angle - previousAngle))
                                previousAngle = angle
                                wheelRotation =
                                    (wheelRotation + Math.toDegrees(delta.toDouble()).toFloat()) % 360f
                                remainder += delta
                                while (abs(remainder) >= stepAngle) {
                                    val direction = if (remainder > 0f) 1 else -1
                                    currentOnStep(direction)
                                    remainder -= direction * stepAngle
                                }
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Canvas(Modifier.fillMaxSize()) {
                    val c = center
                    val r = size.minDimension / 2f
                    val outerTick = r * 0.935f
                    val innerTick = r * 0.875f
                    val metalRadius = r * 0.675f
                    val metalWidth = r * 0.145f

                    rotate(wheelRotation, pivot = c) {
                        for (index in 0 until 64) {
                            val angle = (index * 2.0 * PI / 64.0 - PI / 2.0).toFloat()
                            val major = index % 8 == 0
                            val from = if (major) innerTick - r * 0.014f else innerTick
                            drawLine(
                                color = Color(0xFFE6ECE9),
                                start = Offset(c.x + cos(angle) * from, c.y + sin(angle) * from),
                                end = Offset(c.x + cos(angle) * outerTick, c.y + sin(angle) * outerTick),
                                strokeWidth = if (major) r * 0.024f else r * 0.018f,
                                cap = StrokeCap.Round
                            )
                        }
                        drawCircle(Color(0xFF152421), metalRadius + metalWidth * 0.56f)
                        drawCircle(
                            brush = Brush.sweepGradient(
                                listOf(
                                    Color(0xFF69726F), Color(0xFFF3F3EE),
                                    Color(0xFF8E9694), Color(0xFF434B49),
                                    Color(0xFFD2D5CF), Color(0xFF717976),
                                    Color(0xFF69726F)
                                ),
                                center = c
                            ),
                            radius = metalRadius,
                            style = Stroke(width = metalWidth)
                        )
                        // Thin concentric machined grooves: no texture allocations.
                        for (index in 0 until 24) {
                            val t = index / 23f
                            val grooveRadius = metalRadius - metalWidth * 0.47f +
                                metalWidth * 0.94f * t
                            val alpha = if (index % 3 == 0) 0.22f else 0.10f
                            drawCircle(
                                color = if (index % 2 == 0) Color.White.copy(alpha = alpha)
                                    else Color.Black.copy(alpha = alpha),
                                radius = grooveRadius,
                                style = Stroke(width = r * 0.0022f)
                            )
                        }
                        drawCircle(Color(0xFFE8ECE7), metalRadius - metalWidth * 0.49f,
                            style = Stroke(width = r * 0.011f))
                        drawCircle(Color(0xFF33413E), metalRadius - metalWidth * 0.55f,
                            style = Stroke(width = r * 0.013f))
                        drawCircle(Color(0xFF454D4A), metalRadius + metalWidth * 0.51f,
                            style = Stroke(width = r * 0.009f))
                    }

                    if (pipe != null) {
                        val bore = metalRadius - metalWidth * 0.55f
                        val y = c.y + r * 0.22f
                        val half = sqrt(maxOf(0f, bore * bore - (y - c.y) * (y - c.y)))
                        drawLine(White, Offset(c.x - half, y), Offset(c.x + half, y),
                            strokeWidth = r * 0.009f)
                        for (side in listOf(-1f, 1f)) {
                            val tip = Offset(c.x + side * half, y)
                            drawLine(White, tip, Offset(tip.x - side * r * 0.065f, y - r * 0.030f),
                                strokeWidth = r * 0.01f)
                            drawLine(White, tip, Offset(tip.x - side * r * 0.065f, y + r * 0.030f),
                                strokeWidth = r * 0.01f)
                        }
                        val anchor = Offset(c.x + r * 0.43f, c.y - r * 0.46f)
                        val elbow = Offset(c.x + r * 0.67f, c.y - r * 0.70f)
                        val end = Offset(c.x + r * 0.92f, elbow.y)
                        drawLine(Gold, anchor, elbow, strokeWidth = r * 0.013f)
                        drawLine(Gold, elbow, end, strokeWidth = r * 0.013f)
                        drawCircle(Gold, r * 0.027f, anchor)
                        drawCircle(Gold, r * 0.027f, Offset(c.x + r * 0.49f, c.y - r * 0.52f))
                    }
                    // Fixed twelve-o'clock pointer; independent of rotary layers.
                    val pointer = androidx.compose.ui.graphics.Path().apply {
                        moveTo(c.x, r * 0.115f)
                        lineTo(c.x - r * 0.092f, r * 0.265f)
                        lineTo(c.x + r * 0.092f, r * 0.265f)
                        close()
                    }
                    drawPath(pointer, Color(0xFF382900),
                        style = Stroke(width = r * 0.032f,
                            join = androidx.compose.ui.graphics.StrokeJoin.Round))
                    drawPath(pointer, Gold)
                    drawLine(Gold, Offset(c.x, r * 0.105f), Offset(c.x, r * 0.045f),
                        strokeWidth = r * 0.018f, cap = StrokeCap.Round)
                }
                if (pipe != null) {
                    val dimensionUnit = if (imperial) "in" else "mm"
                    val inner = if (imperial) pipe.idMm / 25.4 else pipe.idMm
                    val wall = if (imperial) pipe.wallMm / 25.4 else pipe.wallMm
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(Alignment.Center)
                            .clickable { onEdit("ID") }.padding(6.dp)) {
                        Text("İç Çap (ID)", color = Muted, fontSize = 13.sp)
                        Text(decimal(inner) + " " + dimensionUnit, color = White,
                            fontWeight = FontWeight.Bold, fontSize = 21.sp)
                    }
                    Text("t " + decimal(wall, if (imperial) 3 else 2) + " " + dimensionUnit,
                        color = Gold, fontWeight = FontWeight.Bold, fontSize = 13.sp,
                        modifier = Modifier.align(Alignment.TopEnd)
                            .padding(top = 34.dp, end = 2.dp).clickable { onEdit("t") })
                } else {
                    Text(nps + "\"", color = White, fontSize = 42.sp,
                        modifier = Modifier.clickable { onEdit("NPS") })
                }
            }
        }
        if (pipe != null) {
            // Full outside diameter dimension bar beneath the physical ring.
            Canvas(Modifier.fillMaxWidth(0.76f).height(20.dp)) {
                val left = Offset(size.width * 0.04f, center.y)
                val right = Offset(size.width * 0.96f, center.y)
                drawLine(White, left, right, strokeWidth = 1.6.dp.toPx())
                for (side in listOf(-1f, 1f)) {
                    val tip = if (side < 0f) left else right
                    val head = tip.x - side * 9.dp.toPx()
                    drawLine(White, tip, Offset(head, tip.y - 4.dp.toPx()),
                        strokeWidth = 1.6.dp.toPx())
                    drawLine(White, tip, Offset(head, tip.y + 4.dp.toPx()),
                        strokeWidth = 1.6.dp.toPx())
                }
            }
            val dimensionUnit = if (imperial) "in" else "mm"
            val outer = if (imperial) pipe.odMm / 25.4 else pipe.odMm
            Text("Dış Çap (OD)  " + decimal(outer) + " " + dimensionUnit,
                color = White, fontSize = 19.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onEdit("OD") }.padding(8.dp))
        }
        Text("Halkayı parmağınızla çevirin", color = Muted, fontSize = 14.sp)
    }
}

@Composable
private fun ResultRows(pipe: PipeSpec, imperial: Boolean, textSp: Int,
    onEdit: (String) -> Unit) {
    val thicknessUnit = if (imperial) "in" else "mm"
    val od = if (imperial) pipe.odMm / 25.4 else pipe.odMm
    val id = if (imperial) pipe.idMm / 25.4 else pipe.idMm
    val t = if (imperial) pipe.wallMm / 25.4 else pipe.wallMm
    val mass = if (imperial) pipe.kgPerMetre * 0.671968975 else pipe.kgPerMetre
    val volume = if (imperial) pipe.litresPerMetre * 0.08052 else pipe.litresPerMetre
    val specs = listOf(
        Triple("Dış Çap (OD)", decimal(od) + " " + thicknessUnit, Icons.Default.Straighten),
        Triple("İç Çap (ID)", decimal(id) + " " + thicknessUnit, Icons.Default.Circle),
        Triple("Et Kalınlığı", decimal(t, if (imperial) 3 else 2) + " " + thicknessUnit, Icons.Default.Tune),
        Triple("Ağırlık", decimal(mass) + if (imperial) " lb/ft" else " kg/m", Icons.Default.Scale),
        Triple("İç Hacim", decimal(volume) + if (imperial) " gal/ft" else " L/m", Icons.Default.WaterDrop)
    )
    Column(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(22.dp))
            .border(1.dp, StrokeColor, RoundedCornerShape(22.dp)).background(Panel)
    ) {
        specs.forEachIndexed { i, item ->
            Row(Modifier.fillMaxWidth().heightIn(min = 61.dp)
                .clickable { onEdit(listOf("OD", "ID", "t", "mass", "volume")[i]) }
                .padding(horizontal = 13.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Icon(item.third, contentDescription = null, tint = White, modifier = Modifier.size(25.dp))
                Spacer(Modifier.width(10.dp))
                Text(item.first, color = White, fontSize = (textSp - 2).sp, modifier = Modifier.weight(1f))
                Text(item.second, color = White, fontWeight = FontWeight.Bold, fontSize = textSp.sp)
            }
            if (i < specs.lastIndex) HorizontalDivider(color = StrokeColor.copy(alpha = 0.6f))
        }
    }
}

@Composable
private fun NumericPipeDialog(
    field: String, standard: String, nps: String, schedule: String,
    selected: PipeSpec?, imperial: Boolean, catalog: PipeCatalog,
    onDismiss: () -> Unit, onChoose: (PipeSpec) -> Unit
) {
    val initial = when (field) {
        "NPS" -> nps
        "SCH" -> schedule
        "OD" -> selected?.odMm?.let { decimal(if (imperial) it / 25.4 else it) }
        "ID" -> selected?.idMm?.let { decimal(if (imperial) it / 25.4 else it) }
        "t" -> selected?.wallMm?.let { decimal(if (imperial) it / 25.4 else it, if (imperial) 3 else 2) }
        "mass" -> selected?.kgPerMetre?.let { decimal(if (imperial) it * 0.671968975 else it) }
        else -> selected?.litresPerMetre?.let { decimal(if (imperial) it * 0.08052 else it) }
    } ?: ""
    var value by remember(field) { mutableStateOf(initial) }
    val input = value.trim().replace(",", ".").replace("″", "").replace("\"", "").trim()
    val number = input.toDoubleOrNull()
    val candidates = catalog.rows.asSequence().filter { it.standard == standard }
        .filter { row ->
            when (field) {
                "NPS" -> row.nps.equals(input, ignoreCase = true)
                "SCH" -> row.nps == nps && row.schedule.equals(input, ignoreCase = true)
                else -> {
                    val actual = when (field) {
                        "OD" -> if (imperial) row.odMm / 25.4 else row.odMm
                        "ID" -> if (imperial) row.idMm / 25.4 else row.idMm
                        "t" -> if (imperial) row.wallMm / 25.4 else row.wallMm
                        "mass" -> if (imperial) row.kgPerMetre * 0.671968975 else row.kgPerMetre
                        else -> if (imperial) row.litresPerMetre * 0.08052 else row.litresPerMetre
                    }
                    val tolerance = if (field == "t" && imperial) 0.00051 else 0.0051
                    number != null && number.isFinite() && abs(actual - number) <= tolerance
                }
            }
        }.distinctBy { it.nps + "|" + it.schedule }.take(30).toList()
    val title = when (field) {
        "NPS" -> "NPS çapı"
        "SCH" -> "Schedule (SCH)"
        "OD" -> "Dış çap (OD)"
        "ID" -> "İç çap (ID)"
        "t" -> "Et kalınlığı"
        "mass" -> "Ağırlık"
        else -> "İç hacim"
    }
    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(title + " yazın", color = White) },
        text = {
            Column {
                OutlinedTextField(value = value, onValueChange = { value = it },
                    singleLine = true, modifier = Modifier.fillMaxWidth(),
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 25.sp, color = White),
                    keyboardOptions = KeyboardOptions(keyboardType =
                        if (field == "NPS" || field == "SCH") KeyboardType.Text else KeyboardType.Decimal),
                    label = { Text("Değer") })
                Spacer(Modifier.height(9.dp))
                Text("Yalnız standartta kayıtlı nominal ölçüler kullanılabilir.",
                    color = Muted, fontSize = 13.sp)
                Spacer(Modifier.height(8.dp))
                if (candidates.isEmpty()) {
                    Text("Eşleşen kayıt yok. NPS, SCH veya değeri kontrol edin.",
                        color = Gold, fontSize = 14.sp)
                } else {
                    Column(Modifier.heightIn(max = 210.dp).verticalScroll(rememberScrollState())) {
                        candidates.forEach { candidate ->
                            Text(candidate.nps + "″  ·  SCH " + candidate.schedule +
                                "  ·  OD " + decimal(candidate.odMm) + " mm",
                                color = White, fontSize = 17.sp,
                                modifier = Modifier.fillMaxWidth()
                                    .clip(RoundedCornerShape(9.dp))
                                    .clickable { onChoose(candidate) }
                                    .padding(vertical = 12.dp, horizontal = 7.dp))
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(enabled = candidates.size == 1,
                onClick = { candidates.singleOrNull()?.let(onChoose) }) {
                Text("Seç", color = if (candidates.size == 1) Gold else Muted)
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("İptal", color = Muted) } }
    )
}

@Composable
private fun LengthDialog(pipe: PipeSpec, imperial: Boolean, onDismiss: () -> Unit) {
    var length by remember { mutableStateOf("6") }
    val metres = length.replace(",", ".").toDoubleOrNull()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Boru uzunluğu hesabı", color = White) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Uzunluğu metre olarak girin.", color = White, fontSize = 17.sp)
                OutlinedTextField(
                    length, { length = it }, label = { Text("Uzunluk (m)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true, modifier = Modifier.fillMaxWidth()
                )
                if (metres != null && metres > 0 && metres <= 100000) {
                    Text("Boş ağırlık: " + decimal(pipe.kgPerMetre * metres) + " kg", fontSize = 19.sp, color = White)
                    Text("İç hacim: " + decimal(pipe.litresPerMetre * metres) + " L", fontSize = 19.sp, color = White)
                    Text("Su dolu ağırlık: " + decimal(pipe.waterFilledKgPerMetre * metres) + " kg", fontSize = 19.sp, color = White)
                } else {
                    Text("Pozitif ve geçerli bir uzunluk girin.", color = Gold)
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Kapat", color = Gold) } }
    )
}

@Composable
private fun PageHeading(title: String, subtitle: String) {
    Text(title, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = White)
    Spacer(Modifier.height(6.dp))
    Text(subtitle, fontSize = 16.sp, color = Muted)
    Spacer(Modifier.height(20.dp))
}

@Composable
private fun LookupPage(catalog: PipeCatalog, standard: String, textSp: Int, imperial: Boolean) {
    var outer by rememberSaveable { mutableStateOf("168,3") }
    var thick by rememberSaveable { mutableStateOf("7,11") }
    var searched by remember { mutableStateOf(false) }
    val odValue = outer.replace(",", ".").toDoubleOrNull()
    val tValue = thick.replace(",", ".").toDoubleOrNull()
    val matches = if (searched && odValue != null && tValue != null)
        catalog.findByMeasurement(odValue, tValue, standard) else emptyList()

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(22.dp)) {
        PageHeading("SCH Bul", "Kumpasla ölçtüğünüz dış çap ve et kalınlığını girin.")
        OutlinedTextField(
            outer, { outer = it; searched = false }, modifier = Modifier.fillMaxWidth(),
            label = { Text("Dış çap (mm)") }, singleLine = true,
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 26.sp, color = White),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        Spacer(Modifier.height(17.dp))
        OutlinedTextField(
            thick, { thick = it; searched = false }, modifier = Modifier.fillMaxWidth(),
            label = { Text("Et kalınlığı (mm)") }, singleLine = true,
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 26.sp, color = White),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        Spacer(Modifier.height(20.dp))
        GoldButton("SCH Bul", { searched = true })
        Spacer(Modifier.height(24.dp))
        if (searched) {
            if (matches.isEmpty()) {
                Text(
                    "Bu ölçülere yakın, doğrulanmış bir " + standard + " kaydı bulunamadı. Ölçümü ve standardı kontrol edin.",
                    fontSize = textSp.sp, color = Gold
                )
            } else {
                Text("Olası nominal eşleşmeler", color = White, fontSize = 21.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(10.dp))
                matches.take(12).forEach { pipe ->
                    Surface(
                        color = Panel, shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).border(1.dp, StrokeColor, RoundedCornerShape(15.dp))
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(pipe.nps + "\"  ·  DN " + pipe.dn + "  ·  SCH " + pipe.schedule,
                                fontSize = 22.sp, color = Gold, fontWeight = FontWeight.Bold)
                            Text("Nominal dış çap " + decimal(pipe.odMm) + " mm  |  et " + decimal(pipe.wallMm) + " mm",
                                color = White, fontSize = 16.sp)
                        }
                    }
                }
            }
            Spacer(Modifier.height(13.dp))
            Text(
                "Bu sonuç ölçüden nominal sınıf tahminidir; basınç dayanımı veya boru uygunluk sertifikası değildir.",
                color = Muted, fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun CombinedTableStandardsPage(
    catalog: PipeCatalog, nps: String, sizes: List<String>, onNps: (String) -> Unit,
    textSp: Int, onSelect: (PipeSpec) -> Unit
) {
    val rows = remember(nps) {
        catalog.rows.filter { it.nps == nps }.sortedWith(
            compareBy<PipeSpec> { it.schedule.filter(Char::isDigit).toIntOrNull() ?: Int.MAX_VALUE }
                .thenBy { it.schedule }.thenBy { it.standard })
    }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp)) {
        PageHeading("Tablo / Standart", "B36.10 ve B36.19 kayıtları birlikte gösterilir.")
        Picker("NPS (inç)", nps + "″", sizes.map { it + "″" },
            { onNps(it.removeSuffix("″")) }, Modifier.fillMaxWidth())
        Spacer(Modifier.height(14.dp))
        Text("SCH   ·   Standart   ·   Et kalınlığı   ·   İç çap",
            color = Muted, fontSize = 14.sp)
        rows.forEach { p ->
            Row(
                Modifier.fillMaxWidth().padding(top = 7.dp)
                    .clip(RoundedCornerShape(12.dp)).background(Panel)
                    .clickable { onSelect(p) }.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(p.schedule, color = Gold, fontSize = 20.sp,
                    fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.9f))
                Column(Modifier.weight(2.2f)) {
                    Text("ASME " + p.standard, color = Muted, fontSize = 14.sp)
                    Text("t " + decimal(p.wallMm) + " mm · ID " +
                        decimal(p.idMm) + " mm", color = White, fontSize = 16.sp)
                }
            }
        }
        Spacer(Modifier.height(24.dp))
        InfoPanel("ASME B36.10M", "Karbon çelik boruların nominal çap, et ve Schedule serileri.", textSp)
        Spacer(Modifier.height(12.dp))
        InfoPanel("ASME B36.19M", "Paslanmaz çelik borularda S işaretli Schedule serileri ayrı kayıtlardır.", textSp)
        Spacer(Modifier.height(12.dp))
        InfoPanel("Hesap formülleri",
            "İç çap = dış çap − 2 × et kalınlığı.\n" +
            "Kütle: çelik kesit alanı × 7850 kg/m³.\n" +
            "İç hacim: π × iç çap² / 4 × uzunluk.\n" +
            "Değerler nominaldir; basınç tasarım hesabı değildir.", textSp)
    }
}

@Composable
private fun InfoPanel(title: String, text: String, textSp: Int) {
    Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
        .background(Panel).border(1.dp, StrokeColor, RoundedCornerShape(16.dp)).padding(18.dp)) {
        Text(title, color = Gold, fontWeight = FontWeight.Bold, fontSize = 21.sp)
        Spacer(Modifier.height(8.dp))
        Text(text, color = White, fontSize = textSp.sp, lineHeight = (textSp + 7).sp)
    }
}

@Composable
private fun SettingsPage(
    largeText: Boolean, onLargeText: (Boolean) -> Unit,
    imperial: Boolean, onImperial: (Boolean) -> Unit, textSp: Int
) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(22.dp)) {
        PageHeading("Ayarlar", "Sahada kolay okunabilen ekran")
        Row(Modifier.fillMaxWidth().heightIn(min = 72.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("Büyük yazı", color = White, fontSize = 21.sp, fontWeight = FontWeight.Bold)
                Text("Daha kolay okunan ölçüler", color = Muted, fontSize = 15.sp)
            }
            Switch(checked = largeText, onCheckedChange = onLargeText)
        }
        HorizontalDivider(color = StrokeColor)
        Row(Modifier.fillMaxWidth().heightIn(min = 72.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("İnç / lb / gal", color = White, fontSize = 21.sp, fontWeight = FontWeight.Bold)
                Text("Metrik yerine Imperial göster", color = Muted, fontSize = 15.sp)
            }
            Switch(checked = imperial, onCheckedChange = onImperial)
        }
        HorizontalDivider(color = StrokeColor)
        Spacer(Modifier.height(18.dp))
        InfoPanel("Çevrimdışı kullanım", "Boru tablosu cihazda bulunur. Hesaplamak için internet gerekmez.", textSp)
        Spacer(Modifier.height(14.dp))
        InfoPanel("Veri kapsamı", "Uygulama yalnızca geçerli nominal et kalınlığı kaydı olan NPS / SCH seçeneklerini listeler. Bilinmeyen veriler tahmin edilmez.", textSp)
    }
}
