package com.example.newsinshort.data.database

import androidx.room.TypeConverter
import com.example.newsinshort.data.database.entities.Article
import com.example.newsinshort.data.database.model.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class ArticlesTypeConvertor {

    val gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Article> {
        if (data == null) {
            return emptyList<Article>()
        }

        val listType: Type = object : TypeToken<List<Article?>?>() {}.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Article?>?): String {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source{
        return Source(name, name)
    }

}