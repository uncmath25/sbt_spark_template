package uncmath25.spark.template

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

class App (val spark: SparkSession, val inputFile: String, val outputFile: String) {
  def run(): Unit = {
    println("Running spark app...")
    val rawDf = Utils.importCsv(spark, inputFile)
    val processedDf = process(rawDf)
    Utils.exportCsv(processedDf, outputFile)
    Utils.consolidateCsv(outputFile)
  }

  private def process(inputDf: DataFrame): DataFrame = {
    inputDf
      .groupBy("customer_id")
      .agg(round(sum("amount"), 2).alias("total"))
      .select("customer_id", "total")
  }
}
