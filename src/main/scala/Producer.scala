package bismuth.producer

import org.apache.kafka.clients.producer._
/**
 * The simple producer class that produces some
 * data in the form of a hypothesized Customer
 * object.
 */
class Basmati[RK, RV](key : RK, value: RV) {

  private[this] val producer: KafkaProducer[RK,RV] = initProducer

  lazy val keysValues =
    Seq(("bootstrap.servers", "broker1:9092,broker2:9092"),
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

  val record = new ProducerRecord[RK, RV]("topic-a", key, value);

  producer.send(record, new RunUponCompletion( (e: Exception) => e.printStackTrace ))
}

