package me.rajin.buttekt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.R.attr.layoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.rajin.butterkt.R
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.activity_main.textView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MyAdapter()
        }

        recyclerView.postDelayed({
            textView.setText(R.string.testing_str)
            // this textView accesses to item view in recycler view.
        }, 1000)
    }
}

class MyAdapter :
        RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(containerView: LinearLayout) : RecyclerView.ViewHolder(containerView)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.ViewHolder {
        val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.my_text_view, parent, false) as LinearLayout
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Do nothing
    }

    override fun getItemCount() = 1
}
