enablePlugins(JavaServerAppPackaging)
enablePlugins(ElasticBeanstalkPlugin)

name := "test-packageName"
version := "0.3.1"
packageName in ElasticBeanstalk := "package-name"
