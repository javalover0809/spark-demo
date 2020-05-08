package com.ycard.sankuai.tutorial
import com.ycard.sankuai.utils.TableSaver
import org.apache.spark.sql.{DataFrame, SparkSession}
class Saver(spark: SparkSession, df: DataFrame, table: String) extends TableSaver(spark, df, table) {
  def getCreateSQL(table: String): String =
    """
       |create table if not exists $table
       |(
       |    `poi_id` bigint comment'门店ID',
       |    `spu_cnt` string comment"菜品计数"
       |)
       |comment '评论服务菜品筛选列表数据'
       |stored as ORC
     """.stripMargin

  override def getWriteSQL(table: String, tempView: String): String =
    s"""
       |insert overwrite table $table
       |select
       |       poi_id,
       |       spu_cnt
       |from    $tempView
     """.stripMargin

}
