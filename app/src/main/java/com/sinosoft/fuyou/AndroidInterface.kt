
import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.just.agentweb.AgentWeb


/**
 *
 *
 * @Created by wrs on 2020/10/16,13:57
 * @Description: js条用android的方法
 */
class AndroidInterface(private val mContext: Context, private val mWebView: AgentWeb) {
    @JavascriptInterface //一定要写，不然H5调不到这个方法
    fun queryHealthyDataByid(id: String?): String? {
        return  null;
    }
    init {

    }
}