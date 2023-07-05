plugins {
    id("java")
    id("io.spring.dependency-management") version "1.1.0"
    id("org.springframework.boot") version "3.1.1" apply false
    id("jacoco")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "jacoco")
    apply(plugin = "io.spring.dependency-management")

    java.sourceCompatibility = JavaVersion.VERSION_17
    java.targetCompatibility = JavaVersion.VERSION_17

    dependencies {
        implementation("org.mapstruct:mapstruct:1.5.5.Final")
        implementation("org.projectlombok:lombok")
        implementation("com.github.javafaker:javafaker:1.0.2")

        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        annotationProcessor("org.projectlombok:lombok")
        annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")


        testImplementation(platform("org.junit:junit-bom:5.9.1"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testImplementation("org.mockito:mockito-junit-jupiter")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:${property("springBootVersion")}")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
    tasks.test {
        finalizedBy(tasks.jacocoTestReport)
    }

    tasks.jacocoTestReport {
        dependsOn(tasks.test)
        reports {
            xml.required.set(true)
            xml.outputLocation.set(file("$buildDir/reports/jacoco.xml"))
            csv.required.set(false)
            html.outputLocation.set(file("$buildDir/reports/jacocoHtml"))
        }
    }

    tasks.getByName<Jar>("jar") {
        enabled = true
    }
}

configure(project.subprojects.filter { it != project(":domain") }) {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jacoco {
    toolVersion = "0.8.8"
    reportsDirectory.set(file("$buildDir/reports"))
}

tasks.register<JacocoReport>("jacocoMergedReport") {
    dependsOn(subprojects.map { project -> project.tasks.jacocoTestReport })
    additionalSourceDirs.setFrom(files(subprojects.map { project -> project.sourceSets.main.get().allSource.srcDirs }))
    sourceDirectories.setFrom(files(subprojects.map { project -> project.sourceSets.main.get().allSource.srcDirs }))
    classDirectories.setFrom(files(subprojects.map { project -> project.sourceSets.main.get().output }))
    executionData.setFrom(
        project.fileTree(project.buildDir) {
            include("**/build/jacoco/test.exec")
        }
    )
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
}