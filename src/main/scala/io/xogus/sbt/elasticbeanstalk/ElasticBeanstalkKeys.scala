package io.xogus.sbt.elasticbeanstalk

import sbt._

trait ElasticBeanstalkKeys {
  val elasticBeanstalkPackageMappings = TaskKey[Seq[(File, String)]]("elastic-beanstalk-package-mappings", "Generates location mappings for Elastic Beanstalk build.")
  val elasticBeanstalkSources = settingKey[Seq[File]]("sources to be packaged")
  val elasticBeanstalkSourceMappings = settingKey[Seq[(File, String)]]("")
}

object ElasticBeanstalkKeys extends ElasticBeanstalkKeys
