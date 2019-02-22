package com.rubiksco.eliya.DBAccess

import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.PrimaryKey
import ninja.sakib.pultusorm.core.PultusORM
import ninja.sakib.pultusorm.core.PultusORMCondition
import ninja.sakib.pultusorm.core.PultusORMUpdater
import java.time.LocalDateTime
import java.util.*

class  StaticDB (var db : PultusORM){




    fun AddOrUpdate(name :String , value :String){


        if (this.Get(name) ==null){
            db.save(Static(name,value))
            return
        }


        val condition: PultusORMCondition = PultusORMCondition.Builder()
            .eq("Name", name)
            .build()

        val updater: PultusORMUpdater = PultusORMUpdater.Builder()
            .set("Name", value)
            .set("CreateTime", Calendar.getInstance())
            .condition(condition)   // condition is optional
            .build()

        db.update(Static(), updater)
    }

    fun Get(name :String ): Static? {

        val condition: PultusORMCondition = PultusORMCondition.Builder()
            .eq("Name", name)
            .build()

        val items: List<Static> =   db.find(Static(),condition).filterIsInstance<Static>()
        if (items.size==1)
            return items[0]
        return  null
    }


}


class Static{
    @PrimaryKey
    @AutoIncrement
    var Id: Int = 0
    var Name: String? = null
    var Value: String? = null
    var CreateTime :Calendar?= Calendar.getInstance()


    constructor() {}
    constructor(names : String, value :String) {
        this.Name = names
        this.Value=value
    }

    fun diffWithNow():Int{

      return  com.rubiksco.eliya.Static.Static.diffOfDaysByDateAndTime(this.CreateTime!!, Calendar.getInstance())
    }
}