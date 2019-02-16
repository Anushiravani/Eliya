package com.rubiksco.eliya


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.rubiksco.eliya.Static.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
 override fun onCreate(savedInstanceState: Bundle?) {
        if (preference(Static.TokenName)==""){val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent) }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


     onlinechat.setOnClickListener {
         Static.ShowWeb(this,"https://eliya.ir/chat.html","  آنلاین")
     }


     aboutusbtn.setOnClickListener {

         val intent = Intent(this, AboutUsActivity::class.java) //not application context

         startActivity(intent)

     }

     senddocbtn.setOnClickListener {
     val intent = Intent(this, ListDocsActivity::class.java) //not application context

      startActivity(intent)
     }



    }

}
