package io.rolgalan.musicsearch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by Roldán Galán on 15/11/2016.
 */

public class MyFloatingSearchView extends FloatingSearchView {
    public MyFloatingSearchView(Context context) {
        super(context);
    }

    public MyFloatingSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(EditText searchBarText, final SearchQueryListener listener) {
        initEditText(searchBarText, listener);

        setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    clearSuggestions();
                } else {
                    //TODO load suggestions based on newQuery
                    //searchView.showProgress();
                    //and pass them with searchView.swapSuggestions(newSuggestions);
                }
            }
        });

        setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                String mLastQuery = searchSuggestion.getBody();
                listener.searchQuery(mLastQuery);
            }

            @Override
            public void onSearchAction(String query) {
                //mLastQuery = query;
                listener.searchQuery(query);
            }
        });
    }

    private void initEditText(EditText searchBarText, final SearchQueryListener listener) {
        //Fix bug in library for som Android versions https://github.com/arimorty/floatingsearchview/issues/159
        searchBarText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchBarText.setSingleLine();
        searchBarText.setMaxLines(1);


        searchBarText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String mLastQuery = textView.getText().toString();
                listener.searchQuery(mLastQuery);
                return true;
            }
        });
    }

    public interface SearchQueryListener {
        void searchQuery(String query);
    }
}
