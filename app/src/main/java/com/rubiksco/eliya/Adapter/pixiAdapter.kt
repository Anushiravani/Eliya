package com.rubiksco.eliya.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rubiksco.eliya.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image.view.*
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable

import java.io.File


 import android.app.Dialog
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import kotlinx.android.synthetic.main.preview_image.*





class pixiAdapter(private val context: Context) : RecyclerView.Adapter<pixiAdapter.pixiViewHolder>() {
        val list  :ArrayList<String> =ArrayList<String>()
    val listhash  :HashSet<String> =HashSet<String>()

    fun addImage(newlist: ArrayList<String>) {
       // this.list.clear()



        listhash.addAll(newlist)

        this.list.clear()
        this.list.addAll(  listhash.toTypedArray())
        notifyDataSetChanged()
    }

    @Suppress("UNCHECKED_CAST")
        inline fun <reified T> Collection<T>.toTypedArray(): Array<T> {
        @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
        val thisCollection = this as java.util.Collection<T>
        return thisCollection.toArray(arrayOfNulls<T>(0)) as Array<T>
    }
    fun RetrunArray():ArrayList<String>{
        return  list;
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): pixiViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_image,parent,false);

        return pixiViewHolder(view)
    }

    override fun onBindViewHolder(holder: pixiViewHolder, position: Int) {

        holder.bindModel(list.get(position), position)


    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        //list.remove(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, list.size)
    }

    inner class pixiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindModel(item: String?, pos :Int?) {

           /* val ex =item!!.substring(item!!.lastIndexOf(".") + 1);
            if (ex =="jpg" || ex=="pmg"){

            }*/

            Picasso.get().load(item)    .placeholder(R.drawable.ic_file_text)
                .into(itemView.iv)

         //   filePath.substring(filePath.lastIndexOf(".") + 1);

            itemView.btndelete.setOnClickListener{


                removeAt(pos!!)

                notifyDataSetChanged()
            }
            itemView.zoom.setOnClickListener {

                val nagDialog =
                    Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
                nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                nagDialog.setCancelable(false)
                nagDialog.setContentView(R.layout.preview_image)




                nagDialog.btnIvClose.setOnClickListener {
                    nagDialog.dismiss()
                }

                Picasso.get().load(item).into(nagDialog.iv_preview_image)

                nagDialog.show()
            }

        }
    }



}