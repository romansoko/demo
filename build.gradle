buildscript {
    repositories {
        maven {
            url 'https://maven.google.com/'
            name 'Google'
        }
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:7.1.2'
        classpath 'com.google.gms:google-services:4.3.10'
        if (project.findProperty("sonar-test-module.enable") == "true") {
            classpath("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:4.0.0.2929")
        }
    }
}

apply plugin: 'java'

group 'com.example'
version '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'com.google.guava:guava:30.1-jre'
    testImplementation 'junit:junit:4.13.2'
    implementation 'org.springframework.boot:spring-boot-starter-web:2.5.4'
    implementation 'org.projectlombok:lombok:1.18.20'
    annotationProcessor 'org.projectlombok:lombok:1.18.20'
}


task customClean(type: Delete) {
    delete rootProject.buildDir
}
