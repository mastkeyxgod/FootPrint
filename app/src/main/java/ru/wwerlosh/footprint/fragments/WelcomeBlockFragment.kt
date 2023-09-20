package ru.wwerlosh.footprint.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import ru.wwerlosh.footprint.R

class WelcomeBlockFragment : Fragment() {
    private val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(),
        R.anim.welcome_fade_in
    )
    private var backButtonPressCount = 0
    private val requiredBackButtonPresses = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.welcome_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backButtonPressCount < requiredBackButtonPresses - 1) {
                    backButtonPressCount++
                } else {
                    isEnabled = false
                    requireActivity().finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        val welcomeLayout = view.findViewById<LinearLayout>(R.id.welcomeLayout)
        welcomeLayout.visibility = View.VISIBLE
        welcomeLayout.startAnimation(fadeInAnimation)
        val welcomeConfirmButton = view.findViewById<Button>(R.id.welcomeConfirmButton)
        welcomeConfirmButton.visibility = View.VISIBLE
        welcomeConfirmButton.startAnimation(fadeInAnimation)

        welcomeConfirmButton.setOnClickListener {
            val firstBlockFragment = FirstBlockFragment()
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.fade_out
            )
            fragmentTransaction.replace(R.id.fragmentContainer, firstBlockFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

    }
}