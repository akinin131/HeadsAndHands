package quiz.example.headandhandstestcase.domain

import kotlin.math.min
import kotlin.random.nextInt
import kotlin.random.Random

open class Creature(
    val name: String,
    private val attack: Int = 0,
    private val defense: Int = 0,
    var health: Int = 0,
    private val damageRange: IntRange = 0..0,
) {

    private var diceRolls: List<Int> = emptyList()

    fun isAlive(): Boolean {
        return health > 0
    }

    private fun takeDamage(damage: Int) {
        health -= damage
        if (health < 0) {
            health = 0
        }
    }

    fun heal() {
        if (health < 100) {
            val maxHeal = (health * 0.3).toInt()
            val healAmount = min(maxHeal, 100 - health)
            health += healAmount
        }
    }

    fun attackTarget(target: Creature): Boolean {

        val modifier = attack - target.defense + 1
        val diceRolls = List(modifier) { Random.nextInt(1, 7) }
        val playerAttackSuccessful = diceRolls.any { it >= 5 }

        if (playerAttackSuccessful) {

            val damage = Random.nextInt(damageRange)
            target.takeDamage(damage)
        }

        this.diceRolls = diceRolls
        return playerAttackSuccessful
    }

    fun getDiceRolls(): List<Int> {

        return diceRolls
    }
}





