package com.example.berteandroid.business.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class FirstWorker(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {
    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}