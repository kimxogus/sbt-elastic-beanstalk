package io.xogus.sbt.elasticbeanstalk

import com.typesafe.sbt.SbtNativePackager.{Docker, Universal}
import com.typesafe.sbt.packager.Keys._
import com.typesafe.sbt.packager.MappingsHelper
import com.typesafe.sbt.packager.docker.DockerPlugin
import com.typesafe.sbt.packager.universal.Archives
import sbt.Keys._
import sbt._
import sbt.io.PathFinder

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

  override lazy val projectSettings: Seq[Def.Setting[_]] = Seq(
    elasticBeanstalkPackageMappings := (sourceDirectory map { dir => MappingsHelper contentOf dir }).value
  ) ++ inConfig(ElasticBeanstalk)(
    Seq(
      dist := {
        val logger = streams.value.log
        val dist = (packageBin in ElasticBeanstalk).value
        logger.info("")
        logger.info("Your package is ready in " + dist.getCanonicalPath)
        logger.info("")
        dist
      },
      name := name.value,
      mappings := (mappings in Docker).value ++ elasticBeanstalkPackageMappings.value ++ elasticBeanstalkSourceMappings.value,
      mappings in packageBin := stage.map { dir => MappingsHelper contentOf dir }.value ++ elasticBeanstalkSourceMappings.value,
      stagingDirectory := (stagingDirectory in Docker).value,
      target := target.value / ElasticBeanstalk.name,
      packageBin := {
        Archives.makeZip(
          target.value,
          packageName.value,
          (mappings in packageBin).value,
          None,
          Seq.empty)
      },
      elasticBeanstalkSources := Nil,
      elasticBeanstalkSourceMappings := {
        val baseDir = baseDirectory.value
        elasticBeanstalkSources.value.filter(_.exists()).flatMap {
          dir => (PathFinder(dir).allPaths --- PathFinder(baseDir)) pair MappingsHelper.relativeTo(new File(dir.getParent))
        }
      },
      packageName := (packageName in Universal).value,
      sourceDirectory := (target in Docker).value,
      stage := (stage in Docker).value
    )
  )
}
