package bismuth.producer

import org.apache.kafka.clients.producer._
/**
 * The simple producer class that produces some
 * data in the form of a hypothesized Customer
 * object.
 */
class Basmati[RK, RV](key : RK, value: RV) {

  private[this] val producer: KafkaProducer[RK,RV] = initProducer

  //
  // TODO:
  // + Lift the brokers to a configuration
  // + Lift the key/value serializer to a way that we can type-check
  //   Currently, Kafka supports the following serializers and deserializers
  //   + ByteArray
  //   + ByteBuffer
  //   + Double, Int, Long, String
  //
  lazy val keysValues =
    Seq(("bootstrap.servers", "localhost:9092,localhost:9093"),
        ("key.serializer",    "org.apache.kafka.common.serialization.StringSerializer"),
        ("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
        )

  private def populateProps  = 
    (p: java.util.Properties) =>
      (kv: Seq[Tuple2[String,String]]) => {
        kv.map(pair => p.put(pair._1, pair._2))
        p
      }

  def initProducer : KafkaProducer[RK, RV] = {
    val props = populateProps(new java.util.Properties())(keysValues)
    new KafkaProducer[RK, RV](props)
  }

  val record = new ProducerRecord[RK, RV]("testAAAB", key, value);

  //
  // TODO:
  // + Send simple metrics of the sent data back to the platform
  //   i.e. Leverage the Future[RecordMetadata] which is the result
  //   object after a `send` is done.
  //   Inside the `RecordMetadata` object, i can lift certain information
  //   out for book-keeping, audit purposes e.g. serializedValueSize() / timestamp()

  producer.send(record, new RunUponCompletion( (e: Exception) => e.printStackTrace ))
}

