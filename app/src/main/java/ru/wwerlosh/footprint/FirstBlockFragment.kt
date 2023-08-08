package ru.wwerlosh.footprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class FirstBlockFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_firstblock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получение ссылок на View-элементы вашего макета
        val inputNameSpace = view.findViewById<EditText>(R.id.inputNameSpace)
        val inputTownSpace = view.findViewById<EditText>(R.id.inputTownSpace)
        val firstInformationConfirmButton = view.findViewById<Button>(R.id.firstInformationConfirmButton)
        // Обработка нажатия кнопки "Подтвердить"
        firstInformationConfirmButton.setOnClickListener {
            val name = inputNameSpace.text.toString()
            val town = inputTownSpace.text.toString()
            // Вы можете обработать или сохранить данные по вашему усмотрению
            // Например, передать их в другой фрагмент или активити
            val secondBlockFragment = SecondBlockFragment()

            // Получение FragmentManager активити
            val fragmentManager = requireActivity().supportFragmentManager

            // Начало транзакции фрагмента
            val fragmentTransaction = fragmentManager.beginTransaction()

            // Замена текущего фрагмента на второй фрагмент
            fragmentTransaction.replace(R.id.fragmentContainer, secondBlockFragment)

            // Добавление транзакции в стек возврата (опционально)
            fragmentTransaction.addToBackStack(null)

            // Завершение транзакции
            fragmentTransaction.commit()
        }
    }
}