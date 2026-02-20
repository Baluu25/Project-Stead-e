![STEAD-E Banner](images/banner_placeholder.png)
# STEAD-E – Szokás Követő


## 📖 Bevezetés és Projekt vízió
A **STEAD-E** egy modern, platformfüggetlen egészségfigyelő alkalmazás. A projekt alapötletét a *Wall-E* című film disztópikus jövőképe inspirálta: célunk megelőzni, hogy az emberek a technológia kényelme miatt elveszítsék fizikai fittségüket.

A piaci standardokkal ellentétben (pl. Samsung Health) a STEAD-E nem a versenysportra és a teljesítménykényszerre fókuszál, hanem **motivációs társként** segíti a felhasználót a fenntartható, egészséges szokások kialakításában.

---

## 🚀 Főbb funkciók
A rendszer kliens-szerver architektúrában működik, az alábbi szolgáltatásokat nyújtva:

* **🔐 Felhasználókezelés:** Biztonságos regisztráció, bejelentkezés és profilkezelés (célok, testsúly, magasság).
* **📊 Aktivitáskövetés (Habit Tracking):**
    * Szokások felvétele (pl. "Napi 2L víz", "30 perc séta").
    * Napi teljesítések naplózása.
    * Ciklusnaptár és hangulatkövetés.
* **📈 Statisztika:** Grafikus visszajelzés a fejlődésről (heti/havi nézet).
* **🏆 Gamification:** Kihívások (Challenges) és Barátok (Friends) kezelése a motiváció fenntartásához.
* **🔔 Értesítések:** Emlékeztetők küldése (pl. folyadékbevitel).

---

## 🛠 Technológiai Stack

A fejlesztés során a **Tiszta Kód (Clean Code)** elveit és a **RESTful** architektúrát követtük.

### Backend (Szerver oldal)
* **Nyelv:** PHP 8.2+
* **Keretrendszer:** **Laravel 10**
* **Adatbázis:** MySQL 8.0
* **API:** RESTful JSON válaszokkal

### Kliens oldali alkalmazások
1.  **Mobil App:**
    * **Platform:** Android (Natív)
    * **Nyelv:** **Kotlin**
    * **Kommunikáció:** Retrofit (HTTP kliens)
2.  **Webes Felület:**
    * **Tech:** Laravel Blade + Bootstrap 
    * **Cél:** Adminisztráció és asztali felhasználás.

---

## 🗄 Adatbázis Modell
A rendszer relációs adatbázist használ. A főbb táblák és kapcsolataik:

![Adatbázis Diagram](images/database_diagram.jpg)
*(A diagram a `docs/images` mappában található)*

**Főbb táblák:**
* `users`: Felhasználói adatok, jelszó hash, streak számlálók.
* `habits`: A felvett szokások definíciói.
* `habit_completions`: Naplózott tevékenységek (napló).
* `challenges`: Közösségi kihívások.
* `friends`: Barátkapcsolatok kezelése.

---

## 🔌 API Dokumentáció (Végpontok)
A backend és a kliensek közötti kommunikáció legfontosabb végpontjai:

| Metódus | Végpont | Leírás |
| :--- | :--- | :--- |
| **Auth** | | |
| `POST` | `/api/register` | Új felhasználó regisztrációja |
| `POST` | `/api/login` | Bejelentkezés (Bearer token igénylés) |
| **Habits** | | |
| `GET` | `/api/habits` | A felhasználó aktív szokásainak listázása |
| `POST` | `/api/habits` | Új szokás létrehozása |
| `POST` | `/api/habits/{id}/log` | Szokás teljesítése (pl. "Ittam egy pohár vizet") |
| **Social** | | |
| `GET` | `/api/friends` | Barátok listázása |
| `GET` | `/api/challenges` | Elérhető kihívások lekérdezése |

---

## 👥 Csapat és Munkamegosztás

A projektet 3 fős fejlesztői csapat valósította meg agilis módszertannal.

| Tag | Szerepkör | Felelősségi körök |
| :--- | :--- | :--- |
| **Tavas Tamara** | **** | UI/UX tervek, Dokumentáció. |
| **Dudás Balázs** | **** | Laravel API fejlesztés, Adatbázis tervezés, Webes frontend. |
| **Kaba Nóra Rebeka** | **** | Android (Kotlin) applikáció fejlesztés, API integráció. |

**Használt eszközök:**
* GitHub (Verziókezelés)
* Trello (Feladatkezelés)
* Discord (Kommunikáció)
* Postman (API tesztelés)

---

## 💻 Telepítési Útmutató

### Előfeltételek
* PHP 8.2, Composer
* MySQL szerver
* Android Studio (a mobil apphoz)

### Backend (Laravel) indítása
1.  Klónozd a repót:
    ```bash
    git clone [https://github.com/user/stead-e.git](https://github.com/user/stead-e.git)
    ```
2.  Lépj a könyvtárba és telepítsd a függőségeket:
    ```bash
    cd stead-e-backend
    composer install
    ```
3.  Környezeti változók beállítása:
    * Másold át a `.env.example` fájlt `.env` néven.
    * Állítsd be a `DB_DATABASE`, `DB_USERNAME`, `DB_PASSWORD` értékeket.
4.  Kulcs generálás és migráció:
    ```bash
    php artisan key:generate
    php artisan migrate
    ```
5.  Szerver indítása:
    ```bash
    php artisan serve
    ```
    *A szerver elérhető: `http://localhost:8000`*

### Mobil App (Android)
---
*A projekt a szoftverfejlesztő szakmai vizsga követelményeinek megfelelően készült. 2026.*