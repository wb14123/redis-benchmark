package me.binwang.redis.benchmark

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import redis.{RedisDispatcher, RedisServer, RedisClientPool}

object RedisBenchmark {

  implicit val akkaSystem = ActorSystem("redis-benchmark")
  implicit val redisDispatcher = RedisDispatcher("akka.actor.default-dispatcher")
  implicit val executionContext = akkaSystem.dispatchers.lookup("akka.actor.default-dispatcher")

  val config = ConfigFactory.load()
  val poolSize = config.getInt("redis.pool-size")
  val redisHost = config.getString("redis.host")
  val redisPort = config.getInt("redis.port")
  val ops = config.getInt("ops")

  val redisClient = RedisClientPool(
    (0 to poolSize).map(n => RedisServer(redisHost,redisPort)))

  def get(): Unit = {
    val key = "some_key"
    val result = redisClient.get(key)
    result onSuccess { case _ => get() }
  }

  def benchmark() = {
    (0 to ops) foreach { _ => get() }
    // (0 to ops).foreach(_ => get(redisClient))
    // Thread.sleep(ops / 1000)
  }

}
