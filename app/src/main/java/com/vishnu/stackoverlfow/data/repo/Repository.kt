package com.vishnu.stackoverlfow.data.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.vishnu.stackoverlfow.data.ApiManger
import com.vishnu.stackoverlfow.data.modelresponse.PostmanResponse
import com.vishnu.stackoverlfow.database.StackDao
import com.vishnu.stackoverlfow.database.StackEntity
import com.vishnu.stackoverlfow.utils.awaitResponse

class Repository(private  val  dao: StackDao, val context: Context) {
    private val api = ApiManger(context).api


    val allStackData: LiveData<List<StackEntity>> = dao.getStackdata()

    suspend fun insert(entity: StackEntity) {
        dao.insert(entity)
    }



    fun getData(
        order:String,sort:String, site:String,tagged:String,
        onApiCallback: (status: Boolean, message: String?, result: PostmanResponse?) -> Unit
    ) {
        api.getdata("ZiXCZbWaOwnDgpVT9Hx8IA((",order,sort,site,tagged).awaitResponse(
            onFailure = {
                onApiCallback(false,it,null)
            }, onSuccess = {
                onApiCallback(true,null,it)

            }
        )
    }
}