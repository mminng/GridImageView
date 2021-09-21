package com.github.mminng.simple

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.mminng.gridimage.EditGridImageView
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by zh on 2021/7/10.
 */
class EditGridImageViewActivity : AppCompatActivity() {

    private val gridImageView: EditGridImageView by lazy {
        findViewById(R.id.edit_imageview)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_grid_imageview)
        val actionBar = supportActionBar
        actionBar?.let {
            actionBar.title = "Add"
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        gridImageView.setOnBindViewListener { data, view ->
            Glide.with(this@EditGridImageViewActivity).asBitmap().load(data).into(view)
        }
        gridImageView.setOnItemClickListener { data, index ->
            startActivity(
                Intent(
                    this@EditGridImageViewActivity,
                    ShowActivity::class.java
                ).putStringArrayListExtra("data", ArrayList(data))
                    .putExtra("position", index)
                    .putExtra("edit", true)
            )
        }
        gridImageView.setOnAddClickListener {
            openGallery()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            gridImageView.add(data?.data.toString())
        }
    }

    private fun openGallery() {
        val intent: Intent = Intent(ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_done, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.edit_add -> {
                if (gridImageView.getData().isNotEmpty()) {
                    val intent = Intent()
                    intent.putStringArrayListExtra("data", ArrayList(gridImageView.getData()))
                    setResult(Activity.RESULT_OK, intent)
                }
                finish()

            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

}