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
    // Свойство для хранения результатов бросков кубика
    private var diceRolls: List<Int> = emptyList()

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


    fun attackTarget(target: Creature): Boolean {
        val modifier = calculateModifier(target) // Рассчитываем модификатор атаки

        val playerDiceRoll = Random.nextInt(1, 7) // Бросок кубика для игрока

        val playerAttackSuccessful = playerDiceRoll >= modifier // Сравниваем с модификатором

        if (playerAttackSuccessful) {
            val damage = Random.nextInt(damageRange)
            target.takeDamage(damage)
        }

        diceRolls = listOf(playerDiceRoll)

        return playerAttackSuccessful
    }
    private fun calculateModifier(target: Creature): Int {
        val modifier = attack - target.defense + 1
        return maxOf(1, modifier)
    }

    // Получить результаты броска кубика
    fun getDiceRolls(): List<Int> {
        return diceRolls
    }
}





