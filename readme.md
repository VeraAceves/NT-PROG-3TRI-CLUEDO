# Cluedo Fantasy

Cluedo Fantasy es un videojuego tipo aventura/investigación desarrollado en JavaFX, donde el jugador debe resolver un asesinato dentro del Castillo de Valenwood interrogando personajes, analizando pistas y realizando acusaciones.

---

## Características principales

- Sistema de investigación con interrogatorios
- Sistema de pistas con penalización de puntos
- Diferentes niveles de dificultad
- Historia narrativa (lore) integrada
- Sistema de puntuación y rondas
- Final de partida con resultado (victoria o derrota)
- Persistencia de partidas y jugadores
- Interfaz gráfica desarrollada en JavaFX

---

## Mecánicas del juego

El jugador debe descubrir:
- El personaje asesino
- El arma utilizada
- El escenario del crimen

Durante la partida puede:
- Interrogar combinaciones de sospechas
- Solicitar pistas (con coste de puntos)
- Acusar directamente cuando esté seguro

---

## 🏗Arquitectura del proyecto

El proyecto sigue una estructura tipo MVC:

- `modelo.beans` → Entidades del juego (Partida, Jugador, Nivel, etc.)
- `modelo.lore` → Contenido narrativo del juego
- `modelo.factory` → Generación de datos base (personajes, armas, escenarios)
- `modelo.enums` → Enumeraciones (estado, dificultad, resultado)
- `controlador` → Controladores JavaFX (lógica de interfaz)
- `aplicacion` → Clase principal (Main)
- `Persistencia` → Acceso a datos (DAO)

---

## Tecnologías utilizadas

- Java 17+
- JavaFX
- Gradle
- MongoDB (persistencia de datos)
- SceneBuilder (interfaz gráfica)

---

## Ejecución del proyecto

### Requisitos
- JDK 17 o superior
- JavaFX configurado
- Gradle instalado (o wrapper incluido)
- MongoDB en ejecución (si aplica persistencia activa)

### Ejecutar con Gradle
```bash
./gradlew run