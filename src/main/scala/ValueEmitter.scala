
package bismuth.producer

sealed abstract class StSource[A] {
  type S
  def init : S // create the initial state
  def emit(s: S) : (A, S) // emit a value, and update state
}

object StSource {
  type Aux[A,S0] = StSource[A] { type S = S0 }

  def apply[A,S0](i : S0)(f : S0 => (A,S0)) : Aux[A,S0] = 
    new StSource[A] {
      type S = S0
      def init = i
      def emit(s: S0) = f(s)
    }
}

object RunStSource {

  def runStSource[A,S](ss: StSource.Aux[A,S], s: S) : (A,S) = ss.emit(s)

}
