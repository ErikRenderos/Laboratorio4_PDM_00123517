package com.example.laboratorio4.network

import android.net.Uri
import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

object NetworkUtils {

    val MOVIE_API_BASE_URL = "http://www.omdbapi.com/"
    val TOKEN_API = "3a49fe3e"

    private val TAG = NetworkUtils::class.java.simpleName

    fun buildUrl(movieName: String): URL? {
        val builtUri = Uri.parse(MOVIE_API_BASE_URL)
            .buildUpon()
            .appendQueryParameter("apikey", TOKEN_API)
            .appendQueryParameter("t", movieName)
            .build()

        var url: URL? = null
        try {
            url = URL(builtUri.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        Log.d(TAG, "Built URI " + url)

        return url
    }

    @Throws(IOException::class)
    fun getResponseFromHttpUrl(url: URL?): String? {
        val urlConnection = url?.openConnection() as HttpURLConnection
        try {
            val ins = urlConnection.inputStream

            val scanner = Scanner(ins)
            scanner.useDelimiter("\\A")

            val hasInput = scanner.hasNext()
            if (hasInput) {
                return scanner.next()
            } else {
                return null
            }
        } finally {
            urlConnection.disconnect()
        }
    }
}