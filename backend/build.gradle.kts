plugins {
	java
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "dev.visie"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring Boot dependencies
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-websocket")

	// STOMP and WebSocket client libraries
	implementation("org.webjars:stomp-websocket:2.3.3")
	implementation("org.webjars:sockjs-client:1.0.2")

	// OpenAPI / Swagger for API documentation
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

	// Model Mapper for object mapping
	implementation("org.modelmapper:modelmapper:2.4.4")

	// Database and persistence
	implementation("org.postgresql:postgresql")
	implementation("mysql:mysql-connector-java:8.0.33")

	// JWT for token handling
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-gson:0.11.5")

	// Lombok for code generation
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// In-memory database for testing
	testImplementation("com.h2database:h2")

	// Testing dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
	systemProperty("spring.profiles.active", "test")

	useJUnitPlatform()

	reports {
		junitXml.required.set(true)
		html.required.set(true)
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
