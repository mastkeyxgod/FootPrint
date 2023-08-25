package ru.wwerlosh.footprint

import android.graphics.Color
import android.os.Bundle
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
import androidx.fragment.app.Fragment
import ru.wwerlosh.footprint.util.GlobalData

class FifthBlockFragment : Fragment() {
    final val AIRCRAFT_COEFFICIENT = 0.2443

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fifthblock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layout = layoutInflater.inflate(R.layout.toast_layout, requireView().findViewById(R.id.toast_root))
        val toast = Toast(requireContext())
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout

        val inputAir = view.findViewById<EditText>(R.id.inputAir)
        val fifthButton = view.findViewById<Button>(R.id.fifthBlockConfirm)
        val airCheckBox = view.findViewById<CheckBox>(R.id.airCheckBox)
        val inputAirTextView = view.findViewById<TextView>(R.id.inputTrainTextView)

        val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        val fadeOutAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)

        airCheckBox.setOnClickListener {
            if (airCheckBox.isChecked) {
                inputAirTextView.startAnimation(fadeOutAnimation)
                inputAir.startAnimation(fadeOutAnimation)
                inputAirTextView.visibility = View.GONE
                inputAir.visibility = View.GONE
            } else {
                inputAirTextView.visibility = View.VISIBLE
                inputAir.visibility = View.VISIBLE
                inputAirTextView.startAnimation(fadeInAnimation)
                inputAir.startAnimation(fadeInAnimation)
            }
        }

        fifthButton.setOnClickListener {
            val inputAirText = inputAir.text.toString();

            if (airCheckBox.isChecked) {
                val sixthBlockFragment = SixthBlockFragment()
                val fragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentContainer, sixthBlockFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                return@setOnClickListener
            }

            if (inputAirText.isEmpty()) {
                val text = "*   Если вы совершали полёты, то стоит указать это, отметив общее время полётов за неделю. (в часах)"
                val spannable = SpannableString(text)

                val colorSpan1 = ForegroundColorSpan(Color.rgb(199, 54, 54)) // Цвет для части 1
                val colorSpan2 = ForegroundColorSpan(Color.WHITE) // Цвет для части 2

                spannable.setSpan(colorSpan1, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 1
                spannable.setSpan(colorSpan2, 2, 100, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Применение стиля к части 2
                inputAirTextView.text = spannable
                toast.show()
                return@setOnClickListener
            }
            val finalAirEmission = inputAirText.toInt() * AIRCRAFT_COEFFICIENT

            GlobalData.total += finalAirEmission

            val sixthBlockFragment = SixthBlockFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, sixthBlockFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

    }


}