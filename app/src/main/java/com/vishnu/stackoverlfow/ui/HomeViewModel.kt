package com.vishnu.stackoverlfow.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vishnu.stackoverlfow.data.ApiCallStatus
import com.vishnu.stackoverlfow.data.ApiMapper
import com.vishnu.stackoverlfow.data.modelresponse.PostmanResponse
import com.vishnu.stackoverlfow.data.repo.Repository
import com.vishnu.stackoverlfow.database.StackDatabase
import com.vishnu.stackoverlfow.database.StackEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(val app :Application):AndroidViewModel(app) {
    var postmanstatus=MutableLiveData<ApiMapper<PostmanResponse>>()
    val dao = StackDatabase.getDatabase(app).getStackDao()

    val repository=Repository(dao,app)

    val allStackdata : LiveData<List<StackEntity>> =  repository.allStackData

    fun addData(stackEntity: StackEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(stackEntity)
    }


    fun getData( order:String,sort:String, site:String,tagged:String){
        postmanstatus.value=ApiMapper(ApiCallStatus.LOADING,null,null)

        repository.getData(order,sort,site,tagged ){status, message, result ->
            when(status){
                true->{
                    postmanstatus.value=ApiMapper(ApiCallStatus.SUCCESS,result,null)

                }
                false->{
                    postmanstatus.value=ApiMapper(ApiCallStatus.SUCCESS,result,null)

                }

            }
        }

    }
}