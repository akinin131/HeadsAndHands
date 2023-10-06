package quiz.example.headandhandstestcase.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import quiz.example.headandhandstestcase.R

import quiz.example.headandhandstestcase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupViews()
        observeViewModel()

        viewModel.startNewGame(getString(R.string.player), getString(R.string.monster))
    }

    private fun setupViews() {
        binding.attackButton.setOnClickListener {
            viewModel.attackMonster()
        }

        binding.healButton.setOnClickListener {
            viewModel.healPlayer()
        }
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(this) { state ->
            updateUI(state)
        }

        viewModel.toastMessage.observe(this) { message ->
            if (!message.isNullOrEmpty()) {
                showToast(message)
            }
        }

        binding.restartButton.setOnClickListener {
            viewModel.restartGame()
        }

    }

    private fun updateUI(state: MainActivityUiState) {
        binding.apply {
            playerHealthProgressBar.progress = state.playerHealth
            monsterHealthProgressBar.progress = state.monsterHealth

            playerAttackTextView.text = state.playerAttackMessage
            monsterAttackTextView.text = state.monsterAttackMessage

            attackButton.isEnabled = state.attackButtonEnabled
            healButton.isEnabled = state.healButtonEnabled

            if (state.gameOver) {
                val gameOverMessage = if (state.playerHealth <= 0) {
                    getString(R.string.you_lose)
                } else {
                    getString(R.string.you_win)
                }
                showToast(gameOverMessage)
                attackButton.isEnabled = false
                healButton.isEnabled = false
            }
            restartButton.visibility = if (state.gameOver) View.VISIBLE else View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}


