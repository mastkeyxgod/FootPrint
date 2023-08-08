package ru.wwerlosh.footprint

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // При запуске приложения добавляем первый фрагмент (firstBlockFragment) в контейнер
            val firstBlockFragment = FirstBlockFragment() // Замените FirstBlockFragment на ваш фрагмент
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, firstBlockFragment)
            fragmentTransaction.addToBackStack(null) // Добавьте фрагмент в стек возврата, если нужно
            fragmentTransaction.commit()
        }
    }
}
