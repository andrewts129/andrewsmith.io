package object db {
  val swayDbFolder: String = sys.env.getOrElse("SWAYDB_DIR", ".swaydb")
}
