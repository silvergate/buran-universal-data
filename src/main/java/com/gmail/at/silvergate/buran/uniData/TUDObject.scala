package com.gmail.at.silvergate.buran.uniData

trait TUDObject {
  def isDefined(key: String): Boolean

  def getDefinedKeys(): scala.collection.Set[String]

  def getType[T](key: String): Option[Either[TType[T], TReadonlyType[T]]]

  def as[T](key: String, ttype: TType[T]): Option[T]

  def as[T](key: String, ttype: TReadonlyType[T]): Option[T]
}