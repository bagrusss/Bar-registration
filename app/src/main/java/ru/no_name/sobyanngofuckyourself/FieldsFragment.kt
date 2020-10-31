package ru.no_name.sobyanngofuckyourself

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_fields.*
import kotlinx.android.synthetic.main.fragment_fields.address
import kotlinx.android.synthetic.main.fragment_ok.*
import java.text.SimpleDateFormat
import java.util.*

class FieldsFragment : Fragment(R.layout.fragment_fields) {

    private val navigator by lazy { findNavController() }
    private val df = SimpleDateFormat("dd.MM.yyyy kk.mm.ss", Locale.getDefault())
    private val calendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ok.setOnClickListener {
            val args = OkFragment.createArgs(
                    type = type.text.toString(),
                    name = name.text.toString(),
                    owner = owner.text.toString(),
                    address = address.text.toString(),
                    formattedTime = df.format(calendar.time)
            )
            navigator.navigate(R.id.action_Fields_to_Ok, args)
        }
    }
}