package com.gmail.at.silvergate.buran.uniData.memoryImpl

import scala.collection.mutable.HashMap
import com.gmail.at.silvergate.buran.uniData.TReadonlyType
import com.gmail.at.silvergate.buran.uniData.TType
import com.gmail.at.silvergate.buran.uniData.TUDObject
import com.gmail.at.silvergate.buran.uniData.TUDObjectMutable
import com.gmail.at.silvergate.buran.uniData.TMutableType

class UDObject extends TUDObject with TUDObjectMutable {

  private def data = new HashMap[String, Any]()

  def isDefined(key: String): Boolean = {
    return this.data.contains(key)
  }

  def getDefinedKeys(): scala.collection.Set[String] = {
    this.data.keySet
  }

  def as[T](key: String, ttype: TType[T]): Option[T] = {
    val value = this.data.get(key)
    if (value.isEmpty)
      return Option.empty
    return Converter.as(value.get, ttype)
  }

  def as[T](key: String, ttype: TReadonlyType[T]): Option[T] = {
    val value = this.data.get(key)
    if (value.isEmpty)
      return Option.empty
    return Converter.as(value.get, ttype)
  }

  def getType[T](key: String): Option[Either[TType[T], TReadonlyType[T]]] = {
    val value = this.data.get(key)
    if (value.isEmpty)
      return Option.empty

    val gotType = Converter.fromJavaTypeToTypeExact[T](value.getClass().asInstanceOf[Class[T]])
    return gotType

  }

  /* Mutable */

  def undefine(key: String) = {
    this.data.remove(key)
  }

  def setAs[T](key: String, ttype: TType[T], value: T) = {
    this.data.put(key, value)
  }

  def setAs[T](key: String, ttype: TMutableType[T], value: T) = {
    this.data.put(key, value)
  }

  def asMutable[T](key: String, ttype: TMutableType[T]): Option[T] = {
    val value = this.data.get(key)
    if (value.isEmpty)
      return Option.empty
    return Converter.asMutable(value.get, ttype)
  }
}