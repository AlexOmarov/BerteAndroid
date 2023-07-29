import java.io.StringReader

plugins {
    id("jacoco")
    alias(libs.plugins.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.sonarqube)
}

android {
    namespace = "com.example.berteandroid"
    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        applicationId = "com.example.berteandroid"
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.compile.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    // Some library (room / koin) has this transitive dep. We already have all the classes in android annotations lib
    configurations {
        implementation.get().exclude(mapOf("group" to "org.jetbrains", "module" to "annotations"))
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    jvmToolchain(11)
}

dependencies {
    annotationProcessor(libs.room.compiler)

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.compose.livedata)
    implementation(libs.compose.material)
    implementation(libs.room.runtime)
    implementation(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.converter.scalar)
    implementation(libs.retrofit)

    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.koin.compose)
    implementation(libs.koin)
    implementation(libs.work.ktx)


    ksp(libs.room.compiler)

    debugImplementation(libs.compose.ui.tooling)

    testImplementation(libs.junit)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.mockito.kotlin)

    androidTestImplementation(libs.androidx.espresso)
    androidTestImplementation(libs.compose.ui.test)
    androidTestImplementation(libs.compose.ui.tooling)
    androidTestImplementation(libs.compose.ui.test.manifest)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.androidx.work.testing)

    /*constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.0") {
            because("kotlin-stdlib-jdk7 is now a part of kotlin-stdlib")
        }
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0") {
            because("kotlin-stdlib-jdk8 is now a part of kotlin-stdlib")
        }
    }*/
}

var exclusions = project.properties["test_exclusions"].toString()

jacoco {

}

sonarqube {
    properties {
        property("sonar.projectKey", "AlexOmarov_BerteAndroid")
        property("sonar.organization", "alexomarov")
        property("sonar.qualitygate.wait", "true")
        property("sonar.core.codeCoveragePlugin", "jacoco")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "${project.buildDir}/reports/jacoco/test/jacocoTestReport.xml"
        )
        property("sonar.cpd.exclusions", exclusions)
        property("sonar.jacoco.excludes", exclusions)
        property("sonar.coverage.exclusions", exclusions)
    }
}

tasks.withType<Test> {
    testLogging {
        events = setOf(
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
        )
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
    finalizedBy("createDebugUnitTestCoverageReport") // Launch JaCoCo coverage verification
}

// Configure generated JaCoCo report
tasks.withType<JacocoReport> {
    reports {
        xml.required.set(true)
    }
    dependsOn("test")

    classDirectories.setFrom(
        files(
            classDirectories.files.map {
                fileTree(it) {
                    exclude(
                        exclusions.split(",")
                    )
                }
            }
        )
    )
    finalizedBy("coverage")
}

// Print total coverage to console
tasks.register("coverage") {
    doLast {
        val testReportFile =
            project.file("${project.buildDir}/reports/jacoco/test/jacocoTestReport.xml")
        if (testReportFile.exists()) {
            val str: String = testReportFile.readText().replace("<!DOCTYPE[^>]*>".toRegex(), "")
            val rootNode =
                javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(org.xml.sax.InputSource(StringReader(str)))
            var totalCovered = 0
            var totalMissed = 0

            val counters: org.w3c.dom.NodeList =
                javax.xml.xpath.XPathFactory.newInstance().newXPath().compile("//counter")
                    .evaluate(
                        rootNode,
                        javax.xml.xpath.XPathConstants.NODESET
                    ) as org.w3c.dom.NodeList

            for (i in 0 until counters.length) {
                try {
                    totalCovered += Integer.valueOf(counters.item(i).attributes.getNamedItem("covered").textContent)
                    totalMissed += Integer.valueOf(counters.item(i).attributes.getNamedItem("missed").textContent)
                } catch (ignored: Exception) {
                }
            }

            // Test coverage parsing regex: Total:\s[\d\.\,]+%
            String.format("%.2f", 100.0 * totalCovered / (totalMissed + totalCovered))
            println(
                "Coverage Total: ${
                    String.format(
                        "%.2f",
                        100.0 * totalCovered / (totalMissed + totalCovered)
                    )
                }%"
            )
        }
    }
}
