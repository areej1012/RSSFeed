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
                if (eventType == XmlPullParser.START_TAG) {
                    if (parser.name.equals("entry",true)) {
                        insideEntry = true;
                    } else if (parser.name.equals("title",true)) {
                        if (insideEntry)
                            title = parser.nextText()
                    } else if (parser.name.equals("name",true)) {
                        if (insideEntry)
                             author = parser.nextText()
                    }
                    else if (parser.name.equals("summary", true)) {
                        if (insideEntry)
                             summary = parser.nextText()
                    }


                } else if (eventType == XmlPullParser.END_TAG && parser.name.equals("entry",true)) {
                    insideEntry = false;
                    listQuestions.add(Questions(title,author,summary))
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