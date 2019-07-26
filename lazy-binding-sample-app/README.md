# Sample app using lazy binding

This app is showing example of dangerous usage of lazy binding.

When same id is used for RecyclerView's item and view in Activity, lazy binding can cause bug of accessing view in RecyclerView's item instead of view in Activity.

```kotlin
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    setContentView(R.layout.activity_main)
    // activity_main has recyclerView and textView

    recycler_view.adpater = MyAdapter()

    recyclerView.postDelayed({
      textView.setText(R.string.testing_str)
      // BUG HERE : this textView accesses to item view in recyclerView.
    }, 1000)
  }
}

class MyAdapter: RecyclerView.Adapter<MyAdapter.ViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup,
                                  viewType: Int): MyAdapter.ViewHolder {
    val textView = LayoutInflater.from(parent.context)
        .inflate(R.layout.my_text_view, parent, false)
    // my_text_view has TextView with id of textView
    return ViewHolder(textView)
  }
}
```

To prevent this, you can a) use distinct layout id or b) binds view before dynamic view creation. Using distinct layout id can be one solution, but layout id can be unnecessarily long. To me, binded view's name should not have context of which layout it exists because class name already have that context.
