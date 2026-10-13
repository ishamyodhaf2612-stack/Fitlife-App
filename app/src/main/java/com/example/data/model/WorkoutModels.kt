package com.example.data.model

data class Exercise(
    val id: String,
    val name: String,
    val category: String, // "Kardio", "Tubuh Bagian Atas", "Tubuh Bagian Bawah", "Core & Perut"
    val defaultDurationSec: Int = 30,
    val defaultReps: Int = 12,
    val isTimerBased: Boolean = true,
    val caloriesBurnPerMinute: Int = 8,
    val targetMuscles: String,
    val difficulty: String, // "Pemula", "Menengah", "Lanjutan"
    val instructions: List<String>,
    val tips: String
)

data class WorkoutProgram(
    val id: String,
    val title: String,
    val subtitle: String,
    val durationMinutes: Int,
    val estimatedCalories: Int,
    val level: String, // "Pemula", "Menengah", "Lanjutan"
    val badgeName: String,
    val exercises: List<Exercise>
)

object WorkoutCatalog {

    val allExercises: List<Exercise> = listOf(
        Exercise(
            id = "jumping_jacks",
            name = "Jumping Jacks",
            category = "Kardio",
            defaultDurationSec = 30,
            defaultReps = 25,
            isTimerBased = true,
            caloriesBurnPerMinute = 10,
            targetMuscles = "Seluruh Tubuh, Betis, Jantung",
            difficulty = "Pemula",
            instructions = listOf(
                "Berdiri tegak dengan kaki rapat dan lengan di samping tubuh.",
                "Lompat dan buka kaki selebar bahu sambil mengangkat kedua lengan ke atas kepala.",
                "Lompat kembali ke posisi awal secara berirama.",
                "Jaga ritme napas tetap teratur."
            ),
            tips = "Mendaratlah dengan lembut di ujung kaki untuk melindungi persendian lutut."
        ),
        Exercise(
            id = "push_up",
            name = "Push-Up Standar",
            category = "Tubuh Bagian Atas",
            defaultDurationSec = 30,
            defaultReps = 12,
            isTimerBased = false,
            caloriesBurnPerMinute = 8,
            targetMuscles = "Dada, Triceps, Bahu, Core",
            difficulty = "Menengah",
            instructions = listOf(
                "Posisikan tangan sedikit lebih lebar dari bahu, tubuh membentuk garis lurus dari kepala hingga tumit.",
                "Kencangkan otot perut dan bokong.",
                "Turunkan dada perlahan hingga hampir menyentuh lantai dengan siku membentuk sudut ~45 derajat.",
                "Dorong lantai kembali ke posisi awal dengan kuat."
            ),
            tips = "Jangan biarkan pinggul merosot ke bawah atau terangkat terlalu tinggi."
        ),
        Exercise(
            id = "knee_push_up",
            name = "Knee Push-Up (Push-up Lutut)",
            category = "Tubuh Bagian Atas",
            defaultDurationSec = 30,
            defaultReps = 10,
            isTimerBased = false,
            caloriesBurnPerMinute = 6,
            targetMuscles = "Dada, Triceps, Bahu",
            difficulty = "Pemula",
            instructions = listOf(
                "Posisikan tubuh seperti push-up biasa, namun tumpukan kedua lutut di lantai.",
                "Jaga garis lurus dari kepala hingga lutut.",
                "Turunkan dada hingga hampir menyentuh lantai, lalu dorong kembali ke atas."
            ),
            tips = "Pilihan sangat bagus untuk melatih kekuatan sebelum push-up penuh."
        ),
        Exercise(
            id = "bodyweight_squat",
            name = "Bodyweight Squat",
            category = "Tubuh Bagian Bawah",
            defaultDurationSec = 40,
            defaultReps = 15,
            isTimerBased = false,
            caloriesBurnPerMinute = 8,
            targetMuscles = "Paha Depan (Quadriceps), Bokong (Glutes), Hamstring",
            difficulty = "Pemula",
            instructions = listOf(
                "Berdiri dengan kaki selebar bahu, jari kaki sedikit mengarah ke luar.",
                "Dorong pinggul ke belakang dan tekuk lutut seolah-olah hendak duduk di kursi.",
                "Turun hingga paha sejajar lantai dengan dada tetap tegak.",
                "Tekan melalui tumit untuk kembali berdiri tegak."
            ),
            tips = "Pastikan lutut tidak menekuk terlalu maju melebihi ujung jari kaki."
        ),
        Exercise(
            id = "plank",
            name = "Forearm Plank",
            category = "Core & Perut",
            defaultDurationSec = 30,
            defaultReps = 1,
            isTimerBased = true,
            caloriesBurnPerMinute = 7,
            targetMuscles = "Otot Perut (Abs), Core Dalam, Punggung Bawah",
            difficulty = "Pemula",
            instructions = listOf(
                "Tumpu tubuh pada lengan bawah dan jari-jari kaki.",
                "Siku tepat di bawah bahu.",
                "Kencangkan seluruh otot perut, bokong, dan paha.",
                "Tahan posisi tanpa menaikkan atau menurunkan pinggul."
            ),
            tips = "Fokus pada pernapasan perlahan dan jangan tahan napas selama plank."
        ),
        Exercise(
            id = "burpee",
            name = "Burpee",
            category = "Kardio",
            defaultDurationSec = 30,
            defaultReps = 10,
            isTimerBased = true,
            caloriesBurnPerMinute = 12,
            targetMuscles = "Seluruh Tubuh, Dada, Kaki, Kapasitas Kardio",
            difficulty = "Lanjutan",
            instructions = listOf(
                "Dari posisi berdiri, jongkok dan letakkan tangan di lantai.",
                "Lontarkan kedua kaki ke belakang ke posisi plank.",
                "Lakukan push-up satu kali (opsional).",
                "Lompatkan kaki kembali ke dekat tangan, lalu lompat ke atas dengan kedua tangan mengarah ke langit."
            ),
            tips = "Pertahankan ritme konsisten daripada memaksakan kecepatan di awal."
        ),
        Exercise(
            id = "mountain_climbers",
            name = "Mountain Climbers",
            category = "Core & Perut",
            defaultDurationSec = 30,
            defaultReps = 20,
            isTimerBased = true,
            caloriesBurnPerMinute = 10,
            targetMuscles = "Core, Bahu, Paha, Kardio",
            difficulty = "Menengah",
            instructions = listOf(
                "Mulai dari posisi push-up dengan tangan di bawah bahu.",
                "Tarik lutut kanan ke arah dada secara eksplosif.",
                "Kembalikan kaki kanan sambil segera menarik lutut kiri ke dada.",
                "Lakukan gerakan bergantian seperti berlari di lantai."
            ),
            tips = "Jaga pinggul tetap stabil dan sejajar, hindari memantul ke atas dan ke bawah."
        ),
        Exercise(
            id = "lunges",
            name = "Alternating Lunges",
            category = "Tubuh Bagian Bawah",
            defaultDurationSec = 40,
            defaultReps = 16,
            isTimerBased = false,
            caloriesBurnPerMinute = 7,
            targetMuscles = "Paha Depan, Glutes, Betis, Keseimbangan",
            difficulty = "Pemula",
            instructions = listOf(
                "Berdiri tegak, langkahkan satu kaki jauh ke depan.",
                "Turunkan pinggul hingga kedua lutut membentuk sudut 90 derajat.",
                "Lutut belakang hampir menyentuh lantai.",
                "Dorong tumit depan untuk kembali ke posisi awal, lalu ulangi kaki satunya."
            ),
            tips = "Pertahankan punggung tetap lurus dan pandangan lurus ke depan."
        ),
        Exercise(
            id = "bicycle_crunches",
            name = "Bicycle Crunches",
            category = "Core & Perut",
            defaultDurationSec = 35,
            defaultReps = 20,
            isTimerBased = true,
            caloriesBurnPerMinute = 8,
            targetMuscles = "Perut Samping (Obliques), Perut Bawah",
            difficulty = "Menengah",
            instructions = listOf(
                "Berbaring telentang dengan tangan di belakang kepala (jangan menarik leher).",
                "Angkat kaki dan tekuk lutut 90 derajat.",
                "Bawa siku kanan ke arah lutut kiri sambil meluruskan kaki kanan.",
                "Ganti sisi secara berputar seperti mengayuh sepeda."
            ),
            tips = "Gerakan perlahan dan terkontrol jauh lebih efektif dibanding kecepatan tinggi."
        ),
        Exercise(
            id = "wall_sit",
            name = "Wall Sit",
            category = "Tubuh Bagian Bawah",
            defaultDurationSec = 30,
            defaultReps = 1,
            isTimerBased = true,
            caloriesBurnPerMinute = 6,
            targetMuscles = "Paha Depan (Quads), Ketahanan Otot Kaki",
            difficulty = "Pemula",
            instructions = listOf(
                "Sandarkan punggung ke dinding yang kokoh.",
                "Geser tubuh ke bawah hingga paha sejajar dengan lantai (sudut 90 derajat di lutut).",
                "Rapatkan punggung ke dinding dan tahan posisi selama durasi target."
            ),
            tips = "Letakkan tangan di samping tubuh atau dada, jangan bertumpu di paha."
        ),
        Exercise(
            id = "high_knees",
            name = "High Knees (Lari Angkat Lutut)",
            category = "Kardio",
            defaultDurationSec = 30,
            defaultReps = 30,
            isTimerBased = true,
            caloriesBurnPerMinute = 11,
            targetMuscles = "Kardio, Paha Depan, Flexor Pinggul",
            difficulty = "Menengah",
            instructions = listOf(
                "Berlari di tempat dengan mengangkat lutut setinggi pinggul.",
                "Ayunkan lengan secara berlawanan mengikuti gerakan kaki.",
                "Pertahankan kecepatan yang konstan."
            ),
            tips = "Mendarat dengan ujung kaki bagian depan untuk mengurangi benturan sendi."
        ),
        Exercise(
            id = "glute_bridge",
            name = "Glute Bridge",
            category = "Tubuh Bagian Bawah",
            defaultDurationSec = 35,
            defaultReps = 15,
            isTimerBased = false,
            caloriesBurnPerMinute = 6,
            targetMuscles = "Bokong (Glutes), Hamstring, Punggung Bawah",
            difficulty = "Pemula",
            instructions = listOf(
                "Berbaring telentang dengan lutut ditekuk dan telapak kaki menapak lantai.",
                "Tekan tumit dan angkat pinggul hingga tubuh membentuk garis lurus dari bahu ke lutut.",
                "Remas otot bokong di puncak gerakan selama 1 detik.",
                "Turunkan pinggul perlahan kembali ke lantai."
            ),
            tips = "Gerakan luar biasa untuk memperbaiki postur akibat terlalu lama duduk."
        ),
        Exercise(
            id = "tricep_dips",
            name = "Chair / Floor Dips",
            category = "Tubuh Bagian Atas",
            defaultDurationSec = 30,
            defaultReps = 12,
            isTimerBased = false,
            caloriesBurnPerMinute = 7,
            targetMuscles = "Triceps, Bahu Depan, Dada Bawah",
            difficulty = "Menengah",
            instructions = listOf(
                "Duduk di tepi kursi atau lantai, letakkan telapak tangan di samping pinggul.",
                "Geser pinggul ke depan keluar dari kursi.",
                "Tekuk siku hingga 90 derajat untuk menurunkan tubuh.",
                "Dorong telapak tangan untuk meluruskan kembali lengan."
            ),
            tips = "Jaga punggung tetap dekat dengan tepi kursi sepanjang gerakan."
        )
    )

    val programs: List<WorkoutProgram> = listOf(
        WorkoutProgram(
            id = "prog_beginner",
            title = "Pemula: Full Body Kickstart",
            subtitle = "Latihan dasar ramah sendi untuk membangun fondasi kebugaran tubuh.",
            durationMinutes = 15,
            estimatedCalories = 120,
            level = "Pemula",
            badgeName = "Langkah Pertama",
            exercises = listOf(
                allExercises.first { it.id == "jumping_jacks" },
                allExercises.first { it.id == "bodyweight_squat" },
                allExercises.first { it.id == "knee_push_up" },
                allExercises.first { it.id == "glute_bridge" },
                allExercises.first { it.id == "wall_sit" },
                allExercises.first { it.id == "plank" }
            )
        ),
        WorkoutProgram(
            id = "prog_fat_burn",
            title = "HIIT Bakar Lemak Cepat",
            subtitle = "Kardio intensitas tinggi tanpa alat untuk memaksimalkan pembakaran kalori.",
            durationMinutes = 18,
            estimatedCalories = 210,
            level = "Menengah",
            badgeName = "Pembakar Lemak",
            exercises = listOf(
                allExercises.first { it.id == "jumping_jacks" },
                allExercises.first { it.id == "high_knees" },
                allExercises.first { it.id == "mountain_climbers" },
                allExercises.first { it.id == "bodyweight_squat" },
                allExercises.first { it.id == "burpee" },
                allExercises.first { it.id == "plank" }
            )
        ),
        WorkoutProgram(
            id = "prog_core",
            title = "Perut Rata & Core Kuat",
            subtitle = "Spesialis pengencangan otot perut, pinggang, dan stabilitas tulang belakang.",
            durationMinutes = 15,
            estimatedCalories = 135,
            level = "Menengah",
            badgeName = "Besi Berotot",
            exercises = listOf(
                allExercises.first { it.id == "plank" },
                allExercises.first { it.id == "bicycle_crunches" },
                allExercises.first { it.id == "mountain_climbers" },
                allExercises.first { it.id == "glute_bridge" },
                allExercises.first { it.id == "bicycle_crunches" }
            )
        ),
        WorkoutProgram(
            id = "prog_intermediate",
            title = "Menengah: Ketahanan & Otot",
            subtitle = "Tingkatkan repetisi dan tempo untuk hasil pembentukan otot yang lebih nyata.",
            durationMinutes = 25,
            estimatedCalories = 240,
            level = "Menengah",
            badgeName = "Pantang Mundur",
            exercises = listOf(
                allExercises.first { it.id == "jumping_jacks" },
                allExercises.first { it.id == "push_up" },
                allExercises.first { it.id == "bodyweight_squat" },
                allExercises.first { it.id == "tricep_dips" },
                allExercises.first { it.id == "lunges" },
                allExercises.first { it.id == "mountain_climbers" },
                allExercises.first { it.id == "plank" }
            )
        ),
        WorkoutProgram(
            id = "prog_advanced",
            title = "Lanjutan: Beast Mode 35 Menit",
            subtitle = "Kombinasi kekuatan, ledakan otot, dan kardio untuk atlet rumahan.",
            durationMinutes = 35,
            estimatedCalories = 340,
            level = "Lanjutan",
            badgeName = "Juara Sejati",
            exercises = listOf(
                allExercises.first { it.id == "high_knees" },
                allExercises.first { it.id == "push_up" },
                allExercises.first { it.id == "burpee" },
                allExercises.first { it.id == "lunges" },
                allExercises.first { it.id == "tricep_dips" },
                allExercises.first { it.id == "mountain_climbers" },
                allExercises.first { it.id == "bodyweight_squat" },
                allExercises.first { it.id == "bicycle_crunches" },
                allExercises.first { it.id == "plank" }
            )
        )
    )
}
