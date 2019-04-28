package com.assignement.user;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.assignement.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> mUserList;
    private UserAdapterListener mUserAdapterListener;

    public UserAdapter(List<User> userList, UserAdapterListener userAdapterListener) {
        mUserList = userList;
        mUserAdapterListener = userAdapterListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_user_data, parent, false);

        RadioGroup rg = view.findViewById(R.id.radioGroup);
        final User user = mUserList.get(i);
        if (user.dataMap != null && user.dataMap.options != null) {

            for (String option : user.dataMap.options) {
                RadioButton radioButton = new RadioButton(parent.getContext());
                radioButton.setId(View.generateViewId());
                radioButton.setText(option);
                rg.addView(radioButton);
            }
        }
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final User user = mUserList.get(i);


        viewHolder.mCommentSwitch.setChecked(user.isCommentEnable);
        viewHolder.mCommentSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        user.isCommentEnable = isChecked;
                        if (isChecked) {
                            viewHolder.mCommentEditText.setVisibility(View.VISIBLE);
                        } else {
                            viewHolder.mCommentEditText.setVisibility(View.GONE);
                        }
                    }
                });

        viewHolder.mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });

        viewHolder.mUserImageView.setTag(i);
        viewHolder.mUserImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserAdapterListener.onUserImageClick((int) viewHolder.mUserImageView.getTag());
            }
        });

        viewHolder.mRemoveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.haveImage) {
                    viewHolder.mRemoveImage.setVisibility(View.GONE);
                    user.haveImage = false;
                    user.imageBitmap = null;
                    viewHolder.mUserImageView.setImageDrawable(
                            ContextCompat.getDrawable(viewHolder.mRemoveImage.getContext(),
                                    R.drawable.avatar));
                }
            }
        });

        if (user.haveImage) {
            viewHolder.mRemoveImage.setVisibility(View.VISIBLE);
            viewHolder.mUserImageView.setImageBitmap(user.imageBitmap);
        } else {
            viewHolder.mRemoveImage.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return mUserList == null ? 0 : mUserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mUserImageView;
        private ImageButton mRemoveImage;
        private RadioGroup mRadioGroup;
        private Switch mCommentSwitch;
        private EditText mCommentEditText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserImageView = itemView.findViewById(R.id.user_img_view);
            mRemoveImage = itemView.findViewById(R.id.remove_img_btn);
            mRadioGroup = itemView.findViewById(R.id.radioGroup);
            mCommentSwitch = itemView.findViewById(R.id.comment_switch);
            mCommentEditText = itemView.findViewById(R.id.comment_edit_text);

        }
    }

    public void setImageInItem(int position, Bitmap bitmap) {
        if (bitmap != null) {
            User user = mUserList.get(position);
            user.haveImage = true;
            user.imageBitmap = bitmap;
            notifyDataSetChanged();
        }
    }

    public interface UserAdapterListener {
        void onUserImageClick(int position);
    }
}
