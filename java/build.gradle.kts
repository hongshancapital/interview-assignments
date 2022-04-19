import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    id("me.champeau.jmh") version "0.6.6"
    id("jacoco")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    maven { url = URI("https://maven.aliyun.com/repository/public/") }
    maven { url = URI("https://maven.aliyun.com/repository/spring/") }
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.h2database:h2")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("junit:junit:4.13.1")
    implementation("com.google.guava:guava:31.1-jre")

    compileOnly("org.springframework.boot:spring-boot-starter-json")
    implementation("org.jacoco:org.jacoco.core:0.8.8")
    implementation("commons-validator:commons-validator:1.7")
    implementation("commons-lang:commons-lang:2.6")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.openjdk.jmh:jmh-core:1.35")
    jmh("org.openjdk.jmh:jmh-core:1.35")
    jmh("org.openjdk.jmh:jmh-generator-annprocess:1.35")
//    testAnnotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.33")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JacocoReport> {
    reports {
        classDirectories.setFrom(
            sourceSets.main.get().output.asFileTree.matching {
                exclude("org/example/B.class")
            }
        )
    }
}

tasks.test {
    finalizedBy("jacocoTestReport")
    doLast {
        println("View code coverage at:")
        println("file://$buildDir/reports/jacoco/test/html/index.html")
    }
}

