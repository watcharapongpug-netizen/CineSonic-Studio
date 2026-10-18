# CineSonic Studio 🎬⚡🎵
> **AI Film & Music Production Suite for Android**  
> เปลี่ยนไอเดียประโยคเดียว ให้กลายเป็นภาพยนตร์สั้นและเพลงประกอบสมบูรณ์แบบด้วย AI Pipeline หลายขั้นตอน

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Language-Kotlin%202.2-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose%20M3-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/CI%2FCD-Build%20APK-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

---

## 📌 จุดเด่นของระบบ (Core Features)

- 🎥 **12-Step Film Pipeline (F1–F12):**
  - **F1 (Brief):** สัมภาษณ์สั้นๆ เพื่อสร้างบรีฟ แนวเรื่อง กลุ่มผู้ชม โทนความยาว 60-180 วินาที
  - **F2 (Story):** Logline, Synopsis และ Beat Sheet 3 องก์ (Setup, Midpoint, Climax, Resolution)
  - **F3 (Bible & Characters):** Style Bible ล็อก **Character Consistency Pack** คุมหน้าตา สีเสื้อผ้า และแสงให้เหมือนกันทุกช็อต
  - **F4 (Screenplay):** บทภาพยนตร์ฉากต่อฉาก บทพูดภาษาไทย/อังกฤษ ที่เป็นธรรมชาติ
  - **F5 (Storyboard):** ช็อตลิสต์ 8 ช็อตพร้อมขนาดภาพ มุมกล้อง การเคลื่อนไหว และตรวจกฎแกนสายตา 180°
  - **F6 (Keyframes):** คีย์เฟรมภาพต้นแบบ ตรวจคะแนนความตรงกับ Consistency Pack และระบุจุด Inpaint
  - **F7 (Video Takes):** Image-to-Video หลายเทค จัดการเทคร่างและเทคจริง
  - **F8 (Voice & Subs):** สังเคราะห์เสียงพูด TTS กำกับอารมณ์ ตรวจสอบวรรณยุกต์ไทย และสร้างซับไตเติลตรงไทม์โค้ด
  - **F9 (Score):** เชื่อมกับ Music Pipeline โหมดภาพยนตร์ พร้อม Cue Sheet
  - **F10 (SFX & Ducking):** บรรยากาศแวดล้อม และระบบปรับลดเสียงอัตโนมัติ (บทพูด > SFX > ดนตรี)
  - **F11 (Editing):** ไทม์ไลน์มัลติแทร็ก 4 สตรีม (Video, Dialogue, SFX, BGM)
  - **F12 (QC & Export):** ตรวจสอบอัตโนมัติ 5 หมวด และส่งออกเป็นวิดีโอ, ซับ SRT, และไฟล์โปรเจกต์ JSON พร้อม C2PA AI Tag

- 🎶 **7-Step Music Pipeline (M1–M7):**
  - **M1 (Brief):** จุดประสงค์ อารมณ์ BPM คีย์เพลง
  - **M2 (Structure):** โครงสร้างเพลง Intro / Verse / Chorus / Outro
  - **M3 (Lyrics):** ประพันธ์เนื้อร้องสองภาษา ตรวจสัมผัสพยางค์และวรรณยุกต์ไทย
  - **M4 (Music Gen):** สังเคราะห์ดนตรีแยก Stems
  - **M5 (Vocals):** สังเคราะห์เสียงร้อง AI ที่ได้รับความยินยอม
  - **M6 (Hit-Points):** ซิงก์จังหวะดนตรีเข้ากับจุดตัดต่อภาพยนตร์
  - **M7 (Master Mix):** ปรับความดังมาตรฐานสากล -14.2 LUFS

- 🤖 **Step Assistant Contract (8 มิติ):**
  ทุกขั้นตอนขับเคลื่อนด้วยผู้ช่วย AI เฉพาะทางที่ส่งมอบผลลัพธ์เป็น JSON Schema 8 มิติ:
  1. **Explain** 2. **Interview** 3. **Propose (3 ตัวเลือก)** 4. **Estimate (เวลา/งบ)** 5. **Critique (1-5 ดาว)** 6. **Refine** 7. **Conflict Check** 8. **Handoff** พร้อม **Approval Gate** ให้ผู้ใช้ตัดสินใจ

- 🎛️ **Dual-Engine Architecture (Mock + Gemini API):**
  - **Mock Procedural Engine:** จำลองสตอรี่บอร์ด คีย์เฟรม เสียงดนตรีสังเคราะห์สดผ่าน Android `AudioTrack` ได้ฟรี 100% โดยไม่ต้องใช้เน็ตหรือคีย์
  - **Google Gemini API:** รองรับการเชื่อมต่อ `gemini-3.5-flash` สำหรับการขยายความคิดสร้างสรรค์จริง

---

## 🏗️ โครงสร้างโปรเจกต์ (Project Structure)

```text
cinesonic-studio/
├── .github/
│   └── workflows/
│       └── build-apk.yml          # GitHub Actions CI/CD อัตโนมัติสร้าง APK
├── app/
│   ├── build.gradle.kts           # การตั้งค่า Dependencies และ Build Configurations
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/example/
│       │   │   ├── MainActivity.kt                # หน้าหลักของแอปพลิเคชัน
│       │   │   ├── data/
│       │   │   │   ├── ai/                        # Mock Engine & Gemini API Client
│       │   │   │   ├── audio/                     # AudioTrack Synthesizer
│       │   │   │   ├── local/                     # Room SQLite Database & DAOs
│       │   │   │   └── model/                     # Project, Shot, Step Contract Models
│       │   │   └── ui/
│       │   │       ├── components/                # Header, Canvas, Timeline, Modals, CoPilot
│       │   │       ├── screens/                   # Workspace Screen (F1-F12, M1-M7)
│       │   │       ├── theme/                     # Cyberpunk Theme Tokens
│       │   │       └── viewmodel/                 # CineSonicViewModel
│       │   └── res/                               # Assets, Drawables, Mipmap Launcher Icon
├── gradle/
│   ├── libs.versions.toml         # Version Catalog
│   └── wrapper/                   # Gradle Wrapper JAR & Properties
├── .env.example                   # ตัวอย่างการตั้งค่า API Keys
├── gradlew / gradlew.bat          # Gradle Wrapper Executable Scripts
├── PLAN.md                        # แผนสถาปัตยกรรมระบบ
├── AGENTS.md                      # บทบาทของ AI Agent และข้อตกลง
├── PROGRESS.md                    # บันทึกความคืบหน้า
└── DECISIONS.md                   # Architecture Decision Records (ADR)
```

---

## 🚀 การติดตั้งและรันในเครื่อง (Getting Started)

### ความต้องการของระบบ:
- **Android Studio Ladybug หรือใหม่กว่า**
- **JDK 17 หรือ JDK 21**
- **Android SDK:** Compile SDK 36, Min SDK 24+ (Android 7.0+)

### คำสั่งโคลนและคอมไพล์:
```bash
git clone https://github.com/YOUR_USERNAME/cinesonic-studio.git
cd cinesonic-studio
chmod +x gradlew

# รัน Unit Tests
./gradlew testDebugUnitTest

# คอมไพล์และสร้างไฟล์ APK สำหรับติดตั้ง
./gradlew assembleDebug
```
ไฟล์ APK จะถูกสร้างขึ้นที่: `app/build/outputs/apk/debug/app-debug.apk`

---

## ⚙️ การตั้งค่า GitHub Actions CI/CD

Repository นี้มีไฟล์ `.github/workflows/build-apk.yml` ติดตั้งมาให้แล้วในตัว:
1. **เมื่อ Push โค้ดเข้าสู่ Branch `main` หรือ `master`:**
   GitHub Actions จะทำ Unit Tests และคอมไพล์เป็น `CineSonic-Studio-debug.apk` แล้วอัปโหลดไว้ในแท็บ **Actions > Artifacts** ให้ดาวน์โหลดไปทดสอบได้ทันที
2. **เมื่อสร้าง Release Tag (เช่น `git tag v1.0.0 && git push --tags`):**
   GitHub Actions จะสร้าง GitHub Release พร้อมแนบไฟล์ APK ให้โดยอัตโนมัติ
3. **การใส่คีย์ Gemini API (ไม่บังคับ):**
   ไปที่ **Settings > Secrets and variables > Actions > New repository secret** แล้วใส่:
   - Name: `GEMINI_API_KEY`
   - Value: `AIzaSy...`

---

## 🔒 ความปลอดภัย จริยธรรม และ PDPA
- ข้อมูลและโปรเจกต์ทั้งหมดถูกเก็บไว้ในเครื่องผู้ใช้ผ่าน Room Database
- ไม่อนุญาตให้ใช้ใบหน้าบุคคลจริงเพื่อทำ Deepfake
- ข้อมูลที่ส่งออก (Export) มีการแนบป้ายกำกับ **C2PA AI-Generated Provenance Metadata**

---

## 📄 ใบอนุญาต (License)
MIT License — สามารถนำไปพัฒนาต่อยอด ศึกษา หรือใช้งานส่วนตัวได้อย่างเสรี
