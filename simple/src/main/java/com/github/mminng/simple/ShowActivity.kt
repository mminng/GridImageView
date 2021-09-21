package com.github.mminng.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by zh on 2021/6/24.
 */
class ShowActivity : AppCompatActivity() {

    private val showListView: ViewPager2 by lazy {
        findViewById(R.id.show_list_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        val actionBar = supportActionBar
        var data: List<GridData> = arrayListOf()
        var editData: List<String> = arrayListOf()
        val position: Int = intent.getIntExtra("position", 0)
        val edit: Boolean = intent.getBooleanExtra("edit", false)
        if (edit) {
            editData = ArrayList(intent.getStringArrayListExtra("data"))
        } else {
            data = ArrayList(intent.getParcelableArrayListExtra("data"))
        }
        actionBar?.let {
            if (edit) {
                actionBar.title = "${position + 1}/${editData.size}"
            } else {
                actionBar.title = "${position + 1}/${data.size}"
            }
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        if (edit) {
            val adapter = EditShowAdapter()
            adapter.setData(editData)
            showListView.adapter = adapter
        } else {
            val adapter = ShowAdapter()
            adapter.setData(data)
            showListView.adapter = adapter
        }

        showListView.setCurrentItem(position, false)
        showListView.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (edit) {
                    actionBar?.title = "${position + 1}/${editData.size}"
                } else {
                    actionBar?.title = "${position + 1}/${data.size}"
                }
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

class ShowAdapter : RecyclerView.Adapter<ShowViewHolder>() {

    private val data: MutableList<GridData> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        return ShowViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_show_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        Glide.with(holder.imageView.context)
            .load(data[position].url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<GridData>) {
        this.data.addAll(data)
    }

}

class EditShowAdapter : RecyclerView.Adapter<ShowViewHolder>() {

    private val data: MutableList<String> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        return ShowViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_show_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        Glide.with(holder.imageView.context)
            .load(data[position])
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<String>) {
        this.data.addAll(data)
    }

}

class ShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val imageView: ImageView = itemView.findViewById(R.id.item_show_imageview)

}