package me.binwang.redis.benchmark

object Main {

  def main(args: Array[String]): Unit = {
    println("Start to benchmark")
    RedisBenchmark.benchmark()
  }

}
