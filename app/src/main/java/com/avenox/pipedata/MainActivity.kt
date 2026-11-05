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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
    val sizes = remember(standard) { catalog.sizes(standard) }
    val activeNps = nps.takeIf { sizes.contains(it) } ?: sizes.first()
    val schedules = remember(standard, activeNps) { catalog.schedules(standard, activeNps) }
    val activeSch = schedule.takeIf { schedules.contains(it) }
        ?: ("40".takeIf { schedules.contains(it) } ?: "40S".takeIf { schedules.contains(it) }
        ?: schedules.first())
    val pipe = catalog.get(standard, activeNps, activeSch)
    val normalSp = if (largeText) 18 else 16

    LaunchedEffect(standard, activeNps, activeSch) {
        if (nps != activeNps) nps = activeNps
        if (schedule != activeSch) schedule = activeSch
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Background).systemBarsPadding()
    ) {
        Row(
            Modifier.fillMaxWidth().heightIn(min = 68.dp).padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { page = 4 }, modifier = Modifier.size(52.dp)) {
                Icon(Icons.Default.Settings, contentDescription = "Ayarlar", tint = White, modifier = Modifier.size(29.dp))
            }
            Text(
                "Boru Hesaplayıcı", color = White, fontWeight = FontWeight.SemiBold,
                fontSize = if (largeText) 25.sp else 22.sp,
                textAlign = TextAlign.Center, modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { page = 3 }, modifier = Modifier.size(52.dp)) {
                Icon(Icons.Default.MenuBook, contentDescription = "Standartlar", tint = White, modifier = Modifier.size(28.dp))
            }
        }
        Box(Modifier.weight(1f)) {
            when (page) {
                0 -> CalculatorPage(
                    standard, { standard = it }, activeNps, sizes,
                    onNps = { nps = it }, activeSch, schedules,
                    onSch = { schedule = it }, pipe, unitImperial, normalSp
                )
                1 -> LookupPage(catalog, standard, normalSp, unitImperial)
                2 -> TablePage(
                    catalog, standard, { standard = it },
                    activeNps, sizes, { nps = it },
                    normalSp, unitImperial, onSelect = { schedule = it; page = 0 }
                )
                3 -> StandardsPage(normalSp)
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
                Triple("Tablo", Icons.Default.TableRows, 2),
                Triple("Standart", Icons.Default.MenuBook, 3),
                Triple("Ayarlar", Icons.Default.Settings, 4)
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
    pipe: PipeSpec?, imperial: Boolean, textSp: Int
) {
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
        Picker("Standart", "ASME " + standard, listOf("ASME B36.10", "ASME B36.19"),
            onChange = { setStandard(it.removePrefix("ASME ")) },
            modifier = Modifier.fillMaxWidth(0.68f)
        )
        Spacer(Modifier.height(8.dp))
        Text("ÇEVİREREK NPS SEÇİN", color = Muted, fontSize = 14.sp, letterSpacing = 1.sp)
        PipeDial(nps, step)
        Spacer(Modifier.height(7.dp))
        Text("Schedule (SCH)", fontSize = textSp.sp, color = White, fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Start))
        Spacer(Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(schedules) { sch ->
                Surface(
                    color = if (sch == schedule) Gold else Panel,
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.heightIn(min = 53.dp)
                        .border(1.dp, if (sch == schedule) Gold else StrokeColor, RoundedCornerShape(24.dp))
                        .clickable { onSch(sch) }
                ) {
                    Box(Modifier.padding(horizontal = 19.dp), contentAlignment = Alignment.Center) {
                        Text(sch, color = if (sch == schedule) Deep else White, fontWeight = FontWeight.Bold, fontSize = 21.sp)
                    }
                }
            }
        }
        Spacer(Modifier.height(18.dp))
        if (pipe == null) {
            Text("Bu NPS ve Schedule için doğrulanmış ölçü yok.", color = Gold, fontSize = textSp.sp)
        } else {
            ResultRows(pipe, imperial, textSp)
        }
        Spacer(Modifier.height(18.dp))
        GoldButton("Hesapla", { showLength = true }, Modifier.fillMaxWidth())
        Spacer(Modifier.height(20.dp))
    }
    if (showLength && pipe != null) LengthDialog(pipe, imperial) { showLength = false }
}

@Composable
private fun PipeDial(nps: String, onStep: (Int) -> Unit) {
    // Rotary gesture continues smoothly across the -PI / +PI seam.
    // A stable pointer handler avoids restarting a drag on every NPS recomposition.
    val currentOnStep by rememberUpdatedState(onStep)
    var wheelRotation by remember { mutableFloatStateOf(0f) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(285.dp)
                .semantics {
                    contentDescription = "Döner NPS sayacı, seçili çap " + nps + " inç"
                    onClick(label = "Sonraki boru çapı") {
                        currentOnStep(1)
                        true
                    }
                }
                .pointerInput(Unit) {
                    var previousAngle = 0f
                    var rotationRemainder = 0f
                    var touchOnWheel = false
                    val oneStep = 0.22f // about 12.6 degrees per nominal size
                    detectDragGestures(
                        onDragStart = { point ->
                            val centerX = size.width / 2f
                            val centerY = size.height / 2f
                            val dx = point.x - centerX
                            val dy = point.y - centerY
                            val radius = hypot(dx, dy)
                            val minDimension = minOf(size.width, size.height).toFloat()
                            // Only an annulus responds; touching the center value cannot
                            // accidentally change the selected nominal diameter.
                            touchOnWheel = radius >= minDimension * 0.24f &&
                                radius <= minDimension * 0.53f
                            previousAngle = atan2(dy, dx)
                            rotationRemainder = 0f
                        },
                        onDragCancel = { touchOnWheel = false; rotationRemainder = 0f },
                        onDragEnd = { touchOnWheel = false; rotationRemainder = 0f }
                    ) { change, _ ->
                        if (touchOnWheel) {
                            change.consume()
                            val dx = change.position.x - size.width / 2f
                            val dy = change.position.y - size.height / 2f
                            val angle = atan2(dy, dx)
                            val delta = atan2(sin(angle - previousAngle), cos(angle - previousAngle))
                            previousAngle = angle
                            wheelRotation = (wheelRotation + Math.toDegrees(delta.toDouble()).toFloat()) % 360f
                            rotationRemainder += delta
                            while (abs(rotationRemainder) >= oneStep) {
                                val direction = if (rotationRemainder > 0f) 1 else -1
                                currentOnStep(direction)
                                rotationRemainder -= direction * oneStep
                            }
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            // Real transparent user-provided assets, not duplicated Canvas rings.
            Image(
                painter = painterResource(R.drawable.dial_ticks),
                contentDescription = null,
                modifier = Modifier.fillMaxHeight(0.98f).aspectRatio(1f)
                    .graphicsLayer(rotationZ = wheelRotation),
                contentScale = ContentScale.Fit
            )
            Image(
                painter = painterResource(R.drawable.dial_metal),
                contentDescription = null,
                modifier = Modifier.fillMaxHeight(0.72f).aspectRatio(1f)
                    .graphicsLayer(rotationZ = wheelRotation),
                contentScale = ContentScale.Fit
            )
            Image(
                painter = painterResource(R.drawable.dial_pointer),
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopCenter)
                    .offset(y = 3.dp).height(47.dp).aspectRatio(0.88f),
                contentScale = ContentScale.Fit
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.offset(y = (-2).dp)
            ) {
                Text(nps + "\"", color = White, fontSize = 52.sp, fontWeight = FontWeight.Bold)
                Text("NPS", color = Muted, fontSize = 17.sp)
            }
        }
        Text("Halkayı parmağınızla çevirin", color = Muted, fontSize = 14.sp)
    }
}

@Composable
private fun ResultRows(pipe: PipeSpec, imperial: Boolean, textSp: Int) {
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
            Row(Modifier.fillMaxWidth().heightIn(min = 61.dp).padding(horizontal = 13.dp),
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
private fun TablePage(
    catalog: PipeCatalog, standard: String, setStandard: (String) -> Unit,
    nps: String, sizes: List<String>, onNps: (String) -> Unit,
    textSp: Int, imperial: Boolean, onSelect: (String) -> Unit
) {
    val rows = remember(standard, nps) { catalog.rows.filter { it.standard == standard && it.nps == nps } }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp)) {
        PageHeading("SCH Tabloları", "Geçerli Schedule ve et kalınlıklarını karşılaştırın.")
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Picker("Standart", standard, catalog.standards, setStandard, Modifier.weight(1.2f))
            Picker("NPS", nps + "\"", sizes.map { it + "\"" }, { onNps(it.removeSuffix("\"")) }, Modifier.weight(0.8f))
        }
        Spacer(Modifier.height(20.dp))
        Text("SCH      ET KALINLIĞI      İÇ ÇAP", color = Muted, fontSize = 14.sp)
        Spacer(Modifier.height(9.dp))
        rows.forEach { p ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(12.dp)).background(Panel)
                    .clickable { onSelect(p.schedule) }.padding(horizontal = 16.dp, vertical = 13.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(p.schedule, color = Gold, fontSize = (textSp + 2).sp,
                    fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.8f))
                Text(decimal(p.wallMm) + " mm", color = White, fontSize = textSp.sp, modifier = Modifier.weight(1f))
                Text(decimal(p.idMm) + " mm", color = White, fontSize = textSp.sp, modifier = Modifier.weight(1f))
            }
        }
        Text("Bir satıra dokunarak hesaplayıcıda açın.", color = Muted, fontSize = 14.sp)
    }
}

@Composable
private fun StandardsPage(textSp: Int) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(22.dp)) {
        PageHeading("Standartlar", "Boru ustaları için hızlı saha referansı")
        InfoPanel("ASME B36.10M", "Karbon çelik ve kaynaklı/kaynaksız çelik borular: NPS, dış çap, nominal et kalınlığı ve Schedule serileri.", textSp)
        Spacer(Modifier.height(14.dp))
        InfoPanel("ASME B36.19M", "Paslanmaz çelik borularda 5S, 10S, 40S ve 80S serileri. S işareti diğer SCH tablolarından ayrı tutulur.", textSp)
        Spacer(Modifier.height(14.dp))
        InfoPanel("Hesap formülleri",
            "İç çap = Dış çap − 2 × et kalınlığı\n" +
            "Çelik ağırlığı = kesit alanı × 7850 kg/m³\n" +
            "İç hacim = π × iç çap² / 4 × uzunluk.\n" +
            "Ölçüler nominaldir; toleranslar ve gerçek yoğunluk farklı olabilir.", textSp)
        Spacer(Modifier.height(14.dp))
        Text("SCH, yalnız dış çaptan hesaplanmaz. Basınç tasarımı ayrıca mühendislik hesabı gerektirir.", color = Gold, fontSize = textSp.sp)
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
