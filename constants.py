"""
Pipedata Engineering Constants & Parameters
Extracted via Reverse Engineering from Pipedata (A-1.6.02)
"""

# Physical Constants
DENSITY_WATER_METRIC = 1000.0        # kg/m³
DENSITY_WATER_IMPERIAL = 62.428      # lb/ft³
DENSITY_CARBON_STEEL_METRIC = 7850.0 # kg/m³
DENSITY_CARBON_STEEL_IMPERIAL = 490.0 # lb/ft³
DENSITY_STAINLESS_STEEL = 8000.0     # kg/m³
DEFAULT_INSULATION_DENSITY_METRIC = 145.0  # kg/m³ (also 150 kg/m³ in doc.txt)
DEFAULT_INSULATION_DENSITY_IMPERIAL = 9.0   # lb/ft³

# Pipe Safe Span Calculation Defaults
SAFE_SPAN_MAX_DEFLECTION_METRIC = 10.0      # mm
SAFE_SPAN_MAX_DEFLECTION_IMPERIAL = 0.375   # inches (3/8")
SAFE_SPAN_ALLOWABLE_STRESS_METRIC = 22.0    # MPa (conservative continuous beam, max 68 MPa)
SAFE_SPAN_ALLOWABLE_STRESS_IMPERIAL = 1.4   # Tonf/in² (~3,136 psi)
SAFE_SPAN_YOUNGS_MODULUS_METRIC = 203390.0  # MPa
SAFE_SPAN_YOUNGS_MODULUS_IMPERIAL = 13169.0 # Tonf/in² (~29.5e6 psi)
SAFE_SPAN_MAX_TEMP_METRIC = 400.0           # °C (300°C to 400°C)
SAFE_SPAN_MAX_TEMP_IMPERIAL = 750.0         # °F
SAFE_SPAN_WIND_SPEED_DEFAULT = 50.0         # m/s
SAFE_SPAN_CORROSION_ALLOWANCE_METRIC = 1.5  # mm
SAFE_SPAN_CORROSION_ALLOWANCE_IMPERIAL = 0.0625 # inches (1/16")

# Nominal Pipe Sizes (NPS to DN mapping)
NPS_DN_MAP = {
    "1/8": 6,
    "1/4": 8,
    "3/8": 10,
    "1/2": 15,
    "3/4": 20,
    "1": 25,
    "1-1/4": 32,
    "1-1/2": 40,
    "2": 50,
    "2-1/2": 65,
    "3": 80,
    "3-1/2": 90,
    "4": 100,
    "5": 125,
    "6": 150,
    "8": 200,
    "10": 250,
    "12": 300,
    "14": 350,
    "16": 400,
    "18": 450,
    "20": 500,
    "22": 550,
    "24": 600,
    "26": 650,
    "28": 700,
    "30": 750,
    "32": 800,
    "34": 850,
    "36": 900,
    "38": 950,
    "40": 1000,
    "42": 1050,
    "44": 1100,
    "46": 1150,
    "48": 1200,
    "52": 1300,
    "54": 1350,
    "56": 1400,
    "60": 1500
}

# Standard Pressure Classes
ASME_FLANGE_CLASSES = ["150", "300", "400", "600", "900", "1500", "2500"]
ASME_B16_47_SERIES_A_CLASSES = ["150", "300", "400", "600", "900"]
ASME_B16_47_SERIES_B_CLASSES = ["75", "150", "300", "400", "600", "900"]
FORGED_FITTINGS_CLASSES = ["2000", "3000", "6000", "9000"]
API_6A_PRESSURE_RATINGS = ["2000", "3000", "5000", "10000", "15000", "20000"]
DIN_PN_RATINGS = ["2.5", "6", "10", "16", "25", "40", "63", "100", "160", "250", "320", "400"]

# Pipe Schedules
PIPE_SCHEDULES = [
    "5", "5S", "10", "10S", "20", "30", "40", "40S", "STD",
    "60", "80", "80S", "XS", "100", "120", "140", "160", "XXS"
]

