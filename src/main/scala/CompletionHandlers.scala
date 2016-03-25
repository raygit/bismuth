package bismuth.producer

/** 
 * Callbacks housed here for the purpose
 * of conducting post-mortem actions w.r.t
 * data
 */

import org.apache.kafka.clients.producer._

trait CompletionHandler extends Callback {
  val fn =
    (onValue: Option[Exception]) => 
      (onException: Exception) => 
        (action: Exception => Unit) => 
          onValue.isEmpty match {
            case false => action(onException)
            case true  => // do nothing
          }

  override def onCompletion(
    recordMetadata : RecordMetadata,
    e : Exception) {
      fn(Option(e))(e)
  }
}

class RunUponCompletion(f: Exception => Unit) extends CompletionHandler {
  override def onCompletion(
    recordMetadata : RecordMetadata,
    e : Exception) {
      fn(Option(e))(e)(f)
  }
}


