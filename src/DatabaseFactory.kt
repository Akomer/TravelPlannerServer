package com.example

import com.example.model.User
import com.example.model.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction


object DatabaseFactory {

    fun init() {
        Database.connect(hikari())
        transaction {
            create(Users)
            User.new {
                name = "Test Pista"
                userName = "uNamePista"
            }
        }
    }

    private fun hikari(): HikariDataSource {
        return HikariDataSource(HikariConfig().apply {
            driverClassName = "org.h2.Driver"
            jdbcUrl = "jdbc:h2:mem:test"
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        })
    }
}