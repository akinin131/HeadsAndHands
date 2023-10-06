package quiz.example.headandhandstestcase.ui

data class MainActivityUiState(
    val playerName: String,
    val playerHealth: Int,
    val monsterName: String,
    val monsterHealth: Int,
    val attackButtonEnabled: Boolean,
    val healButtonEnabled: Boolean,
    val gameOver: Boolean,
    val playerAttackMessage: String,
    val monsterAttackMessage: String
)