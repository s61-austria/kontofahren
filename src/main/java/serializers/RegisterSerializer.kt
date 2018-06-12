package serializers

import utils.Open

@Open
data class RegisterSerializer (
    val username: String,
    val password: String
)
