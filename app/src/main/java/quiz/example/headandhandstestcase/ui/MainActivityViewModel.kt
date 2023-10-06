package quiz.example.headandhandstestcase.ui


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import quiz.example.headandhandstestcase.R
import quiz.example.headandhandstestcase.domain.Monster
import quiz.example.headandhandstestcase.domain.Player

class MainActivityViewModel(private val context: Application) : AndroidViewModel(context) {

    private val _uiState = MutableLiveData<MainActivityUiState>()
    val uiState: LiveData<MainActivityUiState>
        get() = _uiState

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    private var playerHealCount = 0
    private var maxPlayerHealCount = 4

    private lateinit var player: Player
    private lateinit var monster: Monster

    fun startNewGame(playerName: String, monsterName: String) {
        player = Player(playerName, 20, 18, 100, 1..6)
        monster = Monster(monsterName, 20, 18, 100, 3..8)
        updateUiState()
    }

    fun attackMonster() {
        val playerAttackSuccessful = player.attackTarget(monster)
        val monsterAttackSuccessful = monster.isAlive() && monster.attackTarget(player)

        val playerDiceRolls = player.getDiceRolls().joinToString(", ")

        val playerAttackMessage = if (playerAttackSuccessful) {
            context.getString(R.string.attack_successful, monster.name, playerDiceRolls)
        } else {
            context.getString(R.string.attack_failed, monster.name, playerDiceRolls)
        }

        val monsterAttackMessage = if (monsterAttackSuccessful) {
            context.getString(
                R.string.attack_successful,
                player.name,
                monster.getDiceRolls().joinToString(", ")
            )
        } else {
            context.getString(
                R.string.attack_failed,
                player.name,
                monster.getDiceRolls().joinToString(", ")
            )
        }

        _uiState.value = MainActivityUiState(
            player.name,
            player.health,
            monster.name,
            monster.health,
            attackButtonEnabled = player.isAlive() && monster.isAlive(),
            healButtonEnabled = player.isAlive() && monster.isAlive(),
            gameOver = !player.isAlive() || !monster.isAlive(),
            playerAttackMessage = playerAttackMessage,
            monsterAttackMessage = monsterAttackMessage
        )
    }

    fun healPlayer() {
        if (playerHealCount < maxPlayerHealCount) {
            if (player.health < 100) {
                player.healSelf()
                playerHealCount++
                updateUiState()
            }
        } else {
            _toastMessage.value = context.getString(R.string.heal_limit_reached)
        }
    }

    private fun updateUiState() {
        _uiState.value = MainActivityUiState(
            player.name,
            player.health,
            monster.name,
            monster.health,
            attackButtonEnabled = player.isAlive() && monster.isAlive(),
            healButtonEnabled = player.isAlive() && monster.isAlive(),
            gameOver = !player.isAlive() || !monster.isAlive(),
            playerAttackMessage = "",
            monsterAttackMessage = ""
        )
    }

    fun restartGame() {
        startNewGame(context.getString(R.string.player), context.getString(R.string.monster))
        playerHealCount = 0
        _toastMessage.value = null
    }

}
