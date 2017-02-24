val ProjectName      = "functional"
val OrganisationName = "splatter"
val ProjectVersion  = "0.0.1"

val ScalaVersion     = "2.12.1"

def common: Seq[Setting[_]] = Seq(
  organization := OrganisationName
  , version      := ProjectVersion
  , scalaVersion := ScalaVersion
)

lazy val root: Project = (project in file("."))
  .settings( common: _* )
  .settings(
    name := ProjectName,
    libraryDependencies ++= Seq(
        "com.lihaoyi"    %% "fastparse"  % "0.4.2"
      , "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
      , "org.scalactic"  %% "scalactic"  % "3.0.1"  % "test"
      , "org.scalatest"  %% "scalatest"  % "3.0.1"  % "test"
    )
  )
