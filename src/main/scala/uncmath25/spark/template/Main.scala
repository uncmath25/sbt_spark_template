package uncmath25.spark.template

import org.apache.spark.sql.{SparkSession}

object Main {
  val spark = SparkSession.builder()
    .appName("Spark Template")
    .getOrCreate()

  def main(args: Array[String]) {
    val inputFile = args(0)
    val outputFile = args(1)
    new App(spark, inputFile, outputFile).run()
  }
}
