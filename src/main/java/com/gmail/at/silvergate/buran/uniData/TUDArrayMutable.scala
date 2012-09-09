package com.gmail.at.silvergate.buran.uniData

trait TUDArrayMutable extends TUDArray {

  def setAs[T](index: Int, ttype: TType[T], value: T)

  def setAs[T](index: Int, ttype: TMutableType[T], value: T)

  def appendAs[T](ttype: TType[T], value: T)

  def appendAs[T](ttype: TMutableType[T], value: T)

  def clear()

  def asMutable[T](index: Int, ttype: TMutableType[T]): Option[T]
}