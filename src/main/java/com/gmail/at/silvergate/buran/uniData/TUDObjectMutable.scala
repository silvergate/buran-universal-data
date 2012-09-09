package com.gmail.at.silvergate.buran.uniData

trait TUDObjectMutable extends TUDObject {
  def undefine(key: String)

  def setAs[T](key: String, ttype: TType[T], value: T)

  def setAs[T](key: String, ttype: TMutableType[T], value: T)

  def asMutable[T](key: String, ttype: TMutableType[T]): Option[T]
}