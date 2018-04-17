enablePlugins(JavaServerAppPackaging)
enablePlugins(ElasticBeanstalkPlugin)

lazy val project = Project("test-ebextensions", file("."))
  .settings(
    version := "0.7.0",
    elasticBeanstalkSources in ElasticBeanstalk ++= Seq(baseDirectory.value / ".ebextensions")
  )
