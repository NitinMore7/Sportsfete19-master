package spider.app.sportsfete19;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import spider.app.sportsfete19.API.ApiInterface;
import spider.app.sportsfete19.API.SearchByNamePOJO;
import spider.app.sportsfete19.API.SearchUserByRollNo.SearchItem;
import spider.app.sportsfete19.R;
import spider.app.sportsfete19.RecyclerViewAdapter;
import spider.app.sportsfete19.UserDetails;
import spider.app.sportsfete19.UserProfile;

public class UserSearch extends Fragment {
    private RecyclerViewAdapter adapter;
    private ConstraintLayout constraintLayout;
    private List<SearchItem> exampleList;
    private ProgressDialog progressDialog;
    private ArrayList<String> numbers;
    private RecyclerView recyclerView;
    private static final String Key = "rollno";
    private ApiInterface apiInterface;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            String num = numbers.get(position);
            Intent intent = new Intent(getActivity(), UserProfile.class);
            intent.putExtra(Key, num);
            startActivity(intent);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_user_details, container, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        constraintLayout =getView().findViewById(R.id.constraintLayout);
        recyclerView = getView().findViewById(R.id.recycler_view);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {

        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Roll Number / Name");
//        super.onCreateOptionsMenu(menu,inflater);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                query = query.trim();

                if (query.matches("[0-9]+") && query.length() == 9) {
                    Intent intent = new Intent(getActivity(), UserProfile.class);
                    intent.putExtra(Key, query);
                    startActivity(intent);
                }else if (query.matches("^[ A-Za-z]+$")) {
                    progressDialog  = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Searching user");
                    exampleList = new ArrayList<>();
                    numbers = new ArrayList<>();
                    progressDialog.show();
                    getRollNumber(query);
                }else if(query.matches("[0-9]+") && query.length() == 9){
                    Snackbar.make(constraintLayout,"Enter valid roll number", Snackbar.LENGTH_SHORT).show();

                }else{
                    Snackbar.make(constraintLayout,"Enter either a Roll number or a name", Snackbar.LENGTH_SHORT).show();
                }


                return false;


            }

            @Override
            public boolean onQueryTextChange(String newText){
                return false;
            }
        });


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void getRollNumber(String name) {
        apiInterface = ApiInterface.retrofit.create(ApiInterface.class);
        Call<List<SearchByNamePOJO>> call = apiInterface.getRollNo(name);

        call.enqueue(new Callback<List<SearchByNamePOJO>>() {
            @Override
            public void onResponse(Call<List<SearchByNamePOJO>> call, Response<List<SearchByNamePOJO>> response) {
                progressDialog.dismiss();
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Data Not Found!", Toast.LENGTH_LONG).show();
                    return;
                }

                List<SearchByNamePOJO> searchByNamePOJOS = response.body();

                for (SearchByNamePOJO searchByNamePOJO : searchByNamePOJOS) {
                    String name = searchByNamePOJO.getUserName();

                    String roll = searchByNamePOJO.getRollno();

                    if(!name.equals("403115054")){
                        exampleList.add(new SearchItem(name, roll));
                        numbers.add(roll);

                    }

                }
                setUpRecyclerView();
            }

            @Override
            public void onFailure(Call<List<SearchByNamePOJO>> call, Throwable t) {
                Log.e("TAG", "fail");
            }
        });
    }

    private void setUpRecyclerView() {

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new RecyclerViewAdapter(exampleList);
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
        recyclerView.setLayoutAnimation(animation);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(onItemClickListener);
    }

}
