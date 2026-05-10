# 📱 Lab 21 — Capteurs Embarqués Android (Sensor Lab)

Application Android pédagogique permettant d'exploiter les capteurs embarqués d'un smartphone : liste des capteurs, visualisation graphique des mesures, accéléromètre, gyroscope, boussole, compteur de pas et reconnaissance d'activité.

---

## 🎯 Objectifs pédagogiques

À la fin de ce lab, l'étudiant est capable de :

- Comprendre le rôle de `SensorManager` dans Android
- Lister les capteurs disponibles dans un dispositif
- Lire les propriétés techniques d'un capteur (résolution, énergie, plage, délai)
- Exploiter `SensorEventListener` pour recevoir des mesures en temps réel
- Afficher l'évolution d'un capteur sous forme de graphe
- Utiliser l'accéléromètre, le gyroscope, le magnétomètre, le capteur de proximité et le compteur de pas
- Mettre en place une logique simple de reconnaissance d'activité

---

## 🛠️ Environnement technique

| Composant | Version |
|---|---|
| Language | Java |
| Android Gradle Plugin (AGP) | 8.3.2 |
| Gradle | 8.6 |
| Java JDK | 21 |
| compileSdk | 34 (Android 14) |
| minSdk | 26 (Android 8.0) |
| targetSdk | 34 |

---

## 📁 Structure du projet

```
app/src/main/java/com/example/sensors/
│
├── MainActivity.java                        ← Activité principale, gestion du menu
│
├── fragments/
│   ├── SensorsListFragment.java             ← Liste tous les capteurs disponibles
│   ├── SensorGraphFragment.java             ← Graphe générique (température, humidité, etc.)
│   ├── MotionSensorFragment.java            ← Accéléromètre, gravité, gyroscope
│   ├── StepCounterFragment.java             ← Compteur de pas
│   ├── CompassFragment.java                 ← Boussole numérique
│   └── ActivityRecognitionFragment.java     ← Reconnaissance d'activité
│
├── utils/
│   └── SensorFormatter.java                 ← Formate les données d'un capteur en texte
│
└── views/
    └── LineChartView.java                   ← Vue graphique personnalisée (courbe temps réel)

app/src/main/res/
├── layout/
│   └── activity_main.xml                    ← Conteneur fragment (FrameLayout)
├── menu/
│   └── main_menu.xml                        ← Menu avec 11 entrées capteurs
└── values/
    └── strings.xml
```

---

## 🚀 Installation et lancement

### Prérequis
- **Android Studio** Meerkat / Panda (2024.3+)
- **JDK 21** (inclus avec Android Studio)
- Téléphone Android (API 26+) **ou** émulateur AVD

### Étapes

1. **Ouvrir le projet dans Android Studio**
   ```
   File → Open → Sélectionner le dossier Desktop\0
   ```

2. **Attendre la synchronisation Gradle** (2-3 minutes la première fois)

3. **Lancer l'application**
   - Branchez votre téléphone **ou** démarrez un émulateur AVD
   - Cliquez sur le bouton ▶ **Run**

4. **Accéder au menu**
   - Cliquez sur les **3 points ⋮** en haut à droite de l'écran

---

## 📋 Fonctionnalités détaillées

### 1. 📊 Liste des capteurs (`SensorsListFragment`)
Affiche tous les capteurs disponibles sur l'appareil avec leurs propriétés :

| Propriété | Description |
|---|---|
| Id | Identifiant unique du capteur |
| Name | Nom du capteur |
| Vendor | Fabricant |
| Version | Version du capteur |
| Type | Type textuel |
| Int Type | Type entier (identifiant Android) |
| Resolution | Précision de mesure |
| Power | Consommation en milliampères (mA) |
| Maximum Range | Valeur maximale mesurable |
| Min Delay | Délai minimal entre deux mesures (µs) |

---

### 2. 🌡️ Température ambiante (`SensorGraphFragment`)
- Type : `Sensor.TYPE_AMBIENT_TEMPERATURE`
- Affiche la température en °C
- **Simulation automatique** si le capteur est absent (émulateur)
- Courbe animée en temps réel

---

### 3. 💧 Humidité relative (`SensorGraphFragment`)
- Type : `Sensor.TYPE_RELATIVE_HUMIDITY`
- Affiche le taux d'humidité en %
- **Simulation automatique** si le capteur est absent

---

### 4. 📡 Capteur de proximité (`SensorGraphFragment`)
- Type : `Sensor.TYPE_PROXIMITY`
- Valeur faible (0) = objet proche
- Valeur haute (5 cm typique) = aucun objet

---

### 5. 🧲 Champ magnétique (`SensorGraphFragment`)
- Type : `Sensor.TYPE_MAGNETIC_FIELD`
- Mode `MAGNITUDE` : affiche la norme du vecteur
  ```
  magnitude = √(x² + y² + z²)
  ```
- Varie selon l'orientation du téléphone et les objets métalliques proches

---

### 6. ⚡ Accéléromètre (`MotionSensorFragment`)
- Type : `Sensor.TYPE_ACCELEROMETER`
- Mesure l'accélération **incluant la gravité** (≈ 9.81 m/s² au repos)
- Affiche les axes X, Y, Z et la norme

---

### 7. 🌍 Capteur de gravité (`MotionSensorFragment`)
- Type : `Sensor.TYPE_GRAVITY`
- Isole uniquement la composante gravitationnelle
- Comparer avec l'accéléromètre pour voir la différence

---

### 8. 🔄 Gyroscope (`MotionSensorFragment`)
- Type : `Sensor.TYPE_GYROSCOPE`
- Mesure le taux de rotation en **radians/seconde**
- Affiche la rotation sur les axes X, Y, Z

---

### 9. 👣 Compteur de pas (`StepCounterFragment`)
- Type : `Sensor.TYPE_STEP_COUNTER`
- Affiche :
  - Pas depuis le **dernier redémarrage**
  - Pas de la **session actuelle**
- **Permission requise** : `ACTIVITY_RECOGNITION` (Android 10+)

---

### 10. 🧭 Boussole numérique (`CompassFragment`)
- Combine **accéléromètre** + **magnétomètre**
- Calcule la matrice de rotation via `SensorManager.getRotationMatrix()`
- Extrait l'azimut en degrés (0° = Nord)
- Affiche : Nord, Nord-Est, Est, Sud-Est, Sud, Sud-Ouest, Ouest, Nord-Ouest

---

### 11. 🏃 Reconnaissance d'activité (`ActivityRecognitionFragment`)
- Utilise l'accéléromètre avec un **filtre passe-bas** pour isoler la gravité
- Analyse une fenêtre glissante de **30 mesures**
- Détecte :

| Activité | Condition |
|---|---|
| 🦘 Saut | pic d'accélération > 10 m/s² |
| 🚶 Marche | écart-type > 1.2 m/s² |
| 📱 Stable / téléphone à plat | Z > 8 m/s² |
| 🧍 Assis ou debout | X ou Y > 7 m/s² |
| ⏸ Position stable | aucune des conditions précédentes |

**Principe du filtre passe-bas :**
```java
gravity[i] = ALPHA * gravity[i] + (1 - ALPHA) * acceleration[i]
linearAcceleration[i] = acceleration[i] - gravity[i]
```
Avec `ALPHA = 0.8` (80% ancien, 20% nouveau)

---

## 🧪 Tests avec l'émulateur Android

L'émulateur Android Studio intègre des **capteurs virtuels** :

1. Lancez l'émulateur
2. Cliquez sur les `...` (Extended Controls) dans la barre latérale
3. Allez dans l'onglet **"Virtual sensors"**
4. Modifiez :
   - **Accelerometer** : incliner le téléphone simulé
   - **Temperature** : changer la valeur manuellement
   - **Magnetic field** : simuler un champ magnétique

> ⚠️ Les capteurs **humidité** et **température** ne sont pas tous présents sur émulateur. La **simulation automatique** s'active alors.

---

## 🔐 Permissions

```xml
<!-- AndroidManifest.xml -->
<uses-permission android:name="android.permission.ACTIVITY_RECOGNITION" />
```

> Requise uniquement pour le **Compteur de pas** sur Android 10 (API 29) et supérieur. L'application demande automatiquement la permission au premier lancement.

---

## ⚠️ Résolution des erreurs courantes

### Erreur : `Cannot mutate dependencies after resolution`
**Cause** : Incompatibilité AGP/Gradle  
**Solution** : Utiliser AGP `8.3.2` + Gradle `8.6`

### Erreur : `Incompatible Java 21 and Gradle X.X`
**Cause** : Gradle < 8.5 ne supporte pas Java 21  
**Solution** : Mettre Gradle `8.6` dans `gradle-wrapper.properties`

### Capteur absent sur émulateur
**Comportement** : Le message "Simulation activée" s'affiche  
**Solution** : Utiliser l'onglet Virtual sensors de l'AVD Extended Controls

---
