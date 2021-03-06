package com.gmjproductions.gjtwittersearch.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmjproductions.gjtwittersearch.R
import com.gmjproductions.gjtwittersearch.model.TweetsViewModel
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.TweetView
import kotlinx.android.synthetic.main.search_tweets_results.*
import layout.SearchTweetsFragment

/**
 * Created by garyjacobs on 1/19/18.
 */
class SearchTweetsResultsFragment : Fragment() {

    lateinit var myActivity: SearchTweetsActivity
    lateinit var tweetsViewModel: TweetsViewModel

    companion object {
        @JvmStatic
        val TAG = SearchTweetsFragment::class.java.simpleName
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = activity as SearchTweetsActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.search_tweets_results, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        tweetsViewModel = ViewModelProviders.of(myActivity).get(TweetsViewModel::class.java)

        tweets_results_list.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        tweets_results_list.adapter = TweetListAdapter(tweetsViewModel.tweetList.value!!)

        tweetsViewModel.tweetList.observe(this, object : Observer<List<Tweet>> {
            override fun onChanged(tweetList: List<Tweet>?) {
                tweetList?.let {
                    (tweets_results_list.adapter as TweetListAdapter).tweetList = tweetList
                    tweets_results_list.adapter?.notifyDataSetChanged()
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //context?.showToast(getString(R.string.on_create, TAG), Toast.LENGTH_SHORT)
    }

    override fun onDestroy() {
        super.onDestroy()
        context?.showToast(getString(R.string.on_destroy, TAG), Toast.LENGTH_LONG)
    }

    // tweet list components
    class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tweetView: TweetView = itemView as TweetView
    }

    inner class TweetListAdapter(tweetList: List<Tweet>) : RecyclerView.Adapter<TweetViewHolder>() {

        var tweetList = tweetList

        override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
            holder.tweetView.tweet = tweetList.get(position)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
            // create an initial TweetView with any Tweet...
            // new Tweet will be bound during onBindViewHolder before display
            return TweetViewHolder(TweetView(context, tweetList[0]))

        }

        override fun getItemCount(): Int {
            return tweetList.size
        }
    }
}