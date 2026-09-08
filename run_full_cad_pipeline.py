#!/usr/bin/env python3
import os, glob, json
from cad_pipeline import modernize_single_svg, MANIFEST_PATH

svg_files = sorted([os.path.basename(f) for f in glob.glob('/data/data/com.termux/files/home/projects/pipedata/data/svg/*.svg')])
print(f'Starting full AutoCAD modernization pipeline on all {len(svg_files)} SVG files...')

# Load manifest
with open(MANIFEST_PATH) as f:
    manifest = json.load(f)

catalog = manifest.get('svg_catalog', {})
passed_count = 0
failed_files = []

for idx, fn in enumerate(svg_files, 1):
    try:
        out_fn, qc = modernize_single_svg(fn)
        if qc['all_passed']:
            passed_count += 1
            status_str = 'PASS'
        else:
            status_str = 'FAIL'
            failed_files.append((fn, qc))
            
        # Update manifest entry
        if fn in catalog:
            catalog[fn]['status'] = 'completed'
            catalog[fn]['modern_filename'] = out_fn
            catalog[fn]['qc_passed'] = qc['all_passed']
            catalog[fn]['part_lines'] = qc['part_lines_preserved']
            catalog[fn]['hatch_lines'] = qc['hatch_lines_preserved']
            catalog[fn]['arrows_converted'] = qc['arrowheads_converted']
            catalog[fn]['placeholders_verified'] = qc['placeholders']
            
        if idx % 20 == 0 or idx == len(svg_files):
            print(f'[{idx}/{len(svg_files)}] Processed... ({passed_count} PASS, {len(failed_files)} FAIL)')
    except Exception as e:
        print(f'ERROR processing {fn}: {e}')
        failed_files.append((fn, str(e)))

manifest['metadata']['completed_svg_count'] = passed_count
manifest['metadata']['total_svg_count'] = len(svg_files)
manifest['metadata']['qc_pass_rate'] = f'{(passed_count / len(svg_files) * 100):.1f}%'

with open(MANIFEST_PATH, 'w') as f:
    json.dump(manifest, f, indent=2, ensure_ascii=False)

import shutil
shutil.copy(MANIFEST_PATH, '/storage/emulated/0/Download/svg_manifest.json')

print(f'\nPipeline completed!')
print(f'Total: {len(svg_files)}')
print(f'Passed: {passed_count} / {len(svg_files)} ({(passed_count/len(svg_files)*100):.1f}%)')
if failed_files:
    print(f'Failed: {len(failed_files)}')
    for f, reason in failed_files[:10]:
        print(f'  - {f}: {reason}')
