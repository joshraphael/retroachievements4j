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
    testImplementation("com.squareup.okhttp3:mockwebserver:5.2.1")
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter:6.0.0")
    testImplementation("org.easymock:easymock:5.6.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.0")
    implementation("org.apache.httpcomponents.core5:httpcore5:5.3.6")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

publishing {
    repositories {
        maven("https://maven.pkg.github.com/joshraphael/retroachievements4j") {
            name = "githubPackages"
            // username and password (a personal Github access token) should be specified as
            // `githubPackagesUsername` and `githubPackagesPassword` Gradle properties or alternatively
            // as `ORG_GRADLE_PROJECT_githubPackagesUsername` and `ORG_GRADLE_PROJECT_githubPackagesPassword`
            // environment variables
            credentials(PasswordCredentials::class)
        }
    }
}

//if (signAllPublications == "false") {
//    publishing {
//        publications {
//            // Ensure at least one publication is defined here
//            create<MavenPublication>("github") {
//                groupId = projectGroup
//                artifactId = rootProject.name
//                version = projectVersion
//                from(components["java"])
//                pom {
//                    artifactId = project.name.lowercase()
//                    name.set(project.name)
//                    description.set(project.rootProject.file("README.md").readText())
//                    this.url.set("https://github.com/joshraphael/retroachievements4j")
//
//                    licenses {
//                        license {
//                            name.set("MIT")
//                            url.set("https://raw.githubusercontent.com/joshraphael/retroachievements4j/refs/heads/main/LICENSE")
//                        }
//                    }
//                    developers {
//                        developer {
//                            id.set("joshraphael")
//                            name.set("Joshua Raphael")
//                            url.set("https://github.com/joshraphael/")
//                        }
//                    }
//                    scm {
//                        url.set("https://github.com/joshraphael/retroachievements4j")
//                        connection.set("scm:git:git://github.com/joshraphael/retroachievements4j.git")
//                        developerConnection.set("scm:git:ssh://git@github.com/joshraphael/retroachievements4j.git")
//                    }
//                }
//            }
//        }
//        repositories {
//            maven("https://maven.pkg.github.com/joshraphael/retroachievements4j") {
//                name = "GitHubPackages"
//                credentials {
//                    username = System.getenv("GITHUB_ACTOR")
//                    password = System.getenv("GITHUB_TOKEN")
//                }
//            }
//        }
//    }
//}

//if (signAllPublications == "true") {
mavenPublishing {
    coordinates(projectGroup, project.name, projectVersion)

    pom {
        name.set(project.name)
        description.set(project.rootProject.file("README.md").readText())
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
                url.set("https://github.com/joshraphael/")
            }
        }
        scm {
            url.set("https://github.com/joshraphael/retroachievements4j")
            connection.set("scm:git:git://github.com/joshraphael/retroachievements4j.git")
            developerConnection.set("scm:git:ssh://git@github.com/joshraphael/retroachievements4j.git")
        }
    }
}
//}