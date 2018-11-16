package common

case class Get(val key: String)
case class Set(val key: String, val value: String)
