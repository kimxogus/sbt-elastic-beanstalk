package io.xogus.sbt.elasticbeanstalk

import sbt._
import sbt.Keys._
import com.typesafe.sbt.SbtNativePackager.Docker
import com.typesafe.sbt.SbtNativePackager.Universal
import com.typesafe.sbt.packager.Keys._
import com.typesafe.sbt.packager.MappingsHelper
import com.typesafe.sbt.packager.Stager
import com.typesafe.sbt.packager.docker.DockerPlugin
import com.typesafe.sbt.packager.universal.Archives

/**
  * == Elastic Beanstalk Plugin ==
  *
  * This plugin helps you build a dist for an Amazon Elastic Beanstalk environment with Docker configuration.
  *
  * == Configuration ==
  *
  * In order to configure this plugin take a look at the available [[io.xogus.sbt.elasticbeanstalk.ElasticBeanstalkKeys]]
  *
  * == Requirements ==
  *
  * This plugin depends on the SBT Native Packager DockerPlugin.
  * @see http://www.scala-sbt.org/sbt-native-packager/formats/docker.html
  *
  * == Usage ==
  * @example Enable the plugin in the `build.sbt`
  * {{{
  *    enablePlugins(ElasticBeanstalkPlugin)
  * }}}
  */
object ElasticBeanstalkPlugin extends AutoPlugin {

  override def requires: DockerPlugin.type = DockerPlugin

  object autoImport extends ElasticBeanstalkKeys {
    val ElasticBeanstalk = config("elastic-beanstalk") extend Docker
  }

  import autoImport._

  override lazy val projectSettings: Seq[Def.Setting[_]] = inConfig(ElasticBeanstalk)(
    Seq(
      dist := {
        val s = streams.value
        s.log.info("")
        s.log.info("Your package is ready in " + dist.value.getCanonicalPath)
        s.log.info("")
        dist.value
      },
      elasticBeanstalkPackageMappings := (sourceDirectory map { dir => MappingsHelper contentOf dir }).value,
      mappings := ((mappings in Docker).value ++ elasticBeanstalkPackageMappings.value),
      mappings in packageBin := (stage map { dir => MappingsHelper contentOf dir }).value,
      packageBin := {
        Archives.makeZip(
          target.value,
          (packageName in Universal).value,
          (mappings in packageBin).value,
          None, Seq.empty)
      },
      sourceDirectory := baseDirectory.value / ElasticBeanstalk.name,
      stage := {
        Stager.stage(ElasticBeanstalk.name)(streams.value, stagingDirectory.value, mappings.value)
      },
      stagingDirectory := (stagingDirectory in Docker).value,
      target := target.value / ElasticBeanstalk.name))
}
