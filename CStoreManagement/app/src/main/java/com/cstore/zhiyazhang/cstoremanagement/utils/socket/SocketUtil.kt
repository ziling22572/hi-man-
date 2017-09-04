package com.cstore.zhiyazhang.cstoremanagement.utils.socket

import android.os.Message
import com.cstore.zhiyazhang.cstoremanagement.R
import com.cstore.zhiyazhang.cstoremanagement.bean.*
import com.cstore.zhiyazhang.cstoremanagement.utils.MyHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.cstore.zhiyazhang.cstoremanagement.utils.MyApplication
import java.io.*
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketTimeoutException

/**
 * Created by zhiya.zhang
 * on 2017/5/19 15:32.
 * Socket工具类
 * @author zhiya.zhang
 * *
 * @since 1.1
 */

internal class SocketUtil  {
    private var mySocket: Socket

    private constructor(ip: String, msg: String){
        mySocket = Socket()
        host = ip
        message = msg
        loadingTime=10//默认超时时间为10s
    }

    private constructor(ip: String, msg: String, lt:Int){
        mySocket = Socket()
        host = ip
        message = msg
        loadingTime=lt
    }

    companion object {
        private val PORT = 49999
        private val REQUEST_ERROR = "服务器连接超时"
        private val SOCKET_ERROR = "服务器异常，确定连在内网中，确定服务器正常"
        private val NULL_HOST = "host为空"
        private var message = ""
        private var loadingTime:Int=10
        lateinit private var myHandler: MyHandler.MyHandler
        lateinit private var host: String
        private var os: OutputStream? = null
        private var bw: BufferedWriter? = null
        private var `is`: InputStream? = null
        private var br: BufferedReader? = null

        /**
         * 关闭各种流
         */
        private fun closeSocket(socket:Socket) {
            try {
                bw!!.close()
            } catch (e: Exception) { }

            try {
                br!!.close()
            } catch (e: Exception) { }

            try {
                os!!.close()
            } catch (e: Exception) { }

            try {
                `is`!!.close()
            } catch (e: Exception) { }

            try {
                socket.close()
            } catch (e: Exception) { }

        }

        /**
         * 初始化Socket
         */
        fun initSocket(ip: String, message: String): SocketUtil {
            return SocketUtil(ip, message)
        }

        /**
         * 初始化Socket，附带设置等待时间
         */
        fun initSocket(ip: String, message: String, loadingTime:Int): SocketUtil {
            return SocketUtil(ip, message, loadingTime)
        }

        /**
         * 判断ip是否正确
         */
        fun judgmentIP(ip: String, msg: Message, handler: MyHandler.MyHandler): Boolean {
            if (ip == MyApplication.instance().getString(R.string.notFindIP)) {
                msg.obj = ip
                msg.what = MyHandler.ERROR1
                handler.sendMessage(msg)
                return false
            }else return true
        }

        /**
         * 判断得到的数据是否有值
         */
        fun judgmentNull(data: String, msg: Message, handler: MyHandler.MyHandler): Boolean {
            if (data == "" || data == "[]") {
                msg.obj = MyApplication.instance().applicationContext.getString(R.string.noMessage)
                msg.what = MyHandler.ERROR1
                handler.sendMessage(msg)
                return false
            }else return true
        }

        fun getUser(data:String):ArrayList<User>{
            return Gson().fromJson<ArrayList<User>>(data, object : TypeToken<ArrayList<User>>() {}.type)
        }

        fun getScrap(data:String):ArrayList<ScrapContractBean>{
            return Gson().fromJson<ArrayList<ScrapContractBean>>(data, object : TypeToken<ArrayList<ScrapContractBean>>() {}.type)
        }

        fun getCategoryItem(data:String):ArrayList<CategoryItemBean>{
            return Gson().fromJson<ArrayList<CategoryItemBean>>(data, object : TypeToken<ArrayList<CategoryItemBean>>() {}.type)
        }

        fun getCategory(data:String):ArrayList<OrderCategoryBean>{
            return Gson().fromJson<ArrayList<OrderCategoryBean>>(data, object : TypeToken<ArrayList<OrderCategoryBean>>() {}.type)
        }

        fun getShelf(data:String):ArrayList<ShelfBean>{
            return Gson().fromJson<ArrayList<ShelfBean>>(data, object : TypeToken<ArrayList<ShelfBean>>() {}.type)
        }

        fun getSelf(data:String):ArrayList<SelfBean>{
            return Gson().fromJson<ArrayList<SelfBean>>(data, object : TypeToken<ArrayList<SelfBean>>() {}.type)
        }

        fun getFresh(data:String):ArrayList<FreshGroup>{
            return Gson().fromJson<ArrayList<FreshGroup>>(data, object : TypeToken<ArrayList<FreshGroup>>() {}.type)
        }

        fun getNOP(data:String):ArrayList<NOPBean>{
            return Gson().fromJson<ArrayList<NOPBean>>(data, object : TypeToken<ArrayList<NOPBean>>() {}.type)
        }

        fun getScrapHot(data: String):ArrayList<ScrapHotBean>{
            return Gson().fromJson<ArrayList<ScrapHotBean>>(data, object : TypeToken<ArrayList<ScrapHotBean>>() {}.type)
        }
    }

    /**
     * 执行Socket操作,这不是异步，在外层要开thread
     */
    fun inquire():String {
        try {
            mySocket.connect(InetSocketAddress(host, PORT), loadingTime * 1000)
            mySocket.soTimeout = loadingTime * 1000
            os = mySocket.getOutputStream()
            bw = BufferedWriter(OutputStreamWriter(os!!))
            `is` = mySocket.getInputStream()
            br = BufferedReader(InputStreamReader(`is`!!))
            bw!!.write(message)
            bw!!.flush()
            mySocket.shutdownOutput()//可以不用关，这只是个人习惯关闭而已
            var receive: String? = null
            while (receive == null) {
                receive = br!!.readLine()
            }
            return receive
        } catch (ste: SocketTimeoutException) {
            return REQUEST_ERROR
        } catch (ioe: IOException) {
            return SOCKET_ERROR
        } catch (e: Exception) {
            return e.message!!
        } finally {
            closeSocket(mySocket)
        }
    }
}