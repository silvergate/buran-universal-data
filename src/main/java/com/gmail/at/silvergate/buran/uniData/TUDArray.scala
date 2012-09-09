package com.gmail.at.silvergate.buran.uniData

trait TUDArray {
  def getSize(): Int

  def isDefined(index: Int): Boolean

  def as[T](index: Int, ttype: TType[T]): Option[T]

  def as[T](index: Int, ttype: TReadonlyType[T]): Option[T]
}