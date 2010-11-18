package khaos.sbt

import _root_.sbt._
import Process._

trait ProtocPlugin extends Project {
  def protobufDirectory = "src" / "main" / "protobuf"
  def protobufSchemas = protobufDirectory ** "*.proto"
  def protobufOutputPath = "src" / "main" / "java"
  def protobufIncludePath = List(protobufDirectory)
  
  def compileProtobufAction = task {
    for (schema <- protobufSchemas.get) {
      log.info("Compiling schema %s".format(schema))
    }
    protobufOutputPath.asFile.mkdirs()
    val incPath = protobufIncludePath.map(_.absolutePath).mkString("-I ", " -I ", "")
    <x>protoc {incPath} --java_out={protobufOutputPath.absolutePath} {protobufSchemas.getPaths.mkString(" ")}</x> ! log
    None
  } describedAs("Compiles protobuf schemas to java source files.")
  lazy val compileProtobuf = compileProtobufAction
}
