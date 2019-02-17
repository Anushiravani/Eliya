package com.rubiksco.eliya

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.rubiksco.eliya.Adapter.ListDocsAdapter
import com.rubiksco.eliya.Models.DocsItem
import kotlinx.android.synthetic.main.activity_list_docs.*
import android.support.v7.widget.GridLayoutManager

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class ListDocsActivity : AppCompatActivity() {


    lateinit var  listdocAdapter:ListDocsAdapter

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_docs)



        listdocAdapter = ListDocsAdapter(this)
        docs_list.adapter = listdocAdapter
        val mLayoutManager = GridLayoutManager(this, 2)
        docs_list.layoutManager = mLayoutManager




        val listdoc :ArrayList<DocsItem> = ArrayList()

        listdoc.add(DocsItem("کارت ملی" ,""))
        listdoc.add(DocsItem("شناسنامه","" ))
        listdoc.add(DocsItem("کارت پابان خدمت","" ))
        listdoc.add(DocsItem("آگهی تاسیس","" ))
        listdoc.add(DocsItem("شرکتنامه","" ))
        listdoc.add(DocsItem("اساسنامه","" ))
        listdoc.add(DocsItem("تقاضانامه","" ))
        listdoc.add(DocsItem("اظهارنامه","" ))
        listdoc.add(DocsItem("صورتجلسه","" ))
        listdoc.add(DocsItem("جواز","" ))
        listdoc.add(DocsItem("مدرک تحصیلی","" ))
        listdoc.add(DocsItem("برگه کد اقتصادی","" ))
        listdoc.add(DocsItem("برگه مالیاتی","" ))
        listdoc.add(DocsItem("فیش واریزی","" ))
        listdoc.add(DocsItem("اجاره نامه","" ))
        listdoc.add(DocsItem("وکالتنامه","" ))
        listdoc.add(DocsItem("تصدیق علامت","" ))
        listdoc.add(DocsItem("روزنامه علامت","" ))
        listdoc.add(DocsItem("برگه ادعانامه","" ))
        listdoc.add(DocsItem("روزنامه","" ))
        listdoc.add(DocsItem("آگهی تغییرات","" ))
        listdoc.add(DocsItem("سایر","" ))


        listdocAdapter.setItems(listdoc)

    }
}
