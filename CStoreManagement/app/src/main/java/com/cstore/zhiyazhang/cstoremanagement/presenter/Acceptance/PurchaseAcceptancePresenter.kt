package com.cstore.zhiyazhang.cstoremanagement.presenter.acceptance

import android.content.Context
import com.cstore.zhiyazhang.cstoremanagement.R
import com.cstore.zhiyazhang.cstoremanagement.bean.AcceptanceBean
import com.cstore.zhiyazhang.cstoremanagement.bean.AcceptanceItemBean
import com.cstore.zhiyazhang.cstoremanagement.bean.ReturnAcceptanceBean
import com.cstore.zhiyazhang.cstoremanagement.bean.ReturnAcceptanceItemBean
import com.cstore.zhiyazhang.cstoremanagement.model.MyListener
import com.cstore.zhiyazhang.cstoremanagement.model.acceptance.AcceptanceModel
import com.cstore.zhiyazhang.cstoremanagement.utils.MyActivity
import com.cstore.zhiyazhang.cstoremanagement.utils.MyApplication
import com.cstore.zhiyazhang.cstoremanagement.utils.MyHandler.OnlyMyHandler
import com.cstore.zhiyazhang.cstoremanagement.utils.PresenterUtil
import com.cstore.zhiyazhang.cstoremanagement.view.interfaceview.GenericView

/**
 * Created by zhiya.zhang
 * on 2017/9/11 16:51.
 */
class PurchaseAcceptancePresenter(private val gView: GenericView, private val context: Context, private val activity: MyActivity) {
    private val model = AcceptanceModel()

    /**
     * 得到所有进货验收单
     */
    fun getAcceptanceList(date: String) {
        if (!PresenterUtil.judgmentInternet(gView)) return
        model.getAcceptanceList(date, OnlyMyHandler.writeActivity(activity).writeListener(object : MyListener {
            override fun listenerSuccess(data: Any) {
                gView.requestSuccess(data)
                gView.hideLoading()
            }

            override fun listenerFailed(errorMessage: String) {
                gView.showPrompt(context.getString(R.string.socketError)+errorMessage)
                gView.errorDealWith()
            }
        }))
    }

    /**
     * 得到所有退货验收单
     */
    fun getReturnAcceptanceList(date:String){
        if (!PresenterUtil.judgmentInternet(gView)) return
        model.getReturnAcceptanceList(date, OnlyMyHandler.writeActivity(activity).writeListener(object : MyListener {
            override fun listenerSuccess(data: Any) {
                gView.requestSuccess(data)
                gView.hideLoading()
            }

            override fun listenerFailed(errorMessage: String) {
                gView.showPrompt(context.getString(R.string.socketError)+errorMessage)
                gView.hideLoading()
            }
        }))
    }

    /**
     * 更新验收单
     */
    fun updateAcceptance(date:String, ab:AcceptanceBean){
        if (!PresenterUtil.judgmentInternet(gView)) return
        model.updateAcceptance(date, ab, OnlyMyHandler.writeActivity(activity).writeListener(object : MyListener {
            override fun listenerSuccess(data: Any) {
                gView.showPrompt(MyApplication.instance().getString(R.string.saveDone))
                gView.updateDone(data)
            }

            override fun listenerFailed(errorMessage: String) {
                gView.showPrompt(context.getString(R.string.socketError)+errorMessage)
                gView.errorDealWith()
            }
        }))
    }

    /**
     * 更新退货验收单
     */
    fun updateReturnAcceptance(date:String, rab:ReturnAcceptanceBean){
        if (!PresenterUtil.judgmentInternet(gView)) return
        model.updateReturnAcceptance(date, rab, OnlyMyHandler.writeActivity(activity).writeListener(object : MyListener {
            override fun listenerSuccess(data: Any) {
                gView.showPrompt(MyApplication.instance().getString(R.string.saveDone))
                gView.updateDone(data)
            }

            override fun listenerFailed(errorMessage: String) {
                gView.showPrompt(context.getString(R.string.socketError)+errorMessage)
                gView.errorDealWith()
            }
        }))
    }

    /**
     * 得到所有配送商
     */
    fun getVendor(){
        if (!PresenterUtil.judgmentInternet(gView)) return
        model.getVendor(OnlyMyHandler.writeActivity(activity).writeListener(object : MyListener {
            override fun listenerSuccess(data: Any) {
                gView.showView(data)
                gView.hideLoading()
            }

            override fun listenerFailed(errorMessage: String) {
                gView.showPrompt(context.getString(R.string.socketError)+errorMessage)
                gView.errorDealWith()
            }
        }))
    }

    /**
     * 得到输入的商品
     */
    fun getCommodity(ab: AcceptanceBean?, vendorId:String){
        if (!PresenterUtil.judgmentInternet(gView)) return
        model.getCommodity(ab, vendorId, OnlyMyHandler.writeActivity(activity).writeListener(object : MyListener {
            override fun listenerSuccess(data: Any) {
                gView.requestSuccess(data)
                gView.hideLoading()
            }

            override fun listenerFailed(errorMessage: String) {
                gView.showPrompt(context.getString(R.string.socketError)+","+errorMessage)
                gView.hideLoading()
            }
        }))
    }

    fun getReturnCommodity(rab:ReturnAcceptanceBean?,vendorId: String){
        if (!PresenterUtil.judgmentInternet(gView)) return
        model.getReturnCommodity(rab, vendorId, OnlyMyHandler.writeActivity(activity).writeListener(object : MyListener {
            override fun listenerSuccess(data: Any) {
                gView.requestSuccess(data)
                gView.hideLoading()
            }

            override fun listenerFailed(errorMessage: String) {
                gView.showPrompt(context.getString(R.string.socketError)+","+errorMessage)
                gView.hideLoading()
            }
        }))
    }

    /**
     * 创建进货验收单
     */
    fun createAcceptance(date:String, ab: AcceptanceBean?,aib:ArrayList<AcceptanceItemBean>){
        if (!PresenterUtil.judgmentInternet(gView)) return
        model.createAcceptance(date,ab,aib, OnlyMyHandler.writeActivity(activity).writeListener(object : MyListener {
            override fun listenerSuccess(data: Any) {
                gView.updateDone(data)
                gView.hideLoading()
            }

            override fun listenerFailed(errorMessage: String) {
                gView.showPrompt(context.getString(R.string.socketError)+errorMessage)
                gView.hideLoading()
            }
        }))
    }

    /**
     * 创建退货验收单
     */
    fun createReturnAcceptance(date:String, rab:ReturnAcceptanceBean?, raib:ArrayList<ReturnAcceptanceItemBean>){
        if (!PresenterUtil.judgmentInternet(gView)) return
        model.createReturnAcceptance(date, rab, raib, OnlyMyHandler.writeActivity(activity).writeListener(object : MyListener {
            override fun listenerSuccess(data: Any) {
                gView.updateDone(data)
                gView.hideLoading()
            }

            override fun listenerFailed(errorMessage: String) {
                gView.showPrompt(context.getString(R.string.socketError)+errorMessage)
                gView.hideLoading()
            }
        }))
    }


}