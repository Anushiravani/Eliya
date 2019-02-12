import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil
import com.rubiksco.eliya.Adapter.SearchAdapter
import com.rubiksco.eliya.Api.SearchApi
import com.rubiksco.eliya.R
import com.rubiksco.eliya.RegisterActivity
import com.rubiksco.eliya.Static.GetRetrofit
import com.rubiksco.eliya.Static.Static
import com.rubiksco.eliya.Static.hideKeyboard
import com.rubiksco.eliya.Static.preference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Retrofit

class SearchActivity : AppCompatActivity() {


    private lateinit var searchAdapter : SearchAdapter



    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 222) {
            val returnValue = data!!.getStringArrayListExtra(Pix.IMAGE_RESULTS)

            var list2 = returnValue
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(this, 222, 10)
                } else {
                    Toast.makeText(this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG)
                        .show()
                }
                return
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        if (preference(Static.TokenName)==""){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent) }




        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)






        searchAdapter = SearchAdapter(this)
        movies_list.adapter = searchAdapter


        var lastsearch   =  preference("lastsearch")
        if (lastsearch!="") {
            UpdateResultSearch(lastsearch)
            search_txt.setText(lastsearch)
        }else
            UpdateResultSearch()
        button.setOnClickListener {
            Static.ShowWeb(this,"https://eliya.ir/chat.html","  آنلاین")
            //       Pix.start(this, 222, 10);
        }
        swipe.setOnRefreshListener {UpdateResultSearch(search_txt.text.toString(),true) }


    }



    @SuppressLint("CheckResult")
    fun UpdateResultSearch(query:String?="", forceUpdate:Boolean=false){
        val retrofit : Retrofit = this.GetRetrofit()
        //swipe.setRefreshing(true);
        swipe.isRefreshing = false

        hideKeyboard()
        movies_list.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        preference("lastsearch",query!!,"")

        val apisearch = retrofit.create(SearchApi::class.java)


        apisearch.SearchPage(query)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //   var list : SearchModel=it
                if(forceUpdate){
                    searchAdapter.UpdateItems(it.list)
                }
                else{
                    searchAdapter.setItems(it.list)
                }                    //swipe.setRefreshing(false);
                progressBar.visibility = View.GONE
                movies_list.visibility = View.VISIBLE


            },{

            })

    }
}
