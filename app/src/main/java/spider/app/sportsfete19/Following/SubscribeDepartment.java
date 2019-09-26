package spider.app.sportsfete19.Following;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import spider.app.sportsfete19.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubscribeDepartment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubscribeDepartment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubscribeDepartment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG="SubscribeFragment";
    boolean[] checked=new boolean[15];
    List<String> deptList=new ArrayList();
    String[] deptArraySharedPreference=new String[15];
    RecyclerView recyclerView;
    SubscribeRecyclerAdapter adapter;
    Context context;
    SharedPreferences prefs;
    private int currentTransitionEffect = JazzyHelper.TILT;
    JazzyRecyclerViewScrollListener jazzyRecyclerViewScrollListener;


    private OnFragmentInteractionListener mListener;

    public SubscribeDepartment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubscribeDepartment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubscribeDepartment newInstance(String param1, String param2) {
        SubscribeDepartment fragment = new SubscribeDepartment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscribe_dept, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        deptArraySharedPreference=getResources().getStringArray(R.array.department_array_shared_preference);


        for ( int i = 0; i < deptArraySharedPreference.length; i ++)
            deptList.add(deptArraySharedPreference[i]);


        for (int i = 0; i < deptList.size(); i++) {
            checked[i]=prefs.getBoolean(deptArraySharedPreference[i]+"Checked",false);
        }

        recyclerView= (RecyclerView) view.findViewById(R.id.subscribe_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        jazzyRecyclerViewScrollListener = new JazzyRecyclerViewScrollListener();
        jazzyRecyclerViewScrollListener.setTransitionEffect(currentTransitionEffect);
        recyclerView.setOnScrollListener(jazzyRecyclerViewScrollListener);

        adapter=new SubscribeRecyclerAdapter(deptList, getActivity(), checked,
                Typeface.createFromAsset(getActivity().getAssets(), "fonts/InconsolataBold.ttf"), new SubscribeRecyclerAdapter.MyAdapterListener() {
            @Override
            public void buttonPressed(int position) {
                int selected=position;
                if (!checked[selected]){
                    checked[selected]=true;
                    Toast.makeText(getContext(), "Subscribed to "+deptArraySharedPreference[selected], Toast.LENGTH_SHORT).show();
                    FirebaseMessaging.getInstance().subscribeToTopic(deptArraySharedPreference[selected].replace(" ","").toUpperCase());
                    prefs.edit().putBoolean(deptArraySharedPreference[selected]+"Checked", true).apply();
                }else {
                    checked[selected]=false;
                    Toast.makeText(getContext(), "Unsubscribed from "+deptArraySharedPreference[selected], Toast.LENGTH_SHORT).show();
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(deptArraySharedPreference[selected].replace(" ","").toUpperCase());
                    prefs.edit().putBoolean(deptArraySharedPreference[selected]+"Checked", false).apply();
                }
            }
        });
        recyclerView.setAdapter(adapter);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onDestroyView(){
        Runtime.getRuntime().gc();
        super.onDestroyView();
    }
}
