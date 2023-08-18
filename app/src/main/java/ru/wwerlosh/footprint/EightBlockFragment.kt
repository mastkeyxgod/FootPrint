package ru.wwerlosh.footprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        val totalEmissionTextView = view.findViewById<TextView>(R.id.totalEmissionTextView)
        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        val formattedTotal = String.format("%.1f", GlobalData.total)
        totalEmissionTextView.text = formattedTotal
        nameTextView.text = GlobalData.name


        writeToDatabase()
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