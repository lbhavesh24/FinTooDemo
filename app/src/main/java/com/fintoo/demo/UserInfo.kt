package com.fintoo.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.fintoo.demo.Utils.getDate
import com.fintoo.demo.databinding.FragmentUserInfoBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserInfo.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserInfo : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding:FragmentUserInfoBinding? = null
    private val investmentForList = arrayOf("Self", "Wife", "Family")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.dateOfPurchaseEdit.isClickable = true
        binding!!.dateOfPurchaseEdit.isFocusable = false
        binding!!.dateOfPurchaseEdit.isCursorVisible = false
        binding!!.dateOfPurchaseEdit.setOnClickListener{openDatePicker()}
        binding!!.investmentForLy.editText!!.inputType = 0
        val adapter = ArrayAdapter(requireActivity(), R.layout.text_list_item, investmentForList)
        (binding!!.investmentForLy.editText as AutoCompleteTextView?)!!.setAdapter(adapter)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserInfo.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserInfo().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun openDatePicker(){
        // set minimum date
        val minCalendar = Calendar.getInstance()
        minCalendar.add(Calendar.YEAR, -100)
        minCalendar.add(Calendar.DATE, - 1)
        val startYear = minCalendar.timeInMillis

        // set maximum date
        val maxCalendar = Calendar.getInstance()
        maxCalendar.add(Calendar.DATE,-1)
        val endYear = maxCalendar.timeInMillis

        // Build constraints.
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setStart(startYear)
                .setEnd(endYear)
                .setOpenAt(endYear)
                .setValidator(DateValidatorPointBackward.before(endYear))

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date Of Purchase")
                .setSelection(endYear)
                .setCalendarConstraints(constraintsBuilder.build())
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .build()

        datePicker.show(requireActivity().supportFragmentManager,"Picker")

        datePicker.addOnPositiveButtonClickListener {
            // Respond to positive button click.
            binding!!.dateOfPurchaseEdit.setText(getDate(datePicker.selection!!,"MM-dd-yyyy"))
        }
        datePicker.addOnNegativeButtonClickListener {
            // Respond to negative button click.
            datePicker.dismiss()
        }
        datePicker.addOnCancelListener {
            // Respond to cancel button click.
            it.cancel()
        }
        datePicker.addOnDismissListener {
            // Respond to dismiss events.
            it.dismiss()
        }
    }
}