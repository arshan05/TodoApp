package com.example.todo

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.circularreveal.CircularRevealRelativeLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.util.*



class newTask : AppCompatActivity() {
    internal var category = arrayOf("Work", "Personal", "Hustle", "Other")
    internal var images =
        intArrayOf(R.drawable.work, R.drawable.personal, R.drawable.hustle, R.drawable.other)

    lateinit var datePick: TextView
    var day = 0
    var month: Int = 0
    var year: Int = 0
    var dateString: String? = ""

    lateinit var editWordView : TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)

        var expand = findViewById<CircularRevealRelativeLayout>(R.id.expand)
        var addTask = findViewById<FloatingActionButton>(R.id.addTask)
        var sub = findViewById<MaterialButton>(R.id.submitBtn)
        editWordView  = findViewById<TextInputEditText>(R.id.edit_word)
        var closeBtn = findViewById<MaterialButton>(R.id.closeBtn)
        datePick = findViewById<Chip>(R.id.datePick)
        var high = findViewById<Chip>(R.id.chip1)
        var medium = findViewById<Chip>(R.id.chip2)
        var low = findViewById<Chip>(R.id.chip3)
        var chipGroup = findViewById<ChipGroup>(R.id.priority)


        var monthName = arrayListOf<String>(
            "Jan", "Feb",
            "Mar", "Apr", "May", "Jun", "Jul",
            "Aug", "Sept", "Oct", "Nov",
            "Dec"
        )

        val calendar: Calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        dateString = String.format("%s %d, %d",monthName[month], day, year)

        datePick.setText("today")


        closeBtn.setOnClickListener() {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(it.windowToken, 0)
            finish()
        }

        datePick.setOnClickListener() {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)


            val datePickerDialog =
                DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(
                        view: DatePicker?,
                        year: Int,
                        month: Int,
                        dayOfMonth: Int
                    ) {
//                        dateString = String.format("%s %d, %d",monthName[month], dayOfMonth, year)
                        if (dayOfMonth == day) {
                            datePick.setText("today")
                        } else {
                            dateString = String.format("%s %d, %d",monthName[month], dayOfMonth, year)
                            datePick.setText(
                                String.format(
                                    "%s %d, %d",
                                    monthName[month],
                                    dayOfMonth,
                                    year
                                )
                            )
                        }
                    }
                }, year, month, day)

            datePickerDialog.show()
        }

        sub.setOnClickListener() {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
//                val word = editWordView.text.toString()
//                replyIntent.putExtra(EXTRA_REPLY, word)
//                setResult(Activity.RESULT_OK, replyIntent
                var taskT = editWordView.text.toString()
//                when(chipGroup.checkedChipId){
//
//                }
                var ds = dateString.toString()
                var prioId = chipGroup.checkedChipId
                replyIntent.putExtra(EXTRA_TITLE,taskT)
                replyIntent.putExtra(EXTRA_PRIORITY,prioId)
                replyIntent.putExtra(EXTRA_DATE,ds)
                setResult(Activity.RESULT_OK, replyIntent)


            }
            finish()

        }

        high.setOnClickListener() {
            if (high.isChecked) {
                high.setText("high")
                medium.setText("")
                low.setText("")
            } else {
                high.setText("")
            }
        }
        medium.setOnClickListener() {
            if (medium.isChecked) {
                high.setText("")
                medium.setText("medium")
                low.setText("")
            } else {
                medium.setText("")
            }
        }
        low.setOnClickListener() {
            if (low.isChecked) {
                high.setText("")
                medium.setText("")
                low.setText("low")
            } else {
                low.setText("")
            }
        }

        val spin = findViewById<View>(R.id.categorySpinner) as Spinner
        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        val spinAdapter = spinnerAdapter(applicationContext, images, category)
        spin.adapter = spinAdapter
    }


        companion object {
            const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
            const val EXTRA_ID = " com.example.todo.EXTRA_ID"
            const val EXTRA_TITLE = " com.example.todo.EXTRA_TITLE"
            const val EXTRA_DESCRIPTION = " com.example.todo.EXTRA_DESCRIPTION"
            const val EXTRA_PRIORITY = " com.example.todo.EXTRA_PRIORITY"
            const val EXTRA_DATE = "com.example.todo.EXTRA_DATE"
        }

}