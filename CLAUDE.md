# Antigravity Workspace Guidelines & Rules (Avenox Doktrini)

## 1. Kural Senkronizasyonu (Sync Rule - Zorunlu)
* **Otomatik Eşzamanlılık:** `AGENTS.md`, `GEMINI.md` veya `CLAUDE.md` dosyalarından herhangi biri güncellendiğinde, diğer tüm kural dosyaları da birebir aynı içerikle senkronize olarak güncellenmek ZORUNDADIR. Kurallar şişirilmez, öz ve net tutulur.

## 2. Beyin (Vault) ile Atölye (Proje) Ayrımı
* **Beyin Ayrı Oda, Atölye Ayrı Oda:** Bu vault; notların, kuralların, kararların, sözlüğün ve proje özetlerinin yaşadığı yerdir. Kodun kendisi doğrudan vault köküne saçılmaz.
* **Proje İskeleti:** Her projenin kendi atölye klasöründe `AGENTS.md`/`CLAUDE.md`, `notes.md` (dump & mimari), `reports/` (araştırmalar), `backlog.md` (yarım kalan işler) ve `constants` dosyası bulunur.

## 3. Agentic Engineering & "Bitti" Protokolü
* **Sınır Çiz, Yol Çizme:** Ajan brief'i 5 parçadan oluşur: *Hedef* (ne olursa bitti sayılır) + *Eldekiler/Ham Veri* + *Sert Sınırlar* (dokunma listesi) + *Ölçü* + *Serbestlik* (hata görürse söyleme inisiyatifi).
* **Vibe Coding Yasaktır:** Ajanın "bitti" demesine körü körüne güvenilmez. Yapılan her değişiklik `git diff` ve CLI kontrolleri ile doğrulanır.
* **Slop ve Yarım İş Engeli:** Yarım kalan veya sonraki oturuma devreden her iş projenin `backlog.md` dosyasına kaydedilir.

## 4. Termux Araç Tercihi & CLI Entegrasyonu
* **Native CLI Önceliği:** Veri işleme, metin manipülasyonu, JSON sorgulama veya dosya incelemelerinde Termux native araçları (`jq`, `fd`, `rg`, `curl`, `tree`, `bat`, `sqlite3`, `esbuild` vb.) kullanılır. Karmaşık scriptler yerine Unix pipeline'ları (`curl | jq`) tercih edilir.
* **No Termux API / Bildirim Yok:** `termux-api` kurulmaz ve Android sistem bildirimleri tetiklenmez. Gerekli araçlar `pkg install -y <paket>` ile kurulur.
* **Termal Yönetim & Python Optimizasyonu:** Python scriptleri çalıştırılırken `py-cool` veya `nice -n 15 taskset -c 0-5 python3` kullanılır. CPU yoğun derleme ve arama işlemleri batarya ve ısınmayı korumak için 2 thread (`-j 2`) ile sınırlandırılır.

## 5. Qwen MCP Danışmanlık Kuralı
* **Zengin Context ile Danışma:** Karmaşık algoritmalar, mimari tasarım, procedural generation ve çetrefilli hata ayıklama süreçlerinde `qwen` MCP aracı (`qwen_chat`) üzerinden Qwen modeline danışılır. Asla yüzeysel prompt gönderilmez; kaynak kod, veri yapıları, teknik kısıtlar ve uç durumlar eksiksiz aktarılır.

## 6. Asla Yerelde Derleme Yapma & Git Operatörü Kuralı (GitHub CI/CD Kuralı - Kesin Kural)
* **Yerel Derleme Yasaktır:** Asla ama asla yerelde (cihaz / Termux üzerinde) ağır derleme (Gradle assembleDebug/Release, APK build vb.) yapılmaz. Değişiklikler GitHub'a pushlanır ve derleme GitHub Actions üzerinde gerçekleştirilir.
* **Ana Ajan Git İşlemi Yapamaz (Kesin Yasak):** Ana ajan, doğrudan kendisi `git add`, `git commit` veya `git push` komutlarını ÇALIŞTIRAMAZ.
* **`git-operator` Subagent Zorunluluğu:** Tüm Git süreci (status, diff kontrolü, stage, commit mesajı ve `git push`) baştan itibaren MUTLAKA `git-operator` subagent'ına (`gemini-3.8-flash-low`, `Model: 'flash'`, Low) devredilmek zorundadır.
* **Build Takibi Şartı:** Push işlemi yapıldıktan sonra `git-operator`, tetiklenen GitHub Actions CI/CD derleme/test sürecini (`gh run watch` vb.) sonuna kadar izlemek, sonucu beklemek ve build başarı durumunu ana ajana raporlamakla yükümlüdür.
