lazy val project = Project("test-dockerrun", file("."))
  .enablePlugins(JavaServerAppPackaging)
  .enablePlugins(ElasticBeanstalkPlugin)
  .settings(
    version := "0.7.0",
    elasticBeanstalkSources in ElasticBeanstalk ++= Seq(baseDirectory.value / "Dockerrun.aws.json")
  )
