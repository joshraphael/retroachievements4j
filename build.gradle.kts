plugins {
    id("java-library")
    id("maven-publish")
}

val version: String by project
val group: String by project
val artifact: String by project


repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.3.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        // Ensure at least one publication is defined here
        create<MavenPublication>("maven") {
            groupId = group
            artifactId = artifact
            version = version
            from(components["java"])
        }
    }
    repositories {
        maven("https://maven.pkg.github.com/joshraphael/retroachievements4j") {
            name = "GitHubPackages"
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}