package no.fritjof.hgq.dto

data class GoogleUserDto(
    val email: String,
    val name: String,
    val picture: String?
) {
    companion object {
        fun toDto(attributes: Map<String, Any>): GoogleUserDto {
            return GoogleUserDto(
                email = attributes["email"] as String,
                name = attributes["name"] as String,
                picture = attributes["picture"] as String?
            )
        }
    }
}
