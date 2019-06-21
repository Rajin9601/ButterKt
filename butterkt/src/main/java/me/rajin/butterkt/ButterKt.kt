package me.rajin.butterkt

import android.app.Activity
import android.app.Dialog
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.lang.ref.WeakReference
import java.util.WeakHashMap
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

interface ButterViewHolder

// region bindView
fun <V : View> View.bindView(id: Int):
        ReadOnlyProperty<View, V> = requiredLazy(id, viewFinder, this)

fun <V : View> Activity.bindView(id: Int):
        ReadOnlyProperty<Activity, V> = requiredLazy(id, viewFinder, this)

fun <V : View> Dialog.bindView(id: Int):
        ReadOnlyProperty<Dialog, V> = requiredLazy(id, viewFinder, this)

fun <V : View> DialogFragment.bindView(id: Int):
        ReadOnlyProperty<DialogFragment, V> = requiredStatic(id, viewFinder, this)

fun <V : View> Fragment.bindView(id: Int):
        ReadOnlyProperty<Fragment, V> = requiredStatic(id, viewFinder, this)

fun <V : View> ViewHolder.bindView(id: Int):
        ReadOnlyProperty<ViewHolder, V> = requiredLazy(id, viewFinder, this)

fun <V : View> ButterViewHolder.bindView(id: Int):
        ReadOnlyProperty<ButterViewHolder, V> = requiredStatic(id, viewFinder, this)
// endregion

// region bindOptionalView
fun <V : View> View.bindOptionalView(id: Int):
        ReadOnlyProperty<View, V?> = optionalLazy(id, viewFinder, this)

fun <V : View> Activity.bindOptionalView(id: Int):
        ReadOnlyProperty<Activity, V?> = optionalLazy(id, viewFinder, this)

fun <V : View> Dialog.bindOptionalView(id: Int):
        ReadOnlyProperty<Dialog, V?> = optionalLazy(id, viewFinder, this)

fun <V : View> DialogFragment.bindOptionalView(id: Int):
        ReadOnlyProperty<DialogFragment, V?> = optionalStatic(id, viewFinder, this)

fun <V : View> Fragment.bindOptionalView(id: Int):
        ReadOnlyProperty<Fragment, V?> = optionalStatic(id, viewFinder, this)

fun <V : View> ViewHolder.bindOptionalView(id: Int):
        ReadOnlyProperty<ViewHolder, V?> = optionalLazy(id, viewFinder, this)

fun <V : View> ButterViewHolder.bindOptionalView(id: Int):
        ReadOnlyProperty<ButterViewHolder, V?> = optionalStatic(id, viewFinder, this)
// endregion

// region bindViews
fun <V : View> View.bindViews(vararg ids: Int):
        ReadOnlyProperty<View, List<V>> = requiredLazy(ids, viewFinder, this)

fun <V : View> Activity.bindViews(vararg ids: Int):
        ReadOnlyProperty<Activity, List<V>> = requiredLazy(ids, viewFinder, this)

fun <V : View> Dialog.bindViews(vararg ids: Int):
        ReadOnlyProperty<Dialog, List<V>> = requiredLazy(ids, viewFinder, this)

fun <V : View> DialogFragment.bindViews(vararg ids: Int):
        ReadOnlyProperty<DialogFragment, List<V>> = requiredStatic(ids, viewFinder, this)

fun <V : View> Fragment.bindViews(vararg ids: Int):
        ReadOnlyProperty<Fragment, List<V>> = requiredStatic(ids, viewFinder, this)

fun <V : View> ViewHolder.bindViews(vararg ids: Int):
        ReadOnlyProperty<ViewHolder, List<V>> = requiredLazy(ids, viewFinder, this)

fun <V : View> ButterViewHolder.bindViews(vararg ids: Int):
        ReadOnlyProperty<ButterViewHolder, List<V>> = requiredStatic(ids, viewFinder, this)
// endregion

// region bindOptionalViews
fun <V : View> View.bindOptionalViews(vararg ids: Int):
        ReadOnlyProperty<View, List<V>> = optionalLazy(ids, viewFinder, this)

fun <V : View> Activity.bindOptionalViews(vararg ids: Int):
        ReadOnlyProperty<Activity, List<V>> = optionalLazy(ids, viewFinder, this)

fun <V : View> Dialog.bindOptionalViews(vararg ids: Int):
        ReadOnlyProperty<Dialog, List<V>> = optionalLazy(ids, viewFinder, this)

fun <V : View> DialogFragment.bindOptionalViews(vararg ids: Int):
        ReadOnlyProperty<DialogFragment, List<V>> = optionalStatic(ids, viewFinder, this)

fun <V : View> Fragment.bindOptionalViews(vararg ids: Int):
        ReadOnlyProperty<Fragment, List<V>> = optionalStatic(ids, viewFinder, this)

fun <V : View> ViewHolder.bindOptionalViews(vararg ids: Int):
        ReadOnlyProperty<ViewHolder, List<V>> = optionalLazy(ids, viewFinder, this)

fun <V : View> ButterViewHolder.bindOptionalViews(vararg ids: Int):
        ReadOnlyProperty<ButterViewHolder, List<V>> = optionalStatic(ids, viewFinder, this)
// endregion

private val View.viewFinder: View.(Int) -> View?
    get() = { findViewById(it) }
private val Activity.viewFinder: Activity.(Int) -> View?
    get() = { findViewById(it) }
private val Dialog.viewFinder: Dialog.(Int) -> View?
    get() = { findViewById(it) }
private val DialogFragment.viewFinder: (View, Int) -> View?
    get() = { view, id -> view.findViewById(id) }
private val Fragment.viewFinder: (View, Int) -> View?
    get() = { view, id -> view.findViewById(id) }
private val ViewHolder.viewFinder: ViewHolder.(Int) -> View?
    get() = { itemView.findViewById(it) }
private val ButterViewHolder.viewFinder: (View, Int) -> View?
    get() = { view, id -> view.findViewById(id) }

private fun viewNotFound(id: Int): Nothing =
        throw IllegalStateException("View ID ${id.toString(16)} not found.")

@Suppress("UNCHECKED_CAST")
private fun <C, V : View> requiredLazy(id: Int, finder: C.(Int) -> View?, container: C) =
        LazyBinding({ t: C -> t.finder(id) as V? ?: viewNotFound(id) }, container)

@Suppress("UNCHECKED_CAST")
private fun <C, V : View> optionalLazy(id: Int, finder: C.(Int) -> View?, container: C) =
        LazyBinding({ t: C -> t.finder(id) as V? }, container)

@Suppress("UNCHECKED_CAST")
private fun <C, V : View> requiredLazy(ids: IntArray, finder: C.(Int) -> View?, container: C) =
        LazyBinding({ t: C ->
            ids.map {
                t.finder(it) as V? ?: viewNotFound(it)
            }
        }, container)

@Suppress("UNCHECKED_CAST")
private fun <C, V : View> optionalLazy(ids: IntArray, finder: C.(Int) -> View?, container: C) =
        LazyBinding({ t: C -> ids.map { t.finder(it) as V? }.filterNotNull() }, container)

@Suppress("UNCHECKED_CAST")
private fun <C, V : View> requiredStatic(id: Int, finder: (View, Int) -> View?, container: C) =
        StaticBinding({ v: View -> finder(v, id) as V? ?: viewNotFound(id) }, container)

@Suppress("UNCHECKED_CAST")
private fun <C, V : View> optionalStatic(id: Int, finder: (View, Int) -> View?, container: C) =
        StaticBinding({ v: View -> finder(v, id) as V? }, container)

@Suppress("UNCHECKED_CAST")
private fun <C, V : View> requiredStatic(ids: IntArray, finder: (View, Int) -> View?, container: C) =
        StaticBinding({ l: View ->
            ids.map {
                finder(l, it) as V? ?: viewNotFound(it)
            }
        }, container)

@Suppress("UNCHECKED_CAST")
private fun <C, V : View, L> optionalStatic(ids: IntArray, finder: (L, Int) -> View?, container: C) =
        StaticBinding({ v: L -> ids.map { finder(v, it) as V? }.filterNotNull() }, container)

private interface Binding {
    fun loadValue(thisRef: Any)
    fun unloadValue()
}

/**
 * Binding that supports lazy loading.
 */
@Suppress("UNCHECKED_CAST")
private class LazyBinding<ThisRefT, ViewT>(val initializer: (ThisRefT) -> ViewT, container: ThisRefT) : ReadOnlyProperty<ThisRefT, ViewT>, Binding {

    init {
        BindingCache.add(container, this)
    }

    private object EMPTY

    private var value: Any? = EMPTY

    override fun getValue(thisRef: ThisRefT, property: KProperty<*>): ViewT {
        if (value == EMPTY) {
            Log.e("ButterKt", "accessed view before binding. property: ${property.name}, container: $thisRef")
            Log.e("ButterKt", "Did you forget to call ButterKt#bind?")
            value = initializer.invoke(thisRef)
        }
        @Suppress("UNCHECKED_CAST")
        return value as ViewT
    }

    override fun loadValue(thisRef: Any) {
        if (value == EMPTY) {
            initializer.invoke(thisRef as ThisRefT).apply {
                value = this
            }
        }
    }

    override fun unloadValue() {
        value = EMPTY
    }
}

/**
 * Binding that doesn't support lazy loading.
 */
@Suppress("UNCHECKED_CAST")
private class StaticBinding<ThisRefT, ViewT, InitParamT>(val initializer: (InitParamT) -> ViewT, container: ThisRefT) : ReadOnlyProperty<ThisRefT, ViewT>, Binding {

    init {
        BindingCache.add(container, this)
    }

    private object EMPTY

    private var value: Any? = EMPTY

    override fun getValue(thisRef: ThisRefT, property: KProperty<*>): ViewT {
        if (value == EMPTY) {
            throw NoSuchElementException("accessed view before binding. call ButterKt#bind before accessing view.")
        }
        @Suppress("UNCHECKED_CAST")
        return value as ViewT
    }

    override fun loadValue(thisRef: Any) {
        if (value == EMPTY) {
            initializer.invoke(thisRef as InitParamT).apply {
                value = this
            }
        }
    }

    override fun unloadValue() {
        value = EMPTY
    }
}

private object BindingCache {
    private val containerToBindings = WeakHashMap<Any, MutableSet<WeakReference<Binding>>>()

    fun <T> add(container: T, binding: Binding) {
        containerToBindings.getOrPut(container) { mutableSetOf() }.add(WeakReference(binding))
    }

    fun bind(container: Any) {
        bind(container, container)
    }

    fun bind(container: Any, view: Any) {
        containerToBindings[container]?.forEach {
            it.get()?.loadValue(view)
        }
    }

    fun unbind(container: Any) {
        containerToBindings[container]?.forEach {
            it.get()?.unloadValue()
        }
    }
}

object ButterKt {
    fun bind(view: View) {
        BindingCache.bind(view)
    }

    fun unbind(view: View) {
        BindingCache.unbind(view)
    }

    fun bind(activity: Activity) {
        BindingCache.bind(activity)
    }

    fun unbind(activity: Activity) {
        BindingCache.unbind(activity)
    }

    fun bind(dialog: Dialog) {
        BindingCache.bind(dialog)
    }

    fun unbind(dialog: Dialog) {
        BindingCache.unbind(dialog)
    }

    fun bind(fragment: DialogFragment) {
        BindingCache.bind(fragment)
    }

    fun unbind(dialog: DialogFragment) {
        BindingCache.unbind(dialog)
    }

    fun bind(fragment: Fragment) {
        BindingCache.bind(fragment)
    }

    fun unbind(fragment: Fragment) {
        BindingCache.unbind(fragment)
    }

    fun bind(holder: ViewHolder) {
        BindingCache.bind(holder)
    }

    fun unbind(holder: ViewHolder) {
        BindingCache.unbind(holder)
    }

    fun bind(holder: ButterViewHolder) {
        BindingCache.bind(holder)
    }

    fun unbind(holder: ButterViewHolder) {
        BindingCache.unbind(holder)
    }
}
