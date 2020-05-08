package com.ycard.sankuai.tutorial

import org.apache.spark.sql.SparkSession

case class ReviewSpu(poiID: Long, spu: String)
class DataLoader(spark: SparkSession, nowDate: String, topK: Int) extends Serializable {
  import spark.implicits._

  private val spuSql =
    s"""
       |select
       |      poi_id,
       |      recommended_spu_list
       |from
       |      mart_crm.dwa_crm_tenant_poi_user_review_daily
       |where partition_date between date_sub('$nowDate', 60) and '$nowDate'
       |      and length(recommended_spu_list) > 0
     """.stripMargin





  val spu = spark.sql(spuSql)
    .rdd
    .map(row => ReviewSpu(row.getAs[Long]("poi_id"), row.getAs[String]("recommended_spu_list")))
    .groupBy(_.poiID)
    .mapPartitions(iters => {
      for ((poiID, reviewSpus) <- iters) yield {
        val poi = poiID
        val spus = reviewSpus
        val result = (poiID, spuCount(reviewSpus))
        result
      }
    }).toDF("poi_id", "spu_cnt")


//    val spu = spark.sql(spuSql)
//      .rdd
//      .map(row => ReviewSpu(row.getAs[Long]("poi_id"), row.getAs[String]("recommended_spu_list")))
//      .groupBy(_.poiID)
//      .mapPartitions(iters => {
//        for {(poiID, reviews) <- iters
//          poi = poiID
//          spus = spuCount(reviews)
//          result = (poi, spus)
//        } yield result
//      }).toDF("poi_id", "spu_cnt")


  //val spu = spark.sql(spuSql)
  //  .rdd
  //  .map(row => ReviewSpu(row.getAs[Long]("poi_id"), row.getAs[String]("recommended_spu_list")))
  //  .groupBy(_.poiID)
  //  .mapPartitions(iters => {
  //    for ((poiID, reviewSpus) <- iters)
  //      yield (poiID, spuCount(reviewSpus))
  //  })
  //  .toDF("poi_id", "spu_cnt")




  def spuCount(infos: Iterable[ReviewSpu]) :String ={
    val spu = infos.flatMap(_.spu.split(",")).map(_.trim()).filter(_.length <= 18)
    val spuCount = spu.groupBy(identity)
      .mapValues(dishes => dishes.size)
      .toList.sortBy(_._2).reverse
      .map(identity)
    spuCount.take(topK).mkString(":")
  }
}
