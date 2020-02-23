organization := "uncmath25"
name := "spark.template"
scalaVersion := "2.11.12"
version := "0.1.0-SNAPSHOT"

val sparkVersion = "2.4.4"
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided"
)
