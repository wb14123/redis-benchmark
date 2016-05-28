name := "redis_benchmark"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= {
  Seq(
    "com.github.etaty" %% "rediscala" % "1.7.0-SNAPSHOT"
  )
}

parallelExecution in Test := false

import NativePackagerHelper._

enablePlugins(JavaServerAppPackaging)

mainClass in (Compile, run) := Some("me.binwang.redis.benchmark.Main")

mappings in Universal ++= {
  // optional example illustrating how to copy additional directory
  directory("scripts") ++
      // copy configuration files to config directory
      contentOf("src/main/resources").toMap.mapValues("config/" + _)
}

scriptClasspath := Seq("../config/") ++ scriptClasspath.value
javaOptions += "-Xmx8G"
