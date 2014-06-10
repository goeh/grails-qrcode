grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target"
grails.project.target.level = 1.6

grails.project.dependency.resolution = {
    inherits("global") { }
    log "warn"
    repositories {
        grailsCentral()
        mavenCentral()
    }
    dependencies {
        compile "ar.com.hjg:pngj:2.1.0"
        compile "com.google.zxing:core:3.1.0"
    }
    plugins {
        build(":tomcat:$grailsVersion",
                ":release:2.2.1",
                ":rest-client-builder:1.0.3") {
            export = false
        }
        runtime(":hibernate:$grailsVersion") {
            export = false
        }
    }
}
