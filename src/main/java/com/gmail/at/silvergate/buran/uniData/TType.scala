package com.gmail.at.silvergate.buran.uniData

trait TType[T] {
  def getType(): Class[T]
}

trait TReadonlyType[T] {
  def getType(): Class[T]
}

trait TMutableType[T] {
  def getType(): Class[T]
}

class UDType[T](clazz: Class[T]) extends TType[T] {
  def getType(): Class[T] = {
    clazz
  }
}

class UDMutableType[T](clazz: Class[T]) extends TMutableType[T] {
  def getType(): Class[T] = {
    clazz
  }
}

class UDReadonlyType[T](clazz: Class[T]) extends TReadonlyType[T] {
  def getType(): Class[T] = {
    clazz
  }
}

object UDTypes {
  val int = new UDType(classOf[Int])
  val string = new UDType(classOf[String])

  val allTypes: Set[TType[_]] = Set(int, string)

  val binary = new UDReadonlyType(classOf[TBinaryType])
  val obj = new UDReadonlyType(classOf[TUDObject])
  val objMutable = new UDMutableType(classOf[TUDObjectMutable])
  val array = new UDReadonlyType(classOf[TUDArray])
  val arrayMutable = new UDMutableType(classOf[TUDArrayMutable])

  val allReadonlyTypes = Set(binary, obj, array)
  val allMutableTypes = Set(objMutable, arrayMutable)
}