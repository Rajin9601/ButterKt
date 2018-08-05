# ButterKt

ButterKt is a variation of KotterKnife. It is created to overcome some problems in KotterKnife which is

* lazy binding is dangerous if same layout id is reused in multiple file.
    * see lazy-binding-sample-app TODO: link to the readme page
* Unbind call for Fragment

For detail explanation, see TODO: link to blog.

# Installation

Currently, there is no way to add this library with dependencies. Since the core code is short, I recommend copy `ButterKt.kt` file into your source.
Also, it uses

# Actual Usage

## Activity, View, Dialog, RecyclerView.ViewHolder

```kotlin
class MainActivity : AppCompatActivity() {
  private val title by bindView(R.id.item_title)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    bindViews()
  }
}
```

call `bindViews()` in `Activity#onCreate`, `View#onFinishInflate`, `Dialog#onCreate`, `RecyclerView.ViewHolder#init`


## DialogFragment, Fragment

```kotlin
class TestFragment : Fragment() {
  private val title by bindView(R.id.item_title)

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?) {
    val view = inflater.inflate(R.layout.fragment_test, container, false)
    bindViews(view)
    return view
  }

  override fun onDestroyView() {
    super.onDestroyView()
    unbindViews()
  }
}
```

call `bindViews(view)` in `Fragment#onCreateView`, and `unbindViews()` in `Framgent#onDestroyView`

## RecyclerView.ViewHolder

```kotlin
class TestViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
  private val title by bindView(R.id.item_title)

  init {
    bindViews()
  }
}
```

call `bindViews()` in init block

## custom viewHolder ButterViewHolder

```kotlin
class TestViewHolder(val view: View) : ButterViewHolder {
  private val title by bindView(R.id.item_title)

  fun someFunction1() {
    bindViews(view)
  }

  fun someFunction2() {
    unbindViews()
  }
}
```

call `bindViews(view)` and `unbindViews()` when you think it's appropriate.
