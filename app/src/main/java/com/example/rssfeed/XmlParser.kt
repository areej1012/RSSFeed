package com.example.rssfeed

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.net.URL

class XmlParser {
    private val listQuestions = ArrayList<Questions>()
    private var text = ""

    private var title = ""
    private var author = ""
    private var summary = ""

    fun parse(): List<Questions> {
        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            val url = URL("https://stackoverflow.com/feeds")
            parser.setInput(url.openStream(), null)
            var insideEntry = false
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = parser.name
                when (eventType) {
                    XmlPullParser.TEXT -> text = parser.text
                    XmlPullParser.END_TAG -> when {
                        tagName.equals("title", true) -> {
                            Log.e("insade title", insideEntry.toString())
                                title = text
                                println(title)
                        }
                        tagName.equals("author", true) -> {
                          if (tagName.equals("name",true)) {

                              author = text
                              Log.e("name", author)
                          }
                        }
                        tagName.equals("summary ", true) -> {

                                summary = text
                        }
                        else -> {
                            listQuestions.add(Questions(title, author, summary))
                            Log.e("title", title)
                        }
                    }
                    else -> {

                    }
                }
                eventType = parser.next()
            }
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return listQuestions
    }
}