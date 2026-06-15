# Cluedo Fantasy

Cluedo Fantasy es un videojuego tipo aventura/investigación desarrollado en JavaFX, donde el jugador debe resolver un asesinato dentro del Castillo de Valenwood interrogando personajes, analizando pistas y realizando acusaciones.
Repositorio en github: https://github.com/VeraAceves/NT-PROG-3TRI-CLUEDO
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
## Tabla de pruebas
| ID   | Módulo       | Caso de prueba           | Entrada                                       | Resultado esperado                             | Resultado obtenido |
| ---- | ------------ | ------------------------ | --------------------------------------------- | ---------------------------------------------- | ------------------ |
| TP01 | Inicio       | Crear nueva partida      | Nombre de jugador válido + nivel seleccionado | Se crea partida y se abre la pantalla de juego | Correcto           | 
| TP02 | Inicio       | Nombre vacío             | "" (vacío)                                    | Mensaje de error “Debes introducir un nombre”  | Correcto           | 
| TP03 | Inicio       | Nombre inválido          | "   "                                         | Mensaje de error de validación                 | Correcto           | 
| TP04 | Partida      | Interrogar sin selección | null/null/null                                | No permite acción                              | Correcto           | 
| TP05 | Partida      | Interrogar correcto      | Personaje + arma + escenario                  | Avanza ronda y actualiza puntos                | Correcto           |
| TP06 | Partida      | Interrogar incorrecto    | combinación incorrecta                        | Resta puntos y avanza ronda                    | Correcto           | 
| TP07 | Partida      | Solicitar pista          | índice válido                                 | Muestra pista y resta puntos                   | Correcto           | 
| TP08 | Partida      | Solicitar pista repetida | misma pista dos veces                         | No permite repetir pista                       | Correcto           | 
| TP09 | Partida      | Acusar correcto          | combinación correcta                          | Finaliza partida con VICTORIA                  | Correcto           | 
| TP10 | Partida      | Acusar incorrecto        | combinación incorrecta                        | Finaliza partida con DERROTA                   | Correcto           | 
| TP11 | Persistencia | Guardar partida          | partida en curso                              | Se guarda en base de datos                     | Correcto           | 
| TP12 | Persistencia | Cargar partida           | última partida guardada                       | Se carga correctamente                         | Correcto           |
| TP13 | UI           | Cambiar escena           | navegación menú → partida                     | Cambia escena sin errores                      | Correcto           | 
| TP14 | Final        | Mostrar resultado        | partida finalizada                            | Muestra victoria/derrota                       | Correcto           | 

### Requisitos
- JDK 17 o superior
- JavaFX configurado
- Gradle instalado (o wrapper incluido)
- MongoDB en ejecución (si aplica persistencia activa)

### Ejecutar con Gradle
```bash
./gradlew run
