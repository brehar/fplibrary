ThisBuild / name := "monads"

ThisBuild / version := "0.0.1"

ThisBuild / scalaVersion := "2.12.8"

ThisBuild / scalacOptions ++= Seq(
  "-encoding", "utf8",
  "-Xfatal-warnings",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-language:postfixOps",
  "-language:implicitConversions",
  "-language:higherKinds"
)

ThisBuild / triggeredMessage := Watched.clearWhenTriggered

ThisBuild / autoStartServer := false

ThisBuild / scalafmtOnCompile := true

ThisBuild / shellPrompt := (_ => fancyPrompt(name.value))

def fancyPrompt(projectName: String): String =
  s"""|
      |[info] Welcome to the ${cyan(projectName)} project!
      |sbt> """.stripMargin

def cyan(projectName: String): String = scala.Console.CYAN + projectName + scala.Console.RESET

ThisBuild / libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test

lazy val fplibrary =
  project.in(file("./fplibrary")).settings(shellPrompt := (_ => fancyPrompt(name.value)))

lazy val application = project
  .in(file("./application"))
  .settings(shellPrompt := (_ => fancyPrompt(name.value)))
  .dependsOn(fplibrary)

lazy val main = project
  .in(file("./main"))
  .settings(shellPrompt := (_ => fancyPrompt(name.value)))
  .dependsOn(application)

addCommandAlias("cd", "project")

addCommandAlias("root", "project monads")

addCommandAlias("lib", "project fplibrary")

addCommandAlias("app", "project application")

addCommandAlias("main", "project main")
