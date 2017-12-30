import sbt._
import sbt.Keys._

sbtPlugin := true

name := "sbt-elastic-beanstalk"
organization := "io.xogus"
resolvers += Resolver.jcenterRepo

crossSbtVersions := Seq("0.13.16", "1.0.4")
val sbtCrossVersion = sbtVersion in pluginCrossBuild
scalaVersion := (CrossVersion partialVersion sbtCrossVersion.value match {
  case Some((0, 13)) => "2.10.6"
  case Some((1, _))  => "2.12.4"
  case _             => sys error s"Unhandled sbt version ${sbtCrossVersion.value}"
})
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")
scalacOptions := Seq("-deprecation", "-unchecked", "-feature")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.2")
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)

licenses += ("Apache-2.0", url("https://github.com/kimxogus/sbt-elastic-beanstalk/blob/master/LICENSE"))
homepage := Some(url("https://github.com/kimxogus/sbt-elastic-beanstalk"))
scmInfo := Some(ScmInfo(url("https://github.com/kimxogus/sbt-elastic-beanstalk"), "scm:git:git://github.com:kimxogus/sbt-elastic-beanstalk.git"))

git.remoteRepo := "git@github.com:kimxogus/sbt-elastic-beanstalk.git"

// Scripted for testing
scriptedLaunchOpts := { scriptedLaunchOpts.value ++
  Seq("-Dplugin.version=" + version.value)
}
scriptedBufferLog := false
