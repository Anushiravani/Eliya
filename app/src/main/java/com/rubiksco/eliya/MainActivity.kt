package com.rubiksco.eliya


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.rubiksco.eliya.Static.*





class MainActivity : AppCompatActivity() {
 override fun onCreate(savedInstanceState: Bundle?) {
        if (preference(Static.TokenName)==""){val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent) }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





    }

}
