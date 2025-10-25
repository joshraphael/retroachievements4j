import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar

plugins {
    id("java-library")
    id("jacoco")
    id("com.vanniktech.maven.publish") version "0.34.0"
}

val projectVersion: String by project
val projectGroup: String by project

version = projectVersion
group = projectGroup


repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("com.squareup.okhttp3:mockwebserver:5.2.1")
    testImplementation("org.junit.jupiter:junit-jupiter:6.0.0")
    testImplementation("org.easymock:easymock:5.6.0")
    testImplementation("org.slf4j:slf4j-simple:2.0.17")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.0")
    implementation("org.apache.httpcomponents.core5:httpcore5:5.3.6")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.5.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

publishing {
    repositories {
        maven("https://maven.pkg.github.com/joshraphael/retroachievements4j") {
            name = "githubPackages"
            credentials(PasswordCredentials::class)
        }
    }
}

mavenPublishing {
    configure(JavaLibrary(javadocJar = JavadocJar.Javadoc(), sourcesJar = true))
    coordinates(projectGroup, project.name, projectVersion)

    pom {
        name.set(project.name)
        description.set("A Java library that lets you get achievement, user, game data and more from RetroAchievements.")
        inceptionYear.set("2025")
        url.set("https://github.com/joshraphael/retroachievements4j")
        licenses {
            license {
                name.set("MIT")
                url.set("https://raw.githubusercontent.com/joshraphael/retroachievements4j/refs/heads/main/LICENSE")
            }
        }
        developers {
            developer {
                id.set("joshraphael")
                name.set("Joshua Raphael")
                url.set("https://github.com/joshraphael")
            }
        }
        scm {
            url.set("https://github.com/joshraphael/retroachievements4j")
            connection.set("scm:git:git://github.com/joshraphael/retroachievements4j.git")
            developerConnection.set("scm:git:ssh://git@github.com/joshraphael/retroachievements4j.git")
        }
    }
}