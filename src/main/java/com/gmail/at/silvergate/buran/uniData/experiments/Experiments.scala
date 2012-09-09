package com.gmail.at.silvergate.buran.uniData.experiments

import com.gmail.at.silvergate.buran.uniData.memoryImpl.UDObject
import com.gmail.at.silvergate.buran.uniData.UDTypes

object Experiments {

  def main(args: Array[String]) = {
    var udo = new UDObject()
    udo.setAs("keyOne", UDTypes.int, 33)
    System.out.println("keyOne=" + udo.as("keyOne", UDTypes.int))
  }

}