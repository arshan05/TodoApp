package com.example.todo

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.newTask.Companion.EXTRA_PRIORITY
import com.example.todo.newTask.Companion.EXTRA_TITLE
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception
import android.graphics.Paint
import android.widget.TextView
import com.example.todo.newTask.Companion.EXTRA_DATE

class MainActivity : AppCompatActivity() {
    lateinit var adapterTask : TaskListAdapter
    lateinit var recyclerView: RecyclerView
    private val newTaskActivityRequestCode = 1

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as TaskApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        var check = findViewById<CheckBox>(R.id.checkBox)
//

//






        recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        adapterTask = TaskListAdapter()
        recyclerView.adapter = adapterTask
        recyclerView.layoutManager = LinearLayoutManager(this)

        setUpListener()

        taskViewModel.allTasks.observe(this) { tasks ->
            tasks?.let { adapterTask.submitList(it) }
        }

        val addTask = findViewById<FloatingActionButton>(R.id.addTask)
        addTask.setOnClickListener() {
            val intent = Intent(this@MainActivity, newTask::class.java)
            startActivityForResult(intent, newTaskActivityRequestCode)
        }


    }




    private fun setUpListener() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, LEFT or RIGHT ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }



            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Delete")
                builder.setMessage("are you sure?")
                builder.setNegativeButton("yes"){
                    dialog,which->
                    val task = adapterTask.getTaskAt(viewHolder.adapterPosition)
                    taskViewModel.delete(task)
                }
                builder.setNeutralButton("no"){
                    dialog,which->
                    adapterTask.notifyItemChanged(viewHolder.getAdapterPosition())
                }

                builder.setCancelable(false)
                val aD:AlertDialog = builder.create()
                aD.show()

//                val mySnackbar = Snackbar.make(findViewById(R.id.mainLay),
//                    "are you sure to delete?", Snackbar.LENGTH_LONG)
//
//                mySnackbar.setAction("yes", View.OnClickListener {
//                    val task = adapterTask.getTaskAt(viewHolder.adapterPosition)
//                    taskViewModel.delete(task)
//                })
//                mySnackbar.setAction("no", View.OnClickListener {
//                    adapterTask.notifyItemChanged(viewHolder.getAdapterPosition())
//                })
//                mySnackbar.show()
//                adapterTask.notifyItemChanged(viewHolder.getAdapterPosition());
//                val task = adapterTask.getTaskAt(viewHolder.adapterPosition)
//                taskViewModel.delete(task)


            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                try {
                    val icon: Bitmap
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        val itemView = viewHolder.itemView
                        val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                        val width = height / 10
                        viewHolder.itemView.translationX = dX / 3

//                        val icon_dest = RectF(
//                            (itemView.right + dX / 7),
//                            itemView.top
//                                .toFloat() + width,
//                            itemView.right.toFloat() + dX / 20,
//                            itemView.bottom.toFloat() - width
//                        )
                    } else {
                        super.onChildDraw(
                            c,
                            recyclerView!!, viewHolder, dX, dY, actionState, isCurrentlyActive
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }).attachToRecyclerView(recyclerView)
    }

//    fun onCheckboxClicked(view: View){
//        var textItem = findViewById<TextView>(R.id.textView)
//        if(view is CheckBox){
//            val checked: Boolean = view.isChecked
//            if(checked){
//                textItem.setTextColor(resources.getColor(R.color.primaryDarkColor))
//            }
//            else{
//                textItem.setTextColor(resources.getColor(R.color.black))
//            }
//        }
//    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null && requestCode == newTaskActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val title: String? = data.getStringExtra(EXTRA_TITLE)
            val priority: Int = data.getIntExtra(EXTRA_PRIORITY, -1)
            val dateS:String? = data.getStringExtra(EXTRA_DATE)
            title?.let { dateS?.let { it1 -> Task(it,priority, it1) } }?.let { taskViewModel.insert(it) }
            Toast.makeText(this, "Note inserted!", Toast.LENGTH_SHORT).show()

        }else {
            Toast.makeText(this, "Note not saved!", Toast.LENGTH_SHORT).show()
        }
    }
}

