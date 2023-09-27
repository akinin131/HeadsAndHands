package quiz.example.headandhandstestcase.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import quiz.example.headandhandstestcase.domain.Monster
import quiz.example.headandhandstestcase.domain.Player

class MainActivityViewModel : ViewModel() {

    private val _uiState = MutableLiveData<MainActivityUiState>()
    val uiState: LiveData<MainActivityUiState>
        get() = _uiState

    private var playerHealCount = 0
    private val maxPlayerHealCount = 4

    private lateinit var player: Player
    private lateinit var monster: Monster

    fun initializeGame(playerName: String, monsterName: String) {
        player = Player(playerName, 20, 15, 100, 1..6)
        monster = Monster(monsterName, 20, 15, 120, 2..8)

        updateUiState()
    }

    fun attackMonster() {
        val playerAttackSuccessful = player.attackTarget(monster)
        val monsterAttackSuccessful = monster.isAlive() && monster.attackTarget(player)

        val playerAttackMessage = if (playerAttackSuccessful) {
            "${player.name} атакует ${monster.name} и наносит урон."
        } else {
            "${player.name} атакует ${monster.name}, но промахивается."
        }

        val monsterAttackMessage = if (monsterAttackSuccessful) {
            "${monster.name} атакует ${player.name} и наносит урон."
        } else {
            "${monster.name} атакует ${player.name}, но промахивается."
        }

        _uiState.value = _uiState.value?.copy(
            gameLog = "$playerAttackMessage\n$monsterAttackMessage"
        )

        updateUiState()
    }

    fun healPlayer() {
        if (playerHealCount < maxPlayerHealCount) {
            player.healSelf()
            playerHealCount++
            _uiState.value = _uiState.value?.copy(
                gameLog = "${player.name} исцеляется и его здоровье становится ${player.health}."
            )
            updateUiState()
        } else {
            // Добавьте логику, чтобы обработать случай, когда игрок не может больше исцеляться
            // Например, вы можете вывести сообщение, что исцеления больше не доступны.
        }
    }

    private fun updateUiState() {
        _uiState.value = MainActivityUiState(
            player.name,
            player.health,
            monster.name,
            monster.health,
            _uiState.value?.gameLog ?: "",
            attackButtonEnabled = player.isAlive() && monster.isAlive(),
            healButtonEnabled = player.isAlive() && monster.isAlive(),
            gameOver = !player.isAlive() || !monster.isAlive()
        )
    }
}
