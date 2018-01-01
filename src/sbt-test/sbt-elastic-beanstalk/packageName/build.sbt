enablePlugins(JavaServerAppPackaging)
enablePlugins(ElasticBeanstalkPlugin)

name := "test-packageName"
version := "0.4.0"
packageName in ElasticBeanstalk := "package-name"
