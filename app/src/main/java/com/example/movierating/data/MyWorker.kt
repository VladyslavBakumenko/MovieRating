package com.example.movierating.data

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(
    context: Context,
    private  val workerParameters: WorkerParameters
) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        TODO()
    }
}