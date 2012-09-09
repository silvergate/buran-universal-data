package com.gmail.at.silvergate.buran.uniData

import java.io.InputStream

trait TBinaryType {
  def read(reader: TBinaryReader)

  def getNumberOfBytes(): Int
}

trait TBinaryReader {
  def read(inputStream: InputStream)
}