plugins {
    id("java-library")
    id("maven-publish")
}

val projectVersion: String by project
val projectGroup: String by project

version = projectVersion
group = projectGroup


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
            groupId = projectGroup
            artifactId = rootProject.name
            version = projectVersion
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