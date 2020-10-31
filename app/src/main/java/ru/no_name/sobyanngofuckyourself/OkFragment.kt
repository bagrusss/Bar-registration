package ru.no_name.sobyanngofuckyourself

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_ok.*
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class OkFragment : Fragment(R.layout.fragment_ok) {

    private var disposable = Disposable.disposed()

    private var secondsLeft = SECONDS_LEFT

    private val formattedTime
        get() = getString(R.string.ok_minute, secondsLeft / 60, secondsLeft % 60)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.run {
            place_name.text = "${getString(TYPE_KEY)} ${getString(NAME_KEY)}"
            address.text = getString(ADDRESS_KEY)
            bar_owner.text = getString(OWNER_KEY)
            date_time.text = getString(FORMATTED_TIME)
        }

        time.text = formattedTime
    }

    override fun onStart() {
        super.onStart()

        disposable = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .filter { secondsLeft >= 0 }
                .subscribe {
                    secondsLeft -= 1
                    time.text = formattedTime
                }
    }

    override fun onStop() {
        super.onStop()

        disposable.dispose()
    }

    companion object {

        fun createArgs(type: String,
                       name: String,
                       owner: String,
                       address: String,
                       formattedTime: String) = Bundle().apply {
            putString(TYPE_KEY, type)
            putString(NAME_KEY, name)
            putString(OWNER_KEY, owner)
            putString(ADDRESS_KEY, address)
            putString(FORMATTED_TIME, formattedTime)
        }

        private const val TYPE_KEY = "TYPE_KEY"
        private const val NAME_KEY = "NAME_KEY"
        private const val OWNER_KEY = "OWNER_KEY"
        private const val ADDRESS_KEY = "ADDRESS_KEY"
        private const val FORMATTED_TIME = "FORMATTED_TIME"

        private const val SECONDS_LEFT = 60 * 15
    }
}