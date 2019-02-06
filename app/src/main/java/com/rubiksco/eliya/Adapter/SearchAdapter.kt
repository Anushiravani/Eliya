package com.rubiksco.eliya.Adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.rubiksco.eliya.Models.SearchItem
import com.rubiksco.eliya.R
import com.rubiksco.eliya.Static.Static
import com.rubiksco.eliya.WebActivity
import kotlinx.android.synthetic.main.item_search.view.*

class SearchAdapter(val context :Context ) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val list: MutableList<SearchItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.item_search,parent,false);
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bindModel(list[position], position)

    }

    fun setItems(data: List<SearchItem>) {
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun UpdateItems(data: List<SearchItem>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()

    }



    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var CurrentItem:SearchItem?=null
        var CurrentPossition:Int?=0

        init {

            itemView.setOnClickListener {
               // Toast.makeText(context,CurrentItem!!.title,Toast.LENGTH_LONG).show()
              //  val context =itemView.context

                Static.ShowWeb(context,CurrentItem?.url,CurrentItem?.title)
             /*   val intent = Intent(context, WebActivity::class.java)
                intent.putExtra("url",CurrentItem?.url)
                intent.putExtra("title",CurrentItem?.title)
                context.startActivity(intent)*/

              //  val intent = Intent(this, WebActivity::class.java) //not application context
              //  intent.putExtra("url",CurrentItem.url.to())
              //  startActivity(intent)


            }
        }

        fun bindModel(item: SearchItem?, pos :Int?) {
            itemView.text.text=item!!.title

            CurrentItem=item
            CurrentPossition=pos;
        }
    }
}