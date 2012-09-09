package com.gmail.at.silvergate.buran.uniData

import java.io.OutputStream

trait TBinaryTypeMutable extends TBinaryType {
  def write(writer: TBinaryWriter)
}

trait TBinaryWriter {
  def write(writer: OutputStream)
}