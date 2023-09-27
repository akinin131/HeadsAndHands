package quiz.example.headandhandstestcase.ui

data class MainActivityUiState(
    val playerName: String,
    val playerHealth: Int,
    val monsterName: String,
    val monsterHealth: Int,
    val gameLog: String,
    val attackButtonEnabled: Boolean,
    val healButtonEnabled: Boolean,
    val gameOver: Boolean,
)