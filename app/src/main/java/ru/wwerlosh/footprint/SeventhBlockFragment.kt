package ru.wwerlosh.footprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.wwerlosh.footprint.util.GlobalData

class SeventhBlockFragment : Fragment() {
    val SENIOR_MEAT = 0.0003897
    val MIDDLE_MEAT = 0.0003051
    val JUNIOR_MEAT = 0.0002530
    val FISH = 0.0002118
    val VEGATARIAN = 0.0002066
    val VEGAN = 0.0001566

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_seventhblock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputMoneyForGoods = view.findViewById<EditText>(R.id.inputMoneyForGoods)
        val goodsRadioGroup = view.findViewById<RadioGroup>(R.id.goodsRadioGroup)
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

            if (money.isEmpty()) {
                inputMoneyForGoods.setBackgroundResource(R.drawable.spinner_border_red)
                inputMoneyForGoods.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                inputMoneyForGoods.hint = "Введите сумму"
                return@setOnClickListener
            } else {
                inputMoneyForGoods.setBackgroundResource(R.drawable.spinner_border)
                inputMoneyForGoods.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }

            if (typeOfGoods == 0.0) {
                goodsRadioGroup.setBackgroundResource(R.drawable.spinner_border_red)
                return@setOnClickListener
            } else {
                goodsRadioGroup.setBackgroundResource(R.drawable.spinner_border)
            }

            val finalEmission = typeOfGoods * money.toDouble() * 4.0

            GlobalData.total += finalEmission

            val eigthBlockFragment = EightBlockFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, eigthBlockFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}