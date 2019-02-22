package com.rubiksco.eliya.DBAccess

import android.annotation.SuppressLint
import android.view.View
import com.rubiksco.eliya.Api.StaticApi
import com.rubiksco.eliya.Models.UserModel
import com.rubiksco.eliya.Static.Static
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.PrimaryKey
import ninja.sakib.pultusorm.core.PultusORM
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
import kotlin.collections.ArrayList

class  UserDB(var db :PultusORM){



     fun  Add(user : Users){

         db.save(user)

     }

     fun Gets():List<Users>?{
        // val users     = db.find(Users())

         val t    =db.find(Users()).filterIsInstance<Users>()
         return  t
     }

     fun Dispose(){
         db.close()
     }



     @SuppressLint("CheckResult")
     fun resetAndsetUsers(retrofit:Retrofit , db :PultusORM): List<Users>? {


         val staticdb = StaticDB(db)
         val staticItem = staticdb.Get(Static.LastGetUser)

        if (staticItem !=null &&  staticItem.diffWithNow() < 10){
             return  db.find(Users()).filterIsInstance<Users>()

        }
        staticdb.AddOrUpdate(Static.LastGetUser,"saved")

         val api = retrofit.create(StaticApi::class.java)

         var list : ArrayList<UserModel> = ArrayList()



         var response = api.GetUserscall().execute()

         if (response.isSuccessful) {
             val news = response.body()


         } else {

         }

         api.GetUserscall()

          .enqueue(object: Callback<ArrayList<UserModel>> {
              override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {


              }

              override fun onResponse(call: Call<ArrayList<UserModel>>, response: Response<ArrayList<UserModel>>) {
                  var tttt = response.body()
              }

          })


         api.GetUsers()
             .subscribeOn(Schedulers.io())
             .unsubscribeOn(Schedulers.computation())
             .observeOn(AndroidSchedulers.mainThread())

             .doOnComplete(  ({
                 var  tt = list

             }))
             .subscribe({



                 var id = it.size
                 list=it

                 var d = id

             },{

             })


         if (list.size>0){
             db.drop(Users())

         }
         for (item in list){
            db.save(item)
         }

         return list.filterIsInstance<Users>()
     }

}


class Users {
    @PrimaryKey
    @AutoIncrement
    var Id: Int = 0
    var Name: String? = null


    constructor() {


    }
    constructor(names : String) {
        this.Name = names

    }


}
