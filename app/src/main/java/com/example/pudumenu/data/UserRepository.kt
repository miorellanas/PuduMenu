package com.example.pudumenu.data

import com.example.pudumenu.data.model.User

/**
 * A simple in-memory repository for storing user data.
 * This is a singleton, so only one instance will exist in the app.
 */
object UserRepository {

    val users = mutableListOf<User>()

    init {
        val testUser = User(email = "tester@gmail.com", firstName = "tester", lastName = "tester", password = "abc123")
        users.add(testUser)
    }

    fun addUser(user: User): Boolean {
        if (findUserByEmail(user.email) != null) {
            return false // User already exists
        }
        users.add(user)
        return true
    }

    fun findUserByEmail(email: String): User? {
        return users.find { it.email.equals(email, ignoreCase = true) }
    }
}
