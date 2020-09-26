package com.app.imagesearch.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.imagesearch.R
import com.app.imagesearch.ui.search.state.SearchStateEvent
import com.app.imagesearch.util.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_image_search.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class ImageSearchActivity : AppCompatActivity() {

    private var hasMoreData = true
    private var isLoading = false
    private val viewModel: ImageSearchViewModel by viewModels()
    private val disposables = CompositeDisposable()

    @Inject
    lateinit var adapter: ImagesRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_search)
        initRecyclerView()
        subscribeObservers()
        addSearchListener()
    }

    private fun addSearchListener() {

        val observable = Observable.create(ObservableOnSubscribe<String> { emitter ->
            etSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) = Unit

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (emitter?.isDisposed == false) {
                        emitter.onNext(s.toString()) // Pass the query to the emitter
                    }
                }

            })
        }).debounce(250, TimeUnit.MILLISECONDS) // Apply Debounce() operator to limit requests
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        observable.subscribe(object : io.reactivex.rxjava3.core.Observer<String> {
            override fun onComplete() = Unit

            override fun onSubscribe(d: Disposable?) {
                disposables.add(d)
            }

            override fun onNext(t: String?) {
                t?.let {
                    viewModel.setStateEvent(SearchStateEvent.SearchImageEvent(t))
                }

            }

            override fun onError(e: Throwable?) = Unit

        })

    }

    private fun initRecyclerView() {
        rvImages.adapter = adapter
        rvImages.addOnScrollListener(object :
            PaginationScrollListener(rvImages.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                return !hasMoreData
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                if (!isLoading() && !isLastPage())
                    viewModel.setStateEvent(SearchStateEvent.NextPageEvent(etSearch.text.toString()))
            }
        })
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->

            showProgress(dataState.loading)
            // handle Data<T>
            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->

                    println("DEBUG: DataState: $mainViewState")

                    mainViewState.searchResponse?.let {
                        // set summary data
                        viewModel.setSummaryData(it)
                    }

                    mainViewState.nextPageResponse?.let {
                        viewModel.setNextPageData(it)
                    }
                }
            }
        })

        viewModel.viewState.observe(this, Observer { viewState ->
            viewState.searchResponse?.let { images ->
                hasMoreData = images.data.isNotEmpty()
                // set news to RecyclerView
                adapter.submitList(images.data)
                println("DEBUG: Setting news to RecyclerView: $images")
            }
            viewState.nextPageResponse?.let { images ->
                hasMoreData = images.data.isNotEmpty()
                // set news to RecyclerView
                adapter.addMoreData(images.data)
                println("DEBUG: Setting news to RecyclerView: $images")
            }
        })
    }

    private fun showProgress(isVisible: Boolean) {
        if (isVisible) {
            progress.visibility = View.VISIBLE
            isLoading = true
        } else {
            progress.visibility = View.GONE
            isLoading = false
        }
    }
}