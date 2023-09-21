package ru.wwerlosh.footprint.util

import ru.wwerlosh.footprint.data.User
import java.sql.DriverManager
import java.sql.SQLException

object SQLHelper {
    @JvmStatic
    fun connection(user: User) {
        try {
            val c = DriverManager.getConnection(
                "BDUrl",
                "login",
                "password"
            )
            val s = c.createStatement()
            val sql = "INSERT INTO user (total, name, town, age, sex) VALUES ('${user.total}', '${user.name}', '${user.town}', '${user.age}', '${user.sex}')"
            s.executeUpdate(sql)
            s.close()
            c.close()
            println("OK!")
        }catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}
