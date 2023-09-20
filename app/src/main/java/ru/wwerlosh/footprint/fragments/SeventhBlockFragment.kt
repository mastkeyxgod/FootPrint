package ru.wwerlosh.footprint.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import ru.wwerlosh.footprint.R
import ru.wwerlosh.footprint.util.GlobalData

class SeventhBlockFragment : Fragment() {
    private var backButtonPressCount = 0
    private val requiredBackButtonPresses = 2
    private val SENIOR_MEAT = 0.0003897
    private val MIDDLE_MEAT = 0.0003051
    private val JUNIOR_MEAT = 0.0002530
    private val FISH = 0.0002118
    private val VEGATARIAN = 0.0002066
    private val VEGAN = 0.0001566

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_seventhblock, container, false)
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

        GlobalData.isDataSaved = false

        val inputMoneyForGoods = view.findViewById<EditText>(R.id.inputMoneyForGoods)
        val goodsRadioGroup = view.findViewById<RadioGroup>(R.id.goodsRadioGroup)
        val goodsTextView = view.findViewById<TextView>(R.id.goodsTextView)
        val moneyForGoodsTextView = view.findViewById<TextView>(R.id.moneyForGoodsTextView)
        var typeOfGoods: Double = 0.0

        goodsRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.seniorMeatRadioButton -> {
                    typeOfGoods = SENIOR_MEAT
                }
                R.id.middleMeatRadioButton -> {
                    typeOfGoods = MIDDLE_MEAT
                }
                R.id.juniorMeatRadioButton -> {
                    typeOfGoods = JUNIOR_MEAT
                }
                R.id.fishRadioButton -> {
                    typeOfGoods = FISH
                }
                R.id.vegatarianRadioButton -> {
                    typeOfGoods = VEGATARIAN
                }
                R.id.veganRadioButton -> {
                    typeOfGoods = VEGAN
                }
            }
        }

        val seventhBlockConfirm = view.findViewById<Button>(R.id.seventhBlockConfirm)

        seventhBlockConfirm.setOnClickListener {
            val money = inputMoneyForGoods.text.toString()

            if (typeOfGoods == 0.0) {
                val text = "*   Кем вы себя считаете?"
                val spannable = SpannableString(text)

                val colorSpan1 = ForegroundColorSpan(Color.rgb(199, 54, 54))
                val colorSpan2 = ForegroundColorSpan(Color.WHITE)

                spannable.setSpan(colorSpan1, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannable.setSpan(colorSpan2, 2, 24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                goodsTextView.text = spannable
                toast.show()
                return@setOnClickListener
            }

            if (money.isEmpty()) {
                val text = "*   Сколько вы тратите на продукты в неделю? (руб)"
                val spannable = SpannableString(text)

                val colorSpan1 = ForegroundColorSpan(Color.rgb(199, 54, 54))
                val colorSpan2 = ForegroundColorSpan(Color.WHITE)

                spannable.setSpan(colorSpan1, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannable.setSpan(colorSpan2, 2, 50, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                moneyForGoodsTextView.text = spannable
                toast.show()
                return@setOnClickListener
            }

            val finalEmission = typeOfGoods * money.toDouble() * 4.0

            GlobalData.total += finalEmission

            val eigthBlockFragment = EightBlockFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.fade_out
            )
            fragmentTransaction.replace(R.id.fragmentContainer, eigthBlockFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }
    }
}