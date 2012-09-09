package com.gmail.at.silvergate.buran.uniData.jsonImpl

import com.gmail.at.silvergate.buran.uniData.TMutableType
import com.gmail.at.silvergate.buran.uniData.TReadonlyType
import com.gmail.at.silvergate.buran.uniData.TType
import com.gmail.at.silvergate.buran.uniData.TUDObject
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive

object JsonNativeConverter {

  def as[T](inputValue: JsonElement, ttype: TType[T]): Option[T] = {
    val value = inputValue
    val inType = ttype.getType

    try {
      if (inType.equals(classOf[Int]))
        return Option.apply(value.getAsInt().asInstanceOf[T])

      if (inType.equals(classOf[Long]))
        return Option.apply(value.getAsLong().asInstanceOf[T])

      if (inType.equals(classOf[String]))
        return Option.apply(value.getAsString().asInstanceOf[T])

      if (inType.equals(classOf[Boolean]))
        return Option.apply(value.getAsBoolean().asInstanceOf[T])
    } catch {
      case scce: ClassCastException =>
        return Option.empty
      case jcce: ClassCastException =>
        return Option.empty
    }

    Option.empty
  }

  def as[T](inputValue: JsonElement, ttype: TReadonlyType[T]): Option[T] = {
    val value = inputValue
    val inType = ttype.getType

    try {
      if (ttype.getType().isInstanceOf[TUDObject]) {
        val inputObject = inputValue.getAsJsonObject();
        return Option.apply((new UDJSObject(inputObject)).asInstanceOf[T]);
      }

      return Option.apply(value.asInstanceOf[T])
    } catch {
      case scce: ClassCastException =>
        return Option.empty
      case jcce: ClassCastException =>
        return Option.empty
    }

    Option.empty
  }

  def asMutable[T](inputValue: JsonElement, ttype: TMutableType[T]): Option[T] = {
    val value = inputValue
    val inType = ttype.getType

    try {
      if (ttype.getType().isInstanceOf[TUDObject]) {
        val inputObject = inputValue.getAsJsonObject();
        return Option.apply((new UDJSObject(inputObject)).asInstanceOf[T]);
      }

      return Option.apply(value.asInstanceOf[T])
    } catch {
      case scce: ClassCastException =>
        return Option.empty
      case jcce: ClassCastException =>
        return Option.empty
    }

    Option.empty
  }

  def to[T](value: Any, ttype: TType[T]): JsonElement = {
    if (ttype.getType().equals(classOf[Int]))
      return new JsonPrimitive(value.asInstanceOf[Int])

    if (ttype.getType().equals(classOf[String]))
      return new JsonPrimitive(value.asInstanceOf[String])

    if (ttype.getType().equals(classOf[Long]))
      return new JsonPrimitive(value.asInstanceOf[Long])

    if (ttype.getType().equals(classOf[Boolean]))
      return new JsonPrimitive(value.asInstanceOf[Boolean])

    throw new IllegalStateException("ise")
  }

  def to[T](value: Any, ttype: Class[_]): JsonElement = {
    if (ttype.isAssignableFrom(classOf[TUDObject])) {
      val targetJsonObject = new JsonObject
      val obj = value.asInstanceOf[TUDObject]
      val keys = obj.getDefinedKeys
      keys.foreach(key => {
        val sourceType = obj.getType(key)
        if (sourceType.isEmpty)
          throw new IllegalArgumentException("Key defined but has no type")
        val srcType = sourceType.get
        var primitiveJson: JsonElement = null
        if (srcType.isLeft) {
          val value = obj.as(key, srcType.left.get)
          primitiveJson = to(value, srcType.left.get)
        } else {
          val value = obj.as(key, srcType.right.get)
          primitiveJson = to(value, srcType.right.get)
        }
        targetJsonObject.add(key, primitiveJson)
      });
      return targetJsonObject
    }
    return null
  }

  def to[T](value: Any, ttype: TReadonlyType[T]): JsonElement = {
    return to(value, ttype.getType)
  }

  def to[T](value: Any, ttype: TMutableType[T]): JsonElement = {
    return to(value, ttype.getType)
  }

}