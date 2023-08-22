package ru.wwerlosh.footprint

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.wwerlosh.footprint.data.User
import ru.wwerlosh.footprint.util.GlobalData

class EightBlockFragment : Fragment() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_eigthblock, container, false)

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val lastBlockConfirm = view.findViewById<Button>(R.id.lastBlockConfirm)
        val totalEmissionTextView = view.findViewById<TextView>(R.id.totalEmissionTextView)
        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        val formattedTotal = String.format("%.1f", GlobalData.total)
        totalEmissionTextView.text = formattedTotal
        nameTextView.text = GlobalData.name

        // Получите объект SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)

// Получите редактор для редактирования данных
        val editor = sharedPreferences.edit()

// Сохраните данные
        editor.putString("name", GlobalData.name)
        editor.putInt("age", GlobalData.age)
        editor.putString("town", GlobalData.town)
        editor.putString("sex", GlobalData.sex)
        editor.putFloat("totalEmission", GlobalData.total.toFloat())

// Примените изменения
        editor.apply()


        if(!GlobalData.isDataSaved) {
            writeToDatabase()
            GlobalData.isDataSaved = true
        }


        lastBlockConfirm.setOnClickListener {
            val secondBlockFragment = SecondBlockFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, secondBlockFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun writeToDatabase() {
        val user = User(GlobalData.total, GlobalData.name, GlobalData.town, GlobalData.age, GlobalData.sex)
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {

                SQLHelper.connection(user)
            }
        }
    }


}