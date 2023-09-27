package quiz.example.headandhandstestcase.domain

class Monster(name: String, attack: Int = 0, defense: Int = 0, health: Int = 0, damageRange: IntRange = 0..0) :
    Creature(name, attack, defense, health, damageRange)