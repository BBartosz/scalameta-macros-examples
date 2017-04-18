name := "scalameta-macros-examples"

lazy val commonSettings = Seq(
  version := "1.0",
  scalaVersion := "2.11.10",
  scalacOptions += "-Xplugin-require:macroparadise",
  resolvers += Resolver.bintrayIvyRepo("scalameta", "maven"),
  addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M8" cross CrossVersion.full)
)

lazy val root = (project in file("."))
  .settings(commonSettings:_*)
  .dependsOn(macros)

lazy val macros = (project in file("macros"))
  .settings(commonSettings:_*)
  .settings(
    libraryDependencies += "org.scalameta" %% "scalameta" % "1.7.0"
  )