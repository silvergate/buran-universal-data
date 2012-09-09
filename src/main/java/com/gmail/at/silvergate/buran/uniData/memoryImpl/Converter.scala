package com.gmail.at.silvergate.buran.uniData.memoryImpl

import com.gmail.at.silvergate.buran.uniData.TType
import com.gmail.at.silvergate.buran.uniData.TReadonlyType
import com.gmail.at.silvergate.buran.uniData.TMutableType
import com.gmail.at.silvergate.buran.uniData.UDTypes

object Converter {

  def as[T](inputValue: Any, ttype: TType[T]): Option[T] = {
    val value = inputValue
    val inType = ttype.getType

    try {
      return Option.apply(value.asInstanceOf[T])
    } catch {
      case scce: ClassCastException =>
        return Option.empty
      case jcce: ClassCastException =>
        return Option.empty
    }

    Option.empty
  }

  def as[T](inputValue: Any, ttype: TReadonlyType[T]): Option[T] = {
    val value = inputValue
    val inType = ttype.getType

    try {
      return Option.apply(value.asInstanceOf[T])
    } catch {
      case scce: ClassCastException =>
        return Option.empty
      case jcce: ClassCastException =>
        return Option.empty
    }

    Option.empty
  }

  def asMutable[T](inputValue: Any, ttype: TMutableType[T]): Option[T] = {
    val value = inputValue
    val inType = ttype.getType

    try {
      return Option.apply(value.asInstanceOf[T])
    } catch {
      case scce: ClassCastException =>
        return Option.empty
      case jcce: ClassCastException =>
        return Option.empty
    }

    Option.empty
  }

  def fromJavaTypeToTypeExact[T](clazz: Class[T]): Option[Either[TType[T], TReadonlyType[T]]] = {
    UDTypes.allTypes.foreach(typ => {
      if (clazz.equals(typ.getType))
        return Option.apply(Left(typ.asInstanceOf[TType[T]]))
    })
    UDTypes.allReadonlyTypes.foreach(typ => {
      if (clazz.equals(typ.getType))
        return Option.apply(Right(typ.asInstanceOf[TReadonlyType[T]]))
    })
    Option.empty
  }

}