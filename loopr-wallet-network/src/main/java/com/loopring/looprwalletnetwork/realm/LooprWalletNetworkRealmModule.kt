package com.loopring.looprwalletnetwork.realm

import io.realm.annotations.RealmModule

/**
 * Created by Corey on 4/2/2018
 *
 * Project: loopr-wallet-android-network
 *
 * Purpose of Class: To allow users of this library to utilize the objects with a *Realm* database.
 */
@RealmModule(library = true, allClasses = true)
class LooprWalletNetworkRealmModule