package com.example.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class spinnerAdapter(internal var context: Context, internal var images: IntArray, internal var
categoryName: Array<String>): BaseAdapter() {

    internal var inflter: LayoutInflater
    init {
        inflter = LayoutInflater.from(context)
    }
    override fun getCount(): Int {
        return images.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {

        val view = inflter.inflate(R.layout.spinner_layout,null)
        val icon = view.findViewById<View>(R.id.imageView) as ImageView?
        val names = view.findViewById<View>(R.id.textView) as TextView?
        icon!!.setImageResource(images[i])
        names!!.text = categoryName[i]
        return view
    }

}