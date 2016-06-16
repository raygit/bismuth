
name := "Bismuth"

version := "1.0"

scalaVersion := "2.11.7"

scalacOptions += "-feature"

scalacOptions += "-language:higherKinds"

scalacOptions += "-language:implicitConversions"

scalacOptions += "-language:postfixOps"

libraryDependencies += "org.apache.kafka" %% "kafka" % "0.9.0.1"


