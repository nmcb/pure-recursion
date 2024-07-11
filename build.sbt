val ProjectName      = "pure-recursion"
val OrganisationName = "nmcb"
val ProjectVersion   = "0.1.0"
val ScalaVersion     = "2.13.14"

scalacOptions in ThisBuild ++= Seq(
  "-unchecked",
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
               "org.scalactic"  %% "scalactic"  % "3.2.19"  % "test"
             , "org.scalatest"  %% "scalatest"  % "3.2.19"  % "test"
             ))
