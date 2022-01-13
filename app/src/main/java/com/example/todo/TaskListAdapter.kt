package com.example.todo

import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.TaskListAdapter.TaskViewHolder
import androidx.recyclerview.widget.DiffUtil
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getColor


class TaskListAdapter : ListAdapter<Task, TaskViewHolder>(diffCallback) {

    lateinit var tex:TextView



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder{
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recyclerview, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        with(getItem(position)){
            holder.taskItemView.text = task
//            holder.taskPrio.text = priority.toString()

            holder.itemView.setOnClickListener(){
                holder.textDate.visibility = if (holder.textDate.visibility == View.VISIBLE) View
                    .GONE else View.VISIBLE
                holder.textDate.text = dateS
            }







            when(priority){
                R.id.chip1 -> holder.taskPrio.setBackgroundResource(R.drawable.red)
                R.id.chip2 -> holder.taskPrio.setBackgroundResource(R.drawable.yellow)
                R.id.chip3 -> holder.taskPrio.setBackgroundResource(R.drawable.green)
            }

        }

    }


    fun getTaskAt(position: Int) = getItem(position)

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskItemView: TextView = itemView.findViewById(R.id.textView)
        val taskPrio : LinearLayout = itemView.findViewById(R.id.prio)
        val textDate : TextView = itemView.findViewById(R.id.hold)

    }

}

//fun onCheckboxClicked(isChecked: Boolean, holder: TaskViewHolder) {
//    if (isChecked) {
//        holder.taskItemView.setTextColor(Color.parseColor("#ffffff"))
//    } else {
//        holder.taskItemView.setTextColor(Color.parseColor("#000000"))
//    }
//}


private val diffCallback = object : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Task, newItem: Task) =
        oldItem.task == newItem.task
                && oldItem.priority == newItem.priority
}