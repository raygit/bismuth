package bismuth.producer

/** 
 * Callbacks housed here for the purpose
 * of conducting post-mortem actions w.r.t
 * data
 */

import org.apache.kafka.clients.producer._

trait CompletionHandler extends Callback {
  val fn =
    (onValue: Boolean) => 
      (onException: Exception) => 
        (action: Exception => Unit) => 
          onValue match {
            case true => action(onException)
            case _    => // do nothing
          }

  override def onCompletion(
    recordMetadata : RecordMetadata,
    e : Exception) {
      fn(e != null)(e)
  }
}

class RunUponCompletion(f: Exception => Unit) extends CompletionHandler {
  override def onCompletion(
    recordMetadata : RecordMetadata,
    e : Exception) {
      fn(e != null)(e)(f)
  }
}


