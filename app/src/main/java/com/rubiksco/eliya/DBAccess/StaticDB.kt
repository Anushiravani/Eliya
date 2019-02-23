package com.rubiksco.eliya.DBAccess

import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.PrimaryKey
import ninja.sakib.pultusorm.core.PultusORM
import ninja.sakib.pultusorm.core.PultusORMCondition
import ninja.sakib.pultusorm.core.PultusORMUpdater
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*




class  StaticDB (var db : PultusORM){




    fun AddOrUpdate(name :String , value :String){


        if (this.Get(name) ==null){
           var d= db.save(Static(name,value))
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

        val items =   db.find(Static(),condition)
        for (it in items) {
            val std = it as Static
             return  std
        }
        return  null
    }


}


class Static{
    @PrimaryKey
    @AutoIncrement
    var Id: Int = 0
    var Name: String? = null
    var Value: String? = null



    var CreateTime :Date =Calendar.getInstance().time

init {

    CreateTime = Calendar.getInstance().time
}
    constructor()
    constructor(names : String, value :String) {
        this.Name = names
        this.Value=value
    }

    fun toCalendar(date: Date): Calendar {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }
    fun diffWithNow():Int{




      return  com.rubiksco.eliya.Static.Static.diffOfDaysByDateAndTime(toCalendar(this.CreateTime), Calendar.getInstance())
    }
}