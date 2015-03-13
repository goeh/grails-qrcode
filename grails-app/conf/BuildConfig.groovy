grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6

grails.project.fork = false

grails.project.dependency.resolver = "maven"
grails.project.dependency.resolution = {
    inherits("global")

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        compile "ar.com.hjg:pngj:2.1.0"
        compile "com.google.zxing:core:3.2.0"
    }

    plugins {
        build ':release:3.0.1', {
            export = false
        }
    }
}