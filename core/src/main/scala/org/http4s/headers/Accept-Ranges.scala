package org.http4s
package headers

import org.http4s.util.Writer

object `Accept-Ranges` extends HeaderKey.Internal[`Accept-Ranges`] with HeaderKey.Singleton {
  def apply(first: RangeUnit, more: RangeUnit*): `Accept-Ranges` = apply(first +: more)
  def bytes = apply(RangeUnit.bytes)
  def none = apply(Nil)
}

final case class `Accept-Ranges` private[http4s] (rangeUnits: Seq[RangeUnit]) extends Header.Parsed {
  def key = `Accept-Ranges`
  def renderValue(writer: Writer): writer.type = {
    if (rangeUnits.isEmpty) writer.append("none")
    else {
      writer.append(rangeUnits.head)
      rangeUnits.tail.foreach(r => writer.append(", ").append(r))
      writer
    }
  }
}
