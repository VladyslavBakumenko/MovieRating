import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class PaginationScrollListener(var layoutManager: RecyclerView.LayoutManager?) :
    RecyclerView.OnScrollListener() {

    private var currentPage = 1
    private var isLoading = true
    private var previousTotal = 0

    private val visibleThreshold = 5


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = if (layoutManager is LinearLayoutManager)
            layoutManager as LinearLayoutManager
        else
            layoutManager as GridLayoutManager

        val visibleItemCount = layoutManager.childCount

        if (visibleItemCount <= 0) return

        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()


        Log.d("dfgfdghbghf", "total = $totalItemCount, prev = $previousTotal ")

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