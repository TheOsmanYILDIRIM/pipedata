@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)

package com.avenox.pipedata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
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
                        Text(translateUi("Boru standart tablosu yüklenemedi. Uygulamayı güncelleyin.", resources.configuration.locales[0].language), color = White, fontSize = 20.sp)
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
    var unitImperial by rememberSaveable { mutableStateOf(prefs.getBoolean("imperial", true)) }
    var largeText by rememberSaveable { mutableStateOf(prefs.getBoolean("large_text", true)) }
    var languageSelection by rememberSaveable {
        mutableStateOf(prefs.getString("language", "system") ?: "system")
    }
    val systemLanguage = LocalConfiguration.current.locales[0].language.lowercase(Locale.ROOT)
    val language = if (languageSelection == "system") systemLanguage else languageSelection
    var tutorialStep by rememberSaveable {
        mutableIntStateOf(if (prefs.getBoolean("onboarding_seen_v1", false)) -1 else 0)
    }
    val finishTutorial: () -> Unit = {
        tutorialStep = -1
        prefs.edit().putBoolean("onboarding_seen_v1", true).apply()
    }
    val advanceTutorial: () -> Unit = {
        if (tutorialStep >= 2) finishTutorial()
        else if (tutorialStep >= 0) tutorialStep += 1
    }
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

    CompositionLocalProvider(CurrentUiLanguage provides language) {
    Column(
        modifier = Modifier.fillMaxSize().background(Background).systemBarsPadding()
    ) {
        Box(Modifier.fillMaxWidth().heightIn(min = 62.dp).padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center) {
            Text("Boruzi", color = White, fontWeight = FontWeight.SemiBold,
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
                    onImperial = { unitImperial = it; prefs.edit().putBoolean("imperial", it).apply() },
                    catalog = catalog, onChoose = { chosen ->
                        standard = chosen.standard; nps = chosen.nps; schedule = chosen.schedule
                    },
                    tutorialStep = tutorialStep,
                    onTutorialNext = advanceTutorial,
                    onTutorialSkip = finishTutorial
                )
                1 -> LookupPage(catalog, standard, normalSp, unitImperial)
                2 -> CombinedTableStandardsPage(catalog, activeNps, sizes,
                    { nps = it }, normalSp, unitImperial, onSelect = { chosen ->
                        standard = chosen.standard; nps = chosen.nps
                        schedule = chosen.schedule; page = 0
                    })
                else -> SettingsPage(
                    largeText, { largeText = it; prefs.edit().putBoolean("large_text", it).apply() },
                    unitImperial, { unitImperial = it; prefs.edit().putBoolean("imperial", it).apply() },
                    normalSp, languageSelection,
                    onLanguageChange = {
                        languageSelection = it
                        prefs.edit().putString("language", it).apply()
                    },
                    onReplayTutorial = {
                        tutorialStep = 0
                        page = 0
                        prefs.edit().putBoolean("onboarding_seen_v1", false).apply()
                    }
                )
            }
        }
        Row(
            Modifier.fillMaxWidth().background(Deep).navigationBarsPadding().padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val items = listOf(
                Triple(ui("Boru"), Icons.Default.Tune, 0),
                Triple(ui("SCH Bul"), Icons.Default.Search, 1),
                Triple(ui("Tablo"), Icons.Default.MenuBook, 2),
                Triple(ui("Ayarlar"), Icons.Default.Settings, 3)
            )
            items.forEach { (label, icon, index) ->
                val navDescription = if (index == 2) ui("Tablo / Standart") else label
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(8.dp))
                        .clickable { page = index }.padding(vertical = 8.dp)
                        .semantics { contentDescription = navDescription }
                ) {
                    Icon(icon, contentDescription = null, tint = if (page == index) Gold else Muted, modifier = Modifier.size(26.dp))
                    Spacer(Modifier.height(2.dp))
                    Text(label, color = if (page == index) Gold else Muted,
                        fontSize = 11.sp, maxLines = 1, softWrap = false)
                }
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
                Icon(Icons.Default.ArrowDropDown, contentDescription = ui("Seç"), tint = Gold)
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
    onImperial: (Boolean) -> Unit,
    catalog: PipeCatalog, onChoose: (PipeSpec) -> Unit,
    tutorialStep: Int, onTutorialNext: () -> Unit, onTutorialSkip: () -> Unit
) {
    val scrollState = rememberScrollState()
    val schTarget = remember { BringIntoViewRequester() }
    val odTarget = remember { BringIntoViewRequester() }
    LaunchedEffect(tutorialStep) {
        if (tutorialStep == 0) scrollState.animateScrollTo(0)
        if (tutorialStep == 1 || tutorialStep == 2) {
            delay(160)
            if (tutorialStep == 1) schTarget.bringIntoView()
            else odTarget.bringIntoView()
        }
    }
    var editing by remember { mutableStateOf<String?>(null) }
    var showLength by remember { mutableStateOf(false) }
    val index = sizes.indexOf(nps).coerceAtLeast(0)
    val step: (Int) -> Unit = { delta ->
        onNps(sizes[(index + delta + sizes.size) % sizes.size])
    }
    Column(
        Modifier.fillMaxSize().verticalScroll(scrollState)
            .padding(horizontal = 18.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(Modifier.fillMaxWidth().padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(ui("Ölçü birimi"), color = Muted, fontSize = 16.sp)
            Row(Modifier.clip(RoundedCornerShape(14.dp))
                .border(1.dp, StrokeColor, RoundedCornerShape(14.dp))) {
                listOf(true to ui("inç"), false to "mm").forEach { (inch, label) ->
                    Box(Modifier.clip(RoundedCornerShape(12.dp))
                        .background(if (imperial == inch) Gold else Color.Transparent)
                        .clickable { onImperial(inch) }
                        .heightIn(min = 48.dp).padding(horizontal = 18.dp),
                        contentAlignment = Alignment.Center) {
                        Text(label, color = if (imperial == inch) Deep else White,
                            fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        if (tutorialStep == 0) {
            TutorialHint(0, onTutorialNext, onTutorialSkip)
            Spacer(Modifier.height(8.dp))
        }
        PipeDial(nps, step, pipe, imperial,
            onEdit = { editing = it },
            tutorialStep = tutorialStep,
            onWheelDragged = { if (tutorialStep == 0) onTutorialNext() },
            onOdTapped = { if (tutorialStep == 2) onTutorialNext() },
            odTarget = odTarget)
        Spacer(Modifier.height(12.dp))
        if (tutorialStep == 1) {
            TutorialHint(1, onTutorialNext, onTutorialSkip)
            Spacer(Modifier.height(8.dp))
        }
        // The target is the actual SCH controls, not the explanatory text above.
        // Scroll and draw the spotlight around the choices themselves.
        Box(Modifier.fillMaxWidth().bringIntoViewRequester(schTarget)
            .clip(RoundedCornerShape(18.dp))) {
            Column(Modifier.fillMaxWidth().padding(if (tutorialStep == 1) 7.dp else 0.dp)) {
                Text(ui("Schedule (SCH)"), color = White, fontSize = 20.sp,
                    fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start)
                        .clickable { editing = "SCH" }.padding(vertical = 5.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(schedules) { sch ->
                        Surface(
                            color = if (sch == schedule) Gold else Panel,
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier.heightIn(min = 56.dp)
                                .border(
                                    if (tutorialStep == 1) 2.5.dp else 1.dp,
                                    if (tutorialStep == 1 || sch == schedule) Gold else StrokeColor,
                                    RoundedCornerShape(24.dp))
                                .clickable {
                                    onSch(sch)
                                    if (tutorialStep == 1) onTutorialNext()
                                }
                        ) {
                            Row(Modifier.padding(horizontal = 17.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                // Animated touch marker identifies the actual selectable chip.
                                if (tutorialStep == 1 && sch == schedules.first()) {
                                    TutorialTouchMarker()
                                    Spacer(Modifier.width(6.dp))
                                }
                                Text(sch, color = if (sch == schedule) Deep else White,
                                    fontWeight = FontWeight.Bold, fontSize = 21.sp)
                            }
                        }
                    }
                }
            }
            if (tutorialStep == 1) TutorialPulseFrame(Modifier.matchParentSize())
        }
        Spacer(Modifier.height(18.dp))
        if (pipe == null) {
            Text(ui("Bu NPS ve Schedule için doğrulanmış ölçü yok."), color = Gold, fontSize = textSp.sp)
        } else {
            ResultRows(pipe, imperial, textSp) { editing = it }
        }
        Spacer(Modifier.height(18.dp))
        GoldButton(ui("Hesapla"), { showLength = true }, Modifier.fillMaxWidth())
        Spacer(Modifier.height(20.dp))
    }
    if (showLength && pipe != null) LengthDialog(pipe, imperial) { showLength = false }
    editing?.let { field ->
        NumericPipeDialog(field, standard, nps, schedule, pipe, imperial, catalog,
            onDismiss = { editing = null },
            onChoose = { chosen -> onChoose(chosen); editing = null })
    }
}


/** Short, contextual hints; they do not block the actual controls. */
@Composable
private fun TutorialHint(step: Int, onNext: () -> Unit, onSkip: () -> Unit) {
    val message = when (step) {
        0 -> ui("Halkanın kenarını parmağınla çevir. Hareketi dene!")
        1 -> ui("Aşağıdaki SCH değerlerinden birine dokun.")
        else -> ui("Dış çap değerine dokunarak NPS seç.")
    }
    Surface(
        color = Color(0xFF17433D), shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
            .border(2.dp, Gold.copy(alpha = 0.88f), RoundedCornerShape(16.dp))
            .semantics { contentDescription = message }
    ) {
        Column(Modifier.padding(horizontal = 13.dp, vertical = 9.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.TouchApp, contentDescription = null, tint = Gold,
                    modifier = Modifier.size(25.dp))
                Spacer(Modifier.width(9.dp))
                Column(Modifier.weight(1f)) {
                    Text(ui("İlk kullanım") + " · ${step + 1}/3",
                        color = Gold, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Text(message, color = White, fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold, lineHeight = 21.sp)
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onSkip) {
                    Text(ui("Atla"), color = Muted, fontSize = 15.sp)
                }
                TextButton(onClick = onNext) {
                    Text(ui(if (step == 2) "Bitir" else "İleri"),
                        color = Gold, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
        }
    }
}

/** Repeating, non-intercepting spotlight around the exact control the user should tap. */
@Composable
private fun TutorialPulseFrame(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "tutorial_target_pulse")
    val alpha by transition.animateFloat(
        initialValue = 0.35f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(780), repeatMode = RepeatMode.Reverse),
        label = "target_glow"
    )
    Box(modifier.border(3.dp, Gold.copy(alpha = alpha), RoundedCornerShape(18.dp)))
}

/** Small finger icon pulses on the first selectable SCH chip, without blocking taps. */
@Composable
private fun TutorialTouchMarker() {
    val transition = rememberInfiniteTransition(label = "tutorial_touch")
    val alpha by transition.animateFloat(
        initialValue = 0.45f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(680), repeatMode = RepeatMode.Reverse),
        label = "finger_pulse"
    )
    Icon(Icons.Default.TouchApp, contentDescription = null,
        tint = if (alpha > 0.72f) White else Gold,
        modifier = Modifier.size(24.dp))
}

/** Procedural curved swipe arrow and moving fingertip on the real rotary dial. */
@Composable
private fun TutorialRingGesture(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "ring_guide")
    val angle by transition.animateFloat(
        initialValue = -70f, targetValue = 55f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1750, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ), label = "finger_angle"
    )
    Canvas(modifier) {
        val ringR = size.minDimension * 0.395f
        val ringBox = Offset(center.x - ringR, center.y - ringR)
        drawArc(Gold.copy(alpha = 0.9f), startAngle = -73f, sweepAngle = 130f,
            useCenter = false, topLeft = ringBox,
            size = Size(2f * ringR, 2f * ringR),
            style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round))
        val endAngle = Math.toRadians(57.0)
        val ax = center.x + cos(endAngle).toFloat() * ringR
        val ay = center.y + sin(endAngle).toFloat() * ringR
        val tangent = Offset(-sin(endAngle).toFloat(), cos(endAngle).toFloat())
        val tip = Offset(ax, ay)
        drawLine(Gold, tip,
            Offset(ax - tangent.x * 15.dp.toPx() - 5.dp.toPx(),
                ay - tangent.y * 15.dp.toPx()), 3.dp.toPx())
        drawLine(Gold, tip,
            Offset(ax - tangent.x * 15.dp.toPx() + 5.dp.toPx(),
                ay - tangent.y * 15.dp.toPx()), 3.dp.toPx())
        val radians = Math.toRadians(angle.toDouble())
        val finger = Offset(center.x + cos(radians).toFloat() * ringR,
            center.y + sin(radians).toFloat() * ringR)
        drawCircle(Deep.copy(alpha = 0.62f), 19.dp.toPx(), finger)
        drawCircle(White, 12.dp.toPx(), finger)
        drawCircle(Gold, 12.dp.toPx(), finger,
            style = Stroke(width = 3.dp.toPx()))
        drawCircle(Deep, 3.dp.toPx(), finger)
    }
}

/** Entirely procedural dial; no image assets or bitmap transformations. */
@Composable
private fun PipeDial(nps: String, onStep: (Int) -> Unit, pipe: PipeSpec?,
    imperial: Boolean, onEdit: (String) -> Unit,
    tutorialStep: Int = -1,
    onWheelDragged: () -> Unit = {}, onOdTapped: () -> Unit = {},
    odTarget: BringIntoViewRequester? = null) {
    val currentOnStep by rememberUpdatedState(onStep)
    val currentOnWheelDragged by rememberUpdatedState(onWheelDragged)
    var wheelRotation by remember { mutableFloatStateOf(0f) }
    val dialDescription = ui("Döner NPS sayacı, seçili çap ") + nps + " " + ui("inç")
    val nextDiameter = ui("Sonraki boru çapı")
    val uiInnerDiameterDescription = ui("İç Çap (ID)")

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (pipe != null) {
            val dimensionUnit = if (imperial) "in" else "mm"
            val wall = if (imperial) pipe.wallMm / 25.4 else pipe.wallMm
            Column(Modifier.fillMaxWidth().clickable { onEdit("t") }
                .padding(top = 6.dp, bottom = 4.dp),
                horizontalAlignment = Alignment.End) {
                Text(ui("Et Kalınlığı (t)"), color = Gold, fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
                Text(decimal(wall, if (imperial) 3 else 2) + " " + dimensionUnit,
                    color = Gold, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }
        BoxWithConstraints(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            val widthDp = LocalConfiguration.current.screenWidthDp
            val fontScale = LocalDensity.current.fontScale
            val dialCap = when {
                widthDp <= 340 && fontScale >= 1.2f -> 208.dp
                widthDp <= 360 && fontScale >= 1.2f -> 238.dp
                widthDp <= 340 -> 250.dp
                else -> 285.dp
            }
            val dialSide = minOf(maxWidth, dialCap)
            Box(
                modifier = Modifier.size(dialSide)
                    .semantics {
                        contentDescription = dialDescription
                        onClick(label = nextDiameter) {
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
                                    currentOnWheelDragged()
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
                        val y = c.y + r * (if (fontScale >= 1.2f) 0.43f else 0.22f)
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
                        val end = Offset(c.x + r * 0.94f, elbow.y)
                        drawLine(Gold, anchor, elbow, strokeWidth = r * 0.013f)
                        drawLine(Gold, elbow, end, strokeWidth = r * 0.013f)
                        drawCircle(Gold, r * 0.027f, anchor)

                        // Proper technical drawing: extension lines from the
                        // outer pipe tangents, joining a double-arrow dimension.
                        val tangent = metalRadius + metalWidth * 0.52f
                        val dimensionY = size.height - r * 0.075f
                        val leftX = c.x - tangent
                        val rightX = c.x + tangent
                        for (x in listOf(leftX, rightX)) {
                            drawLine(White, Offset(x, c.y), Offset(x, dimensionY + r * 0.028f),
                                strokeWidth = r * 0.009f)
                        }
                        drawLine(White, Offset(leftX, dimensionY),
                            Offset(rightX, dimensionY), strokeWidth = r * 0.009f)
                        for (sign in listOf(-1f, 1f)) {
                            val tip = Offset(c.x + sign * tangent, dimensionY)
                            drawLine(White, tip,
                                Offset(tip.x - sign * r * 0.065f,
                                    dimensionY - r * 0.029f), strokeWidth = r * 0.009f)
                            drawLine(White, tip,
                                Offset(tip.x - sign * r * 0.065f,
                                    dimensionY + r * 0.029f), strokeWidth = r * 0.009f)
                        }
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
                if (tutorialStep == 0) TutorialRingGesture(Modifier.fillMaxSize())
                if (pipe != null) {
                    val dimensionUnit = if (imperial) "in" else "mm"
                    val inner = if (imperial) pipe.idInches else pipe.idMm
                    // Keep the bore annotation inside the pipe without allowing
                    // translated titles to cover the metallic ring or diameter marks.
                    // Full localized name remains available to accessibility services.
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(Alignment.Center)
                            .widthIn(max = dialSide * 0.55f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Deep.copy(alpha = 0.88f))
                            .border(1.dp, StrokeColor.copy(alpha = 0.65f),
                                RoundedCornerShape(12.dp))
                            .semantics { contentDescription = uiInnerDiameterDescription }
                            .clickable { onEdit("ID") }
                            .padding(horizontal = 8.dp, vertical = 7.dp)) {
                        Text("ID", color = Muted, fontSize = 15.sp,
                            fontWeight = FontWeight.Bold, maxLines = 1)
                        Text(decimal(inner) + " " + dimensionUnit, color = White,
                            fontWeight = FontWeight.Bold, fontSize = 20.sp,
                            maxLines = 1, softWrap = false)
                    }
                } else {
                    Text(nps + "\"", color = White, fontSize = 42.sp,
                        modifier = Modifier.clickable { onEdit("NPS") })
                }
            }
        }
        if (pipe != null) {
            val dimensionUnit = if (imperial) "in" else "mm"
            val outer = if (imperial) pipe.odInches else pipe.odMm
            if (tutorialStep == 2) {
                Column(Modifier.fillMaxWidth().then(
                    if (odTarget != null) Modifier.bringIntoViewRequester(odTarget) else Modifier
                )) {
                    TutorialHint(2, onOdTapped, onOdTapped)
                    Spacer(Modifier.height(7.dp))
                }
            }
            Box(Modifier.fillMaxWidth().padding(top = 5.dp)
                .clip(RoundedCornerShape(16.dp))) {
                Column(Modifier.fillMaxWidth()
                    .border(1.5.dp, Gold.copy(alpha = 0.85f), RoundedCornerShape(16.dp))
                    .clickable { onEdit("NPS"); if (tutorialStep == 2) onOdTapped() }
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(ui("Dış Çap (OD) · Seçmek için dokunun"), color = Muted,
                        fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                    Text(decimal(outer, if (imperial && pipe.odInches < 14.0) 3 else 2) +
                        " " + dimensionUnit, color = White, fontSize = 29.sp,
                        fontWeight = FontWeight.Bold)
                }
                if (tutorialStep == 2) TutorialPulseFrame(Modifier.matchParentSize())
            }
        }
        Text(ui("Halkayı parmağınızla çevirin"), color = Muted, fontSize = 14.sp)
    }
}


/**
 * Technical drawing symbols: outside diameter, bore diameter and wall thickness.
 * Uses the same dimension geometry at any density; no bitmap dependencies.
 */
@Composable
private fun PipeDimensionIcon(kind: Int, modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val c = center
        val r = size.minDimension * 0.42f
        val line = size.minDimension * 0.055f
        drawCircle(White, radius = r, style = Stroke(width = line))
        drawCircle(Muted, radius = r * 0.56f, style = Stroke(width = line))
        if (kind == 0 || kind == 1) {
            val extent = if (kind == 0) r else r * 0.56f
            drawLine(Gold, Offset(c.x - extent, c.y),
                Offset(c.x + extent, c.y), strokeWidth = line)
            for (direction in listOf(-1f, 1f)) {
                val x = c.x + direction * extent
                drawLine(Gold, Offset(x, c.y),
                    Offset(x - direction * r * 0.22f, c.y - r * 0.16f), line)
                drawLine(Gold, Offset(x, c.y),
                    Offset(x - direction * r * 0.22f, c.y + r * 0.16f), line)
            }
        } else {
            val innerX = c.x + r * 0.56f
            val outerX = c.x + r
            val y = c.y - r * 0.72f
            drawLine(Gold, Offset(innerX, y), Offset(outerX, y), line)
            drawLine(Gold, Offset(innerX, y - r * 0.18f),
                Offset(innerX, y + r * 0.18f), line)
            drawLine(Gold, Offset(outerX, y - r * 0.18f),
                Offset(outerX, y + r * 0.18f), line)
        }
    }
}

@Composable
private fun ResultRows(pipe: PipeSpec, imperial: Boolean, textSp: Int,
    onEdit: (String) -> Unit) {
    val thicknessUnit = if (imperial) "in" else "mm"
    val od = if (imperial) pipe.odInches else pipe.odMm
    val id = if (imperial) pipe.idInches else pipe.idMm
    val t = if (imperial) pipe.wallMm / 25.4 else pipe.wallMm
    val mass = if (imperial) pipe.kgPerMetre * 0.671968975 else pipe.kgPerMetre
    val volume = if (imperial) pipe.litresPerMetre * 0.08052 else pipe.litresPerMetre
    val specs = listOf(
        Triple(ui("Dış Çap (OD)"), decimal(od, if (imperial && pipe.odInches < 14.0) 3 else 2) + " " + thicknessUnit, Icons.Default.OpenInFull),
        Triple(ui("İç Çap (ID)"), decimal(id) + " " + thicknessUnit, Icons.Default.CenterFocusStrong),
        Triple(ui("Et Kalınlığı"), decimal(t, if (imperial) 3 else 2) + " " + thicknessUnit, Icons.Default.LineWeight),
        Triple(ui("Ağırlık"), decimal(mass) + if (imperial) " lb/ft" else " kg/m", Icons.Default.Scale),
        Triple(ui("İç Hacim"), decimal(volume) + if (imperial) " gal/ft" else " L/m", Icons.Default.WaterDrop)
    )
    Column(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(22.dp))
            .border(1.dp, StrokeColor, RoundedCornerShape(22.dp)).background(Panel)
    ) {
        specs.forEachIndexed { i, item ->
            Row(Modifier.fillMaxWidth().heightIn(min = 61.dp)
                .clickable { onEdit(listOf("NPS", "ID", "t", "mass", "volume")[i]) }
                .padding(horizontal = 13.dp),
                verticalAlignment = Alignment.CenterVertically) {
                if (i < 3) {
                    PipeDimensionIcon(i, Modifier.size(28.dp))
                } else {
                    Icon(item.third, contentDescription = null, tint = White,
                        modifier = Modifier.size(28.dp))
                }
                Spacer(Modifier.width(10.dp))
                Text(item.first, color = White, fontSize = (textSp - 2).sp, modifier = Modifier.weight(1f))
                Text(item.second, color = if (i == 0) Gold else White,
                    fontWeight = FontWeight.Bold, fontSize = textSp.sp)
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
        "OD" -> selected?.let { decimal(if (imperial) it.odInches else it.odMm, if (imperial && it.odInches < 14.0) 3 else 2) }
        "ID" -> selected?.let { decimal(if (imperial) it.idInches else it.idMm) }
        "t" -> selected?.wallMm?.let { decimal(if (imperial) it / 25.4 else it, if (imperial) 3 else 2) }
        "mass" -> selected?.kgPerMetre?.let { decimal(if (imperial) it * 0.671968975 else it) }
        else -> selected?.litresPerMetre?.let { decimal(if (imperial) it * 0.08052 else it) }
    } ?: ""
    var value by remember(field) { mutableStateOf(initial) }
    val input = value.trim().replace(",", ".").replace("″", "").replace("\"", "").trim()
    val number = input.toDoubleOrNull()
    val candidates = catalog.rows.asSequence()
        .filter { row ->
            when (field) {
                // The NPS picker is a catalogue, not a list of other SCHs for
                // the current size. A blank/current query lists every size.
                "NPS" -> input.isEmpty() || input == nps ||
                    row.nps.startsWith(input, ignoreCase = true)
                "SCH" -> row.nps == nps &&
                    (input.isEmpty() || row.schedule.startsWith(input, ignoreCase = true))
                else -> {
                    val actual = when (field) {
                        "OD" -> if (imperial) row.odInches else row.odMm
                        "ID" -> if (imperial) row.idInches else row.idMm
                        "t" -> if (imperial) row.wallMm / 25.4 else row.wallMm
                        "mass" -> if (imperial) row.kgPerMetre * 0.671968975 else row.kgPerMetre
                        else -> if (imperial) row.litresPerMetre * 0.08052 else row.litresPerMetre
                    }
                    val tolerance = if (imperial && (field == "t" || (field == "OD" && row.odInches < 14.0))) 0.00051 else 0.0051
                    number != null && number.isFinite() && abs(actual - number) <= tolerance
                }
            }
        }.distinctBy { if (field == "NPS") it.nps else
            it.standard + "|" + it.nps + "|" + it.schedule }
            .take(100).toList()
    val title = when (field) {
        "NPS" -> ui("NPS çapı")
        "SCH" -> ui("Schedule (SCH)")
        "OD" -> ui("Dış çap (OD)")
        "ID" -> ui("İç çap (ID)")
        "t" -> ui("Et kalınlığı")
        "mass" -> ui("Ağırlık")
        else -> ui("İç hacim")
    }
    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(
            if (CurrentUiLanguage.current == "tr") title + " " + ui("yazın")
            else ui("yazın") + " " + title, color = White
        ) },
        text = {
            Column {
                OutlinedTextField(value = value, onValueChange = { value = it },
                    singleLine = true, modifier = Modifier.fillMaxWidth(),
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 25.sp, color = White),
                    keyboardOptions = KeyboardOptions(keyboardType =
                        if (field == "NPS" || field == "SCH") KeyboardType.Text else KeyboardType.Decimal),
                    label = { Text(ui("Değer")) })
                Spacer(Modifier.height(9.dp))
                Text(ui("Yalnız tabloda kayıtlı nominal ölçüler kullanılabilir."),
                    color = Muted, fontSize = 13.sp)
                Spacer(Modifier.height(8.dp))
                if (candidates.isEmpty()) {
                    Text(ui("Eşleşen kayıt yok. NPS, SCH veya değeri kontrol edin."),
                        color = Gold, fontSize = 14.sp)
                } else {
                    Column(Modifier.heightIn(max = 210.dp).verticalScroll(rememberScrollState())) {
                        candidates.forEach { candidate ->
                            Text(if (field == "NPS") candidate.nps + "″" else
                                candidate.nps + "″  ·  SCH " + candidate.schedule +
                                "  ·  ASME " + candidate.standard,
                                color = White, fontSize = 17.sp,
                                modifier = Modifier.fillMaxWidth()
                                    .clip(RoundedCornerShape(9.dp))
                                    .clickable {
                                        val match = if (field == "NPS") {
                                            catalog.rows.firstOrNull {
                                                it.nps == candidate.nps && it.schedule == schedule &&
                                                    it.standard == standard
                                            } ?: catalog.rows.firstOrNull {
                                                it.nps == candidate.nps && it.schedule == schedule
                                            } ?: candidate
                                        } else candidate
                                        onChoose(match)
                                    }
                                    .padding(vertical = 12.dp, horizontal = 7.dp))
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(enabled = candidates.size == 1,
                onClick = { candidates.singleOrNull()?.let(onChoose) }) {
                Text(ui("Seç"), color = if (candidates.size == 1) Gold else Muted)
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text(ui("İptal"), color = Muted) } }
    )
}

@Composable
private fun LengthDialog(pipe: PipeSpec, imperial: Boolean, onDismiss: () -> Unit) {
    var length by remember { mutableStateOf("6") }
    val rawLength = length.replace(",", ".").toDoubleOrNull()
    val metres = rawLength?.let { if (imperial) it * 0.3048 else it }
    val massFactor = if (imperial) 2.20462262185 else 1.0
    val volumeFactor = if (imperial) 0.26417205236 else 1.0
    val massUnit = if (imperial) " lb" else " kg"
    val volumeUnit = if (imperial) " US gal" else " L"
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(ui("Boru uzunluğu hesabı"), color = White) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(ui(if (imperial) "Uzunluğu fit (ft) olarak girin." else
                    "Uzunluğu metre olarak girin."), color = White, fontSize = 17.sp)
                OutlinedTextField(
                    length, { length = it },
                    label = { Text(ui(if (imperial) "Uzunluk (ft)" else "Uzunluk (m)")) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true, modifier = Modifier.fillMaxWidth()
                )
                if (metres != null && metres > 0 && metres <= 100000) {
                    Text(ui("Boş ağırlık: ") + decimal(pipe.kgPerMetre * metres * massFactor) +
                        massUnit, fontSize = 19.sp, color = White)
                    Text(ui("İç hacim: ") + decimal(pipe.litresPerMetre * metres * volumeFactor) +
                        volumeUnit, fontSize = 19.sp, color = White)
                    Text(ui("Su dolu ağırlık: ") +
                        decimal(pipe.waterFilledKgPerMetre * metres * massFactor) +
                        massUnit, fontSize = 19.sp, color = White)
                } else {
                    Text(ui("Pozitif ve geçerli bir uzunluk girin."), color = Gold)
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text(ui("Kapat"), color = Gold) } }
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
    var useInches by rememberSaveable { mutableStateOf(imperial) }
    var outer by rememberSaveable { mutableStateOf(if (imperial) "6.625" else "168,3") }
    var thick by rememberSaveable { mutableStateOf(if (imperial) "0.280" else "7,11") }
    var searched by remember { mutableStateOf(false) }
    var selectedSchKey by rememberSaveable { mutableStateOf<String?>(null) }
    val keyboardController = androidx.compose.ui.platform.LocalSoftwareKeyboardController.current
    val focusManager = androidx.compose.ui.platform.LocalFocusManager.current
    val odInput = outer.replace(",", ".").toDoubleOrNull()
    val tInput = thick.replace(",", ".").toDoubleOrNull()
    val odMm = odInput?.let { if (useInches) it * 25.4 else it }
    val wallMm = tInput?.let { if (useInches) it * 25.4 else it }
    val suggestions = remember(odMm, catalog) {
        if (odMm == null || !odMm.isFinite() || odMm <= 0) emptyList()
        else catalog.rows
            .filter { kotlin.math.abs(it.odMm - odMm) <=
                maxOf(0.6, it.odMm * 0.002) }
            .sortedWith(compareBy<PipeSpec> { it.wallMm }
                .thenBy { it.schedule }.thenBy { it.standard })
            .distinctBy { it.standard + "|" + it.nps + "|" + it.schedule }
    }
    val matches = if (searched && odMm != null && wallMm != null) {
        val selected = selectedSchKey?.let { key ->
            suggestions.firstOrNull {
                it.standard + "|" + it.nps + "|" + it.schedule == key &&
                    kotlin.math.abs(it.wallMm - wallMm) < 0.015
            }
        }
        if (selected != null) listOf(selected)
        else catalog.findByMeasurement(odMm, wallMm)
    } else emptyList()

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(22.dp)) {
        PageHeading(ui("SCH Bul"), ui("Ölçtüğünüz dış çapı ve et kalınlığını girin."))
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(ui("Ölçü birimi"), color = White, fontSize = 18.sp, modifier = Modifier.weight(1f))
            listOf(true to ui("inç"), false to "mm").forEach { (inch, label) ->
                Surface(
                    color = if (useInches == inch) Gold else Panel,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.heightIn(min = 50.dp)
                        .clickable {
                            if (useInches != inch) {
                                val factor = if (inch) 1.0 / 25.4 else 25.4
                                outer = odInput?.let { decimal(it * factor, if (inch) 3 else 2) } ?: ""
                                thick = tInput?.let { decimal(it * factor, if (inch) 3 else 2) } ?: ""
                                useInches = inch
                                searched = false
                                selectedSchKey = null
                            }
                        }
                ) {
                    Box(Modifier.padding(horizontal = 18.dp), contentAlignment = Alignment.Center) {
                        Text(label, color = if (useInches == inch) Deep else White,
                            fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        OutlinedTextField(
            outer, { outer = it; searched = false; selectedSchKey = null }, modifier = Modifier.fillMaxWidth(),
            label = { Text(ui("Dış çap (") + if (useInches) ui("inç") + ")" else "mm)") },
            singleLine = true,
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 26.sp, color = White),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        Spacer(Modifier.height(17.dp))
        OutlinedTextField(
            thick, { thick = it; searched = false; selectedSchKey = null }, modifier = Modifier.fillMaxWidth(),
            label = { Text(ui("Et kalınlığı (") + if (useInches) ui("inç") + ")" else "mm)") },
            singleLine = true,
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 26.sp, color = White),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        if (suggestions.isNotEmpty()) {
            Spacer(Modifier.height(13.dp))
            Text(ui("Dış çapa uygun et kalınlıkları"), color = White,
                fontSize = 17.sp, fontWeight = FontWeight.Bold)
            Text(ui("Bir öneriye dokunarak SCH seçin"), color = Muted,
                fontSize = 14.sp)
            Spacer(Modifier.height(9.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(suggestions) { option ->
                    val wall = if (useInches) option.wallMm / 25.4 else option.wallMm
                    val formatted = decimal(wall, if (useInches) 3 else 2)
                    val unit = if (useInches) "in" else "mm"
                    val key = option.standard + "|" + option.nps + "|" + option.schedule
                    Surface(
                        modifier = Modifier.heightIn(min = 58.dp)
                            .clickable {
                                thick = formatted
                                selectedSchKey = key
                                searched = true
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            },
                        color = if (selectedSchKey == key) Gold else Panel,
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(Modifier.padding(horizontal = 14.dp, vertical = 8.dp)) {
                            Text("SCH " + option.schedule,
                                color = if (selectedSchKey == key) Deep else White,
                                fontSize = 17.sp, fontWeight = FontWeight.Bold)
                            Text(formatted + " " + unit + " · " + option.standard,
                                color = if (selectedSchKey == key) Deep else Muted,
                                fontSize = 14.sp)
                        }
                    }
                }
            }
        } else if (odMm != null && odMm.isFinite() && odMm > 0) {
            Spacer(Modifier.height(10.dp))
            Text(ui("Bu dış çapa uygun kayıt bulunamadı."),
                color = Muted, fontSize = 14.sp)
        }
        Spacer(Modifier.height(20.dp))
        GoldButton(ui("SCH Bul"), { searched = true })
        Spacer(Modifier.height(24.dp))
        if (searched) {
            if (matches.isEmpty()) {
                Text(ui("Bu ölçüye yakın kayıt bulunamadı. Birimi ve ölçüleri kontrol edin."),
                    fontSize = textSp.sp, color = Gold)
            } else {
                Text(ui("Olası nominal eşleşmeler"), color = White,
                    fontSize = 21.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(10.dp))
                matches.take(12).forEach { pipe ->
                    Surface(
                        color = Panel, shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                            .border(1.dp, StrokeColor, RoundedCornerShape(15.dp))
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(pipe.nps + "″  ·  DN " + pipe.dn + "  ·  SCH " + pipe.schedule,
                                fontSize = 22.sp, color = Gold, fontWeight = FontWeight.Bold)
                            Text("ASME " + pipe.standard, color = Muted, fontSize = 14.sp)
                            val odDisplay = if (useInches) pipe.odInches else pipe.odMm
                            val wallDisplay = if (useInches) pipe.wallMm / 25.4 else pipe.wallMm
                            val unit = if (useInches) "in" else "mm"
                            Text("OD " + decimal(odDisplay, if (useInches && pipe.odInches < 14.0) 3 else 2) + " " + unit,
                                color = White, fontSize = 17.sp)
                            Text("t " + decimal(wallDisplay, if (useInches) 3 else 2) + " " + unit,
                                color = White, fontSize = 17.sp)
                        }
                    }
                }
            }
            Spacer(Modifier.height(13.dp))
            Text(ui("Nominal eşleştirmedir; basınç uygunluğu veya sertifika değildir."),
                color = Muted, fontSize = 14.sp)
        }
    }
}

@Composable
private fun CombinedTableStandardsPage(
    catalog: PipeCatalog, nps: String, sizes: List<String>, onNps: (String) -> Unit,
    textSp: Int, imperial: Boolean, onSelect: (PipeSpec) -> Unit
) {
    val rows = remember(nps) {
        catalog.rows.filter { it.nps == nps }.sortedWith(
            compareBy<PipeSpec> { it.schedule.filter(Char::isDigit).toIntOrNull() ?: Int.MAX_VALUE }
                .thenBy { it.schedule }.thenBy { it.standard })
    }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp)) {
        PageHeading(ui("Tablo / Standart"), ui("ASME standartları ve nominal ölçüler"))
        InfoPanel("ASME B36.10M", ui("Karbon çelik boruların nominal çap, et ve Schedule serileri."), textSp)
        Spacer(Modifier.height(12.dp))
        InfoPanel("ASME B36.19M", ui("Paslanmaz çelik borularda S işaretli Schedule serileri ayrı kayıtlardır."), textSp)
        Spacer(Modifier.height(12.dp))
        InfoPanel(ui("Hesap formülleri"),
            ui("İç çap = dış çap − 2 × et kalınlığı.") + "\n" +
            ui("Kütle: çelik kesit alanı × 7850 kg/m³.") + "\n" +
            ui("İç hacim: π × iç çap² / 4 × uzunluk.") + "\n" +
            ui("Değerler nominaldir; basınç tasarım hesabı değildir."), textSp)
        Spacer(Modifier.height(22.dp))
        Picker(ui("NPS (inç)"), nps + "″", sizes.map { it + "″" },
            { onNps(it.removeSuffix("″")) }, Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        Text(ui("Nominal SCH ölçüleri"), color = White, fontWeight = FontWeight.Bold,
            fontSize = 20.sp)
        rows.forEach { p ->
            Column(
                Modifier.fillMaxWidth().padding(top = 8.dp)
                    .clip(RoundedCornerShape(12.dp)).background(Panel)
                    .clickable { onSelect(p) }.padding(14.dp)
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("SCH " + p.schedule, color = Gold, fontSize = 21.sp,
                        fontWeight = FontWeight.Bold)
                    Text("ASME " + p.standard, color = Muted, fontSize = 14.sp)
                }
                Spacer(Modifier.height(6.dp))
                val unit = if (imperial) "in" else "mm"
                val thick = if (imperial) p.wallMm / 25.4 else p.wallMm
                val inner = if (imperial) p.idInches else p.idMm
                Text(ui("Et: ") + decimal(thick, if (imperial) 3 else 2) + " " + unit,
                    color = White, fontSize = 17.sp)
                Text(ui("İç çap: ") + decimal(inner, if (imperial) 3 else 2) + " " + unit,
                    color = White, fontSize = 17.sp)
            }
        }
        Spacer(Modifier.height(24.dp))
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
    imperial: Boolean, onImperial: (Boolean) -> Unit, textSp: Int,
    languageSelection: String, onLanguageChange: (String) -> Unit,
    onReplayTutorial: () -> Unit
) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(22.dp)) {
        PageHeading(ui("Ayarlar"), ui("Sahada kolay okunabilen ekran"))
        Row(Modifier.fillMaxWidth().heightIn(min = 72.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(ui("Büyük yazı"), color = White, fontSize = 21.sp, fontWeight = FontWeight.Bold)
                Text(ui("Daha kolay okunan ölçüler"), color = Muted, fontSize = 15.sp)
            }
            Switch(checked = largeText, onCheckedChange = onLargeText)
        }
        HorizontalDivider(color = StrokeColor)
        Row(Modifier.fillMaxWidth().heightIn(min = 72.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(ui("İnç / lb / gal"), color = White, fontSize = 21.sp, fontWeight = FontWeight.Bold)
                Text(ui("Metrik yerine Imperial göster"), color = Muted, fontSize = 15.sp)
            }
            Switch(checked = imperial, onCheckedChange = onImperial)
        }
        HorizontalDivider(color = StrokeColor)
        Spacer(Modifier.height(18.dp))
        // Language names are autonyms: they must not change when the UI language changes.
        val languageOptions = listOf(
            "system" to ui("Sistem dili"),
            "tr" to "Türkçe",
            "en" to "English",
            "ru" to "Русский"
        )
        Picker(ui("Dil"), languageOptions.first { it.first == languageSelection }.second,
            languageOptions.map { it.second },
            { label -> onLanguageChange(languageOptions.first { it.second == label }.first) },
            Modifier.fillMaxWidth())
        Spacer(Modifier.height(18.dp))
        InfoPanel(ui("Çevrimdışı kullanım"), ui("Boru tablosu cihazda bulunur. Hesaplamak için internet gerekmez."), textSp)
        Spacer(Modifier.height(14.dp))
        InfoPanel(ui("Veri kapsamı"), ui("Uygulama yalnızca geçerli nominal et kalınlığı kaydı olan NPS / SCH seçeneklerini listeler. Bilinmeyen veriler tahmin edilmez."), textSp)
        Spacer(Modifier.height(14.dp))
        GoldButton(ui("Öğreticiyi tekrar göster"), onReplayTutorial)
    }
}
