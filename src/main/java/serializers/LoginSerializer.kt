package serializers

import utils.Open

@Open
data class LoginSerializer (
    val username: String,
    val password: String
)
