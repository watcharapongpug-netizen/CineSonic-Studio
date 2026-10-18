package com.example.data.ai

import com.example.data.model.*

object MockAiEngine {

    fun generateInitialProject(userPrompt: String): Pair<BriefData, List<ProposalOption>> {
        val cleanPrompt = if (userPrompt.isBlank()) "นักสืบไซบอร์กตามหาความทรงจำที่หายไปในกรุงเทพฯ ปี 2088" else userPrompt
        val concepts = listOf(
            "Concept A (Cyber-Noir): ${cleanPrompt} — บรรยากาศฝนตกนีออน มีปมความขัดแย้งของจิตสำนึกมนุษย์และ AI",
            "Concept B (Action-Thriller): การไล่ล่าข้ามเขตสลัมลอยฟ้าชิงฮาร์ดไดรฟ์ความจำก่อนถูกลบล้างใน 60 วินาที",
            "Concept C (Philosophical Drama): การพบกันระหว่างตัวเอกกับร่างโคลนในร้านอาหารเก่าย่านเยาวราชไซเบอร์"
        )
        val brief = BriefData(
            genre = "Cyberpunk Neo-Noir",
            targetAudience = "ผู้ชมที่ชื่นชอบไซไฟ ปรัชญา และดนตรีสังเคราะห์",
            tone = "ลึกลับ น่าติดตาม มีความหวังแฝงอยู่ในความมืดหม่น",
            durationSec = 60,
            primaryLanguage = "ไทยและอังกฤษ",
            budgetCap = 0.0,
            concepts = concepts
        )
        val proposals = listOf(
            ProposalOption(
                id = "prop_1",
                titleTh = "แนวคิดที่ 1: Cyber-Noir สืบสวนเชิงจิตวิทยา (แนะนำ)",
                descriptionTh = "เน้นความลึกของอารมณ์และภาพเมืองนีออนยามค่ำคืน ตัวเอกค้นพบว่าความทรงจำที่ตามหาไม่ใช่ของตนเอง",
                prosTh = "ประหยัดงบ CGI, ให้ความสำคัญกับแสงเงาและความรู้สึกตัวละครสูง",
                consTh = "จังหวะเล่าเรื่องอาจช้ากว่าแนวแอ็กชัน",
                isRecommended = true
            ),
            ProposalOption(
                id = "prop_2",
                titleTh = "แนวคิดที่ 2: High-Octane Chase ไล่ล่าระทึกขวัญ",
                descriptionTh = "เปิดฉากด้วยการหลบหนีโดรนตรวจจับบนทางด่วนลอยฟ้า จบด้วยการตัดสินใจเสี่ยงตาย",
                prosTh = "ตื่นเต้น ดึงดูดผู้ชมรวดเร็ว เหมาะกับคลิปสั้น 60 วิ",
                consTh = "ต้องใช้ช็อตเคลื่อนไหวเร็ว อาจท้าทายความต่อเนื่องของโมเดลวิดีโอ",
                isRecommended = false
            ),
            ProposalOption(
                id = "prop_3",
                titleTh = "แนวคิดที่ 3: Intimate Neon Melancholy ดราม่าสะท้อนใจ",
                descriptionTh = "บทสนทนาเข้มข้นระหว่างมนุษย์คนสุดท้ายกับหุ่นยนต์พี่เลี้ยงในห้องทดลองร้าง",
                prosTh = "บทพูดซาบซึ้ง ถ่ายทอดเสียงพากย์ได้ทรงพลัง",
                consTh = "สถานที่ฉากจำกัด อาจมีความหลากหลายทางภาพน้อยลง",
                isRecommended = false
            )
        )
        return Pair(brief, proposals)
    }

    fun getStoryBeats(idea: String): StoryBeatSheet {
        return StoryBeatSheet(
            logline = "ในกรุงเทพฯ ยุค 2088 'แก้ว' นักสืบไซบอร์กค้นพบความทรงจำปลอมที่ถูกฝังไว้ในชิปสมอง ก่อนที่จะถูกหน่วยล่ากวาดล้างลบตัวตน",
            synopsis = "แก้ว ตื่นขึ้นมาในห้องทดลองลับใต้สะพานพุทธพร้อมข้อมูลรหัสลับที่ไม่สมบูรณ์ เธอต้องเดินทางผ่านตลาดมืดเยาวราชเพื่อถอดรหัสกับ 'ดร.อรุณ' แฮกเกอร์รุ่นเก๋า แต่กลับพบว่าตัวเธอเองคือต้นแบบของ AI ที่กองทัพตามล่า เธอจึงเลือกเผยแพร่ความจริงสู่สาธารณะแทนการหลบหนี",
            act1Setup = "องก์ที่ 1 (วินาที 0-15): แก้วตื่นขึ้นท่ามกลางฝนตกและแสงนีออน หน้าจอ HUD แจ้งเตือนความจำเสียหาย 80%",
            act1Inciting = "มีข้อความปริศนาดังขึ้นทางคลื่นวิทยุสั่งให้เธอหนีออกจากตึกก่อนถูกรีเซ็ต",
            act2Midpoint = "องก์ที่ 2 (วินาที 15-40): แก้วหนีผ่านตรอกแคบ เผชิญหน้ากับหุ่นยนต์ลาดตระเวน และค้นพบชิปหลักฐาน",
            act2Crisis = "ดร.อรุณยืนยันว่าหากอัปโหลดข้อมูลนี้ ร่างกายจักรกลของเธอจะโอเวอร์โหลดและหยุดทำงาน",
            act3Climax = "องก์ที่ 3 (วินาที 40-52): แก้วเชื่อมต่อสายส่งข้อมูลเข้าเสาสัญญาณหลักท่ามกลางการล้อมของโดรน",
            act3Resolution = "วินาที 52-60: ข้อมูลถูกกระจายสู่อากาศ แสงนีออนทั่วเมืองกะพริบเป็นคำว่า 'เสรีภาพ' แก้วยิ้มก่อนสัญญาณดับลง",
            protagonistGoal = "เปิดโปงความจริงเรื่องโครงการควบคุมความทรงจำ",
            stakes = "การสูญเสียตัวตนและความเป็นมนุษย์ชั่วนิรันดร์"
        )
    }

    fun getStyleBibleAndCharacters(): Pair<StyleBible, List<CharacterProfile>> {
        val bible = StyleBible(
            visualAesthetic = "Cinematic 35mm Neo-Bangkok 2088, Anamorphic Lens Flare, Blade Runner homage",
            colorPalette = listOf("#00F0FF", "#FF007F", "#1A2238", "#FFB800"),
            lightingMood = "Wet asphalt wet reflections, volumetric cyan fog, high-contrast pink neon silhouettes",
            negativePrompt = "blurry, low poly, 3D render look, cartoon, oversaturated, deformed eyes, extra fingers",
            consistencySeed = 7891234L
        )
        val characters = listOf(
            CharacterProfile(
                id = "char_kaew",
                name = "แก้ว (Kaew)",
                role = "ตัวเอก / นักสืบไซบอร์ก",
                visualPrompt = "Thai female detective, mid 20s, sharp jawline, short sleek cybernetic hair with blue highlights, silver cyber-implant on left temple, worn dark trench coat with neon stitching",
                voiceType = "Female Calm & Resolute (Thai/English bilingual)",
                consistencyTokens = "[CHAR_KAEW: asian female 25yo, sleek hair blue tips, left temple cyber-circuit, dark trenchcoat]"
            ),
            CharacterProfile(
                id = "char_arun",
                name = "ดร. อรุณ (Dr. Arun)",
                role = "ผู้ช่วย / แฮกเกอร์ใต้ดิน",
                visualPrompt = "Elderly Thai engineer, 60s, weathered face, round glowing optical goggles, grease-stained mechanic jacket, benevolent expression",
                voiceType = "Male Elder Mentor, Raspy yet Warm",
                consistencyTokens = "[CHAR_ARUN: elderly asian male 60yo, round cyber-goggles, gray beard, patched mechanic vest]"
            )
        )
        return Pair(bible, characters)
    }

    fun getScreenplay(): List<SceneScript> {
        return listOf(
            SceneScript(
                sceneNumber = 1,
                heading = "ฉากที่ 1: ภายนอก ตรอกสะพานพุทธไซเบอร์ - กลางคืน (ฝนตก)",
                description = "แสงนีออนสีฟ้าสะท้อนบนพื้นถนนเปียกน้ำ แก้ว ยืนกุมศีรษะขณะที่อินเทอร์เฟซ HUD กะพริบสีแดงเตือนภัย",
                dialogueLines = listOf(
                    DialogueLine("HUD", "Memory fragment integrity 22%. Reboot sequence imminent.", "ความจำเหลือ 22% ระบบกำลังจะรีบูต", "Neutral System"),
                    DialogueLine("แก้ว", "ฉันต้องรู้ว่าใครเป็นคนขโมยอดีตของฉันไป...", "I have to know who stole my past...", "Determined / Whispering")
                )
            ),
            SceneScript(
                sceneNumber = 2,
                heading = "ฉากที่ 2: ภายใน ร้านซ่อมวิทยุเก่า ดร.อรุณ - กลางคืน",
                description = "จอภาพมอนิเตอร์โบราณล้อมรอบ ดร.อรุณ กำลังเสียบสายสัญญาณเข้ากับขมับของแก้ว",
                dialogueLines = listOf(
                    DialogueLine("ดร.อรุณ", "ถ้าส่งสัญญาณออกไป ร่างกายเธอจะไม่ไหวเอานะแก้ว", "Uploading this will burn out your core, Kaew.", "Concerned"),
                    DialogueLine("แก้ว", "ถ้าต้องอยู่แบบจำตัวเองไม่ได้ สู้ดับไปพร้อมความจริงดีกว่า", "Living without my truth is no living at all.", "Steely Resolve")
                )
            ),
            SceneScript(
                sceneNumber = 3,
                heading = "ฉากที่ 3: ภายนอก ดาดฟ้าเสาส่งสัญญาณ - กลางคืน",
                description = "ลมกรรโชกแรง โดรนตรวจจับส่องไฟสปอตไลต์ลงมา แก้วกดปุ่มส่งสัญญาณ ข้อมูลระเบิดเป็นคลื่นแสงทั่วฟ้า",
                dialogueLines = listOf(
                    DialogueLine("แก้ว", "ตื่นได้แล้ว... ทุกคน", "Wake up... everyone.", "Gentle Hope")
                )
            )
        )
    }

    fun getStoryboardShots(): List<ShotItem> {
        return listOf(
            ShotItem(
                shotId = "shot_01",
                sceneIndex = 1,
                shotNumber = 1,
                shotSize = "Wide Establishing",
                cameraAngle = "High Angle Tilt Down",
                cameraMotion = "Slow Crane Down",
                durationSec = 5.0f,
                visualPromptEn = "Cinematic wide establishing shot of futuristic Bangkok skyline year 2088 in heavy rain, giant holographic neon signs in Thai script, dark rain-slicked alleyway below, anamorphic 35mm lens",
                dialogue = "เสียงไซเรนระยะไกลและเสียงฝนกระทบหลังคาเหล็ก",
                keyframeHue = 195,
                consistencyScore = 96,
                takes = listOf(
                    VideoTake(1, "Take 1 (Fast motion)", "Draft", 5.0f, 102938L, 88),
                    VideoTake(2, "Take 2 (Cinematic slow rain)", "Approved", 5.0f, 102939L, 97)
                ),
                selectedTakeIndex = 1
            ),
            ShotItem(
                shotId = "shot_02",
                sceneIndex = 1,
                shotNumber = 2,
                shotSize = "Close-up",
                cameraAngle = "Low Eye Level",
                cameraMotion = "Subtle Handheld Push-in",
                durationSec = 4.5f,
                visualPromptEn = "Dramatic close-up on [CHAR_KAEW: asian female 25yo, sleek hair blue tips, left temple cyber-circuit], rain droplets running down cheek, glowing cyan HUD reflections in dark eyes, bokeh neon background",
                dialogue = "HUD: 'Memory integrity 22%' / แก้ว: 'ฉันต้องรู้...'",
                keyframeHue = 210,
                consistencyScore = 98,
                takes = listOf(
                    VideoTake(1, "Take 1 (Default)", "Approved", 4.5f, 203948L, 95)
                )
            ),
            ShotItem(
                shotId = "shot_03",
                sceneIndex = 1,
                shotNumber = 3,
                shotSize = "Medium Tracking",
                cameraAngle = "Side Profile",
                cameraMotion = "Tracking Left to Right",
                durationSec = 6.0f,
                visualPromptEn = "Side tracking shot of Kaew running through bustling cyber market, street food steam glowing in pink neon lights, bystanders looking in surprise, dynamic motion blur",
                dialogue = "เสียงฝีเท้าเร่งรีบกระทบแอ่งน้ำ",
                keyframeHue = 320,
                consistencyScore = 93,
                takes = listOf(
                    VideoTake(1, "Take 1 (Smooth Dolly)", "Approved", 6.0f, 304958L, 94)
                )
            ),
            ShotItem(
                shotId = "shot_04",
                sceneIndex = 2,
                shotNumber = 4,
                shotSize = "Medium Two-Shot",
                cameraAngle = "Eye Level",
                cameraMotion = "Static Stable",
                durationSec = 7.0f,
                visualPromptEn = "Two-shot inside retro-futuristic workshop, [CHAR_ARUN: elderly asian male, round cyber-goggles] connecting fiber cables to [CHAR_KAEW] sitting on metal workbench, warm amber workshop lights contrasting with cool rain outside",
                dialogue = "ดร.อรุณ: 'ถ้าส่งสัญญาณออกไป ร่างกายเธอจะไม่ไหวนะ'",
                keyframeHue = 45,
                consistencyScore = 95,
                takes = listOf(
                    VideoTake(1, "Take 1 (Moody Lighting)", "Approved", 7.0f, 405968L, 96)
                )
            ),
            ShotItem(
                shotId = "shot_05",
                sceneIndex = 2,
                shotNumber = 5,
                shotSize = "Extreme Close-up",
                cameraAngle = "Dutch Angle",
                cameraMotion = "Zoom In",
                durationSec = 4.0f,
                visualPromptEn = "Macro shot of cybernetic data jack plugging into glowing temple port, spark of blue electrical arc, digital binary code streaming rapidly across transparent screens",
                dialogue = "เสียงสัญญาณความถี่สูงและการเชื่อมต่อข้อมูล",
                keyframeHue = 185,
                consistencyScore = 92,
                takes = listOf(
                    VideoTake(1, "Take 1 (Macro Glow)", "Approved", 4.0f, 506978L, 91)
                )
            ),
            ShotItem(
                shotId = "shot_06",
                sceneIndex = 3,
                shotNumber = 6,
                shotSize = "Low Angle Hero Wide",
                cameraAngle = "Dramatic Worm's Eye View",
                cameraMotion = "Pan Upward",
                durationSec = 7.5f,
                visualPromptEn = "Low angle hero shot of Kaew standing on rainy rooftop edge holding wireless transmitter antenna, spotlights from circling hunter drones cutting through dark clouds, cinematic scale",
                dialogue = "เสียงใบพัดโดรนดังกึกก้องเหนือศีรษะ",
                keyframeHue = 240,
                consistencyScore = 94,
                takes = listOf(
                    VideoTake(1, "Take 1 (Dramatic Wind)", "Approved", 7.5f, 607988L, 93)
                )
            ),
            ShotItem(
                shotId = "shot_07",
                sceneIndex = 3,
                shotNumber = 7,
                shotSize = "Medium Close-up",
                cameraAngle = "Direct Frontal",
                cameraMotion = "Slow Drift In",
                durationSec = 5.0f,
                visualPromptEn = "Frontal medium close-up of Kaew smiling serenely in the rain, pressing glowing broadcast button, soft warm rim light illuminating her face as city lights behind ignite",
                dialogue = "แก้ว: 'ตื่นได้แล้ว... ทุกคน' (Wake up... everyone)",
                keyframeHue = 330,
                consistencyScore = 97,
                takes = listOf(
                    VideoTake(1, "Take 1 (Emotional Glow)", "Approved", 5.0f, 708998L, 98)
                )
            ),
            ShotItem(
                shotId = "shot_08",
                sceneIndex = 3,
                shotNumber = 8,
                shotSize = "Extreme Wide Pull-Back",
                cameraAngle = "Aerial Bird's Eye View",
                cameraMotion = "Rapid Pull Back & Ascend",
                durationSec = 6.0f,
                visualPromptEn = "Epic aerial view of entire Neo-Bangkok metropolis at night, a pulse of glowing cyan light spreading outward across skyscrapers, rain ceasing as title CineSonic Studio resolves gracefully",
                dialogue = "ดนตรีไคลแมกซ์ซินธ์เวฟผสมวงออร์เคสตราดังกระหึ่ม",
                keyframeHue = 175,
                consistencyScore = 99,
                takes = listOf(
                    VideoTake(1, "Take 1 (Master Finish)", "Approved", 6.0f, 809008L, 99)
                )
            )
        )
    }

    fun getMusicBrief(): MusicBrief {
        return MusicBrief(
            intent = "Original Film Score & Cyber Theme",
            genre = "Cyber-Synthwave with Cinematic Strings",
            mood = "Melancholic Intro &rarr; Tense Pulse &rarr; Triumphant Climax",
            bpm = 120,
            musicalKey = "D Minor",
            durationSec = 60,
            lyricsThEn = """
                [Intro - 00:00]
                (Ambient Pad & Analog Synth Arpeggio)
                ในเงามืดแห่งเมืองศิวิไลซ์ (In shadows of neon skies)
                เศษความจำที่เคยหายไป (Fragments of what was mine)
                
                [Verse - 00:15]
                (Bass Drop & 808 Electro Rhythm)
                สายฝนโปรยปรายสะท้อนแสงไฟ
                ก้าวต่อไปแม้ไร้หนทาง
                I hear your voice through the digital rain
                
                [Chorus / Climax - 00:40]
                (Full Orchestral Strings & Saw Leads)
                ความจริงจะปลดปล่อยเราทุกคน!
                Break the code, let the freedom run!
                (Final Synth Fade - 00:55)
            """.trimIndent(),
            loudnessLufs = -14.2f
        )
    }

    fun getTimelineClips(): List<TimelineClip> {
        return listOf(
            // Video Track
            TimelineClip("v_01", TrackType.VIDEO, "Shot 1: Skyline Estab.", 0, 5000),
            TimelineClip("v_02", TrackType.VIDEO, "Shot 2: Kaew Close-up", 5000, 4500),
            TimelineClip("v_03", TrackType.VIDEO, "Shot 3: Market Chase", 9500, 6000),
            TimelineClip("v_04", TrackType.VIDEO, "Shot 4: Workshop Sync", 15500, 7000),
            TimelineClip("v_05", TrackType.VIDEO, "Shot 5: Data Arc Jack", 22500, 4000),
            TimelineClip("v_06", TrackType.VIDEO, "Shot 6: Rooftop Drone", 26500, 7500),
            TimelineClip("v_07", TrackType.VIDEO, "Shot 7: Broadcast Smile", 34000, 5000),
            TimelineClip("v_08", TrackType.VIDEO, "Shot 8: City Pulse Out", 39000, 6000),
            
            // Dialogue Track
            TimelineClip("d_01", TrackType.DIALOGUE, "HUD: Memory Warning", 5200, 2000, volume = 0.95f),
            TimelineClip("d_02", TrackType.DIALOGUE, "Kaew: ฉันต้องรู้...", 7300, 2000, volume = 1.0f),
            TimelineClip("d_03", TrackType.DIALOGUE, "Dr.Arun: ร่างกายเธอจะไม่ไหว", 16000, 3200, volume = 0.95f),
            TimelineClip("d_04", TrackType.DIALOGUE, "Kaew: ถ้าต้องอยู่แบบลืมตัวตน", 19500, 2800, volume = 1.0f),
            TimelineClip("d_05", TrackType.DIALOGUE, "Kaew: ตื่นได้แล้วทุกคน", 34500, 2500, volume = 1.0f),

            // SFX Track
            TimelineClip("s_01", TrackType.SFX, "Rain & Distant Siren", 0, 15000, volume = 0.6f),
            TimelineClip("s_02", TrackType.SFX, "Puddle Splash Footsteps", 9500, 6000, volume = 0.75f),
            TimelineClip("s_03", TrackType.SFX, "Data Sparks & Cable Click", 22500, 4000, volume = 0.8f),
            TimelineClip("s_04", TrackType.SFX, "Drone Rotor Blades", 26500, 7500, volume = 0.7f),
            TimelineClip("s_05", TrackType.SFX, "EMP Pulse Blast Wave", 39000, 6000, volume = 0.85f),

            // BGM Track
            TimelineClip("b_01", TrackType.BGM, "Theme Intro (Synth Pad)", 0, 15000, volume = 0.65f),
            TimelineClip("b_02", TrackType.BGM, "Tense Beat Pulse (Ducking)", 15000, 19000, volume = 0.5f),
            TimelineClip("b_03", TrackType.BGM, "Orchestral Climax Drop", 34000, 26000, volume = 0.85f)
        )
    }

    fun getQCChecklist(): List<QCCheckItem> {
        return listOf(
            QCCheckItem(
                id = "qc_01",
                category = "ความต่อเนื่อง (Visual Continuity)",
                title = "ความคงเส้นคงวาของใบหน้าและเครื่องแต่งกายตัวละคร",
                description = "ตรวจการสอดคล้องกับ Style Bible Consistency Pack [CHAR_KAEW] ครบทั้ง 8 ช็อต",
                passed = true,
                scoreText = "96.4% Match"
            ),
            QCCheckItem(
                id = "qc_02",
                category = "กฎทางภาพยนตร์ (Cinematography)",
                title = "กฎแกนสายตา 180 องศา และความหลากหลายของขนาดภาพ",
                description = "ไม่มีการข้ามแกนในฉากสนทนา มีการสลับ Wide, Medium, Close-up เหมาะสม",
                passed = true,
                scoreText = "Pass (0 Axis Crosses)"
            ),
            QCCheckItem(
                id = "qc_03",
                category = "มาตรฐานเสียง (Audio & Loudness)",
                title = "ระดับความดังเสียงรวมและลำดับชั้น Ducking",
                description = "บทพูด > SFX > ดนตรี พร้อมควบคุมความดังเฉลี่ยที่ -14.2 LUFS (มาตรฐานสากล)",
                passed = true,
                scoreText = "-14.2 LUFS (Target -14 to -16)"
            ),
            QCCheckItem(
                id = "qc_04",
                category = "ความถูกต้องภาษา (Thai Subtitle Sync)",
                title = "ความตรงเวลาของคำบรรยายและการตัดคำภาษาไทย",
                description = "ทดสอบการตัดคำไทย ไม่ตัดคำกลางพยางค์ และสระ/วรรณยุกต์ไม่ซ้อนทับ",
                passed = true,
                scoreText = "100% Synced (Zero Drift)"
            ),
            QCCheckItem(
                id = "qc_05",
                category = "ความปลอดภัยและลิขสิทธิ์ (Ethics & Policy)",
                title = "การตรวจสอบการละเมิดลิขสิทธิ์และ Deepfake บุคคลจริง",
                description = "ไม่พบการอ้างอิงชื่อดารา/บุคคลจริง และแนบ C2PA AI-Provenance Metadata ครบถ้วน",
                passed = true,
                scoreText = "Clean & Verified"
            )
        )
    }

    fun getStepAssistantContract(filmStep: FilmStep): StepAssistantResponse {
        return when (filmStep) {
            FilmStep.F1_BRIEF -> StepAssistantResponse(
                stepCode = "F1",
                assistantName = "AI Producer / Creative Brief Specialist",
                explain = AssistantExplain(
                    summaryTh = "ขั้นนี้เป็นการเปลี่ยนไอเดียประโยคเดียวของคุณให้เป็น 'พิมพ์เขียวโปรเจกต์' ที่มีทั้งแนวเรื่อง กลุ่มผู้ชม และเป้าหมายที่ชัดเจน",
                    exampleTh = "เช่น จากคำว่า 'นักสืบหาความทรงจำ' จะถูกขยายเป็นไซไฟสืบสวน 60 วินาที ในธีมนีออนบางกอก"
                ),
                interview = listOf(
                    InterviewQuestion(
                        id = "q1",
                        questionTh = "คุณอยากให้ภาพยนตร์สั้นเรื่องนี้เน้นอารมณ์แบบใดมากที่สุด?",
                        optionsTh = listOf("ลึกลับกดดัน (Cyber-Noir)", "ตื่นเต้นระทึกขวัญ (Action)", "สะเทือนอารมณ์และปรัชญา (Drama)")
                    ),
                    InterviewQuestion(
                        id = "q2",
                        questionTh = "ความยาวและจังหวะของเรื่องที่ต้องการ?",
                        optionsTh = listOf("60 วินาที กระชับทรงพลัง", "90 วินาที เล่าเรื่องเข้มข้น", "120 วินาที รายละเอียดครบถ้วน")
                    )
                ),
                proposals = listOf(
                    ProposalOption(
                        id = "p1",
                        titleTh = "Cyber-Noir สืบสวนความทรงจำ (แนะนำ)",
                        descriptionTh = "บรรยากาศฝนพรำ นีออนฟ้า-ชมพู เน้นความขัดแย้งภายในใจและการค้นพบความจริงอันน่าตกใจ",
                        prosTh = "ประหยัดงบ CGI, ให้มิติทางอารมณ์สูงมาก",
                        consTh = "จังหวะไม่หวือหวาเท่าหนังแอ็กชันเพียวๆ",
                        isRecommended = true
                    ),
                    ProposalOption(
                        id = "p2",
                        titleTh = "High-Voltage Chase การหลบหนี",
                        descriptionTh = "โดรนไล่ล่าข้ามตึกสูงระฟ้า ลุ้นระทึกทุกวินาที",
                        prosTh = "เร้าใจ เหมาะกับการดึงดูดสายตาบนโซเชียลมีเดีย",
                        consTh = "อาจต้องใช้เวลาปรับแต่งความต่อเนื่องของภาพเคลื่อนไหวสูง",
                        isRecommended = false
                    ),
                    ProposalOption(
                        id = "p3",
                        titleTh = "Memory Antique ร้านความทรงจำโบราณ",
                        descriptionTh = "เน้นบทสนทนาเปี่ยมความหมายในห้องทดลองลับใต้ดิน",
                        prosTh = "คุมความต่อเนื่องของฉากได้ง่ายและแม่นยำที่สุด",
                        consTh = "ฉากหลังไม่หลากหลายเท่าสองแนวคิดแรก",
                        isRecommended = false
                    )
                ),
                estimate = ResourceEstimate(
                    estimatedTimeSec = 2,
                    estimatedCostUsd = 0.0,
                    providerName = "Mock Engine + Gemini 3.5 Flash",
                    noteTh = "รันฟรี 100% ในเครื่อง ไม่มีค่าบริการเครดิต"
                ),
                critique = StepCritique(
                    score = 5,
                    strengthsTh = "บรีฟมีความชัดเจนทั้งกลุ่มเป้าหมาย โทนสี และภาษาที่สอดคล้องกับไซเบอร์พังก์",
                    weaknessesTh = "ควรเตรียมคำอธิบายตัวละครให้โดดเด่นเพื่อป้องกันภาพตัวละครเพี้ยนในขั้นตอน F3",
                    suggestedFixTh = "ใช้ Character Consistency Pack ที่ล็อกคีย์เวิร์ดหน้าตาและเครื่องแต่งกายถาวร"
                ),
                quickRefinements = listOf("เพิ่มความลึกลับ", "เน้นจังหวะเร้าใจ", "ปรับโทนให้สว่างขึ้น", "เพิ่มกลิ่นอายไทยดั้งเดิม"),
                conflictCheck = ConflictCheckResult(
                    hasConflict = false,
                    detailsTh = "ไม่พบความขัดแย้ง: สอดคล้องกับเพดานงบประมาณ $0.00 และเวลา 60 วินาที"
                ),
                handoffSummary = "ล็อกบรีฟ: แนว Cyber-Noir, เวลา 60 วิ, ธีมนีออนบางกอก 2088 พร้อมส่งต่อให้ผู้ช่วย F2 วางโครงเรื่อง 3 องก์"
            )

            FilmStep.F2_STORY -> StepAssistantResponse(
                stepCode = "F2",
                assistantName = "Story Architect & Narrative Consultant",
                explain = AssistantExplain(
                    summaryTh = "สร้างโครงเรื่องและจังหวะเล่าเรื่อง 3 องก์ (จุดเริ่ม ปมวิกฤต และจุดไคลแมกซ์) เพื่อให้หนังมีความหมายและตรึงใจคนดู",
                    exampleTh = "แบ่งเป็น: 0-15s ปูเรื่องและปัญหา, 15-40s เผชิญอุปสรรค, 40-60s ไคลแมกซ์และบทสรุป"
                ),
                proposals = listOf(
                    ProposalOption("s1", "โครงเรื่องแบบ Hero's Sacrifice (แนะนำ)", "ตัวเอกสละระบบตนเองเพื่อกระจายความจริงสู่เมือง", "ทรงพลัง อารมณ์ซาบซึ้งตราตรึง", "จบแบบ Bittersweet", true),
                    ProposalOption("s2", "โครงเรื่องแบบ Twist Revelation", "ค้นพบว่าคนที่ตามล่าคือความทรงจำในอดีตของตนเอง", "หักมุม ฉลาด น่าทึ่ง", "ต้องจัดวางคำใบ้ให้พอดีในเวลาสั้น", false),
                    ProposalOption("s3", "โครงเรื่องแบบ Open Ending", "หนีรอดไปได้พร้อมคำถามที่ทิ้งไว้ให้คนดูขบคิดต่อ", "กระตุ้นการสนทนาในหมู่ผู้ชม", "อาจทำให้บางคนรู้สึกไม่จบ", false)
                ),
                estimate = ResourceEstimate(2, 0.0, "Mock Narrative Engine", "ฟรี ไม่คิดค่าบริการ"),
                critique = StepCritique(5, "โครงเรื่อง 3 องก์ มีความกระชับและมี Hit-point ตรงกับช่วงเวลา 60 วินาที", "ช่วงองก์ 2 อาจมีบทพูดยาวเกินไปเล็กน้อย", "ตัดทอนคำพูดให้เหลือเฉพาะประโยคสำคัญเพื่อเปิดทางให้ดนตรีทำงาน"),
                quickRefinements = listOf("หักมุมให้เข้มข้นขึ้น", "ลดบทพูดลง", "เพิ่มเดิมพันเดิมชีวิต", "ปรับตอนจบให้ฟีลกู๊ด"),
                conflictCheck = ConflictCheckResult(false, "สอดคล้องกับบรีฟ F1 สมบูรณ์"),
                handoffSummary = "โครงสร้าง 3 องก์และ Logline ถูกบันทึกลงโปรเจกต์ ส่งต่อให้ฝ่ายคอสตูมและวิชวล F3"
            )

            FilmStep.F3_BIBLE -> StepAssistantResponse(
                stepCode = "F3",
                assistantName = "Style Bible & Character Designer",
                explain = AssistantExplain(
                    summaryTh = "สร้าง Character Sheet และ Style Bible เพื่อล็อกหน้าตา ตัวละคร เครื่องแต่งกาย และโทนแสงสีให้ตรงกันทุกช็อต",
                    exampleTh = "ล็อกโค้ด [CHAR_KAEW: asian female 25yo, sleek hair blue tips, dark trenchcoat] ให้ติดไปในทุกพรอมต์"
                ),
                proposals = listOf(
                    ProposalOption("b1", "Neon Rain Cyber-Bangkok Style (แนะนำ)", "คู่สี #00F0FF และ #FF007F เงาสะท้อนน้ำฝน แสงฟุ้งสไตล์ Anamorphic", "ภาพมีความลึก ดูเป็นภาพยนตร์ระดับสูง", "ต้องการการคุมคอนทราสต์ที่ดี", true),
                    ProposalOption("b2", "Golden Amber Dystopian", "คู่สีทองอำพัน ผสมฝุ่นควันและแสงนีออนส้ม", "ดิบ ดุดัน สไตล์ Blade Runner 2049", "ความสดใสของแสงนีออนน้อยลง", false),
                    ProposalOption("b3", "Monochrome Noir with Single Color Accent", "ภาพขาวดำ มีเฉพาะแสงนีออนสีฟ้าที่สว่าง", "แนวอาร์ต โดดเด่นไม่ซ้ำใคร", "อาจไม่ตรงกับตลาดแมส", false)
                ),
                estimate = ResourceEstimate(2, 0.0, "Style Consistency Engine", "ฟรีในโหมด Mock"),
                critique = StepCritique(5, "Character Consistency Pack มีคีย์เวิร์ดที่ชัดเจนและมี Negative Prompt ป้องกันข้อผิดพลาด", "ควรระบุชนิดเลนส์กล้องให้ชัดขึ้น", "เพิ่ม 'anamorphic 35mm lens flare' ในพรอมต์หลัก"),
                quickRefinements = listOf("เพิ่มความคมชัด", "เน้นแสงสะท้อนน้ำฝน", "ปรับทรงผมตัวละคร", "เพิ่มสีทองนีออน"),
                conflictCheck = ConflictCheckResult(false, "ตัวละครและสไตล์เข้ากับเรื่องย่อ F2 100%"),
                handoffSummary = "บันทึก Style Bible Seed #7891234 และ Character Pack ส่งต่อไปยังขั้นตอนการเขียนบท F4"
            )

            FilmStep.F4_SCRIPT -> StepAssistantResponse(
                stepCode = "F4",
                assistantName = "Screenwriter & Dialogue Polisher",
                explain = AssistantExplain(
                    summaryTh = "เขียนบทภาพยนตร์ฉากต่อฉาก พร้อมตรวจสอบความยาวบทพูดให้อ่านทันตามไทม์โค้ดและออกเสียงไทยได้อย่างเป็นธรรมชาติ",
                    exampleTh = "คำนวณอัตราเร็วการพูดเฉลี่ย 3-4 คำต่อวินาที เพื่อให้ซับไตเติลอ่านง่ายสบายตา"
                ),
                proposals = listOf(
                    ProposalOption("sc1", "บทภาษาไทยกระชับ ซับไตเติลอังกฤษคู่ขนาน (แนะนำ)", "บทพูดคมคาย ไพเราะ ไม่เยิ่นเย้อ สื่อสารอารมณ์ได้ทันที", "ฟังลื่นหู เป็นธรรมชาติในภาษาไทย", "ต้องซิงก์เสียงให้ตรงจุดตัดภาพ", true),
                    ProposalOption("sc2", "บทสไตล์ Cinematic Voice-Over", "เน้นเสียงบรรยายในใจของตัวเอกตลอดทั้งเรื่อง", "คุมจังหวะเล่าเรื่องง่าย", "ปฏิสัมพันธ์ระหว่างตัวละครน้อยลง", false),
                    ProposalOption("sc3", "บทสนทนาภาษาอังกฤษล้วน (พากย์สากล)", "เหมาะสำหรับเผยแพร่ในเวทีนานาชาติ", "เข้าถึงผู้ชมทั่วโลกได้กว้าง", "อาจสูญเสียเสน่ห์สำเนียงท้องถิ่น", false)
                ),
                estimate = ResourceEstimate(1, 0.0, "Screenplay Parser", "ตรวจไวยากรณ์และความยาวฟรี"),
                critique = StepCritique(4, "บทพูดมีความคมชัด แสดงจุดยืนตัวละครได้ดี", "ฉากที่ 2 มีการพูดซ้อนกับช่วงตัดต่อภาพเล็กน้อย", "เว้นช่องว่าง 0.5 วินาทีก่อนพูดประโยคสุดท้าย"),
                quickRefinements = listOf("ตัดบทพูดให้สั้นลง", "เพิ่มความดุดัน", "ปรับให้สละสลวยขึ้น", "เปลี่ยนเป็นเสียงกระซิบ"),
                conflictCheck = ConflictCheckResult(false, "ความยาวบทรวม 28 วินาที อยู่ในสัดส่วนที่พอดีกับหนัง 60 วินาที"),
                handoffSummary = "บทภาพยนตร์ 3 ฉากพร้อมบรรยายท่าทางพร้อมส่งต่อให้ F5 จัดทำ Shot List และมุมกล้อง"
            )

            FilmStep.F5_STORYBOARD -> StepAssistantResponse(
                stepCode = "F5",
                assistantName = "Cinematographer & Shot List Director",
                explain = AssistantExplain(
                    summaryTh = "แตกฉากออกเป็น 8 ช็อตสั้น ระบุขนาดภาพ (Wide/Medium/Close-up) มุมกล้อง และการเคลื่อนไหว พร้อมเช็กกฎ 180 องศา",
                    exampleTh = "เริ่มจาก Wide เปิดเมือง &rarr; Close-up ตัวละคร &rarr; Tracking วิ่ง &rarr; Low-angle ไคลแมกซ์"
                ),
                proposals = listOf(
                    ProposalOption("sh1", "Dynamic 8-Shot Sequence (แนะนำ)", "สลับจังหวะช้า-เร็วอย่างสมดุล รักษากฎแกนสายตา 180 องศาแม่นยำ", "ได้ภาพหลากหลาย เล่าเรื่องไม่น่าเบื่อ", "ต้องตรวจ keyframe ทุกช็อต", true),
                    ProposalOption("sh2", "Long Take Simulation 4-Shot", "เน้นช็อตยาว ลื่นไหล ต่อเนื่องดั่งกล้องเดินตามตัวเอก", "มีความสมจริง ดื่มด่ำ", "ความเสี่ยงเรื่องโมเดลวิดีโอสร้างภาพบิดเบือนสูงขึ้น", false),
                    ProposalOption("sh3", "Rapid Cut MTV Style 12-Shot", "ตัดช็อตละ 2-3 วินาที เน้นความเร้าใจ", "พลังงานสูงมาก เหมาะกับมิวสิควิดีโอ", "ผู้ชมอาจซึมซับอารมณ์ดราม่าไม่ทัน", false)
                ),
                estimate = ResourceEstimate(2, 0.0, "Shot Planner", "คำนวณความต่อเนื่องฟรี"),
                critique = StepCritique(5, "มีการผสมผสานมุมมองที่หลากหลายและปลอดภัยจากแกนสายตากลับทิศ", "ช็อตที่ 6 ต้องการลมและควันเพิ่มเติม", "ระบุหมอกและสปอตไลต์โดรนใน visual prompt"),
                quickRefinements = listOf("เปลี่ยนเป็นมุมมองบุคคลที่หนึ่ง", "เพิ่มการเคลื่อนกล้องแบบสโลว์โมชั่น", "ใช้มุมกล้องเอียง (Dutch Angle)", "ตัดต่อให้กระชับขึ้น"),
                conflictCheck = ConflictCheckResult(false, "ระยะเวลารวม 8 ช็อต = 45.5 วินาที เหลือเวลาให้หัว-ท้าย 14.5 วินาที รวม 60 วินาทีพอดี"),
                handoffSummary = "Shot List 8 ช็อตพร้อมข้อมูลทางภาพยนตร์ส่งต่อไปยังฝ่าย Keyframe Rendering F6"
            )

            FilmStep.F6_KEYFRAMES -> StepAssistantResponse(
                stepCode = "F6",
                assistantName = "Keyframe & Concept Art Supervisor",
                explain = AssistantExplain(
                    summaryTh = "สร้างภาพนิ่งต้นเฟรม (Keyframe) สำหรับทุกช็อต และตรวจคะแนนความตรงกับ Style Bible และ Character Sheet",
                    exampleTh = "ประเมินความแม่นยำของใบหน้าแก้วและคู่สีนีออน ได้คะแนนเฉลี่ย 96% พร้อมชี้จุดที่ควร inpaint"
                ),
                proposals = listOf(
                    ProposalOption("kf1", "Procedural Master Keyframes (แนะนำ)", "สร้างภาพ 8 ช็อตผ่านเอนจินจำลองภาพยนตร์ความละเอียดสูง", "ควบคุมแสงเงาและใบหน้าตรงตามสไตล์เป๊ะ", "มีระบบ inpaint แนะนำจุดแก้ไข", true),
                    ProposalOption("kf2", "High Contrast Grain Look", "เพิ่มเกรนฟิล์ม 35mm ให้ดูสมจริงแบบภาพยนตร์คลาสสิก", "มีเสน่ห์ทางศิลปะสูง", "อาจมีสัญญาณรบกวนในบริเวณเงามืด", false),
                    ProposalOption("kf3", "Vivid Neon Pop Art", "เร่งสีคู่ตรงข้ามให้จัดจ้านขึ้น 20%", "สะดุดตา โดดเด่นบนจอมือถือ", "โทนสีอาจหลุดจากความเป็นหนังหม่นเล็กน้อย", false)
                ),
                estimate = ResourceEstimate(3, 0.0, "Mock Image Engine", "สร้างภาพจำลองฟรีในเสี้ยววินาที"),
                critique = StepCritique(5, "ความคงเส้นคงวาของใบหน้าและชุดแจ็กเก็ตของแก้วอยู่ในเกณฑ์ดีเยี่ยม (95%+)", "ช็อตที่ 4 แสงไฟเวิร์กช็อปอาจอุ่นเกินไปเล็กน้อย", "ปรับอุณหภูมิสีให้เจือโทนน้ำเงินในบริเวณขอบมืด"),
                quickRefinements = listOf("เพิ่มความสว่าง", "ลดแสงสะท้อน", "เปลี่ยนสีนีออนเป็นชมพู", "ซ่อมแซมรายละเอียดใบหน้า"),
                conflictCheck = ConflictCheckResult(false, "ภาพทุกช็อตแมตช์กับอัตราส่วนภาพ 16:9 และโค้ดสี Style Bible"),
                handoffSummary = "คีย์เฟรมผ่านการตรวจสอบครบ 8 ช็อต ส่งต่อไปยังการเรนเดอร์คลิปวิดีโอ F7"
            )

            FilmStep.F7_VIDEO -> StepAssistantResponse(
                stepCode = "F7",
                assistantName = "Video Takes & Motion Animator",
                explain = AssistantExplain(
                    summaryTh = "เปลี่ยนภาพนิ่งให้เป็นคลิปวิดีโอเคลื่อนไหว (Image-to-Video) ควบคุมการเคลื่อนไหวของกล้องและเก็บหลาย Takes เพื่อเลือกอันที่ดีที่สุด",
                    exampleTh = "ทดสอบทำ Take 1 (Draft) ก่อน เมื่อพอใจทิศทางกล้องจึงอนุมัติเป็น Approved Take"
                ),
                proposals = listOf(
                    ProposalOption("v1", "Multi-take Cinematic Flow (แนะนำ)", "จำลองการเคลื่อนไหวของสายฝน แสงนีออนไหว และการเดินกล้องดอลลี่", "การเคลื่อนไหวนุ่มนวล ไม่วูบวาบ", "ใช้เวลาประมวลผลมากกว่าแบบนิ่ง", true),
                    ProposalOption("v2", "Dynamic Speed Ramp", "เร่งความเร็วช่วงกลางช็อตและสโลว์ช่วงต้น-ท้าย", "ดูทันสมัยสไตล์เทรลเลอร์ฮอลลีวูด", "อาจไม่เหมาะกับช็อตดราม่าอารมณ์ซึ้ง", false),
                    ProposalOption("v3", "Subtle Atmospheric Movement", "เน้นเฉพาะควัน ไฟกะพริบ และหยดน้ำฝนไหลผ่านเลนส์", "ปลอดภัย ภาพไม่เบี้ยวหลุดฟอร์มแน่นอน", "ความรู้สึกหวือหวาน้อยลง", false)
                ),
                estimate = ResourceEstimate(3, 0.0, "Mock Video Engine", "จำลองการเรนเดอร์ฟรีในเครื่อง"),
                critique = StepCritique(5, "ไม่มีอาการภาพย้วย (Morphing artifact) และการเคลื่อนกล้องสอดคล้องกับ Storyboard", "ช็อตวิ่งช็อต 3 ควรเพิ่มความเร็วการก้าวขา", "ปรับพารามิเตอร์ motion scale เป็น 1.15"),
                quickRefinements = listOf("เพิ่มสโลว์โมชั่น", "ลดการสั่นของกล้อง", "เร่งความเร็วฝนตก", "เปลี่ยนเทคใหม่"),
                conflictCheck = ConflictCheckResult(false, "ความยาวคลิปทุกเทกตรงกับ Shot List เป๊ะ"),
                handoffSummary = "คลิปวิดีโอ Approved Takes ทั้ง 8 ช็อตพร้อมประกอบเข้ากับเสียงพูดและซับไตเติลใน F8"
            )

            FilmStep.F8_VOICE -> StepAssistantResponse(
                stepCode = "F8",
                assistantName = "Voice Director & Subtitle Architect",
                explain = AssistantExplain(
                    summaryTh = "สร้างเสียงพากย์ด้วย AI TTS ที่กำกับอารมณ์รายประโยค ตรวจสอบวรรณยุกต์ไทย และสร้างซับไตเติลตรงไทม์โค้ด",
                    exampleTh = "ประโยคของแก้ว 'ฉันต้องรู้...' ใช้เสียงกระซิบสะกดอารมณ์ และสร้างคำบรรยายไทย/อังกฤษพร้อมกัน"
                ),
                proposals = listOf(
                    ProposalOption("vo1", "เสียงพากย์ไทยอารมณ์สมจริง + ซับ 2 ภาษา (แนะนำ)", "ออกเสียง ร/ล ชัดเจน วรรณยุกต์ไม่เพี้ยน อารมณ์เข้ากับฉาก", "ฟังดูเป็นมืออาชีพ เข้าถึงผู้ชมทั้งไทยและต่างชาติ", "ต้องตรวจช่วงเวลาไม่ให้ซับบังหน้าตัวละคร", true),
                    ProposalOption("vo2", "เสียงพากย์อังกฤษสำเนียง Cyber-Cinematic", "เสียงพากย์ภาษาอังกฤษพร้อมคำบรรยายภาษาไทย", "ให้ฟีลลิ่งภาพยนตร์ไซไฟฮอลลีวูด", "คนดูไทยต้องอ่านซับตลอดเวลา", false),
                    ProposalOption("vo3", "Minimalist Voice (บทพูดน้อย เน้นบรรยากาศ)", "ตัดบทพูดเหลือเพียง 2 ประโยคสำคัญที่สุด", "เปิดพื้นที่ให้ดนตรีและภาพสื่อสารอย่างเต็มที่", "เรื่องราวอาจต้องตีความเพิ่มขึ้น", false)
                ),
                estimate = ResourceEstimate(2, 0.0, "Mock TTS & Subtitle Engine", "ประมวลผลฟรีในเครื่อง"),
                critique = StepCritique(5, "การออกเสียงไทยถูกต้องตามพจนานุกรม และเวลาซับไตเติลตรงกับรูปปากในระดับเฟรม", "ช่วงท้ายของเสียง HUD ควรเพิ่มเอฟเฟกต์วิทยุสื่อสาร", "ใส่ตัวกรองเสียง Bandpass และ Radio noise เล็กน้อย"),
                quickRefinements = listOf("เพิ่มความดุดันในน้ำเสียง", "ปรับให้เสียงนุ่มนวลขึ้น", "ขยับซับไตเติลลงด้านล่าง", "เปลี่ยนฟอนต์ซับ"),
                conflictCheck = ConflictCheckResult(false, "เวลาของเสียงไม่ล้นเกินความยาวช็อตวิดีโอ"),
                handoffSummary = "ไฟล์เสียงพากย์และแทร็กคำบรรยาย SRT/VTT ถูกบรรจุเข้าสู่โครงการ ส่งต่อให้แผนกดนตรี F9"
            )

            FilmStep.F9_SCORE -> StepAssistantResponse(
                stepCode = "F9",
                assistantName = "Film Composer & Music Pipeline Lead",
                explain = AssistantExplain(
                    summaryTh = "ประพันธ์ดนตรีประกอบภาพยนตร์ (Score) โดยใช้ Music Pipeline เชื่อมโยงจังหวะและอารมณ์ตามแต่ละฉากอย่างกลมกลืน",
                    exampleTh = "เริ่มจาก Synth Arpeggio หม่นในฉากตรอก &rarr; จังหวะกลองเร้าใจในฉากหนี &rarr; ออร์เคสตราอลังการฉากจบ"
                ),
                proposals = listOf(
                    ProposalOption("sc1", "Cyber-Orchestral Hybrid Score (แนะนำ)", "ผสมผสานซินธิไซเซอร์วินเทจกับวงสายสตริง ดราม่าและยิ่งใหญ่", "ให้พลังและมิติทางอารมณ์สูงมาก", "ต้องมิกซ์อย่างระวังไม่ให้บดบังเสียงพูด", true),
                    ProposalOption("sc2", "Pure Retro Synthwave 80s", "กลองอิเล็กทรอนิกส์หนักแน่น เบสไลน์กระแทกกระทั้น", "เท่ มีสไตล์เฉพาะตัวสูงมาก", "อาจให้ความรู้สึกย้อนยุคมากกว่าล้ำอนาคต", false),
                    ProposalOption("sc3", "Dark Ambient Soundscape", "เน้นเสียงโดรนความถี่ต่ำและบรรยากาศหลอนลึก ไม่เน้นทำนอง", "สร้างความอึดอัดและลึกลับถึงขีดสุด", "ขาดความตื่นเต้นในฉากไคลแมกซ์", false)
                ),
                estimate = ResourceEstimate(3, 0.0, "Mock Score Synthesizer", "จำลองการสร้างดนตรีฟรี"),
                critique = StepCritique(5, "คีย์ D Minor เข้ากับความรู้สึกสูญเสียและค้นหาความจริงได้อย่างสมบูรณ์แบบ", "ช่วงวิกฤตินาทีที่ 25 ควรเพิ่มเสียงเบสดรอป", "มาร์กเกอร์ Hit-point ตรงกับช็อตสปาร์กสายเคเบิล"),
                quickRefinements = listOf("เพิ่มความเร็ว BPM", "ลดเสียงกลองลง", "เพิ่มเสียงเปียโนเศร้า", "เร่งออร์เคสตราให้ดังขึ้น"),
                conflictCheck = ConflictCheckResult(false, "ความยาวเพลง 60 วินาที ซิงก์กับคัตติ้งของวิดีโอสมบูรณ์"),
                handoffSummary = "Score Track พร้อม Cue Sheet ถูกบันทึกแล้ว ส่งต่อให้แผนกซาวด์เอฟเฟกต์ F10"
            )

            FilmStep.F10_SFX -> StepAssistantResponse(
                stepCode = "F10",
                assistantName = "Sound Designer & Audio Mixer",
                explain = AssistantExplain(
                    summaryTh = "ออกแบบเสียงแวดล้อม (Ambience) และเสียงเอฟเฟกต์ (SFX) พร้อมตั้งระบบ Audio Ducking ให้เสียงพูดชัดเจนเสมอ",
                    exampleTh = "เมื่อแก้วพูด เสียงดนตรีและเสียงฝนจะลดความดังลงอัตโนมัติ 4dB เพื่อให้ฟังชัดเจน"
                ),
                proposals = listOf(
                    ProposalOption("sfx1", "Immersive Cyber-Atmosphere (แนะนำ)", "เสียงฝนกระทบโลหะ เสียงโดรน และคลื่นแม่เหล็กไฟฟ้า EMP", "สร้างโลกที่จับต้องได้และมีชีวิตชีวา", "ต้องปรับสมดุลย่านเสียงกลางอย่างพิถีพิถัน", true),
                    ProposalOption("sfx2", "Stylized Punchy Comic Sound", "เสียงเอฟเฟกต์เน้นความกระแทกกระทั้น ดุดัน ชัดเจนเกินจริง", "ตื่นเต้น สดใหม่ สะใจ", "อาจลดทอนความสมจริงของบรรยากาศลง", false),
                    ProposalOption("sfx3", "Minimalist Clean Mix", "ใส่เฉพาะเสียงที่จำเป็นจริงๆ เพื่อเน้นความเงียบงัน", "บทพูดและดนตรีเด่นชัดที่สุด", "โลกในหนังอาจดูแห้งแล้งไปบ้าง", false)
                ),
                estimate = ResourceEstimate(2, 0.0, "Mock SFX & Ducking Mixer", "ประมวลผลฟรีในเครื่อง"),
                critique = StepCritique(5, "ลำดับชั้นความดัง บทพูด > SFX > ดนตรี เป็นไปตามมาตรฐานอุตสาหกรรม", "เสียงฝนตอนต้นอาจแย่งความสนใจไปเล็กน้อย", "ตั้ง Ducking ล่วงหน้า 200ms ก่อนเริ่มประโยคพูด"),
                quickRefinements = listOf("เพิ่มเสียงฟ้าร้อง", "ลดเสียงแวดล้อม", "เพิ่มเสียงก้าวเท้า", "ปรับเสียงดนตรีให้เบาลง"),
                conflictCheck = ConflictCheckResult(false, "ไม่มีการเกิดเสียงแตกหรือ Clipping (Peak อยู่ที่ -1.5 dBFS)"),
                handoffSummary = "แทร็กเสียงสมบูรณ์ 4 แทร็กพร้อมส่งเข้าสู่ไทม์ไลน์ F11 สำหรับการตัดต่อขั้นสุดท้าย"
            )

            FilmStep.F11_EDITING -> StepAssistantResponse(
                stepCode = "F11",
                assistantName = "Lead Editor & Colorist",
                explain = AssistantExplain(
                    summaryTh = "ตัดต่อภาพบนไทม์ไลน์แบบ Multi-track ใส่การเปลี่ยนภาพ (Transitions) และย้อมสีภาพด้วย Color LUT แบบภาพยนตร์",
                    exampleTh = "ใช้คัตธรรมดาช่วงแอ็กชัน และใช้ Cross-dissolve สั้นๆ ในช่วงการระเบิดของสัญญาณแสง"
                ),
                proposals = listOf(
                    ProposalOption("ed1", "Cyber-Neon Teal & Orange LUT (แนะนำ)", "ย้อมสีน้ำเงินอมเขียวในเงามืด และเร่งสีผิวโทนอุ่นสะท้อนแสงนีออน", "คลาสสิก ฮอลลีวูด ให้ความรู้สึกพรีเมียม", "ต้องควบคุมระดับสีดำไม่ให้จม", true),
                    ProposalOption("ed2", "Pure Cyan-Magenta Cyberpunk", "เน้นสีฟ้าสว่างและชมพูจัดจ้านตามสไตล์ Synthwave", "เอกลักษณ์โดดเด่น แปลกตา", "สีผิวอาจดูหลุดธรรมชาติไปบ้าง", false),
                    ProposalOption("ed3", "Raw Desaturated Realistic Look", "ลดความสดของสีลง 30% ให้ดูสมจริง ดิบ หม่นหมอง", "ให้อารมณ์จริงจัง สะเทือนใจ", "ความว้าวทางสายตาลดลง", false)
                ),
                estimate = ResourceEstimate(2, 0.0, "Timeline Compositor Engine", "เรนเดอร์พรีวิว 60fps ฟรี"),
                critique = StepCritique(5, "จังหวะการตัดต่อภาพตรงกับ Hit-point ของดนตรีอย่างสมบูรณ์แบบ", "รอยต่อช็อต 7 ไป 8 สามารถยาวขึ้นได้อีก 0.5 วินาที", "ยืดการเฟดภาพตอนจบเพื่อให้ตัวอักษร CineSonic อ่านง่ายขึ้น"),
                quickRefinements = listOf("เร่งสีนีออน", "เพิ่มฟิลเตอร์เกรนฟิล์ม", "ปรับจังหวะตัดต่อให้เร็วขึ้น", "เปลี่ยน Transition"),
                conflictCheck = ConflictCheckResult(false, "ทุกคลิปบนไทม์ไลน์อยู่ในกรอบเวลา 60.0 วินาทีพอดี"),
                handoffSummary = "มาสเตอร์ไทม์ไลน์พร้อมส่งตรวจคุณภาพใน F12 ก่อนการเรนเดอร์และส่งออก"
            )

            FilmStep.F12_QC_EXPORT -> StepAssistantResponse(
                stepCode = "F12",
                assistantName = "Quality Assurance & Delivery Specialist",
                explain = AssistantExplain(
                    summaryTh = "ตรวจสอบคุณภาพแบบอัตโนมัติ (QC) ครอบคลุมความต่อเนื่อง ภาพ ความดังเสียง ลิขสิทธิ์ และส่งออกผลงานพร้อมแพ็กเกจโปรเจกต์",
                    exampleTh = "ตรวจผ่านเกณฑ์ 5/5 ข้อ ระดับเสียง -14.2 LUFS ซับตรงเวลา และแนบป้าย AI Generated เรียบร้อย"
                ),
                proposals = listOf(
                    ProposalOption("ex1", "Full Master Package (แนะนำ)", "วิดีโอ 1080p, แทร็กเสียงแยก Stems, ซับไตเติล SRT/VTT, และไฟล์โปรเจกต์", "ครบถ้วนสมบูรณ์ นำไปใช้งานได้ทุกแพลตฟอร์ม", "ขนาดไฟล์รวมใหญ่กว่าแบบคลิปเดี่ยว", true),
                    ProposalOption("ex2", "Quick Social Media Cut (MP4 + Burned Sub)", "วิดีโอฝังซับไตเติลในตัว พร้อมแชร์ลง TikTok, Reels, YouTube Shorts", "สะดวก รวดเร็ว พร้อมโพสต์ทันที", "ไม่สามารถแก้ไขซับไตเติลในภายหลังได้", false),
                    ProposalOption("ex3", "Audio Soundtrack Album Edition", "ส่งออกเฉพาะไฟล์เสียงดนตรีและซาวด์แทร็กคุณภาพสูง WAV 24-bit", "เหมาะสำหรับผู้ที่ต้องการนำเพลงไปฟังแยก", "ไม่มีไฟล์วิดีโอแนบไปด้วย", false)
                ),
                estimate = ResourceEstimate(4, 0.0, "Master Export Pipeline", "ส่งออกฟรีในเครื่อง 100%"),
                critique = StepCritique(5, "โปรเจกต์ผ่านเกณฑ์คุณภาพทุกมิติ (Quality Score 98/100) ปลอดภัยต่อลิขสิทธิ์และพร้อมเผยแพร่", "ไม่มีข้อผิดพลาดสำคัญ", "แนะนำให้แชร์เป็น Full Master Package เพื่อเก็บสำรองข้อมูล"),
                quickRefinements = listOf("ส่งออกเป็น MP4", "ส่งออกเฉพาะเสียงเพลง", "ดาวน์โหลดซับไตเติล SRT", "บันทึกข้อมูลสำรอง"),
                conflictCheck = ConflictCheckResult(false, "ไม่มีข้อขัดแย้ง: ต้นทุนรวมคงเหลือ $0.00 อยู่ในเพดานงบประมาณ"),
                handoffSummary = "ภาพยนตร์สั้น 60 วินาทีและเพลงประกอบ CineSonic Studio พร้อมส่งออกและเผยแพร่เรียบร้อยแล้ว!"
            )
        }
    }

    fun getMusicStepAssistantContract(musicStep: MusicStep): StepAssistantResponse {
        return when (musicStep) {
            MusicStep.M1_BRIEF -> StepAssistantResponse(
                stepCode = "M1",
                assistantName = "Music Producer / Brief Specialist",
                explain = AssistantExplain("กำหนดแนวทางดนตรี อารมณ์ BPM คีย์เพลง และภาษาสำหรับเพลงประกอบ", "เช่น เพลงไซไฟซินธ์เวฟ 120 BPM คีย์ Dm ภาษาไทยและอังกฤษ"),
                proposals = listOf(
                    ProposalOption("mb1", "Cyber-Synthwave Orchestral (แนะนำ)", "ลูกผสมเสียงซินธ์อะนาล็อกกับวงสายสตริงดราม่า", "ทรงพลัง เหมาะกับหนังไซไฟ", "ต้องมิกซ์อย่างระวัง", true),
                    ProposalOption("mb2", "Melodic Cyber-Pop", "ดนตรีอิเล็กทรอนิกส์ป็อปฟังสบาย ทำนองติดหู", "เข้าถึงง่าย ร้องตามได้", "ความสลัวของอารมณ์ดราฟต์ลดลง", false),
                    ProposalOption("mb3", "Dark Industrial EBM", "เบสไลน์สังเคราะห์ดุดัน จังหวะกระแทกกระทั้น", "เท่ ดิบ เร้าใจ", "อาจฟังยากสำหรับบางกลุ่ม", false)
                ),
                estimate = ResourceEstimate(2, 0.0, "Mock Music Brief Engine", "ฟรี"),
                critique = StepCritique(5, "กำหนด BPM 120 และคีย์ D Minor เหมาะสมกับอารมณ์ค้นหาความจริง", "ไม่มี", "ดำเนินต่อไปยังการวางโครงสร้างท่อนเพลง M2"),
                quickRefinements = listOf("เพิ่มความเร็ว BPM", "ลดความเร็วลง", "เปลี่ยนเป็นเพลงรักเศร้า", "เพิ่มจังหวะร็อก"),
                conflictCheck = ConflictCheckResult(false, "สอดคล้องกับภาพยนตร์ F9 สมบูรณ์"),
                handoffSummary = "ล็อกบรีฟดนตรี 120 BPM, Key D Minor ส่งต่อให้ผู้ช่วยจัดโครงสร้าง M2"
            )
            MusicStep.M2_STRUCTURE -> StepAssistantResponse(
                stepCode = "M2",
                assistantName = "Arranger & Song Form Architect",
                explain = AssistantExplain("จัดโครงสร้างเพลง: Intro &rarr; Verse &rarr; Build-up &rarr; Climax Chorus &rarr; Outro", "แบ่งเป็นช่วงสั้นๆ ละ 15 วินาที เพื่อให้พอดีกับภาพยนตร์ 60 วินาที"),
                proposals = listOf(
                    ProposalOption("ms1", "Cinematic Arc Form (แนะนำ)", "เริ่มเบา &rarr; ค่อยๆ สะสมพลัง &rarr; ระเบิดช่วงไคลแมกซ์", "นำพาอารมณ์คนดูได้ดีที่สุด", "ต้องเกลี่ยระดับเสียงให้สมดุล", true),
                    ProposalOption("ms2", "Standard Verse-Chorus Pop", "ท่อนร้องสลับท่อนฮุกแบบเพลงมาตรฐาน", "จำง่าย ติดหู", "เวลา 60 วิอาจสั้นไปสำหรับสองท่อนฮุก", false),
                    ProposalOption("ms3", "Loop & Development", "พัฒนาธีมหลักจากเครื่องดนตรีชิ้นเดียวจนเต็มวง", "คลาสสิก ละเมียดละไม", "ขาดจุดระเบิดอารมณ์ที่ฉับพลัน", false)
                ),
                estimate = ResourceEstimate(2, 0.0, "Structure Engine", "ฟรี"),
                critique = StepCritique(5, "โครงสร้าง 60 วินาที มีการแบ่งท่อนที่กระชับและรองรับจุดตัดต่อภาพยนตร์", "ไม่มี", "ไปต่อที่การประพันธ์เนื้อร้อง M3"),
                quickRefinements = listOf("เพิ่มท่อนบริดจ์", "ขยายท่อนฮุก", "ตัดท่อนอินโทรให้สั้นลง", "เพิ่มท่อนกีตาร์โซโล่"),
                conflictCheck = ConflictCheckResult(false, "ตรงกับเวลา 60 วินาที"),
                handoffSummary = "โครงสร้างท่อนเพลงถูกล็อก ส่งต่อให้ฝ่ายประพันธ์เนื้อร้อง M3"
            )
            MusicStep.M3_LYRICS -> StepAssistantResponse(
                stepCode = "M3",
                assistantName = "Lyricist & Prosody Specialist",
                explain = AssistantExplain("ประพันธ์เนื้อเพลงสองภาษา (ไทย/อังกฤษ) ตรวจการสัมผัส จำนวนพยางค์ และการผันเสียงวรรณยุกต์ไทยไม่ให้เพี้ยนกับทำนอง", "เช่น คำว่า 'สายฝน' ผันเสียงเข้ากับเมโลดี้โน้ตต่ำไปสูงอย่างไพเราะ"),
                proposals = listOf(
                    ProposalOption("ly1", "เนื้อร้องสองภาษา ปรัชญาความหวัง (แนะนำ)", "ท่อนไทยถ่ายทอดความรู้สึก ท่อนอังกฤษกระตุ้นพลังเสรีภาพ", "ลงตัว ทันสมัย สากล", "ต้องระวังการออกเสียงเชื่อมคำ", true),
                    ProposalOption("ly2", "เนื้อร้องภาษาไทยล้วนเชิงกวี", "ใช้คำเปรียบเปรยแบบวรรณศิลป์ลึกซึ้ง", "ไพเราะ งดงาม มีเอกลักษณ์", "ชาวต่างชาติอาจต้องพึ่งคำแปล", false),
                    ProposalOption("ly3", "เนื้อร้องภาษาอังกฤษล้วน Cyber Anthem", "เหมาะกับแนวเพลงอิเล็กทรอนิกส์สากล", "ร้องง่าย ติดหูชาวเน็ต", "ขาดเสน่ห์ภาษาแม่", false)
                ),
                estimate = ResourceEstimate(2, 0.0, "Thai Prosody Checker", "ฟรี"),
                critique = StepCritique(5, "คำร้องวรรณยุกต์ไทยตรงกับคอร์ด Dm และเมโลดี้ ไม่มีคำที่ร้องแล้วความหมายเพี้ยน", "สัมผัสวรรคที่ 3 สามารถคล้องจองได้อีก", "เปลี่ยนคำท้ายวรรคให้สัมผัสสระไอ"),
                quickRefinements = listOf("เพิ่มสัมผัสสระ", "ปรับให้ร้องง่ายขึ้น", "เพิ่มท่อนแร็ปภาษาอังกฤษ", "เปลี่ยนเป็นโทนโรแมนติก"),
                conflictCheck = ConflictCheckResult(false, "สอดคล้องกับเนื้อเรื่องภาพยนตร์"),
                handoffSummary = "เนื้อร้องสองภาษาผ่านการตรวจสัมผัสและวรรณยุกต์ ส่งต่อให้ M4 สร้างดนตรี"
            )
            MusicStep.M4_MUSIC_GEN -> StepAssistantResponse(
                stepCode = "M4",
                assistantName = "Sound Generator & Synthesizer Lead",
                explain = AssistantExplain("สร้างเวอร์ชันดนตรีประกอบพร้อมแยกแทร็ก (Stems: Drums, Bass, Synth, Strings)", "สร้าง 3 เวอร์ชันให้ฟังเปรียบเทียบก่อนเลือกเวอร์ชันที่ใช่ที่สุด"),
                proposals = listOf(
                    ProposalOption("mg1", "Master Cyber-Orchestra Take (แนะนำ)", "ผสมซินธ์ Sawtooth กับเชลโลและไวโอลินสด", "มิติเสียงกว้าง อลังการ มีมวลเสียงแน่น", "ใช้พื้นที่ความถี่สูง", true),
                    ProposalOption("mg2", "Drive Electro Take", "เน้นบีตกลอง 808 และเบสไลน์ทรงพลัง", "จังหวะขยับตัวสนุกเร้าใจ", "ขาดความอ่อนหวานของเสียงเครื่องสาย", false),
                    ProposalOption("mg3", "Cosmic Ambient Take", "เน้นเสียงสังเคราะห์ลอยละล่อง นุ่มนวล ชวนฝัน", "ผ่อนคลาย สมาธิสูง", "ขาดพลังในการฉุดอารมณ์ไคลแมกซ์", false)
                ),
                estimate = ResourceEstimate(3, 0.0, "Mock Music Generator", "สร้างดนตรีฟรี"),
                critique = StepCritique(5, "แทร็กดนตรีมีความสมดุลทางความถี่และมีช่วงไดนามิกส์ที่ยอดเยี่ยม", "ไม่มี", "เลือกเวอร์ชัน 1 และดำเนินการสร้างเสียงร้อง M5"),
                quickRefinements = listOf("เพิ่มเสียงเบส", "ลดเสียงฉาบแฉ", "เร่งเสียงเครื่องสาย", "ปรับมิกซ์ให้กว้างขึ้น"),
                conflictCheck = ConflictCheckResult(false, "ไม่พบความขัดแย้ง"),
                handoffSummary = "ดนตรีเวอร์ชัน Master ถูกเลือกและส่งต่อให้แผนกเสียงร้อง M5"
            )
            MusicStep.M5_VOCAL_GEN -> StepAssistantResponse(
                stepCode = "M5",
                assistantName = "Vocal Producer & Synthesis Director",
                explain = AssistantExplain("สังเคราะห์เสียงร้อง (AI Vocals) ด้วยเสียงที่ได้รับความยินยอม ตรวจสอบการออกเสียงภาษาไทยและอังกฤษอย่างถูกต้อง", "ปรับจูน Pitch, Vibrato และ Formant ให้กลมกลืนกับดนตรี"),
                proposals = listOf(
                    ProposalOption("vg1", "เสียงร้องหญิงอบอุ่นปนเศร้า (แนะนำ)", "สำเนียงชัดเจน สื่อสารอารมณ์ตัดพ้อแต่เปี่ยมความหวัง", "เข้ากับบทบาทของแก้วอย่างยิ่ง", "ต้องบาลานซ์เสียงรีเวิร์บให้พอดี", true),
                    ProposalOption("vg2", "เสียงร้องคู่ชาย-หญิงประสานเสียง", "เพิ่มมิติความยิ่งใหญ่ของบทเพลง", "อลังการ ทรงพลัง", "อาจซับซ้อนเกินไปสำหรับเพลงสั้น 60 วิ", false),
                    ProposalOption("vg3", "เสียงร้องเอฟเฟกต์ Vocoder / Cyber Voice", "ให้ฟีลลิ่งหุ่นยนต์ไซบอร์กแท้ๆ", "เข้ากับธีมล้ำยุคมาก", "ความลึกซึ้งทางอารมณ์ลดลง", false)
                ),
                estimate = ResourceEstimate(2, 0.0, "Mock Vocal Synthesizer", "ฟรี"),
                critique = StepCritique(5, "วรรณยุกต์เสียงตรีและโทในท่อนคอรัสถูกคุมระดับเสียงได้อย่างแม่นยำ ไพเราะ ไม่หลุดคีย์", "ไม่มี", "ส่งต่อให้แผนกตัดต่อภาพ M6"),
                quickRefinements = listOf("เพิ่มความหวาน", "ใส่เอฟเฟกต์หุ่นยนต์เบาๆ", "เพิ่มเสียงเอคโค่", "ปรับให้ร้องนุ่มขึ้น"),
                conflictCheck = ConflictCheckResult(false, "สอดคล้องกับโปรไฟล์ตัวละครแก้ว"),
                handoffSummary = "แทร็กเสียงร้องผ่านการจูนคีย์ D Minor สมบูรณ์ ส่งต่อให้ M6 ซิงก์กับภาพ"
            )
            MusicStep.M6_HIT_POINTS -> StepAssistantResponse(
                stepCode = "M6",
                assistantName = "Hit-Point Editor & Sync Master",
                explain = AssistantExplain("จัดจังหวะเพลงให้ตรงกับจุดตัดต่อภาพยนตร์ (Hit Points) เช่น จังหวะเบสดรอปตรงกับตอนที่แก้วกดปุ่มส่งสัญญาณ", "สร้างความรู้สึกร่วมทางอารมณ์สูงสุดเมื่อภาพและเสียงสอดประสานกัน"),
                proposals = listOf(
                    ProposalOption("hp1", "Frame-Accurate Hit Sync (แนะนำ)", "จัดวางจุดตกของเสียงกลองและเอฟเฟกต์ให้ตรงกับ 4 จุดเปลี่ยนฉากสำคัญ", "สร้างแรงกระแทกทางอารมณ์ชัดเจนมาก", "ต้องล็อกตำแหน่งตัดต่อภาพให้คงที่", true),
                    ProposalOption("hp2", "Loose Organic Drift", "ปล่อยให้เพลงไหลไปตามธรรมชาติโดยไม่ล็อกจุดเปลี่ยนฉากเป๊ะๆ", "ฟังดูเป็นธรรมชาติ ไม่ถูกบีบบังคับ", "อาจพลาดจังหวะการเน้นอารมณ์ในบางฉาก", false),
                    ProposalOption("hp3", "Stinger Accent Sync", "ใส่เสียงเครื่องสายกระชาก (Stinger) ในทุกช็อตที่เปลี่ยนมุมกล้อง", "เร้าใจ สไตล์ตัวอย่างภาพยนตร์แอ็กชัน", "อาจรบกวนความต่อเนื่องของดนตรี", false)
                ),
                estimate = ResourceEstimate(2, 0.0, "Hit-Point Alignment Tool", "ฟรี"),
                critique = StepCritique(5, "ความแม่นยำในการซิงก์ตรงกับรอยต่อวินาทีที่ 15.5, 34.0, และ 39.0 สมบูรณ์แบบ", "ไม่มี", "เข้าสู่ขั้นตอนสุดท้าย M7 มิกซ์และมาสเตอริ่ง"),
                quickRefinements = listOf("ขยับจุดกระแทกเสียงไปข้างหน้า 0.2 วิ", "ลดความกระชากของเสียง", "เพิ่มเสียงกลองสแนร์", "ปรับความต่อเนื่อง"),
                conflictCheck = ConflictCheckResult(false, "ตรงกับไทม์ไลน์ภาพยนตร์ 100%"),
                handoffSummary = "ดนตรีถูกล็อกเข้ากับภาพ พร้อมส่งเข้าห้องมิกซ์ M7"
            )
            MusicStep.M7_MASTER_MIX -> StepAssistantResponse(
                stepCode = "M7",
                assistantName = "Mastering Engineer & Delivery Lead",
                explain = AssistantExplain("ปรับความดังรวมให้อยู่ในมาตรฐานสากล (-14.0 ถึง -16.0 LUFS) ป้องกันเสียงแตก และส่งออกไฟล์ WAV/MP3 คุณภาพสูง", "ส่งมอบทั้งเพลงเต็ม, ดนตรีเปล่า (Backing Track), และ Cue Sheet"),
                proposals = listOf(
                    ProposalOption("mm1", "Streaming Master -14 LUFS (แนะนำ)", "ความดังมาตรฐาน YouTube, Spotify, และโรงภาพยนตร์สตรีมมิ่ง", "เสียงโปร่ง อิมแพ็คแน่น ไม่โดนแพลตฟอร์มบีบลดเสียง", "เป็นมาตรฐานที่ยอมรับสูงสุด", true),
                    ProposalOption("mm2", "High Dynamic Range -18 LUFS", "เน้นช่วงกว้างของความเบา-ความดังสูงสุด", "เหมาะสำหรับรับฟังในระบบโฮมเธียเตอร์ระดับไฮเอนด์", "อาจเบาไปเล็กน้อยบนลำโพงมือถือ", false),
                    ProposalOption("mm3", "Loud & Punchy -11 LUFS", "บีบอัดเสียงให้ดังสะใจสไตล์คลับ", "กระแทกกระทั้น ดังเด่นชัด", "อาจสูญเสียมิติของเสียงเครื่องสายไปบ้าง", false)
                ),
                estimate = ResourceEstimate(3, 0.0, "LUFS Mastering Engine", "มาสเตอริ่งฟรีในเครื่อง"),
                critique = StepCritique(5, "ความดังเฉลี่ย -14.2 LUFS, True Peak ที่ -1.2 dBFS ผ่านมาตรฐานการออกอากาศสากล", "ไม่มี", "เพลงประกอบเสร็จสมบูรณ์พร้อมส่งออก!"),
                quickRefinements = listOf("ส่งออกเป็น WAV 24-bit", "ส่งออกเป็น MP3 320kbps", "ดาวน์โหลดเฉพาะเนื้อเพลง", "ดาวน์โหลด Cue Sheet"),
                conflictCheck = ConflictCheckResult(false, "ผ่านเกณฑ์ QC ด้านเสียงทุกประการ"),
                handoffSummary = "เพลงประกอบภาพยนตร์ CineSonic เสร็จสมบูรณ์ บันทึกลงในโปรเจกต์พร้อมใช้งานทันที!"
            )
        }
    }
}
