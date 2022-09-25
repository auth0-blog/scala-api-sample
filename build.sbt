name := """scala-api-sample"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.8"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test

val jwtScalaVersion = "9.1.1"
libraryDependencies ++= Seq(
  "com.github.jwt-scala" %% "jwt-play" % jwtScalaVersion,
  "com.github.jwt-scala" %% "jwt-core" % jwtScalaVersion,
  "com.auth0" % "jwks-rsa" % "0.21.2"
)

// Workaround for https://github.com/jwt-scala/jwt-scala/issues/403
dependencyOverrides += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.11.4"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.11.1"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
