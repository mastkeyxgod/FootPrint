package ru.wwerlosh.footprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class WelcomeBlockFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.welcome_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.welcome_fade_in)

        val welcomeLayout = view.findViewById<LinearLayout>(R.id.welcomeLayout)
        welcomeLayout.visibility = View.VISIBLE
        welcomeLayout.startAnimation(fadeInAnimation)
        val welcomeConfirmButton = view.findViewById<Button>(R.id.welcomeConfirmButton)
        welcomeConfirmButton.visibility = View.VISIBLE
        welcomeConfirmButton.startAnimation(fadeInAnimation)

        welcomeConfirmButton.setOnClickListener {
            val firstBlockFragment = FirstBlockFragment() // Замените FirstBlockFragment на ваш фрагмент
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(
                R.anim.fade_in, // Анимация появления для нового фрагмента
                R.anim.fade_out, // Анимация затухания для текущего фрагмента
                R.anim.fade_in, // Анимация появления для текущего фрагмента (обратная анимация)
                R.anim.fade_out // Анимация затухания для нового фрагмента (обратная анимация)
            )
            fragmentTransaction.replace(R.id.fragmentContainer, firstBlockFragment)
            fragmentTransaction.addToBackStack(null) // Добавьте фрагмент в стек возврата, если нужно
            fragmentTransaction.commit()
        }

    }
}