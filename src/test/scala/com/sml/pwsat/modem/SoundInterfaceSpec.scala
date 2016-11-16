package com.sml.pwsat.modem

import java.io.File
import javax.sound.sampled.{SourceDataLine, TargetDataLine}

import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class SoundInterfaceSpec extends FlatSpec with Matchers with BeforeAndAfter {

  val config = ConfigFactory.load()

  "A sound interface factory" should "return InputSoundInterface instance" in {
    val interfaceName: String = config.getString("com.softwaremill.pwsat.modem.soundInterfaceName")
    val sif = SoundInterfaceFactory(SoundInterfaceType.INPUT, interfaceName)
    sif shouldBe a[InputSoundInterface]
    sif.getDataLine shouldBe a[Some[TargetDataLine]]
  }

  "A sound interface factory" should "return OutputSoundInterface instance" in {
    val interfaceName: String = config.getString("com.softwaremill.pwsat.modem.soundInterfaceName")
    val sif = SoundInterfaceFactory(SoundInterfaceType.OUTPUT, interfaceName)
    sif shouldBe a[OutputSoundInterface]
    sif.getDataLine shouldBe a[Some[SourceDataLine]]
  }

  "A sound interface factory" should "return FileInputSoundInterface instance" in {
    val file = new File(getClass.getResource("/packet12.wav").getPath)
    val sif = SoundInterfaceFactory(SoundInterfaceType.FILE_INPUT, file.getAbsolutePath)
    sif shouldBe a[FileInputSoundInterface]
    sif.getDataLine shouldBe a[Some[SourceDataLine]]
  }

  "A sound interface factory" should "return None for unknown interface name and OUTPUT type" in {
    val interfaceName: String = "Wrong-Interface-Name"
    val sif = SoundInterfaceFactory(SoundInterfaceType.OUTPUT, interfaceName)
    sif.getDataLine shouldBe a[Option[Nothing]]
  }

  "A sound interface factory" should "return None for unknown interface name and INPUT type" in {
    val interfaceName: String = "Wrong-Interface-Name"
    val sif = SoundInterfaceFactory(SoundInterfaceType.INPUT, interfaceName)
    sif.getDataLine shouldBe a[Option[Nothing]]
  }

  "A sound interface instance" should "return proper string for its interfaceName" in {
    val interfaceName: String = config.getString("com.softwaremill.pwsat.modem.soundInterfaceName")
    val sif = SoundInterfaceFactory(SoundInterfaceType.OUTPUT, interfaceName)
    sif.getDataLine shouldBe a[Some[SourceDataLine]]
    sif.toString shouldBe "System Interface Name: " + interfaceName
  }
}
