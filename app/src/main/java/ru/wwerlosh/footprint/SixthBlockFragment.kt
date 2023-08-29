package ru.wwerlosh.footprint

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import ru.wwerlosh.footprint.util.GlobalData

class SixthBlockFragment : Fragment() {
    private var backButtonPressCount = 0
    private val requiredBackButtonPresses = 2
    final val TRAIN_COEFFICIENT = 0.03694
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sixthblock, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layout2 = layoutInflater.inflate(R.layout.toast_exit, requireView().findViewById(R.id.toast_exit_root))
        val toastExit = Toast(requireContext())
        toastExit.duration = Toast.LENGTH_SHORT
        toastExit.view = layout2
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val timer = object: CountDownTimer(5000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {}

                    override fun onFinish() {
                        backButtonPressCount = 0
                    }
                }
                timer.start()
                if (backButtonPressCount < requiredBackButtonPresses - 1) {
                    backButtonPressCount++
                    toastExit.show()
                } else {
                    isEnabled = false
                    requireActivity().finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        val layout = layoutInflater.inflate(R.layout.toast_layout, requireView().findViewById(R.id.toast_root))
        val toast = Toast(requireContext())
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout

        val sixthButton = view.findViewById<Button>(R.id.seventhBlockConfirm)
        val inputTrain = view.findViewById<EditText>(R.id.inputMoneyForGoods)
        val trainCheckBox = view.findViewById<CheckBox>(R.id.trainCheckBox)
        val inputTrainTextView = view.findViewById<TextView>(R.id.inputTrainTextView)

        val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        val fadeOutAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)

        trainCheckBox.setOnClickListener {
            if (trainCheckBox.isChecked) {
                inputTrainTextView.startAnimation(fadeOutAnimation)
                inputTrain.startAnimation(fadeOutAnimation)
                inputTrainTextView.visibility = View.GONE
                inputTrain.visibility = View.GONE
            } else {
                inputTrainTextView.visibility = View.VISIBLE
                inputTrain.visibility = View.VISIBLE
                inputTrainTextView.startAnimation(fadeInAnimation)
                inputTrain.startAnimation(fadeInAnimation)
            }
        }

        sixthButton.setOnClickListener {
            val inputTrainText = inputTrain.text.toString()

            if (trainCheckBox.isChecked) {
                val seventhBlockFragment = SeventhBlockFragment()
                val fragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.setCustomAnimations(
                    R.anim.fade_in, // Анимация появления для нового фрагмента
                    R.anim.fade_out, // Анимация затухания для текущего фрагмента
                    R.anim.fade_in, // Анимация появления для текущего фрагмента (обратная анимация)
                    R.anim.fade_out // Анимация затухания для нового фрагмента (обратная анимация)
                )
                fragmentTransaction.replace(R.id.fragmentContainer, seventhBlockFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                return@setOnClickListener
            }

            if (inputTrainText.isEmpty()) {
                val text = "*   Если вы совершали поездки, то стоит указать это, отметив общее время, проведенное в пути за неделю. (в часах)"
                val spannable = SpannableString(text)

                val colorSpan1 = ForegroundColorSpan(Color.rgb(199, 54, 54)) // Цвет для части 1
                val colorSpan2 = ForegroundColorSpan(Color.WHITE) // Цвет для части 2

                spannable.setSpan(colorSpan1, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 1
                spannable.setSpan(colorSpan2, 2, 112, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 2
                inputTrainTextView.text = spannable
                toast.show()
                return@setOnClickListener
            }

            val finalTrainEmission = inputTrainText.toInt() * TRAIN_COEFFICIENT

            GlobalData.total += finalTrainEmission

            val seventhBlockFragment = SeventhBlockFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(
                R.anim.fade_in, // Анимация появления для нового фрагмента
                R.anim.fade_out, // Анимация затухания для текущего фрагмента
                R.anim.fade_in, // Анимация появления для текущего фрагмента (обратная анимация)
                R.anim.fade_out // Анимация затухания для нового фрагмента (обратная анимация)
            )
            fragmentTransaction.replace(R.id.fragmentContainer, seventhBlockFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}