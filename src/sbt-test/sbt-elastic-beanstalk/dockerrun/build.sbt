enablePlugins(JavaServerAppPackaging)
enablePlugins(ElasticBeanstalkPlugin)

name := "test-dockerrun"
version := "0.4.0"
elasticBeanstalkSources ++= Seq(baseDirectory.value / "Dockerrun.aws.json")
