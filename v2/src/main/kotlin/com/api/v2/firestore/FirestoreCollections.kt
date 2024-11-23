package com.api.v2.firestore

import com.google.cloud.firestore.CollectionReference
import com.google.firebase.cloud.FirestoreClient

class FirestoreCollections {

    companion object {

        fun getPeopleCollectionReference(): CollectionReference {
            return FirestoreClient.getFirestore().collection("people")
        }

        fun getCustomersCollectionReference(): CollectionReference {
            return FirestoreClient.getFirestore().collection("customers")
        }

    }

}