package me.binwang.redis.benchmark

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import redis.{RedisDispatcher, RedisServer, RedisClientPool}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object RedisBenchmark {

  implicit val akkaSystem = ActorSystem("redis-benchmark")
  implicit val redisDispatcher = RedisDispatcher("akka.actor.default-dispatcher")
  implicit val executionContext = akkaSystem.dispatchers.lookup("akka.actor.default-dispatcher")

  val config = ConfigFactory.load()
  val poolSize = config.getInt("redis.pool-size")
  val redisHost = config.getString("redis.host")
  val redisPort = config.getInt("redis.port")

  val redisClient = RedisClientPool(
    (0 to poolSize).map(n => RedisServer(redisHost,redisPort)))

  def get(redisClient: RedisClientPool) = {
    val key = "some_key"
    redisClient.get(key)
  }

  def benchmark(ops: Int) = {
    val seqResult = (0 to ops).map(_ => get(redisClient))
    val result = Future.sequence(seqResult)
    Await.result(result, Duration.Inf)
    // (0 to ops).foreach(_ => get(redisClient))
    // Thread.sleep(ops / 1000)
  }

}
