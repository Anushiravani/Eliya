package com.rubiksco.eliya.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rubiksco.eliya.Models.DocsItem
import com.rubiksco.eliya.R
import com.rubiksco.eliya.SendActivity
import kotlinx.android.synthetic.main.item_docs.view.*


class ListDocsAdapter(val context : Context) : RecyclerView.Adapter<ListDocsAdapter.ListDocsViewHolder>() {



    private val list: MutableList<DocsItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDocsViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_docs,parent,false);

        return ListDocsViewHolder(view)
    }



    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: ListDocsViewHolder, position: Int) {
        holder.bindModel(list[position], position)

    }
    fun setItems(data: List<DocsItem>) {
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun UpdateItems(data: List<DocsItem>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()

    }



    inner class ListDocsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var CurrentItem:DocsItem?=null
        var CurrentPossition:Int?=0

        init {

            itemView.setOnClickListener {

            //    Static.ShowWeb(context,CurrentItem?.url,CurrentItem?.title)

               val intent = Intent(context, SendActivity::class.java) //not application context
                intent.putExtra("title",CurrentItem?.title)
              context.startActivity(intent)

            }
        }

        fun bindModel(item: DocsItem?, pos :Int?) {
            itemView.docItemTitle.text=item!!.title

            CurrentItem=item
            CurrentPossition=pos;
        }
    }
}
