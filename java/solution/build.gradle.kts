plugins {
  kotlin ("jvm") version "1.4.31"
  id("io.vertx.vertx-plugin") version "1.2.0"
}

group = "cn.scdt"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenLocal()
  mavenCentral()
  jcenter()
}

dependencies {
  implementation("io.vertx:vertx-web")
  implementation("io.vertx:vertx-web-client")
  implementation("io.vertx:vertx-lang-kotlin-coroutines")
  implementation("io.vertx:vertx-lang-kotlin")
  implementation("io.vertx:vertx-redis-client")
  implementation(kotlin("stdlib-jdk8"))
  testImplementation("io.vertx:vertx-junit5")
}

tasks {
  compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.useIR = true
  }
  compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.useIR = true
  }
  test {
    useJUnitPlatform()
//    testLogging {
//      events = setOf(PASSED, SKIPPED, FAILED)
//      showExceptions = true
//    }
  }
}

vertx {
  mainVerticle = "cn.scdt.url.MainVerticle"
  vertxVersion = "4.0.3"
  config = "config.json"
}
