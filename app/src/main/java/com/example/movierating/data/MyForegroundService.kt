package com.example.movierating.data

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ContentProviderOperation
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.provider.ContactsContract
import androidx.core.app.NotificationCompat
import com.example.movierating.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus


class MyForegroundService() : Service() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        coroutineScope.launch {
            val getContactsMode: Boolean =
                intent?.getStringExtra(MyWorker.CONTACT_ID).isNullOrEmpty()

            if (getContactsMode) {
                getContacts()
                stopSelf()
            } else {
                val contactName = intent?.getStringExtra(CONTACT_NAME).toString()
                val contactNumber = intent?.getStringExtra(CONTACT_NUMBER).toString()
                val contactId = intent?.getStringExtra(CONTACT_ID).toString()

                editContact(contactName, contactNumber, contactId)
                stopSelf()
            }
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun editContact(contactName: String, contactNumber: String, contactId: String) {

        val cpo = ArrayList<ContentProviderOperation>()

        val nameParams =
            arrayOf(contactId, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
        val numberParams =
            arrayOf(contactId, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)

        val where =
            ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"

        cpo.add(
            ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(where, nameParams)
                .withValue(
                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                    contactName
                )
                .build()
        )

        cpo.add(
            ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(where, numberParams)
                .withValue(ContactsContract.CommonDataKinds.Email.DATA, contactNumber)
                .build()
        )

        applicationContext.contentResolver.applyBatch(ContactsContract.AUTHORITY, cpo)
    }

    @SuppressLint("Range")
    private fun getContacts(): List<ContactInfo> {
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val cursor =
            applicationContext.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                null,
                null,
                null
            )

        val contactsList = mutableListOf<ContactInfo>()

        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {

                val id =
                    cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
                    )

                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val mobileNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                EventBus.getDefault().post(ContactInfo(name, mobileNumber, id))
            }
            cursor.close()
        }
        return contactsList
    }

    private fun createNotificationChannel() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Title")
            .setContentText("Text")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
    }

    companion object {
        fun newIntentForGetContacts(context: Context): Intent {
            return Intent(context, MyForegroundService::class.java)
        }

        fun newIntentForEditContact(context: Context, contactInfo: ContactInfo): Intent {
            return Intent(context, MyForegroundService::class.java).apply {
                putExtra(CONTACT_NAME, contactInfo.name)
                putExtra(CONTACT_NUMBER, contactInfo.number)
                putExtra(CONTACT_ID, contactInfo.id)
            }
        }

        private const val CONTACT_NAME = "contact_name"
        private const val CONTACT_NUMBER = "contact_number"
        private const val CONTACT_ID = "contact_id"

        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "channel_name"
        private const val NOTIFICATION_ID = 8
    }
}