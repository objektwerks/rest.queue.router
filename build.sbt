val akkaVersion = "2.6.15"
val akkaHttpVersion = "10.2.4"

lazy val commonSettings = Defaults.coreDefaultSettings ++ Seq(
  name := "akka.http.queue.router",
  organization := "objektwerks",
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.13.6",
  libraryDependencies ++= {
    Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.rabbitmq" % "amqp-client" % "5.12.0",
      "com.iheart" %% "ficus" % "1.5.0",
      "ch.qos.logback" % "logback-classic" % "1.2.3"
    )
  }
)
lazy val root = (project in file(".")).
  configs(IntegrationTest).
  settings(commonSettings: _*).
  settings(Defaults.itSettings: _*).
  settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "it,test",
      "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % "it,test",
      "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % "it,test",
      "org.scalatest" %% "scalatest" % "3.2.9" % "it,test"
    )
  )
