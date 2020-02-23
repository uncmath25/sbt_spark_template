package uncmath25.spark.template

import java.io.File
import java.nio.file.{Files, Path, StandardCopyOption}
import org.apache.spark.sql.{DataFrame, SparkSession}


object Utils {
  def importCsv(spark: SparkSession, fileName: String): DataFrame = spark.read.option("header", "true").csv(fileName)

  def exportCsv(dataFrame: DataFrame, fileName: String): Unit = dataFrame.coalesce(1).write.option("header", "true").csv(fileName)

  def consolidateCsv(fileName: String): Unit = {
    val sourceFile = (new File(fileName)).listFiles.filter(!_.getName.startsWith(".")).filter(_.getName.endsWith(".csv"))(0)
    Files.copy(sourceFile.toPath, (new File("temp.csv")).toPath)
    deleteRecursively(new File(fileName))
    Files.move((new File("temp.csv")).toPath, (new File(fileName)).toPath)
  }

  def deleteRecursively(file: File): Unit = {
    if (file.isDirectory) {
      file.listFiles.foreach(deleteRecursively)
    }
    if (file.exists && !file.delete) {
      throw new Exception(s"Unable to delete ${file.getAbsolutePath}")
    }
  }
}
