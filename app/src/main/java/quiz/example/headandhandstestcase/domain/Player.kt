package quiz.example.headandhandstestcase.domain

class Player(
    name: String,
    attack: Int = 0,
    defense: Int = 0,
    health: Int = 0,
    damageRange: IntRange = 0..0
) :
    Creature(name, attack, defense, health, damageRange) {

    fun healSelf() {
        if (health > 0) {
            heal()
        }
    }
}