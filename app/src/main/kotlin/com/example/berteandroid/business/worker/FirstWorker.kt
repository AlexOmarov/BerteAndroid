package com.example.berteandroid.business.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.berteandroid.R

class FirstWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        Log.d(TAG, "doWork: start")
        val taskData = inputData
        val taskDataString = taskData.getString("KEY")

        showNotification("Make it Easy", taskDataString.toString())
        showNotification("Make it Easy21", taskDataString.toString())

        val outputData = Data.Builder().putString("work_result", "Task Finished").build()

        Log.d(TAG, "doWork: end")

        return Result.success(outputData)
    }

    private fun showNotification(task: String, desc: String) {
        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "message_channel"
        val channelName = "message_name"

        val channel = NotificationChannel(
            channelId, channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        manager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(task)
            .setContentText(desc)
            .setSmallIcon(R.drawable.ic_launcher_background)

        manager.notify(1, builder.build())
    }
}