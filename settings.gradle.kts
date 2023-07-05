rootProject.name = "nisum-integracion-bci"

include("application")
include("domain")

include(":rest-api")
project(":rest-api").projectDir = file("./infrastructure/rest-api")

include(":jpa-repository")
findProject(":jpa-repository")?.projectDir = file("./infrastructure/jpa-repository")

include(":jwt-service")
findProject(":jwt-service")?.projectDir = file("./infrastructure/jwt-service")
