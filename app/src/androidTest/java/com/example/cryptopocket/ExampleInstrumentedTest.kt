package com.example.cryptopocket

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cryptopocket.database.CurrencyDatabase
import com.example.cryptopocket.database.CurrencyDatabaseDao
import com.example.cryptopocket.database.DatabaseCurrency
import com.example.cryptopocket.database.PocketRecord
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException
import kotlin.jvm.Throws

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var dao: CurrencyDatabaseDao
    private lateinit var db: CurrencyDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, CurrencyDatabase::class.java).allowMainThreadQueries().build()
        dao = db.currencyDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

//    Adjust insertCurrency to return List<DatabaseCurrency> instead of Livedate in order to take this test
//    @Test
//    fun testDb() { runBlocking {
//        val currency = DatabaseCurrency(
//            "BTC",
//            "USD",
//            3701.86,
//            3663.92,
//            "https://cdn.coinhako.com/assets/wallet-mkr-64b9c2c2128ed4f2cce7c48131818bd607cba7b41c9314abf8ea1d90f2d465e3.png",
//            "Bitcoin",
//            "2021-06-04"
//        )
//        dao.insertCurrencies(currency)
//        val result = dao.getAllCurrency()
//        val count = result.count()
//        assertEquals(1, count)
//    }
//    }

//        @Test
//    fun testGetCurrenciesFromPocket() { runBlocking {
//        val currency = DatabaseCurrency(
//            "BTC",
//            "USD",
//            3701.86,
//            3663.92,
//            "https://cdn.coinhako.com/assets/wallet-mkr-64b9c2c2128ed4f2cce7c48131818bd607cba7b41c9314abf8ea1d90f2d465e3.png",
//            "Bitcoin",
//            "2021-06-04"
//        )
//        dao.insertCurrencies(currency)
//        dao.insertToPocket(PocketRecord("BTC"))
//        val result = dao.getCurrenciesFromPocket()
//        val count = result.count()
//        assertEquals(1, count)
//    }
//    }

//    @Test
//    fun testgetJointData() { runBlocking {
//        val currency = DatabaseCurrency(
//            "BTC",
//            "USD",
//            3701.86,
//            3663.92,
//            "https://cdn.coinhako.com/assets/wallet-mkr-64b9c2c2128ed4f2cce7c48131818bd607cba7b41c9314abf8ea1d90f2d465e3.png",
//            "Bitcoin",
//            "2021-06-04"
//        )
//        dao.insertCurrencies(currency)
//        dao.insertToPocket(PocketRecord("BTC"))
//        val result = dao.getJointData()
//        val flag = result.first().added
//        assertEquals(1, flag)
//    }
//    }
}