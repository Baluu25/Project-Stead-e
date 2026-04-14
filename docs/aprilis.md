<img src="images/banner_placeholder.png" alt="STEAD-E Banner" width="50" style="display: block; margin: 0 auto;">

# Április

## Dátum: 2026.04.01–02.

## Elvégzett feladatok

### 1. Profile oldal hozzáadva

**Funkciók:**
- Profilkép feltöltése és eltávolítása előnézettel
- Személyes adatok: név, felhasználónév, email, nem, születési dátum, testsúly (kg), magasság (cm)
- Preferenciák: alvásidő, ébredési idő, fitnesz cél (fogyás, konzisztencia, szokás leadás, felfedezés)
- Forma validáció hibajelzéssel

### 2. Goals aloldal dinamikus adatkezelése elkezdve

- `goals` tábla migrációja elkészítve (title, description, icon, category, target_value, current_value, unit, deadline, status)
- `Goal` modell és `GoalController` alapstruktúrája megírva
- Dinamikus goal-kártya alap megjelenítése

---

## Dátum: 2026.04.03.

## Elvégzett feladatok

### 1. Felesleges tábla oszlopok törölve

Törölt funkciók és összes hozzájuk tartozó fájl (összesen 38 fájl):

| Törölt rész | Érintett fájlok |
|-------------|-----------------|
| Friends funkció | Controller, Model, Policy, Factory, Migration, Seeder |
| Reminders funkció | Controller, Model, Policy, Factory, Migration, Seeder |
| Challenges funkció | Controller, Model, Policy, Factory, Migration, Seeder |
| Notifications funkció | Controller, Model, Policy, Factory, Migration, Seeder |
| Felesleges migrációk | `create_cache_table`, `create_jobs_table` |

---

## Dátum: 2026.04.08.

## Elvégzett feladatok

### 1. Goals aloldal befejezve

**Megvalósított elemek:**
- Goal-kártya: cím, leírás, ikon, haladás (progress bar + százalék), határidő, státusz
- Dinamikus összesítő (összesen aktív / folyamatban / kész / nem kezdett el)
- Szűrő gombok: All, In Progress, Completed, Not Started
- Goal hozzáadása popup formmal (cím, leírás, kategória, célérték, mértékegység, határidő)
- Goal törlése megerősítéssel
- Progress manuális módosítása (+/− input)

---

## Dátum: 2026.04.09.

## Elvégzett feladatok

### 1. Home oldal dinamikussá tétele

- Napi szokások listája az aktuális naphoz szűrve
- Dátumnavigáció: ← Prev / Today / Next → gombok, 7-napos nézet
- `daily-progress-section`: napi teljesítési arány körös SVG progress ring-gel

### 2. Habit progress javítások

- Teljesített szokásoknál pipa badge, akciógombok elrejtve
- Nem teljesített szokásoknál +/− gombok valós idejű UI frissítéssel (oldal újratöltés nélkül)

### 3. Habit value input

- Számértékes input mező a szokás akciók mellett (min: 1)
- Mennyiség megadható a progress növeléséhez/csökkentéséhez

---

## Dátum: 2026.04.10.

## Elvégzett feladatok

### 1. Statistics aloldalon grafikon elkészült

- Vonaldiagram (Chart.js): az utolsó 7 nap napi teljesítési aránya
- Teljesítési adatok aggregálása a `habit_completions` tábla alapján

### 2. Home oldalon streak blokk kijavítva

- Folyamatos nap-sorozat (streak) helyes számítása
- Heti nézet: utolsó 7 nap körös jelölőkkel (teljesített / nem teljesített)
- Napfeliratok (H, K, Sze, Cs, P, Szo, V)

---

## Dátum: 2026.04.11.

## Elvégzett feladatok

### 1. CSS javítások és reszponzív design fejlesztés

- Dashboard oldalak layoutjának finomhangolása különböző képernyőméretekre
- Kártyák, gombok és form elemek egységesítése

### 2. Doughnut chart hozzáadva

- Kategóriánkénti teljesítés megjelenítése a statistics oldalon (Chart.js doughnut)
- Összesítő widgetek: összes szokás, aktív szokások, jelenlegi streak, legjobb streak

---

## Dátum: 2026.04.13.

## Elvégzett feladatok

### 1. Habit edit funkció befejezve

- Szerkesztési mód: form automatikusan feltöltődik a meglévő értékekkel
- Dinamikus form cím: „Add Habit" → „Edit Habit" szerkesztéskor
- Submit gomb szövege dinamikus: „Add Habit" / „Update Habit"
- Kategória-specifikus ikon-választó megőrzi a kiválasztott értéket

### 2. Goal progress actions módosítva

- Régi stepper helyett, home aloldalhoz hasonló -/+ gombok
- Állapotátmenet oldalújratöltés nélkül: `not-started` → `in-progress` → `completed`
- Goal progress actions elrejtve, ha a cél eléri a 100%-ot

---

## Dátum: 2026.04.14.

## Elvégzett feladatok

### 1. Goal–Habit összekötés implementálva

**Adatbázis:**
- `goal_id` nullable foreign key hozzáadva a `habits` táblához (`nullOnDelete`)

**Modellek:**
- `Habit` modell: `goal()` — `belongsTo(Goal::class)`
- `Goal` modell: `habits()` — `hasMany(Habit::class)`

**Funkció:**
- Habit létrehozásakor / szerkesztésekor kiválasztható a kapcsolódó goal (dropdown)
- Habit teljesítésekor automatikusan növekszik a kapcsolódó goal `current_value`-ja a megadott mennyiséggel
- Státusz-átmenet automatikus: `not-started` → `in-progress` → `completed` (ha `current_value >= target_value`)
- Habit lista megjeleníti a kapcsolódó goal nevét
