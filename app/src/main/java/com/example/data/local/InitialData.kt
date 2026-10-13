package com.example.data.local

object InitialData {

    val indonesianFoods: List<FoodEntity> = listOf(
        // === MAKANAN POKOK ===
        FoodEntity(name = "Nasi Putih", category = "Makanan Pokok", calories = 135, protein = 2.7f, carbs = 29.3f, fat = 0.3f, servingUnit = "1 porsi (100g)"),
        FoodEntity(name = "Nasi Merah", category = "Makanan Pokok", calories = 111, protein = 2.6f, carbs = 23.5f, fat = 0.9f, servingUnit = "1 porsi (100g)"),
        FoodEntity(name = "Nasi Goreng Spesial Telur", category = "Makanan Pokok", calories = 385, protein = 11.2f, carbs = 48.0f, fat = 16.5f, servingUnit = "1 piring"),
        FoodEntity(name = "Nasi Uduk", category = "Makanan Pokok", calories = 260, protein = 5.1f, carbs = 42.0f, fat = 8.5f, servingUnit = "1 porsi (150g)"),
        FoodEntity(name = "Nasi Kuning", category = "Makanan Pokok", calories = 245, protein = 4.8f, carbs = 39.5f, fat = 7.8f, servingUnit = "1 porsi (150g)"),
        FoodEntity(name = "Nasi Liwet", category = "Makanan Pokok", calories = 230, protein = 4.5f, carbs = 38.0f, fat = 6.8f, servingUnit = "1 porsi"),
        FoodEntity(name = "Lontong / Ketupat", category = "Makanan Pokok", calories = 130, protein = 2.4f, carbs = 28.5f, fat = 0.4f, servingUnit = "1 buah (100g)"),
        FoodEntity(name = "Bubur Ayam Komplit", category = "Makanan Pokok", calories = 365, protein = 13.5f, carbs = 45.0f, fat = 14.0f, servingUnit = "1 mangkok"),
        FoodEntity(name = "Mie Goreng Instan", category = "Makanan Pokok", calories = 380, protein = 8.0f, carbs = 54.0f, fat = 15.0f, servingUnit = "1 bungkus (85g)"),
        FoodEntity(name = "Mie Rebus Instan", category = "Makanan Pokok", calories = 330, protein = 7.0f, carbs = 48.0f, fat = 12.0f, servingUnit = "1 bungkus"),
        FoodEntity(name = "Bihun Goreng", category = "Makanan Pokok", calories = 290, protein = 6.2f, carbs = 46.0f, fat = 9.5f, servingUnit = "1 piring sedang"),
        FoodEntity(name = "Kwetiau Goreng Sapi", category = "Makanan Pokok", calories = 430, protein = 16.0f, carbs = 55.0f, fat = 16.8f, servingUnit = "1 piring"),
        FoodEntity(name = "Oatmeal Matang", category = "Makanan Pokok", calories = 150, protein = 5.0f, carbs = 27.0f, fat = 2.5f, servingUnit = "1 mangkok (150g)"),
        FoodEntity(name = "Roti Tawar Gandum", category = "Makanan Pokok", calories = 70, protein = 3.5f, carbs = 12.5f, fat = 1.0f, servingUnit = "1 lembar"),
        FoodEntity(name = "Roti Tawar Putih", category = "Makanan Pokok", calories = 80, protein = 2.5f, carbs = 15.0f, fat = 1.1f, servingUnit = "1 lembar"),
        FoodEntity(name = "Kentang Rebus", category = "Makanan Pokok", calories = 87, protein = 1.9f, carbs = 20.1f, fat = 0.1f, servingUnit = "1 buah sedang (100g)"),
        FoodEntity(name = "Ubi Jalar Kukus", category = "Makanan Pokok", calories = 90, protein = 2.0f, carbs = 20.7f, fat = 0.2f, servingUnit = "1 buah sedang (100g)"),
        FoodEntity(name = "Singkong Rebus", category = "Makanan Pokok", calories = 160, protein = 1.4f, carbs = 38.0f, fat = 0.3f, servingUnit = "1 potong (100g)"),

        // === LAUK HEWANI ===
        FoodEntity(name = "Dada Ayam Panggang / Bakar", category = "Lauk Pauk", calories = 165, protein = 31.0f, carbs = 0.0f, fat = 3.6f, servingUnit = "1 potong (100g)"),
        FoodEntity(name = "Ayam Goreng Lengkuas", category = "Lauk Pauk", calories = 245, protein = 24.0f, carbs = 3.5f, fat = 15.0f, servingUnit = "1 potong (100g)"),
        FoodEntity(name = "Ayam Geprek Crispy Sambal Bawang", category = "Lauk Pauk", calories = 370, protein = 26.0f, carbs = 18.0f, fat = 21.0f, servingUnit = "1 porsi"),
        FoodEntity(name = "Ayam Pop Khas Minang", category = "Lauk Pauk", calories = 180, protein = 22.0f, carbs = 1.0f, fat = 9.5f, servingUnit = "1 potong"),
        FoodEntity(name = "Rendang Daging Sapi", category = "Lauk Pauk", calories = 215, protein = 20.5f, carbs = 4.2f, fat = 13.0f, servingUnit = "1 potong (70g)"),
        FoodEntity(name = "Empal Daging Goreng", category = "Lauk Pauk", calories = 200, protein = 21.0f, carbs = 4.0f, fat = 11.0f, servingUnit = "1 potong (60g)"),
        FoodEntity(name = "Semur Daging Sapi", category = "Lauk Pauk", calories = 190, protein = 19.5f, carbs = 7.0f, fat = 9.5f, servingUnit = "1 porsi"),
        FoodEntity(name = "Sate Ayam Madura (5 tusuk)", category = "Lauk Pauk", calories = 260, protein = 22.0f, carbs = 11.0f, fat = 14.5f, servingUnit = "5 tusuk bumbu kacang"),
        FoodEntity(name = "Sate Kambing Bumbu Kecap (5 tusuk)", category = "Lauk Pauk", calories = 310, protein = 25.0f, carbs = 8.5f, fat = 19.0f, servingUnit = "5 tusuk"),
        FoodEntity(name = "Sate Taichan (5 tusuk)", category = "Lauk Pauk", calories = 160, protein = 24.0f, carbs = 2.0f, fat = 6.0f, servingUnit = "5 tusuk sambal"),
        FoodEntity(name = "Telur Ayam Rebus", category = "Lauk Pauk", calories = 74, protein = 6.3f, carbs = 0.4f, fat = 5.0f, servingUnit = "1 butir"),
        FoodEntity(name = "Telur Dadar Goreng", category = "Lauk Pauk", calories = 135, protein = 6.8f, carbs = 1.2f, fat = 11.5f, servingUnit = "1 butir"),
        FoodEntity(name = "Telur Ceplok (Mata Sapi)", category = "Lauk Pauk", calories = 115, protein = 6.5f, carbs = 0.5f, fat = 9.8f, servingUnit = "1 butir"),
        FoodEntity(name = "Telur Balado", category = "Lauk Pauk", calories = 140, protein = 7.0f, carbs = 4.5f, fat = 10.0f, servingUnit = "1 butir"),
        FoodEntity(name = "Ikan Nila Bakar Kecap", category = "Lauk Pauk", calories = 175, protein = 26.0f, carbs = 4.0f, fat = 5.5f, servingUnit = "1 ekor sedang (150g)"),
        FoodEntity(name = "Ikan Lele Goreng", category = "Lauk Pauk", calories = 220, protein = 21.0f, carbs = 3.0f, fat = 14.0f, servingUnit = "1 ekor (120g)"),
        FoodEntity(name = "Ikan Kembung Goreng", category = "Lauk Pauk", calories = 180, protein = 22.5f, carbs = 1.0f, fat = 9.5f, servingUnit = "1 ekor (100g)"),
        FoodEntity(name = "Ikan Tongkol Balado", category = "Lauk Pauk", calories = 195, protein = 24.0f, carbs = 5.0f, fat = 8.5f, servingUnit = "1 potong (100g)"),
        FoodEntity(name = "Ikan Gurame Asam Manis", category = "Lauk Pauk", calories = 280, protein = 23.0f, carbs = 18.0f, fat = 12.0f, servingUnit = "1 porsi"),
        FoodEntity(name = "Udang Goreng Tepung", category = "Lauk Pauk", calories = 225, protein = 18.0f, carbs = 14.0f, fat = 11.0f, servingUnit = "5 ekor"),
        FoodEntity(name = "Cumi Bakar Kecap Pedas", category = "Lauk Pauk", calories = 150, protein = 23.0f, carbs = 6.0f, fat = 3.5f, servingUnit = "1 porsi (120g)"),
        FoodEntity(name = "Bebek Goreng Sambal Korek", category = "Lauk Pauk", calories = 330, protein = 24.0f, carbs = 2.0f, fat = 25.0f, servingUnit = "1 potong paha/dada"),
        FoodEntity(name = "Bakso Sapi Kuah Tanpa Mie", category = "Lauk Pauk", calories = 210, protein = 16.0f, carbs = 12.0f, fat = 11.0f, servingUnit = "1 mangkok (5 butir)"),
        FoodEntity(name = "Bakso Sapi Komplit dengan Mie", category = "Lauk Pauk", calories = 345, protein = 19.0f, carbs = 38.0f, fat = 14.0f, servingUnit = "1 mangkok"),
        FoodEntity(name = "Pempek Kapal Selam", category = "Lauk Pauk", calories = 295, protein = 14.0f, carbs = 36.0f, fat = 10.5f, servingUnit = "1 buah + cuko"),
        FoodEntity(name = "Pempek Lenjer", category = "Lauk Pauk", calories = 160, protein = 9.0f, carbs = 24.0f, fat = 3.5f, servingUnit = "1 buah sedang"),

        // === LAUK NABATI & OLAHAN KEDELAI ===
        FoodEntity(name = "Tempe Bacem", category = "Lauk Pauk", calories = 95, protein = 7.5f, carbs = 8.0f, fat = 3.5f, servingUnit = "1 potong (50g)"),
        FoodEntity(name = "Tempe Goreng Tepung (Mendoan)", category = "Lauk Pauk", calories = 140, protein = 6.0f, carbs = 11.0f, fat = 9.0f, servingUnit = "1 potong"),
        FoodEntity(name = "Tempe Goreng Polos", category = "Lauk Pauk", calories = 115, protein = 9.0f, carbs = 4.5f, fat = 7.0f, servingUnit = "1 potong (50g)"),
        FoodEntity(name = "Orek Tempe Kering", category = "Lauk Pauk", calories = 180, protein = 10.0f, carbs = 14.0f, fat = 9.5f, servingUnit = "3 sendok makan (50g)"),
        FoodEntity(name = "Tahu Goreng Polos", category = "Lauk Pauk", calories = 85, protein = 6.5f, carbs = 2.0f, fat = 6.0f, servingUnit = "1 potong (50g)"),
        FoodEntity(name = "Tahu Isi Sayuran Goreng", category = "Lauk Pauk", calories = 145, protein = 5.0f, carbs = 12.0f, fat = 9.0f, servingUnit = "1 buah"),
        FoodEntity(name = "Tahu Bacem", category = "Lauk Pauk", calories = 80, protein = 5.5f, carbs = 7.0f, fat = 3.0f, servingUnit = "1 potong"),
        FoodEntity(name = "Tahu Telur Bumbu Petis", category = "Lauk Pauk", calories = 310, protein = 17.0f, carbs = 18.0f, fat = 19.0f, servingUnit = "1 porsi"),
        FoodEntity(name = "Pepes Tahu Jamur", category = "Lauk Pauk", calories = 110, protein = 9.0f, carbs = 6.0f, fat = 5.0f, servingUnit = "1 bungkus (100g)"),

        // === SAYURAN & SUP ===
        FoodEntity(name = "Sayur Bening Bayam & Jagung", category = "Sayuran & Sup", calories = 65, protein = 3.0f, carbs = 12.0f, fat = 0.5f, servingUnit = "1 mangkok"),
        FoodEntity(name = "Sayur Asem Khas Sunda/Betawi", category = "Sayuran & Sup", calories = 75, protein = 2.8f, carbs = 14.5f, fat = 1.0f, servingUnit = "1 mangkok"),
        FoodEntity(name = "Sayur Lodeh", category = "Sayuran & Sup", calories = 165, protein = 4.0f, carbs = 12.0f, fat = 11.5f, servingUnit = "1 mangkok"),
        FoodEntity(name = "Capcay Sayuran Kuah", category = "Sayuran & Sup", calories = 115, protein = 5.5f, carbs = 12.0f, fat = 5.0f, servingUnit = "1 porsi sedang"),
        FoodEntity(name = "Capcay Goreng Seafood", category = "Sayuran & Sup", calories = 195, protein = 14.0f, carbs = 15.0f, fat = 9.0f, servingUnit = "1 piring"),
        FoodEntity(name = "Gado-Gado dengan Saus Kacang", category = "Sayuran & Sup", calories = 315, protein = 12.0f, carbs = 32.0f, fat = 16.0f, servingUnit = "1 porsi"),
        FoodEntity(name = "Karedok Khas Sunda", category = "Sayuran & Sup", calories = 260, protein = 10.0f, carbs = 25.0f, fat = 14.0f, servingUnit = "1 porsi"),
        FoodEntity(name = "Pecel Sayur Saus Kacang", category = "Sayuran & Sup", calories = 280, protein = 11.0f, carbs = 28.0f, fat = 15.0f, servingUnit = "1 porsi"),
        FoodEntity(name = "Tumis Kangkung Belacan", category = "Sayuran & Sup", calories = 120, protein = 4.0f, carbs = 8.0f, fat = 8.5f, servingUnit = "1 piring sedang"),
        FoodEntity(name = "Tumis Buncis Jagung Manis", category = "Sayuran & Sup", calories = 95, protein = 3.5f, carbs = 11.0f, fat = 4.5f, servingUnit = "1 piring"),
        FoodEntity(name = "Tumis Tauge Tahu", category = "Sayuran & Sup", calories = 105, protein = 6.0f, carbs = 7.0f, fat = 6.0f, servingUnit = "1 piring"),
        FoodEntity(name = "Soto Ayam Lamongan Kuah Bening", category = "Sayuran & Sup", calories = 265, protein = 22.0f, carbs = 18.0f, fat = 11.0f, servingUnit = "1 mangkok"),
        FoodEntity(name = "Soto Betawi Kuah Santan Daging", category = "Sayuran & Sup", calories = 430, protein = 24.0f, carbs = 16.0f, fat = 31.0f, servingUnit = "1 mangkok"),
        FoodEntity(name = "Rawon Daging Sapi", category = "Sayuran & Sup", calories = 330, protein = 26.0f, carbs = 10.0f, fat = 20.0f, servingUnit = "1 mangkok"),
        FoodEntity(name = "Sop Buntut Sapi", category = "Sayuran & Sup", calories = 380, protein = 28.0f, carbs = 8.0f, fat = 26.0f, servingUnit = "1 mangkok"),
        FoodEntity(name = "Sop Ayam Jamur", category = "Sayuran & Sup", calories = 150, protein = 19.0f, carbs = 6.0f, fat = 5.0f, servingUnit = "1 mangkok"),
        FoodEntity(name = "Ketoprak Jakarta Telur", category = "Sayuran & Sup", calories = 410, protein = 14.0f, carbs = 46.0f, fat = 19.5f, servingUnit = "1 porsi"),
        FoodEntity(name = "Gulai Daun Singkong", category = "Sayuran & Sup", calories = 190, protein = 5.0f, carbs = 14.0f, fat = 13.0f, servingUnit = "1 porsi"),
        FoodEntity(name = "Urap Sayur Bumbu Kelapa", category = "Sayuran & Sup", calories = 145, protein = 4.5f, carbs = 16.0f, fat = 7.5f, servingUnit = "1 porsi (100g)"),

        // === CAMILAN & GORENGAN ===
        FoodEntity(name = "Bakwan Sayur (Bala-Bala)", category = "Camilan & Gorengan", calories = 145, protein = 3.0f, carbs = 16.0f, fat = 8.0f, servingUnit = "1 buah"),
        FoodEntity(name = "Pisang Goreng Tepung", category = "Camilan & Gorengan", calories = 150, protein = 1.5f, carbs = 24.0f, fat = 6.0f, servingUnit = "1 buah"),
        FoodEntity(name = "Risoles Mayo Telur Daging", category = "Camilan & Gorengan", calories = 195, protein = 5.5f, carbs = 18.0f, fat = 11.5f, servingUnit = "1 buah"),
        FoodEntity(name = "Cireng Goreng Bumbu Rujak", category = "Camilan & Gorengan", calories = 130, protein = 1.0f, carbs = 22.0f, fat = 4.5f, servingUnit = "3 keping kecil"),
        FoodEntity(name = "Martabak Telur Daging", category = "Camilan & Gorengan", calories = 185, protein = 8.0f, carbs = 12.0f, fat = 12.0f, servingUnit = "1 potong"),
        FoodEntity(name = "Martabak Manis Coklat Keju", category = "Camilan & Gorengan", calories = 290, protein = 6.0f, carbs = 42.0f, fat = 11.5f, servingUnit = "1 potong"),
        FoodEntity(name = "Lemper Ayam Kukus", category = "Camilan & Gorengan", calories = 140, protein = 5.0f, carbs = 19.0f, fat = 5.0f, servingUnit = "1 buah"),
        FoodEntity(name = "Pastel Goreng Isi Sayur Telur", category = "Camilan & Gorengan", calories = 170, protein = 4.5f, carbs = 18.0f, fat = 9.0f, servingUnit = "1 buah"),
        FoodEntity(name = "Siomay Bandung Ikan + Bumbu Kacang", category = "Camilan & Gorengan", calories = 240, protein = 12.0f, carbs = 22.0f, fat = 12.0f, servingUnit = "3 butir"),
        FoodEntity(name = "Batagor Goreng Saus Kacang", category = "Camilan & Gorengan", calories = 310, protein = 11.0f, carbs = 28.0f, fat = 17.5f, servingUnit = "1 porsi sedang"),
        FoodEntity(name = "Tahu Gejrot Cirebon", category = "Camilan & Gorengan", calories = 90, protein = 4.0f, carbs = 10.0f, fat = 3.5f, servingUnit = "1 porsi"),
        FoodEntity(name = "Kerupuk Putih Kaleng", category = "Camilan & Gorengan", calories = 65, protein = 0.5f, carbs = 8.0f, fat = 3.5f, servingUnit = "1 buah"),
        FoodEntity(name = "Kerupuk Kulit Sapi (Jidor)", category = "Camilan & Gorengan", calories = 85, protein = 14.0f, carbs = 0.0f, fat = 3.2f, servingUnit = "1 bungkus kecil (15g)"),
        FoodEntity(name = "Emping Melinjo Goreng", category = "Camilan & Gorengan", calories = 110, protein = 2.0f, carbs = 14.0f, fat = 5.5f, servingUnit = "1 genggam (20g)"),
        FoodEntity(name = "Edamame Rebus", category = "Camilan & Gorengan", calories = 95, protein = 9.0f, carbs = 7.0f, fat = 4.0f, servingUnit = "1 mangkok kecil (80g)"),

        // === MINUMAN ===
        FoodEntity(name = "Air Mineral", category = "Minuman", calories = 0, protein = 0.0f, carbs = 0.0f, fat = 0.0f, servingUnit = "1 gelas (250ml)"),
        FoodEntity(name = "Es Teh Manis", category = "Minuman", calories = 95, protein = 0.0f, carbs = 24.0f, fat = 0.0f, servingUnit = "1 gelas (250ml)"),
        FoodEntity(name = "Es Teh Tawar", category = "Minuman", calories = 2, protein = 0.0f, carbs = 0.5f, fat = 0.0f, servingUnit = "1 gelas (250ml)"),
        FoodEntity(name = "Kopi Hitam / Americano Tanpa Gula", category = "Minuman", calories = 5, protein = 0.3f, carbs = 0.8f, fat = 0.0f, servingUnit = "1 cangkir (200ml)"),
        FoodEntity(name = "Kopi Susu Gula Aren Kekinian", category = "Minuman", calories = 195, protein = 4.5f, carbs = 28.0f, fat = 7.5f, servingUnit = "1 cup (300ml)"),
        FoodEntity(name = "Jus Alpukat + Susu Coklat", category = "Minuman", calories = 250, protein = 3.0f, carbs = 32.0f, fat = 13.0f, servingUnit = "1 gelas (300ml)"),
        FoodEntity(name = "Air Kelapa Muda Murni", category = "Minuman", calories = 45, protein = 1.0f, carbs = 9.0f, fat = 0.5f, servingUnit = "1 gelas (250ml)"),
        FoodEntity(name = "Susu Kedelai Murni Tanpa Gula", category = "Minuman", calories = 85, protein = 7.0f, carbs = 4.0f, fat = 4.0f, servingUnit = "1 gelas (250ml)"),
        FoodEntity(name = "Susu Sapi UHT Full Cream", category = "Minuman", calories = 150, protein = 8.0f, carbs = 12.0f, fat = 8.0f, servingUnit = "1 kotak/gelas (250ml)"),
        FoodEntity(name = "Susu Sapi Low Fat (Rendah Lemak)", category = "Minuman", calories = 100, protein = 8.0f, carbs = 12.0f, fat = 2.5f, servingUnit = "1 gelas (250ml)"),
        FoodEntity(name = "Whey Protein Shake", category = "Minuman", calories = 125, protein = 24.0f, carbs = 3.0f, fat = 1.5f, servingUnit = "1 scoop (30g)"),
        FoodEntity(name = "Jus Jeruk Murni Peras", category = "Minuman", calories = 110, protein = 1.7f, carbs = 25.0f, fat = 0.5f, servingUnit = "1 gelas (250ml)"),
        FoodEntity(name = "Jus Mangga Manis", category = "Minuman", calories = 145, protein = 1.0f, carbs = 35.0f, fat = 0.5f, servingUnit = "1 gelas (250ml)"),
        FoodEntity(name = "Wedang Jahe Gula Merah", category = "Minuman", calories = 70, protein = 0.2f, carbs = 17.0f, fat = 0.1f, servingUnit = "1 cangkir (200ml)"),
        FoodEntity(name = "Boba Brown Sugar Milk Tea", category = "Minuman", calories = 360, protein = 4.0f, carbs = 58.0f, fat = 12.0f, servingUnit = "1 cup (400ml)"),

        // === BUAH-BUAHAN & PENUTUP ===
        FoodEntity(name = "Pisang Ambon / Cavendish", category = "Buah", calories = 105, protein = 1.3f, carbs = 27.0f, fat = 0.3f, servingUnit = "1 buah sedang (118g)"),
        FoodEntity(name = "Apel Fuji / Malang", category = "Buah", calories = 95, protein = 0.5f, carbs = 25.0f, fat = 0.3f, servingUnit = "1 buah sedang (180g)"),
        FoodEntity(name = "Pepaya Potong Segar", category = "Buah", calories = 60, protein = 0.8f, carbs = 15.0f, fat = 0.4f, servingUnit = "1 mangkok (150g)"),
        FoodEntity(name = "Semangka Merah Potong", category = "Buah", calories = 45, protein = 0.9f, carbs = 11.5f, fat = 0.2f, servingUnit = "1 mangkok (150g)"),
        FoodEntity(name = "Jeruk Manis Segar", category = "Buah", calories = 62, protein = 1.2f, carbs = 15.4f, fat = 0.2f, servingUnit = "1 buah sedang (130g)"),
        FoodEntity(name = "Melon Hijau Potong", category = "Buah", calories = 55, protein = 1.0f, carbs = 13.5f, fat = 0.2f, servingUnit = "1 mangkok (150g)"),
        FoodEntity(name = "Nanas Madu Potong", category = "Buah", calories = 75, protein = 0.8f, carbs = 19.5f, fat = 0.2f, servingUnit = "1 mangkok (150g)"),
        FoodEntity(name = "Alpukat Mentega Matang", category = "Buah", calories = 160, protein = 2.0f, carbs = 8.5f, fat = 14.7f, servingUnit = "1/2 buah (100g)"),
        FoodEntity(name = "Buah Naga Merah", category = "Buah", calories = 60, protein = 1.2f, carbs = 13.0f, fat = 0.6f, servingUnit = "1 mangkok (100g)"),
        FoodEntity(name = "Mangga Harum Manis", category = "Buah", calories = 135, protein = 1.1f, carbs = 35.0f, fat = 0.6f, servingUnit = "1 buah sedang (200g)"),
        FoodEntity(name = "Salak Pondoh", category = "Buah", calories = 70, protein = 0.7f, carbs = 17.5f, fat = 0.3f, servingUnit = "2 buah (90g)"),
        FoodEntity(name = "Jambu Biji Kristal", category = "Buah", calories = 68, protein = 2.5f, carbs = 14.3f, fat = 0.9f, servingUnit = "1 buah sedang (100g)"),

        // === SAUS & TAMBAHAN MAKANAN ===
        FoodEntity(name = "Sambal Terasi Uleg", category = "Lauk Pauk", calories = 35, protein = 1.0f, carbs = 4.0f, fat = 1.8f, servingUnit = "1 sendok makan (20g)"),
        FoodEntity(name = "Sambal Bawang Goreng", category = "Lauk Pauk", calories = 45, protein = 0.5f, carbs = 2.0f, fat = 4.0f, servingUnit = "1 sendok makan (15g)"),
        FoodEntity(name = "Kecap Manis", category = "Lauk Pauk", calories = 40, protein = 0.8f, carbs = 9.0f, fat = 0.1f, servingUnit = "1 sendok makan (15ml)"),
        FoodEntity(name = "Bawang Goreng Tabur", category = "Lauk Pauk", calories = 35, protein = 0.5f, carbs = 3.0f, fat = 2.5f, servingUnit = "1 sendok makan (10g)")
    )

    val defaultMealSchedules: List<MealScheduleEntity> = listOf(
        MealScheduleEntity(mealType = "Sarapan Pagi", scheduledTime = "07:00", customMenuNotes = "Telur rebus + Oatmeal pisang / Roti gandum"),
        MealScheduleEntity(mealType = "Snack / Camilan Pagi", scheduledTime = "10:00", customMenuNotes = "Apel / Buah pepaya potong"),
        MealScheduleEntity(mealType = "Makan Siang", scheduledTime = "12:30", customMenuNotes = "Nasi merah + Dada ayam bakar + Sayur bayam bening"),
        MealScheduleEntity(mealType = "Snack / Camilan Sore", scheduledTime = "16:00", customMenuNotes = "Segelas air kelapa muda / Edamame rebus"),
        MealScheduleEntity(mealType = "Makan Malam", scheduledTime = "19:00", customMenuNotes = "Ikan nila bakar + Capcay kuah sayuran (sebelum jam 20:00)")
    )

    val defaultChecklistTitles = listOf(
        Pair("Minum 1 Gelas Air Sebelum Makan", "Membantu metabolisme dan mencegah makan berlebihan"),
        Pair("Porsi Sayur Minimal 50% Piring", "Kaya serat alami, vitamin, dan membuat kenyang lebih lama"),
        Pair("Cukupi Asupan Protein Tiap Jam Makan", "Mempertahankan massa otot dan mengoptimalkan pembakaran lemak"),
        Pair("Hindari Minuman Manis Berlebih", "Ganti teh/kopi manis dengan air mineral atau infused water"),
        Pair("Makan Malam Sebelum Pukul 20.00", "Memberikan jeda waktu pencernaan sebelum waktu tidur"),
        Pair("Kunyah Makanan Perlahan & Nikmati", "Membantu sinyal kenyang mencapai otak tepat waktu")
    )

    val nutritionTips = listOf(
        Pair("Prinsip Defisit Kalori", "Untuk menurunkan berat badan 0.5 kg per minggu, kurangi sekitar 300 - 500 kkal dari total kebutuhan harian (TDEE) Anda tanpa mengurangi asupan nutrisi esensial."),
        Pair("Kekuatan Hidrasi", "Minum air 500ml sebelum makan dapat meningkatkan laju metabolisme sebesar 24-30% selama 1-1.5 jam dan membantu mengontrol porsi makan."),
        Pair("Protein Sebagai Pembakar Alami", "Tubuh membakar hingga 20-30% kalori protein hanya untuk mencernanya (Thermic Effect of Food), tertinggi dibanding karbohidrat (5-10%) atau lemak (0-3%)."),
        Pair("Istirahat & Hormon Lapar", "Tidur cukup 7-8 jam per malam menyeimbangkan hormon ghrelin (pemicu lapar) dan leptin (penahan lapar), sehingga Anda tidak mudah 'ngidam' junk food."),
        Pair("Konsistensi vs Kesempurnaan", "Satu hari makan berlebih tidak merusak progres Anda, sebagaimana satu hari diet tidak langsung membuat kurus. Kuncinya adalah konsistensi jangka panjang!")
    )
}
