package com.example.packtaxi;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.packtaxi.model.Model;
import com.example.packtaxi.model.Package;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class mainScreenSenderFragment extends Fragment {
    private View view;
    private packageListViewModel viewModel;
    private MyAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private TextView noPackagesMessage;
    private ProgressBar pb;

    public mainScreenSenderFragment() {
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(packageListViewModel.class);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.sender_menu, menu);
        inflater.inflate(R.menu.base_menu, menu);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_pack_sender:
                @NonNull NavDirections action = mainScreenSenderFragmentDirections.actionMainScreenSenderFragmentToAddPackageFragment();
                Navigation.findNavController(view).navigate(action);
                return true;
            case R.id.my_profile_sender:
                Navigation.findNavController(view).navigate(mainScreenSenderFragmentDirections.actionMainScreenSenderFragmentToSenderProfileFragment());
                return true;
            case R.id.aboutAs_menu:
                Navigation.findNavController(view).navigate(mainScreenSenderFragmentDirections.actionMainScreenSenderFragmentToAboutUsFragment());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void noPackagesMessage(){
        if(viewModel.getPackage().getValue()!=null){
            if(viewModel.getPackage().getValue().size() == 0)
                noPackagesMessage.setVisibility(View.VISIBLE);
            else
                noPackagesMessage.setVisibility(View.GONE);
        }
        else
            noPackagesMessage.setVisibility(View.VISIBLE);
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        view= inflater.inflate(R.layout.fragment_main_screen_sender, container, false);
        pb = view.findViewById(R.id.packagesList_progressBar);
        pb.setVisibility(View.VISIBLE);
        RecyclerView list = view.findViewById(R.id.packageList_recycler);
        list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);
        swipeRefresh = view.findViewById(R.id.packagesList_swipeRefresh);
        noPackagesMessage = view.findViewById(R.id.packagesList_tv_noPackagesMessage);
        noPackagesMessage.setVisibility(View.GONE);

        adapter = new mainScreenSenderFragment.MyAdapter();
        list.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Package p = viewModel.getPackage().getValue().get(position);
//                @NonNull NavDirections action = mainScreenSenderFragmentDirections.actionMainScreenSenderFragmentToPackageDetailsFragment(p.getPackageID());
//                Navigation.findNavController(v).navigate(action);
                mainScreenSenderFragmentDirections.ActionMainScreenSenderFragmentToPackageDetailsFragment action = mainScreenSenderFragmentDirections.actionMainScreenSenderFragmentToPackageDetailsFragment(p.getPackageID());
                Navigation.findNavController(v).navigate(action);
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Model.getInstance().reloadPackagesList();
            }
        });

        viewModel.getPackage().observe(getViewLifecycleOwner(), (packagesList)-> {
            adapter.notifyDataSetChanged();
            noPackagesMessage();
        });

        swipeRefresh.setRefreshing(Model.getInstance().getPackagesListLoadingState().getValue() == Model.LoadingState.loading);
        Model.getInstance().getPackagesListLoadingState().observe(getViewLifecycleOwner(), loadingState -> {
            swipeRefresh.setRefreshing(loadingState == Model.LoadingState.loading);
        });

        pb.setVisibility(View.GONE);
        setHasOptionsMenu(true);
        return view;
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final OnItemClickListener listener;
        TextView sourceToDestination;
        TextView cost;

        TextView date;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            sourceToDestination = itemView.findViewById(R.id.pacListRow_so);
            cost = itemView.findViewById(R.id.pacListRow_de);
            date = itemView.findViewById(R.id.pacListRow_da);
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

        public void bind(Package p) {
            sourceToDestination.setText(p.getSource()+" -> "+p.getDestination());
            cost.setText(p.getCost()+" ₪");
            date.setText(String.valueOf(p.getDate()));
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
                View rowView = inflater.inflate(R.layout.package_list_row, parent, false);
                MyViewHolder viewHolder = new MyViewHolder(rowView, listener);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                Package P = viewModel.getPackage().getValue().get(position);
                holder.bind(P);
            }

            @Override
            public int getItemCount() {
                if (viewModel.getPackage().getValue() == null)
                    return 0;
                return viewModel.getPackage().getValue().size();
            }
        }

    }
