package ru.wwerlosh.footprint

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.wwerlosh.footprint.fragments.WelcomeBlockFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT




        if (savedInstanceState == null) {
            // При запуске приложения добавляем первый фрагмент (firstBlockFragment) в контейнер
            val welcomeBlockFragment = WelcomeBlockFragment() // Замените FirstBlockFragment на ваш фрагмент
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, welcomeBlockFragment)
            fragmentTransaction.addToBackStack(null) // Добавьте фрагмент в стек возврата, если нужно
            fragmentTransaction.commit()
        }
    }

//    override fun onBackPressed() {
//        if (supportFragmentManager.backStackEntryCount == 1
//            || supportFragmentManager.backStackEntryCount == 8) {
//            // Если на первом фрагменте, то закрыть приложение
//            finish()
//        }
//
//    }
}
