package com.example.mynotes.Base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.crocodic.core.base.fragment.CoreFragment
import com.crocodic.core.data.CoreSession
import com.crocodic.core.helper.list.EndlessScrollListener
import com.example.mynotes.R
//import com.example.mynotes.databinding.FragmentHomeBinding
import com.example.mynotes.home.HomeViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//@AndroidEntryPoint
open class Fragment<vb : ViewDataBinding>(@LayoutRes private val layoutRes: Int) :
    CoreFragment<vb>(layoutRes) {
    protected val app: App by lazy { requireActivity().application as App }

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var session: CoreSession

//    @Inject
//    lateinit var homeBinding: FragmentHomeBinding
//
//    @Inject
//    lateinit var fragmentNotificationBinding: FragmentHomeBinding
//
//    @Inject
//    lateinit var activityViewModel: HomeViewModel


}
//class NotificationFragment : BaseFragment<FragmentNotificationBinding>(R.layout.fragmen_notification){
//    private val notificationFragmentViewModel: HomeViewModel by activityViewModels()
//    private val notificationData: ArrayList<NotificationFragment?>()
//    private val endlessScrollListener: EndlessScrollListener? = null
//    private val onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }
//
//}



