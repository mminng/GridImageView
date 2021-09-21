package com.github.mminng.simple

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mminng.gridimage.GridImageView

class MainActivity : AppCompatActivity() {

    private val listView: RecyclerView by lazy {
        findViewById(R.id.list_view)
    }

    val listAdapter: ListAdapter by lazy {
        ListAdapter(listView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView.layoutManager = LinearLayoutManager(this)
        listView.adapter = listAdapter
        listAdapter.setData(createData())
    }

    fun createData(): List<List<GridData>> {
        val temp: MutableList<List<GridData>> = arrayListOf()
        temp.add(data1)
        temp.add(data2)
        temp.add(data14)
        temp.add(data1_1)
        temp.add(data14)
        temp.add(data3)
        temp.add(data1_8)
        temp.add(data14)
        temp.add(data4)
        temp.add(data1_2)
        temp.add(data14)
        temp.add(data5)
        temp.add(data1_3)
        temp.add(data14)
        temp.add(data9)
        temp.add(data1_4)
        temp.add(data14)
        temp.add(data1_5)
        temp.add(data14)
        temp.add(data10)
        temp.add(data14)
        temp.add(data1_7)
        temp.add(data14)
        temp.add(data1_6)
        temp.add(data2_1)
        temp.add(data14)
        temp.add(data1_9)
        temp.add(data14)
        return temp
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            data?.let {
                val source: ArrayList<String>? = data.extras?.getStringArrayList("data")
                val temp: MutableList<GridData> = arrayListOf()
                source?.forEach {
                    val item = GridData(it, 500F, 750F)
                    temp.add(item)
                }
                val list: MutableList<MutableList<GridData>> = arrayListOf()
                list.addAll(listOf(temp))
                listAdapter.setData(list)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_add -> {
                startActivityForResult(Intent(this, EditGridImageViewActivity::class.java), 100)
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

class ListAdapter(private val listView: RecyclerView) : RecyclerView.Adapter<ListViewHolder>() {

    private val data: MutableList<List<GridData>> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val gridAdapter = GridAdapter()
        gridAdapter.setData(data[position])
        gridAdapter.setAspectRatio(data[position][0].w / data[position][0].h)
        gridAdapter.setOnItemClickListener { data, index ->
            holder.itemView.context.startActivity(
                Intent(
                    holder.itemView.context,
                    ShowActivity::class.java
                ).putParcelableArrayListExtra("data", ArrayList(data))
                    .putExtra("position", index)
            )
        }
        holder.gridImage.setAdapter(gridAdapter)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<List<GridData>>) {
        this.data.addAll(0, data)
        notifyItemRangeInserted(0, data.size)
        listView.smoothScrollToPosition(0)
    }
}

class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val gridImage: GridImageView = itemView.findViewById(R.id.item_grid_image)

}

private val data1: List<GridData> = listOf(
    GridData(
        "https://images.unsplash.com/photo-1624556880359-e27608246aab?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0fHx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    )
)

private val data1_1: List<GridData> = listOf(
    GridData(
        "https://images.unsplash.com/photo-1624558507666-a295c8649b01?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw3fHx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        666F
    )
)
private val data1_2: List<GridData> = listOf(
    GridData(
        "https://images.unsplash.com/photo-1609618969958-89e683902b71?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDI1fGJvOGpRS1RhRTBZfHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        755F
    )
)
private val data1_3: List<GridData> = listOf(
    GridData(
        "https://images.unsplash.com/photo-1622988869811-a4f3d3fe5441?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDE0OXxibzhqUUtUYUUwWXx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    )
)
private val data1_4: List<GridData> = listOf(
    GridData(
        "https://images.unsplash.com/photo-1624215824600-ed3118b76873?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDE4fGJvOGpRS1RhRTBZfHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        332F
    ),
)
private val data1_5: List<GridData> = listOf(
    GridData(
        "https://images.unsplash.com/photo-1624424008769-a97c2d38307f?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDF8aG1lbnZRaFVteE18fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        375F
    )
)
private val data1_6: List<GridData> = listOf(
    GridData(
        "https://images.unsplash.com/photo-1623200147876-c857cf1187d7?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDExOXxobWVudlFoVW14TXx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        331F
    )
)
private val data1_7: List<GridData> = listOf(
    GridData(
        "https://images.unsplash.com/photo-1617069470302-9b5592c80f66?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDMzfHRvd0paRnNrcEdnfHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        885F
    ),
)
private val data1_8: List<GridData> = listOf(
    GridData(
        "https://images.unsplash.com/photo-1624375147958-678d727cc0c4?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDQ5fGJvOGpRS1RhRTBZfHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        333F
    ),
)
private val data1_9: List<GridData> = listOf(
    GridData(
        "https://images.unsplash.com/photo-1598812401722-0efbdbbaa0fe?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDMxfHRvd0paRnNrcEdnfHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        903F
    ),
)
private val data2: List<GridData> = listOf(
    GridData(
        "https://images.unsplash.com/photo-1624129007116-2ef108b218fb?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDI3fHFQWXNEenZKT1ljfHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1606655151331-2b5e63510fab?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDZ8cm5TS0RId3dZVWt8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        647F
    )
)
private val data2_1: List<GridData> = listOf(
    GridData(
        "https://images.unsplash.com/photo-1624547218595-d85704cf4647?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw4NjJ8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624888978643-cad8abba35f1?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDh8Xzh6Rkh1aFJoeW98fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    )
)
private val data3: List<GridData> = listOf(
    GridData(
        "https://images.unsplash.com/photo-1541019656903-0df423a131cd?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDh8cm5TS0RId3dZVWt8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        625F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624375589127-c2edf33b803e?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDE4fEZ6bzN6dU9ITjZ3fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1619701957786-99ee09dfdf65?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDQ4fHRvd0paRnNrcEdnfHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    )
)
private val data4: List<GridData> = listOf(
    GridData(
        "https://images.unsplash.com/photo-1579128191615-c22cde169e1e?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDN8NnNNVmpUTFNrZVF8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1523386571890-790e639b10a4?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDR8NnNNVmpUTFNrZVF8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1534228746812-f15c4d80e702?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDZ8NnNNVmpUTFNrZVF8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1621024828756-8ca186f15daa?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDE1fDZzTVZqVExTa2VRfHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        400F
    )
)
private val data5: List<GridData> = listOf(
    GridData(
        "https://images.unsplash.com/photo-1600935136963-847a8247946b?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDE2fDZzTVZqVExTa2VRfHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        334F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624551797042-9c89f95b49c4?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyOHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624462966475-15d78c57d20e?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0MHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624462966581-bc6d768cbce5?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw2Mnx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624487926326-41f8e4c0a0d3?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw2N3x8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    )
)
private val data9: List<GridData> = listOf(
    GridData(
        "https://images.unsplash.com/photo-1624311866007-93a337ca5565?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw3N3x8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1606787366850-de6330128bfc?ixid=MnwxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHw5MHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        333F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624439896354-8bffc258d9f8?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw5M3x8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        718F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624322578153-d0bb3cfea7b8?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw5OXx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624486606439-2eafa44a0b2b?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxMTJ8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624514326167-7b4788f37dac?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxMjR8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624472896340-08dda9938113?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxNDl8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        749F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624475236903-de53c2b25ec3?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxNzZ8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        375F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624466551208-4fcd504cc9c9?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxODV8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        751F
    )
)

private val data10: List<GridData> = listOf(
    GridData(
        "https://images.unsplash.com/photo-1624406561763-6a772eab1b19?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxOTl8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624433403885-23953d7811dd?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyMDN8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        890F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624285206507-92894310aabf?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyMDl8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60\\",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624234161254-d02a0f5e63d2?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyMzd8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        333F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624306999934-ebe56feee1d4?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyNDR8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        889F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624375147958-678d727cc0c4?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyNjN8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        333F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624448213689-63f9a1e9f2d1?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyNDJ8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624457798984-be18a7212fad?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzMDF8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624442608061-cfaf1184927c?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzMzh8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        500F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624446373316-33399bcd253b?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzMzd8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    )
)
private val data14: List<GridData> = listOf(
    GridData(
        "https://images.unsplash.com/photo-1624388621600-02c079b3d6c0?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzNzZ8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        810F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624298593791-cb52673d222e?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzOTB8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624352710566-bafc9b2ac824?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzOTJ8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        667F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624364856940-14523188b6e4?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0MDB8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624279858993-6227bd7a10c1?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0NjN8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624338351074-949dcaa43066?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1MDZ8fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1536957604029-6779ab858551?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDN8RnpvM3p1T0hONnd8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1571687949921-1306bfb24b72?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDM4fEZ6bzN6dU9ITjZ3fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1623191576393-06909b2d65ab?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDUzfEZ6bzN6dU9ITjZ3fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        730F
    ),
    GridData(
        "https://images.unsplash.com/photo-1572181815474-d026ba2d22b6?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDc1fEZ6bzN6dU9ITjZ3fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        749F
    ),
    GridData(
        "https://images.unsplash.com/photo-1602207182439-9d49db72fad5?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDk3fEZ6bzN6dU9ITjZ3fHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        750F
    ),
    GridData(
        "https://images.unsplash.com/photo-1624016687205-b1808c76ec55?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDE2fGJEbzQ4Y1Vod25ZfHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        667F
    ),
    GridData(
        "https://images.unsplash.com/photo-1622451217684-185ac49734db?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDEwN3xiRG80OGNVaHduWXx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        667F
    ),
    GridData(
        "https://images.unsplash.com/photo-1621099650729-49f5eb7740c5?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDIxMXxiRG80OGNVaHduWXx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
        500F,
        667F
    )
)