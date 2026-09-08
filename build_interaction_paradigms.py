#!/usr/bin/env python3
"""
Build High-Fidelity Interactive Showcase for 7 Radical Interaction Paradigms
Derived from Real Professional Engineering & Industrial Applications.
"""

import os, re, shutil

PROJECT_DIR = '/data/data/com.termux/files/home/projects/pipedata'
OUT_HTML = os.path.join(PROJECT_DIR, 'pipedata_interaction_paradigms.html')
DOWNLOAD_HTML = '/storage/emulated/0/Download/pipedata_interaction_paradigms.html'

def load_svg(fname):
    p = os.path.join(PROJECT_DIR, 'data/svg_modern', fname)
    if os.path.exists(p):
        with open(p) as f:
            c = f.read()
        return re.sub(r'<\?xml.*?\?>', '', c)
    return '<svg viewBox="0 0 100 100"><text x="10" y="50" fill="#fff">SVG Missing</text></svg>'

svg_flange = load_svg('modern_flan1.svg')
svg_pipe = load_svg('modern_pipe1.svg').replace('[[3]]', '14"').replace('[[4]]', '0.375"').replace('[[5]]', '5.5"').replace('[[6]]', '13.25"')
svg_tee = load_svg('modern_ftbw6.svg').replace('[[4]]', '11.0"').replace('[[5]]', '11.0"')
svg_cap = load_svg('modern_ftbw8.svg').replace('[[4]]', '6.50"').replace('[[DB2_4]]', '0.375"')

print("SVGs loaded successfully.")
