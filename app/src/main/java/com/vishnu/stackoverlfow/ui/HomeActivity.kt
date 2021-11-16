package com.vishnu.stackoverlfow.ui

import android.R.attr
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vishnu.stackoverlfow.R
import com.vishnu.stackoverlfow.data.ApiCallStatus
import com.vishnu.stackoverlfow.data.modelresponse.PostmanResponse
import com.vishnu.stackoverlfow.ui.adapter.ClickListener
import com.vishnu.stackoverlfow.ui.adapter.DataAdapter
import kotlinx.android.synthetic.main.activity_home.*
import android.content.Intent
import android.net.Uri
import android.view.inputmethod.EditorInfo

import android.widget.TextView

import android.widget.TextView.OnEditorActionListener

import android.R.attr.password
import android.content.Context
import android.net.ConnectivityManager
import android.view.Gravity
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.vishnu.stackoverlfow.utils.AppDialog
import com.vishnu.stackoverlfow.utils.isNetworkAvailable
import java.lang.Exception


class HomeActivity : AppCompatActivity(),ClickListener {
    var order:String="desc"
    var sort:String="activity"
    var site:String="stackoverflow"

    var showProgress=true

    private  lateinit var appDialog: AppDialog;
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        appDialog= AppDialog(this)
        viewModel=ViewModelProvider(this).get(HomeViewModel::class.java)

        observe()
        viewModel.getData(order,sort,site,"")

        if(!isNetworkAvailable(this)){
            val myToast = Toast.makeText(applicationContext,"No network connection",Toast.LENGTH_SHORT)
            myToast.show()
        }

        et_search.doOnTextChanged { text, start, count, after ->
            showProgress=false
            viewModel.getData(order,sort,site,text.toString())


        }

        et_search.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->

            showProgress=true

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.getData(order,sort,site,et_search.text.toString())
                true
            } else false
        })


        iv_filter.setOnClickListener {
            if(fl_bottom.isVisible)
            {
                fl_bottom.visibility=View.GONE

            }else{
                fl_bottom.visibility=View.VISIBLE

            }
        }

        fl_bottom.setOnClickListener {
            if(fl_bottom.isVisible)
            {
                fl_bottom.visibility=View.GONE

            }else{
                fl_bottom.visibility=View.VISIBLE

            }
        }

        ll_python.setOnClickListener {
            viewModel.getData(order,sort,site,"python")
            ll_python.setBackgroundColor(resources.getColor(R.color.black))
            ll_model.setBackgroundColor(resources.getColor(R.color.white))
            ll_django.setBackgroundColor(resources.getColor(R.color.white))

            tv_python.setTextColor(resources.getColor(R.color.white))
            tv_model.setTextColor(resources.getColor(R.color.black))
            tv_django.setTextColor(resources.getColor(R.color.black))

        }

        ll_django.setOnClickListener {
            viewModel.getData(order,sort,site,"django")
            ll_django.setBackgroundColor(resources.getColor(R.color.black))
            ll_python.setBackgroundColor(resources.getColor(R.color.white))
            ll_model.setBackgroundColor(resources.getColor(R.color.white))

            tv_python.setTextColor(resources.getColor(R.color.black))
            tv_model.setTextColor(resources.getColor(R.color.black))
            tv_django.setTextColor(resources.getColor(R.color.white))
        }

        ll_model.setOnClickListener {
            viewModel.getData(order,sort,site,"model")
            ll_model.setBackgroundColor(resources.getColor(R.color.black))
            ll_django.setBackgroundColor(resources.getColor(R.color.white))
            ll_python.setBackgroundColor(resources.getColor(R.color.white))

            tv_python.setTextColor(resources.getColor(R.color.black))
            tv_model.setTextColor(resources.getColor(R.color.white))
            tv_django.setTextColor(resources.getColor(R.color.black))
        }

        ll_clear.setOnClickListener {
            viewModel.getData(order,sort,site,"")

            ll_model.setBackgroundColor(resources.getColor(R.color.white))
            ll_django.setBackgroundColor(resources.getColor(R.color.white))
            ll_python.setBackgroundColor(resources.getColor(R.color.white))

            tv_python.setTextColor(resources.getColor(R.color.black))
            tv_model.setTextColor(resources.getColor(R.color.black))
            tv_django.setTextColor(resources.getColor(R.color.black))


        }

    }

    fun observe(){
        viewModel.apply {
            postmanstatus.observe(this@HomeActivity, Observer {
                when(it.status){
                    ApiCallStatus.LOADING->{
                        if(showProgress){
                            appDialog.show_dialog()

                        }else{
                            appDialog.dismiss_dialog()

                        }
                        fl_bottom.visibility=View.GONE

                    }
                    ApiCallStatus.SUCCESS->{
                        appDialog.dismiss_dialog()

                        fl_bottom.visibility=View.GONE

                        it.data?.items?.let {
                            rv_adapter.apply {
                                layoutManager=LinearLayoutManager(this@HomeActivity,RecyclerView.VERTICAL,false)
                                adapter=DataAdapter(it,this@HomeActivity)

                            }
                        }

                    }
                    ApiCallStatus.ERROR->{
                        appDialog.dismiss_dialog()
                        fl_bottom.visibility=View.GONE
                        val myToast = Toast.makeText(applicationContext,it.errorMessage,Toast.LENGTH_SHORT)
                        myToast.setGravity(Gravity.LEFT,200,200)
                        myToast.show()

                    }
                }
            })
        }
    }


    override fun onClickListener(data: PostmanResponse.Item) {
        val url =data.link
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}