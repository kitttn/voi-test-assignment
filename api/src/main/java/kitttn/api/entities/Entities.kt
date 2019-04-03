package kitttn.api.entities

data class Artist(val genres: List<String>,
                  val id: String,
                  val images: List<Image>,
                  val name: String,
                  val uri: String)

data class Image(val width: Int, val height: Int, val url: String)