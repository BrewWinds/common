package spark

import org.apache.spark.sql.SparkSession

object DataSource {
    def main(args: Array[String]): Unit = {

        val spark = sparkSession();

        import spark.implicits._

        // read from json
        val jsonDF = spark.read.option("multiLine", "true").json("people.json")
        jsonDF.show();

        // save as csv
        jsonDF.write.mode("overwrite").option("header","true")
                .csv("./test")

        // save as parquet
        jsonDF.write.format("parquet").mode("overwrite")
                .save("./test")


        // read parquest
        val parquetDF = spark.read.format("prquet").load("./test")
        parquetDF.select($"name").show()

        // read csv
        val csvDF = spark.read.format("csv").option("header", "true")
                .load("./test")
        csvDF.select($"age").show()
    }


    def sparkSession():SparkSession = {
        SparkSession.builder().appName("cjun spark app")
                .config("spark.some.config.option", "some-value")
                .getOrCreate()

    }
}
