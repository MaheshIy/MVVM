package com.mvvm.kotlin.data.repository

import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.mvvm.kotlin.data.api.ApiHelper
import com.mvvm.kotlin.data.room.DatabaseHelper
import com.mvvm.kotlin.data.room.entity.MatchesEntity
import com.mvvm.kotlin.model.*

class MatchesRepositories(private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper) {

    suspend fun getMatches(): MatchesResponse {
        val isUiThread =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) Looper.getMainLooper().isCurrentThread else Thread.currentThread() === Looper.getMainLooper().thread

        println("Threaddd---------repositories"+isUiThread)
        val getVals = apiHelper.getMatches()
        if (getVals.isSuccessful && getVals.body() != null) {
            WritingFromRemoteToDatabase(getVals.body()!!.results)
        } else
            return MatchesResponse(emptyList())

        return getVals.body() ?: MatchesResponse(emptyList())
    }

    //Here I'm picking only necessary data from the api and writing it down into my database.
    private suspend fun WritingFromRemoteToDatabase(results: List<Result>) {

        val fromDB = dbHelper.getRepository()

        if (fromDB.isEmpty()) {

            val dbInsertionValues = mutableListOf<MatchesEntity>()

            for (remoteValues in results) {
                val dbInsertion = MatchesEntity(
                    remoteValues.status,
                    remoteValues.name.first + " " + remoteValues.name.last,
                    remoteValues.location.city,
                    remoteValues.dob.age,
                    remoteValues.picture.large
                )
                dbInsertionValues.add(dbInsertion)
            }
            dbHelper.insertAll(dbInsertionValues)
        } else {
            results.forEachIndexed { index, result ->
                result.status = fromDB.get(index).status ?: 0
            }
        }
    }

    //Calling Room db to update the particular row's status with the ones user selected(accept/decline)
    suspend fun UpdateRoomList(id: Int, status: Int) {

        dbHelper.updateOnClickedRow(id, status)
    }


    //Here Creating Pojo similar to network call but filling only with the db values and passing rest with a dummy values.
    suspend fun loadFromDB(): MatchesResponse {

        lateinit var listItem: List<Result>

        val fromDB = dbHelper.getRepository()

        if (!fromDB.isEmpty()) {

            listItem = mutableListOf()

            fromDB.forEachIndexed { index, matchesEntity ->
                val name = Name("", fromDB[index].name.toString(), "")
                val location = Location(
                    Street(0, ""),
                    fromDB[index].city.toString(),
                    "",
                    "",
                    "",
                    Coordinates("", "0.0"),
                    Timezone("", ""))

                val dob = DateOfBirth("", fromDB[index].age!!)
                val picture = Picture(fromDB[index].picture!!, "", "")
                val repos = Result(
                    "",
                    name,
                    location,
                    "",
                    Login("", "", "", "", "", "", ""),
                    dob,
                    Registered("", 0),
                    "",
                    "",
                    Id("", ""),
                    fromDB[index].status ?: 0,
                    picture,
                    ""
                )
                listItem.add(repos)
            }

            return MatchesResponse(listItem)
        } else
            return MatchesResponse(emptyList())
    }

}