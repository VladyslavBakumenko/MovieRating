import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class PaginationScrollListener(var layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    private var currentPage = 0
    private var isLoading = true
    private var previousTotal = 0

    private val visibleThreshold = 5


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        Log.d("fdgdfdggdf", "SCROLLLLLLLLl")
        Log.d("fdgdfdggdf", layoutManager.childCount.toString())
        Log.d("fdgdfdggdf", layoutManager.itemCount.toString())
        Log.d("fdgdfdggdf", layoutManager.findFirstVisibleItemPosition().toString())
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (isLoading) {
            if (totalItemCount > previousTotal) {
                isLoading = false
                previousTotal = totalItemCount
            }
        }

        if (!isLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItemPosition + visibleThreshold)) {
            currentPage++
            loadMoreItems(currentPage)
            isLoading = true
        }
    }


    abstract fun loadMoreItems(page: Int)
}