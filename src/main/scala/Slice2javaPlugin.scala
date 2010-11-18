package khaos.sbt

import _root_.sbt._
import Process._

trait Slice2javaPlugin extends Project {
  def sliceDirectory = "src" / "main" / "slice"
  def sliceSchemas = sliceDirectory ** "*.ice"
  def sliceOutputPath = "src" / "main" / "java"
  
  def compileSliceAction = task {
    for (schema <- sliceSchemas.get) {
      log.info("Compiling schema %s".format(schema))
    }
    sliceOutputPath.asFile.mkdirs()
    <x>slice2java --output-dir={sliceOutputPath.absolutePath} {sliceSchemas.getPaths.mkString(" ")}</x> ! log
    None
  } describedAs("Compiles slice schemas to java source files.")
  lazy val compileSlice = compileSliceAction
}
