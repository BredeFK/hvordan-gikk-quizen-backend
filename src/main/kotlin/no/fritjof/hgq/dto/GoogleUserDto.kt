package no.fritjof.hgq.dto

data class GoogleUserDto(
    val id: String? = null,
    val authenticated: Boolean = false,
    val email: String? = null,
    val givenName: String? = null,
    val familyName: String? = null,
    val picture: String? = null
) {
    companion object {
        fun toDto(attributes: Map<String, Any>): GoogleUserDto {
            return GoogleUserDto(
                id = attributes["sub"] as String,
                authenticated = true,
                email = attributes["email"] as String,
                givenName = attributes["given_name"] as String,
                familyName = attributes["family_name"] as String,
                picture = attributes["picture"] as String?
            )
        }
    }
}
