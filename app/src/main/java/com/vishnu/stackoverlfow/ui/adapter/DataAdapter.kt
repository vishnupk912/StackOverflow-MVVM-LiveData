package com.vishnu.stackoverlfow.ui.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vishnu.stackoverlfow.R
import com.vishnu.stackoverlfow.data.modelresponse.PostmanResponse
import com.vishnu.stackoverlfow.utils.dateFormat
import kotlinx.android.synthetic.main.listitem.view.*
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide


class DataAdapter(val  data:List<PostmanResponse.Item>,private val clickListener: ClickListener):
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    open class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.listitem,parent,false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            tv_heading.text=data[position].title
            tv_decription.text=data[position].owner.displayName

            tv_viewcount.text=data[position].viewCount.toString()
            tv_answercount.text=data[position].answerCount.toString()
            val date = Instant.ofEpochSecond(data[position].creationDate.toLong())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()

            Glide.with(this)
                .load(data[position].owner.profileImage)
                .into(iv_profileimage)

            val formatteddate=dateFormat(date.toString())
            tv_postedOn.text="Posted on : $formatteddate"
            cl_item.setOnClickListener {
                clickListener.onClickListener(data[position])
            }
        }

    }

    override fun getItemCount(): Int {
      return  data.size
    }

}

interface ClickListener{
    fun onClickListener(data: PostmanResponse.Item)
}