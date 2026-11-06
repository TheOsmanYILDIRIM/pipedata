package com.avenox.pipedata

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.currentComposer

/** Language preference: "system", "tr", "en" or "ru". */
internal val CurrentUiLanguage = compositionLocalOf { "tr" }

private val translations: Map<String, Pair<String, String>> = mapOf(
    "Boru" to ("Pipe" to "Труба"),
    "SCH Bul" to ("Find SCH" to "Найти SCH"),
    "Tablo" to ("Table" to "Таблица"),
    "Ayarlar" to ("Settings" to "Настройки"),
    "Ölçü birimi" to ("Unit" to "Единицы"),
    "inç" to ("inch" to "дюйм"),
    "İç Çap (ID)" to ("Inner diameter (ID)" to "Внутренний диаметр (ID)"),
    "Et Kalınlığı (t)" to ("Wall thickness (t)" to "Толщина стенки (t)"),
    "Dış Çap (OD) · Seçmek için dokunun" to ("Outside diameter (OD) · Tap to select" to "Наружный диаметр (OD) · Выбрать"),
    "Halkayı parmağınızla çevirin" to ("Rotate the dial with your finger" to "Поверните шкалу пальцем"),
    "Schedule (SCH)" to ("Schedule (SCH)" to "Серия (SCH)"),
    "Bu NPS ve Schedule için doğrulanmış ölçü yok." to ("No validated dimensions for this NPS/SCH." to "Нет проверенных размеров для NPS/SCH."),
    "Hesapla" to ("Calculate" to "Рассчитать"),
    "Dış Çap (OD)" to ("Outside diameter (OD)" to "Наружный диаметр (OD)"),
    "Et Kalınlığı" to ("Wall thickness" to "Толщина стенки"),
    "Ağırlık" to ("Weight" to "Масса"),
    "İç Hacim" to ("Internal volume" to "Внутренний объём"),
    "NPS çapı" to ("NPS size" to "Размер NPS"),
    "Dış çap (OD)" to ("Outside diameter (OD)" to "Наружный диаметр (OD)"),
    "İç çap (ID)" to ("Inner diameter (ID)" to "Внутренний диаметр (ID)"),
    "Et kalınlığı" to ("Wall thickness" to "Толщина стенки"),
    "İç hacim" to ("Internal volume" to "Внутренний объём"),
    "yazın" to ("Enter" to "Введите"),
    "Değer" to ("Value" to "Значение"),
    "Yalnız tabloda kayıtlı nominal ölçüler kullanılabilir." to ("Only cataloged nominal dimensions are supported." to "Доступны только номинальные размеры из таблицы."),
    "Eşleşen kayıt yok. NPS, SCH veya değeri kontrol edin." to ("No match. Check NPS, SCH or the value." to "Нет совпадений. Проверьте NPS, SCH или значение."),
    "Seç" to ("Select" to "Выбрать"),
    "İptal" to ("Cancel" to "Отмена"),
    "Boru uzunluğu hesabı" to ("Pipe length calculation" to "Расчёт длины трубы"),
    "Uzunluğu fit (ft) olarak girin." to ("Enter length in feet (ft)." to "Введите длину в футах (ft)."),
    "Uzunluğu metre olarak girin." to ("Enter length in metres (m)." to "Введите длину в метрах (m)."),
    "Uzunluk (ft)" to ("Length (ft)" to "Длина (ft)"),
    "Uzunluk (m)" to ("Length (m)" to "Длина (m)"),
    "Boş ağırlık: " to ("Empty weight: " to "Масса пустой трубы: "),
    "İç hacim: " to ("Internal volume: " to "Внутренний объём: "),
    "Su dolu ağırlık: " to ("Water-filled weight: " to "Масса с водой: "),
    "Pozitif ve geçerli bir uzunluk girin." to ("Enter a valid positive length." to "Введите положительную длину."),
    "Kapat" to ("Close" to "Закрыть"),
    "Ölçtüğünüz dış çapı ve et kalınlığını girin." to ("Enter the measured OD and wall thickness." to "Введите наружный диаметр и толщину стенки."),
    "Dış çap (" to ("Outside diameter (" to "Наружный диаметр ("),
    "Et kalınlığı (" to ("Wall thickness (" to "Толщина стенки ("),
    "Bu ölçüye yakın kayıt bulunamadı. Birimi ve ölçüleri kontrol edin." to ("No close match. Check the units and measurements." to "Совпадений нет. Проверьте единицы и размеры."),
    "Olası nominal eşleşmeler" to ("Possible nominal matches" to "Возможные номинальные совпадения"),
    "Nominal eşleştirmedir; basınç uygunluğu veya sertifika değildir." to ("Nominal match only; not a pressure rating or certification." to "Только номинальное соответствие; не расчёт давления и не сертификат."),
    "Tablo / Standart" to ("Table / Standards" to "Таблица / Стандарты"),
    "ASME standartları ve nominal ölçüler" to ("ASME standards and nominal dimensions" to "Стандарты ASME и номинальные размеры"),
    "Karbon çelik boruların nominal çap, et ve Schedule serileri." to ("Nominal dimensions, wall thicknesses and schedules for carbon steel pipes." to "Номинальные размеры, толщины стенок и SCH для труб из углеродистой стали."),
    "Paslanmaz çelik borularda S işaretli Schedule serileri ayrı kayıtlardır." to ("S-series schedules for stainless steel pipes are separate records." to "Серии SCH с суффиксом S для нержавеющих труб указаны отдельно."),
    "Hesap formülleri" to ("Calculation formulas" to "Формулы расчёта"),
    "İç çap = dış çap − 2 × et kalınlığı." to ("ID = OD − 2 × wall thickness." to "Внутренний диаметр = наружный − 2 × толщина стенки."),
    "Kütle: çelik kesit alanı × 7850 kg/m³." to ("Mass: steel cross-section × 7850 kg/m³." to "Масса: площадь сечения металла × 7850 кг/м³."),
    "İç hacim: π × iç çap² / 4 × uzunluk." to ("Internal volume: π × ID² / 4 × length." to "Внутренний объём: π × ID² / 4 × длина."),
    "Değerler nominaldir; basınç tasarım hesabı değildir." to ("Nominal values; not a pressure design calculation." to "Номинальные значения; не расчёт давления."),
    "NPS (inç)" to ("NPS (inch)" to "NPS (дюйм)"),
    "Nominal SCH ölçüleri" to ("Nominal SCH dimensions" to "Номинальные размеры SCH"),
    "Et: " to ("Wall: " to "Стенка: "),
    "İç çap: " to ("ID: " to "Внутр. диаметр: "),
    "Sahada kolay okunabilen ekran" to ("Easy-to-read field display" to "Удобное отображение размеров"),
    "Büyük yazı" to ("Large text" to "Крупный текст"),
    "Daha kolay okunan ölçüler" to ("Easier-to-read dimensions" to "Более читаемые размеры"),
    "İnç / lb / gal" to ("Inch / lb / gal" to "Дюйм / фунт / галлон"),
    "Metrik yerine Imperial göster" to ("Use imperial units instead of metric" to "Показывать британские единицы вместо метрических"),
    "Çevrimdışı kullanım" to ("Offline use" to "Работа без интернета"),
    "Boru tablosu cihazda bulunur. Hesaplamak için internet gerekmez." to ("Pipe data is stored on device; no internet is required." to "Таблица труб хранится на устройстве; интернет не требуется."),
    "Veri kapsamı" to ("Data coverage" to "Объём данных"),
    "Uygulama yalnızca geçerli nominal et kalınlığı kaydı olan NPS / SCH seçeneklerini listeler. Bilinmeyen veriler tahmin edilmez." to ("Only NPS/SCH entries with valid nominal wall thickness are listed; missing data is never guessed." to "Показаны только NPS/SCH с известной толщиной стенки. Недостающие данные не выдумываются."),
    "Dil" to ("Language" to "Язык"),
    "Sistem dili" to ("System language" to "Язык системы"),
    "Türkçe" to ("Turkish" to "Турецкий"),
    "İngilizce" to ("English" to "Английский"),
    "Rusça" to ("Russian" to "Русский"),
    "Dış çapa uygun et kalınlıkları" to ("Wall thicknesses for this OD" to "Толщины стенок для этого диаметра"),
    "Bir öneriye dokunarak SCH seçin" to ("Tap a suggestion to select SCH" to "Нажмите на вариант, чтобы выбрать SCH"),
    "Bu dış çapa uygun kayıt bulunamadı." to ("No OD match in the catalog." to "Для этого диаметра нет записей."),
    "Boru standart tablosu yüklenemedi. Uygulamayı güncelleyin." to ("Pipe data failed to load. Update the app." to "Не удалось загрузить таблицу труб. Обновите приложение."),
    "Döner NPS sayacı, seçili çap " to ("NPS dial, selected size " to "Шкала NPS, выбранный размер "),
    "Sonraki boru çapı" to ("Next pipe diameter" to "Следующий диаметр трубы"),
    "NPS seç" to ("Select NPS" to "Выбрать NPS"),
    "Et kalınlığı önerileri" to ("Wall thickness suggestions" to "Варианты толщины стенки")
)

internal fun translateUi(value: String, language: String): String = when (language) {
    "en" -> translations[value]?.first ?: value
    "ru" -> translations[value]?.second ?: value
    else -> value
}

@Composable
internal fun ui(value: String): String = translateUi(value, CurrentUiLanguage.current)
