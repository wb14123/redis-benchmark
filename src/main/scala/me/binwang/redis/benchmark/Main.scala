package me.binwang.redis.benchmark

object Main {

  def main(args: Array[String]): Unit = {
    val redisHost = "127.0.0.1"
    val redisPort = 6379
    println("Start to benchmark")
    val perBatch = 1000000
    (1 to 10000).foreach { i =>
        val processed = i * perBatch
        println(s"Processed ${processed.toString}")
        RedisBenchmark.benchmark(perBatch)
    }
  }

}
