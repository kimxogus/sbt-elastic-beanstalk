import sbt._
import sbt.Keys._

sbtPlugin := true

name := "sbt-elastic-beanstalk"
organization := "io.xogus"
resolvers += Resolver.jcenterRepo

scalaVersion := "2.12.4"
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
