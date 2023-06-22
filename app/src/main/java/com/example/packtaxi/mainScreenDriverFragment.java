package com.example.packtaxi;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.packtaxi.model.FutureRoute;
import com.example.packtaxi.model.Model;

public class mainScreenDriverFragment extends Fragment {
    private FutureRoutesListViewModel viewModel;
    private View view;
    private MyAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private TextView noRoutesMessage;
    private ProgressBar pb;

    public mainScreenDriverFragment() {
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(FutureRoutesListViewModel.class);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.driver_menu, menu);
        inflater.inflate(R.menu.base_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_drive_driver:
                @NonNull NavDirections action = mainScreenDriverFragmentDirections.actionMainScreenDriverFragmentToAddFutureRoudFragment();
                Navigation.findNavController(view).navigate(action);
                return true;
            case R.id.my_profile_driver:
                Navigation.findNavController(view).navigate(mainScreenDriverFragmentDirections.actionMainScreenDriverFragmentToDriverProfileFragment());
                return true;
            case R.id.aboutAs_menu:
                Navigation.findNavController(view).navigate(mainScreenDriverFragmentDirections.actionMainScreenDriverFragmentToAboutUsFragment());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void noRoutesMessage(){
        if(viewModel.getRoutes().getValue()!=null){
            if(viewModel.getRoutes().getValue().size() == 0)
                noRoutesMessage.setVisibility(View.VISIBLE);
            else
                noRoutesMessage.setVisibility(View.GONE);
        }
        else
            noRoutesMessage.setVisibility(View.VISIBLE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_screen_driver, container, false);
        pb = view.findViewById(R.id.packagesList_progressBar);
        pb.setVisibility(View.VISIBLE);
        RecyclerView list = view.findViewById(R.id.packageList_recycler);
        list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);
        swipeRefresh = view.findViewById(R.id.packagesList_swipeRefresh);
        noRoutesMessage = view.findViewById(R.id.packagesList_tv_noPackagesMessage);
        noRoutesMessage.setVisibility(View.GONE);

        adapter = new mainScreenDriverFragment.MyAdapter();
        list.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                FutureRoute fr = viewModel.getRoutes().getValue().get(position);
                @NonNull NavDirections action = mainScreenDriverFragmentDirections.actionMainScreenDriverFragmentToFutuerRoudDetailsFragment(fr.getFutureRouteID());
                Navigation.findNavController(v).navigate(action);
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Model.getInstance().reloadRoutesList();
            }
        });

        viewModel.getRoutes().observe(getViewLifecycleOwner(), (routesList)-> {
            adapter.notifyDataSetChanged();
            noRoutesMessage();
        });

        swipeRefresh.setRefreshing(Model.getInstance().getRoutesListLoadingState().getValue() == Model.LoadingState.loading);
        Model.getInstance().getRoutesListLoadingState().observe(getViewLifecycleOwner(), loadingState -> {
            swipeRefresh.setRefreshing(loadingState == Model.LoadingState.loading);
        });

        pb.setVisibility(View.GONE);
        setHasOptionsMenu(true);
        return view;
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final OnItemClickListener listener;
        TextView sourceToDestination;
        TextView date;
        TextView cost;
        ImageView match;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            sourceToDestination = itemView.findViewById(R.id.routeListRow_sorToDes);
            cost= itemView.findViewById(R.id.routeListRow_cost);
            date = itemView.findViewById(R.id.routeListRow_date);
            match = itemView.findViewById(R.id.matchDriver);

            this.listener = listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (listener != null)
                        listener.onItemClick(pos, v);
                }
            });
        }
        public void bind(FutureRoute fr) {
            sourceToDestination.setText(fr.getSource()+" -> "+ fr.getDestination());
            cost.setText(fr.getCost()+ "₪ per delivery");
            date.setText(String.valueOf(fr.getDate()));
            date.setText(String.valueOf(fr.getDate()));
            if(!fr.getPackagesNumbers().equals(",")) {
                match.setVisibility(View.VISIBLE);
            }
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private OnItemClickListener listener;

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View rowView = inflater.inflate(R.layout.routes_list_row, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(rowView, listener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            FutureRoute fr = viewModel.getRoutes().getValue().get(position);
            holder.bind(fr);
        }

        @Override
        public int getItemCount() {
            if(viewModel.getRoutes().getValue() == null)
                return 0;
            return viewModel.getRoutes().getValue().size();
        }
    }
}