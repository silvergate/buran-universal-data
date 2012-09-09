package com.gmail.at.silvergate.buran.uniData.memoryImpl

import java.io.ByteArrayInputStream
import java.util.Arrays
import com.gmail.at.silvergate.buran.uniData.TBinaryReader
import com.gmail.at.silvergate.buran.uniData.TBinaryType
import com.gmail.at.silvergate.buran.uniData.TBinaryWriter
import com.gmail.at.silvergate.buran.uniData.TBinaryTypeMutable
import java.io.ByteArrayOutputStream

class BinaryType extends TBinaryType with TBinaryTypeMutable {

  private var data: Array[Byte] = null

  def read(reader: TBinaryReader) = {
    val bais = new ByteArrayInputStream(this.data)
    reader.read(bais)
    bais.close()
  }

  def getNumberOfBytes(): Int = {
    if (this.data != null)
      data.size
    else
      return 0
  }

  /* Mutable */

  def write(writer: TBinaryWriter) = {
    val baos = new ByteArrayOutputStream
    writer.write(baos)
    // Close?
    this.data = baos.toByteArray()
  }
}