enablePlugins(JavaServerAppPackaging)
enablePlugins(ElasticBeanstalkPlugin)

name := "test-ebextensions"
version := "0.4.0"
elasticBeanstalkSources ++= Seq(baseDirectory.value / ".ebextensions")
