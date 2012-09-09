package com.gmail.at.silvergate.buran.uniData.jsonImpl

import com.gmail.at.silvergate.buran.uniData.TReadonlyType
import com.gmail.at.silvergate.buran.uniData.TType
import com.gmail.at.silvergate.buran.uniData.TMutableType
import com.google.gson.JsonObject

class UDJSObject(data: JsonObject = new JsonObject()) {

  def isDefined(key: String): Boolean = {
    return this.data.has(key)
  }

  def as[T](key: String, ttype: TType[T]): Option[T] = {
    val value = this.data.get(key)
    if (value == null)
      return Option.empty
    return JsonNativeConverter.as(value, ttype)
  }

  def as[T](key: String, ttype: TReadonlyType[T]): Option[T] = {
    val value = this.data.get(key)
    if (value == null)
      return Option.empty
    return JsonNativeConverter.as(value, ttype)
  }

  /* Mutable */

  def undefine(key: String) = {
    this.data.remove(key)
  }

  def setAs[T](key: String, ttype: TType[T], value: T) = {
    val primitive = JsonNativeConverter.to(value, ttype)
    this.data.add(key, primitive);
  }


  def setAs[T](key: String, ttype: TMutableType[T], value: T) = {
    val primitive = JsonNativeConverter.to(value, ttype)
    this.data.add(key, primitive);
  }

  def asMutable[T](key: String, ttype: TMutableType[T]): Option[T] = {
    val value = this.data.get(key)
    if (value == null)
      return Option.empty
    return JsonNativeConverter.asMutable(value, ttype)
  }
}