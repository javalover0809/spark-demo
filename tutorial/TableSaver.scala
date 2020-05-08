package com.ycard.sankuai.tutorial

import org.apache.spark.sql.{DataFrame, SparkSession}

abstract class TableSaver(spark: SparkSession, df: DataFrame, table:String) extends Serializable {
  def getCreateSQL(table:String): String
  def getWriteSQL(table: String, tempView:String): String

  def commit(tempView:String = "result"): Unit = {
    val createSQL = getCreateSQL(table)
    val writeSQL = getWriteSQL(table, tempView)

    spark.sql(createSQL)
    df.createOrReplaceTempView(tempView)
    spark.sql(writeSQL)
  }
}