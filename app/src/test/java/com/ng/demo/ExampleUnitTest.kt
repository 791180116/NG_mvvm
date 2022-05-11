package com.ng.demo

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Example local unit web, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        //assertEquals(4, 2 + 2)
    }

    @Test
    fun test协程() {
        runBlocking {
            launch {        //launch①
                //delay(1000)                 //挂起launch①
                println("test2")
            }
            println("test1")
            coroutineScope {                //第一次挂起runBlocking,直至内部逻辑完成
                launch {    //launch②
                    //delay(1000)             //挂起launch②
                    println("test3")
                }
                //delay(1000)     //delay①    //第二次挂起runBlocking
                println("test4")
            }
            println("test5")
        }
    }

    @Test
    fun forprn() {
        for (i in arrayOf(2, 4, 6, 8)) {
            println(i.toString())
        }
        for (i in 1..10) {
            println(i.toString())
        }
    }

    @Test
    fun threadaaa() {
        var a = 1
        /*kotlin.concurrent.thread {
            a = 10
        }*/

        object : Thread() {
            override fun run() {
                super.run()
                a = 10
            }
        }.start()
        object : Thread() {
            override fun run() {
                super.run()
                println("a---------=" + a.toString())
            }
        }.start()

        /*kotlin.concurrent.thread {
            println("a---------=" + a.toString())
        }*/

        println("a---------=" + a.toString())

    }

    /**
     * 冒泡排序
     */
    @Test
    fun 冒泡排序() {
        val arr: Array<Int> = arrayOf(1, 5, 3, 8, 4, 9, 5, 5, 6, 7, 2, 88, 54, 666, 47)

        for (i in 0..arr.size) {
            for (j in 0 until arr.size - i - 1) {
                if (arr[j] > arr[j + 1]) {
                    val temp = arr[j]
                    arr[j] = arr[j + 1]
                    arr[j + 1] = temp
                }
            }
        }

        arr.forEach { i -> println(i.toString()) }
    }


    /**
     * 快速排序
     */
    @Test
    fun 快速排序() {
        val arr: Array<Int> = arrayOf(1, 5, 3, 8, 4, 9, 5, 5, 6, 7, 2, 88, 54, 666, 47)


    }

    /**
     * 选择排序
     */

    @Test
    fun 选择排序() {
        val arr: Array<Int> = arrayOf(1, 5, 3, 8, 4, 9, 5, 5, 6, 7, 2, 88, 54, 666, 47)


    }

    @Test
    fun 统计出现次数() {
        val strs = "ahkjeindaaldoekaddflae"
        val map = HashMap<String, Int>();
        for (c in strs.toCharArray()) {
            if (map[c.toString()] != null) {
                map[c.toString()] = map[c.toString()]!! + 1
            } else {
                map[c.toString()] = 1
            }
        }
        var max = 0
        var str = ""
        map.forEach {
            if (max < it.value) {
                max = it.value
                str = it.key
            }
            println("key=${it.key};value=${it.value}")
        }

        println("出现最多的字符为$str,出现了${max}次")
    }
}