package com.assignement.user;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.assignement.R;
import com.assignement.util.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class UserListFragment extends Fragment implements UserAdapter.UserAdapterListener {

    public static int REQUEST_IMAGE_CAPTURE = 100;
    public static int MY_PERMISSIONS_REQUEST_CAMERA = 200;
    /**
     * VAriable declaration
     */
    private UserAdapter mUserAdapter;
    private List<User> mUserList;

    /**
     * UI variable declaration
     */
    private RecyclerView mRecyclerView;
    private View mProgressBar;
    private int mPosition;

    public static UserListFragment newInstance() {

        Bundle args = new Bundle();

        UserListFragment fragment = new UserListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mUserList == null) {
            loadUserData();
        } else {
            setAdapter();
        }
    }

    private void setAdapter() {
        if (mUserAdapter == null) {
            mUserAdapter = new UserAdapter(mUserList, this);
            mRecyclerView.setAdapter(mUserAdapter);
        } else if (mRecyclerView.getAdapter() == null) {
            mRecyclerView.setAdapter(mUserAdapter);
        } else {
            mUserAdapter.notifyDataSetChanged();
        }
    }

    /**
     * load data from server using volley or ratrofit.
     * right now used static data from json file in asserts folder.
     */
    private void loadUserData() {
        mProgressBar.setVisibility(View.VISIBLE);
        String data = JsonUtil.loadJSONFromAsset();
        Gson gson = new Gson();
        mUserList = gson.fromJson(data, new TypeToken<List<User>>() {
        }.getType());
        setAdapter();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onUserImageClick(int position) {
        mPosition = position;
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length > 0) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(getActivity(), "Camera permission required to access camera",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            mUserAdapter.setImageInItem(mPosition, (Bitmap) extras.get("data"));
        }
    }
}
