enablePlugins(JavaServerAppPackaging)
enablePlugins(ElasticBeanstalkPlugin)

name := "test-packageName"
version := "0.7.0"
packageName in ElasticBeanstalk := "package-name"
