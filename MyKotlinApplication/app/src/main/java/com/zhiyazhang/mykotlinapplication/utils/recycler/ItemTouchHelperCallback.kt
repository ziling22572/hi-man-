package com.zhiyazhang.mykotlinapplication.utils.recycler

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * Created by zhiya.zhang
 * on 2017/6/2 17:13.
 * 左右滑动删除，上下拖拽排序
 */
class ItemTouchHelperCallback : ItemTouchHelper.Callback() {

    var moveAndSwipedListener: onMoveAndSwipedListener? = null

    fun ItemTouchHelperCallback(listener: onMoveAndSwipedListener) {
        this.moveAndSwipedListener = listener
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            //单列的RecyclerView支持上下拖动和左右侧滑
            return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.START or ItemTouchHelper.END)
        } else {
            //多列的RecyclerView支持上下左右拖动和不支持左右侧滑
            return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT, 0)
        }
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        //如果两个item不是同一个类型的，不让他拖拽
        if (viewHolder.itemViewType != target.itemViewType) return false
        moveAndSwipedListener!!.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        moveAndSwipedListener!!.onItemDismiss(viewHolder.adapterPosition)
    }
}