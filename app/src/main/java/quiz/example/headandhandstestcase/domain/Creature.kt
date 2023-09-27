package quiz.example.headandhandstestcase.domain

import kotlin.random.nextInt
import kotlin.random.Random

// Базовый класс для всех существ (Игрок и Монстр)
open class Creature(
    val name: String,        // Имя существа
    private val attack: Int = 0,        // Параметр атаки
    private val defense: Int = 0,      // Параметр защиты
    var health: Int = 0,              // Здоровье
    private val damageRange: IntRange = 0..0,  // Диапазон урона
) {
    // Проверка на живучесть существа
    fun isAlive(): Boolean {
        return health > 0
    }

    // Подсчет урона и вычитание его из здоровья
    private fun takeDamage(damage: Int) {
        health -= damage
        if (health < 0) {
            health = 0
        }
    }

    // Исцеление существа
    fun heal() {
        val maxHeal = (health * 0.3).toInt()   // Максимальное исцеление (30% от текущего здоровья)
        val healAmount = Random.nextInt(1, maxHeal + 1) // Случайное количество исцеления
        health += healAmount
        if (health > 100) {
            health = 100  // Здоровье не может превышать 100
        }
    }

    // Расчет модификатора атаки при атаке другого существа
    private fun calculateModifier(target: Creature): Int {
        return maxOf(1, attack - target.defense + 1)
    }

    // Атака другого существа
    fun attackTarget(target: Creature): Boolean {
        val modifier = calculateModifier(target)

        // Бросаем N кубиков для атаки игрока и монстра
        val playerDiceRolls = List(modifier) { Random.nextInt(1, 7) }
        val monsterDiceRolls = List(modifier) { Random.nextInt(1, 7) }

        // Определяем успешность удара для игрока и монстра
        val playerAttackSuccessful = 5 in playerDiceRolls || 6 in playerDiceRolls
        val monsterAttackSuccessful = 5 in monsterDiceRolls || 6 in monsterDiceRolls

        if (playerAttackSuccessful) {
            val damage = Random.nextInt(damageRange)
            target.takeDamage(damage)
        }

        return playerAttackSuccessful
    }

}





