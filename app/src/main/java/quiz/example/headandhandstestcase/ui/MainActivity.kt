package quiz.example.headandhandstestcase.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

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

        // Инициализация ViewModel
        viewModel.initializeGame("Игрока", "Монстра")

        binding.attackButton.setOnClickListener {
            viewModel.attackMonster()
        }

        binding.healButton.setOnClickListener {
            viewModel.healPlayer()
        }

        // Наблюдение за состоянием игры и обновление UI
        viewModel.uiState.observe(this) { state ->
            binding.apply {
                playerHealthTextView.text = "Здоровье ${state.playerName}: ${state.playerHealth}"
                monsterHealthTextView.text = "Здоровье ${state.monsterName}: ${state.monsterHealth}"
                gameLogTextView.text = state.gameLog

                attackButton.isEnabled = state.attackButtonEnabled
                healButton.isEnabled = state.healButtonEnabled

                if (state.gameOver) {
                    if (state.playerHealth <= 0) {
                        gameLogTextView.append("\nВы проиграли!")
                    } else {
                        gameLogTextView.append("\nВы победили!")
                    }
                    attackButton.isEnabled = false
                    healButton.isEnabled = false
                }
            }
        }
    }
}


