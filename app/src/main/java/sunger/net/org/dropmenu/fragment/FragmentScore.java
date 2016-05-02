package sunger.net.org.dropmenu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Arrays;

import sunger.net.org.dropmenu.adapter.FilterAdapter;
import sunger.net.org.dropmenu.new_drop;

/**
 * Created by sunger on 16/4/16.
 */
public class FragmentScore extends Fragment {

    private ListView listView;
    private FilterAdapter adapter;
    private String score[] = {"Service", "Flavor", "Environment", "Total"};
    private new_drop newdrop;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newdrop =(new_drop)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listView = new ListView(getActivity());
          return listView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new FilterAdapter(getActivity(), Arrays.asList(score));
        listView.setDividerHeight(0);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newdrop.onFilter(0, score[position]);
                adapter.setCheckItem(position);
            }
        });
    }
}
