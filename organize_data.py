import os, shutil, glob, csv, json

src_dir = os.path.expanduser("~/projects/pipedata/raw_apk/assets")
dest_root = os.path.expanduser("~/projects/pipedata/data")

svg_dir = os.path.join(dest_root, "svg")
dxf_dir = os.path.join(dest_root, "dxf")
img_dir = os.path.join(dest_root, "images")
raw_csv_dir = os.path.join(dest_root, "csv", "raw")

for d in [svg_dir, dxf_dir, img_dir, raw_csv_dir]:
    os.makedirs(d, exist_ok=True)

# Copy vectors and images
for f in os.listdir(src_dir):
    src_f = os.path.join(src_dir, f)
    if not os.path.isfile(src_f): continue
    fl = f.lower()
    if fl.endswith(".svg"):
        shutil.copy2(src_f, os.path.join(svg_dir, f))
    elif fl.endswith(".dxf"):
        shutil.copy2(src_f, os.path.join(dxf_dir, f))
    elif fl.endswith(".png") or fl.endswith(".jpg") or fl.endswith(".gif"):
        shutil.copy2(src_f, os.path.join(img_dir, f))
    elif fl.endswith(".csv"):
        shutil.copy2(src_f, os.path.join(raw_csv_dir, f))

svg_cnt = len(os.listdir(svg_dir))
dxf_cnt = len(os.listdir(dxf_dir))
img_cnt = len(os.listdir(img_dir))
csv_cnt = len(os.listdir(raw_csv_dir))

print(f"SVGs copied: {svg_cnt}")
print(f"DXFs copied: {dxf_cnt}")
print(f"Images copied: {img_cnt}")
print(f"Raw CSVs copied: {csv_cnt}")

