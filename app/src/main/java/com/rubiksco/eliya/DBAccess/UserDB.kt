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
    fun UpdateTotal(users : List<UserModel> ){



        db.drop(Users())


        for (item in users){
           var user =Users(item.Name)
            db.save(user)
        }

        val staticdb = StaticDB(db)
        val staticItem = staticdb.AddOrUpdate(Static.LastGetUser,"yes")

    }
    fun Dispose(){
        db.close()
    }



    @SuppressLint("CheckResult")
    fun GetsFromFb(  db :PultusORM): MutableList<Any>? {


        val staticdb = StaticDB(db)

        val staticItem = staticdb.Get(Static.LastGetUser)


        return if (staticItem !=null &&  staticItem.diffWithNow()>-10)
            db.find(Users())
        else
            null



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
