package org.typelevel.log4cats
package slf4j

import cats.effect.Sync
import org.slf4j.{Logger => JLogger}

trait Slf4jLoggerFactory[F[_]] {
  def getLogger(implicit name: LoggerName): SelfAwareStructuredLogger[F]
  def getLoggerFromName(name: String): SelfAwareStructuredLogger[F]
  def getLoggerFromClass(clazz: Class[_]): SelfAwareStructuredLogger[F]
  def getLoggerFromSlf4j(logger: JLogger): SelfAwareStructuredLogger[F]
  def create(implicit name: LoggerName): F[SelfAwareStructuredLogger[F]]
  def fromName(name: String): F[SelfAwareStructuredLogger[F]]
  def fromClass(clazz: Class[_]): F[SelfAwareStructuredLogger[F]]
  def fromSlf4j(logger: JLogger): F[SelfAwareStructuredLogger[F]]
}

object Slf4jLoggerFactory {
  def apply[F[_]](lf: Slf4jLoggerFactory[F]): Slf4jLoggerFactory[F] = lf

  implicit def forSync[F[_]: Sync]: Slf4jLoggerFactory[F] = new Slf4jLoggerFactory[F] {
    override def getLogger(implicit name: LoggerName): SelfAwareStructuredLogger[F] =
      Slf4jLogger.getLogger

    override def getLoggerFromName(name: String): SelfAwareStructuredLogger[F] =
      Slf4jLogger.getLoggerFromName(name)
    override def getLoggerFromClass(clazz: Class[_]): SelfAwareStructuredLogger[F] =
      Slf4jLogger.getLoggerFromClass(clazz)
    override def getLoggerFromSlf4j(logger: JLogger): SelfAwareStructuredLogger[F] =
      Slf4jLogger.getLoggerFromSlf4j(logger)

    def create(implicit name: LoggerName): F[SelfAwareStructuredLogger[F]] =
      Slf4jLogger.create

    override def fromName(name: String): F[SelfAwareStructuredLogger[F]] =
      Slf4jLogger.fromName(name)

    override def fromClass(clazz: Class[_]): F[SelfAwareStructuredLogger[F]] =
      Slf4jLogger.fromClass(clazz)

    override def fromSlf4j(logger: JLogger): F[SelfAwareStructuredLogger[F]] =
      Slf4jLogger.fromSlf4j(logger)
  }
}
