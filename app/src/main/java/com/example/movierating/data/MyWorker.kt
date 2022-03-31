package com.example.movierating.data

import android.annotation.SuppressLint
import android.content.ContentProviderOperation
import android.content.Context
import android.provider.ContactsContract
import androidx.work.*


class MyWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {

        val getContactsMode : Boolean = inputData.getString(CONTACT_ID).isNullOrEmpty()
        var resultData = workDataOf(Pair("EMPTY", null))


        if (getContactsMode) resultData = workDataOf(Pair(CONTACTS_LIST, getContacts()))

         else {
            val contactName = inputData.getString(CONTACT_NAME).toString()
            val contactNumber = inputData.getString(CONTACT_NUMBER).toString()
            val contactId = inputData.getString(CONTACT_ID).toString()

            editContact(contactName, contactNumber, contactId)
        }

        return Result.success(resultData)
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

        context.contentResolver.applyBatch(ContactsContract.AUTHORITY, cpo)
    }

    @SuppressLint("Range")
    private fun getContacts(): List<ContactInfo> {
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val cursor =
            context.contentResolver.query(
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


                contactsList.add(ContactInfo(name, mobileNumber, id))
            }
            cursor.close()
        }
        return contactsList
    }


    companion object {
        const val WORK_NAME = "work_name"

        private const val CONTACT_NAME = "contact_name"
        private const val CONTACT_NUMBER = "contact_number"
        private const val CONTACT_ID = "contact_id"
        private const val CONTACTS_LIST = "contacts_list"


        fun makeRequest(
            contactName: String?,
            contactNumber: String?,
            contactId: String?
        ): OneTimeWorkRequest {
            val contactInputData = Data.Builder()
                .putString(CONTACT_NAME, contactName)
                .putString(CONTACT_NUMBER, contactNumber)
                .putString(CONTACT_ID, contactId)
                .build()

            return OneTimeWorkRequestBuilder<MyWorker>()
                .setInputData(contactInputData)
                .build()
        }
    }
}
