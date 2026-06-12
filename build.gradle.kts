plugins {
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("eclipse")
}

group = "com.cluedofantasy"
version = "1.0"

javafx {
    version = "21"
    modules(
        "javafx.base",
        "javafx.graphics",
        "javafx.controls",
        "javafx.fxml"
    )
}

application {
    mainModule = "CluedoFantasy"
    mainClass = "aplicacion.Main"
}

repositories {
    mavenCentral()
}

dependencies {
    // MongoDB Driver (sincrónico, el más común para JavaFX)
    implementation("org.mongodb:mongodb-driver-sync:5.7.0")
    
    // (Opcional) Para logs de MongoDB - recomiendo añadirlo
    implementation("org.slf4j:slf4j-simple:2.0.16")
}
