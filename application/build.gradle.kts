apply(plugin = "org.springframework.boot")

dependencies {
    implementation(project(":domain"))
    implementation(project(":jpa-repository"))
    implementation(project(":rest-api"))
    implementation(project(":jwt-service"))

    compileOnly("org.springframework.boot:spring-boot-devtools")
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    this.archiveFileName.set("${project.parent?.name}.${archiveExtension.get()}")
    this.enabled = true
}

tasks.getByName<Jar>("jar") {
    enabled = false
}
