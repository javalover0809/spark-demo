import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.util.GenericOptionsParser
import org.apache.spark.sql.SparkSession

object Tutorial {
  val defaultTableName = "mart_catering.dwa_crm_tutorial_test"
  def main(args: Array[String]): Unit = {

    // 生成SparkSession
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .appName("tutorial")
      .getOrCreate()

    // 定义配置，获取外部配置参数
    val conf = new Configuration()
    val parser = new GenericOptionsParser(conf, args)
    val remainArgs = parser.getRemainingArgs
    val tableName = conf.get("output_table_name", defaultTableName)
    val dt = remainArgs(0)  // dt $now.delta(1).date
    val topK = remainArgs(1).toInt

    // 生成数据加载器
    val dataLoader = new DataLoader(spark, dt, topK)
    val spu = dataLoader.spu.repartition(10)


    // 生成数据存储器
    val saver = new Saver(spark, spu, tableName)
    saver.commit()
  }
}
