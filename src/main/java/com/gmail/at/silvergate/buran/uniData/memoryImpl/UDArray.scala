package com.gmail.at.silvergate.buran.uniData.memoryImpl

import com.gmail.at.silvergate.buran.uniData.TUDArray
import com.gmail.at.silvergate.buran.uniData.TReadonlyType
import com.gmail.at.silvergate.buran.uniData.TType
import scala.collection.mutable.ArrayBuffer
import com.gmail.at.silvergate.buran.uniData.TUDArrayMutable
import com.gmail.at.silvergate.buran.uniData.TMutableType

class UDArray extends TUDArray with TUDArrayMutable {
  private def data = new ArrayBuffer[Any]

  def getSize(): Int = {
    this.data.size
  }

  def isDefined(index: Int): Boolean = (index < this.data.size)

  def as[T](index: Int, ttype: TType[T]): Option[T] = {
    if (!isDefined(index))
      return Option.empty
    val value = this.data(index)
    Converter.as(value, ttype)
  }

  def as[T](index: Int, ttype: TReadonlyType[T]): Option[T] = {
    if (!isDefined(index))
      return Option.empty
    val value = this.data(index)
    Converter.as(value, ttype)
  }

  /* Mutable */

  def setAs[T](index: Int, ttype: TType[T], value: T) = {
    this.data(index) = value
  }

  def setAs[T](index: Int, ttype: TMutableType[T], value: T) = {
    this.data(index) = value
  }

  def appendAs[T](ttype: TType[T], value: T) = {
    this.data.append(value)
  }

  def appendAs[T](ttype: TMutableType[T], value: T) = {
    this.data.append(value)
  }

  def clear() = {
    this.data.clear
  }

  def asMutable[T](index: Int, ttype: TMutableType[T]): Option[T] = {
    if (!isDefined(index))
      return Option.empty
    val value = this.data(index)
    Converter.asMutable(value, ttype)
  }
}