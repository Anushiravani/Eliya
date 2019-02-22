package com.rubiksco.eliya.Static

import android.os.AsyncTask

abstract class ImageCompressionAsyncTask : AsyncTask<String, Void, ByteArray>() {

    override fun doInBackground(vararg strings: String): ByteArray? {
        return if (strings.isEmpty()) null else ImageUtils.compressImage(strings[0])
    }

    abstract override fun onPostExecute(imageBytes: ByteArray)
}