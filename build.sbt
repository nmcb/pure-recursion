val ProjectName      = "pure-recursion"
val OrganisationName = "nmcb"
val ProjectVersion   = "0.1.0"
val ScalaVersion     = "3.8.4"

ThisBuild / scalacOptions ++= Seq(
  "-encoding", "utf8",
  "-feature",
  "-language:implicitConversions",
  "-language:existentials",
  "-unchecked",
  "-Werror",
  "-deprecation"
)

def common: Seq[Setting[_]] =
  Seq( organization := OrganisationName
     , version      := ProjectVersion
     , scalaVersion := ScalaVersion
     )

lazy val recursion: Project =
  (project in file("."))
    .settings( common: _* )
    .settings( name := ProjectName
             , libraryDependencies ++= Seq(
               "org.scalactic"  %% "scalactic"  % "3.2.20"  % "test"
             , "org.scalatest"  %% "scalatest"  % "3.2.20"  % "test"
             ))
