package com.app.imagesearch.ui.comments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;

import com.app.imagesearch.R;
import com.app.imagesearch.constants.AppConstants;
import com.app.imagesearch.data.local.db.entity.CommentsEntity;
import com.app.imagesearch.data.remote.model.ImageSearchResponse;
import com.bumptech.glide.RequestManager;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CommentsActivity extends AppCompatActivity {


    private CommentsViewModel viewModel;
    ImageSearchResponse.Data data;
    AppCompatImageView imageView, imgBack;
    AppCompatTextView addComment, tvTitle;
    AppCompatEditText etComment;
    RecyclerView rvComments;
    @Inject
    RequestManager glideInstance;
    @Inject
    CommentsRecyclerAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        getExtras();
        initViews();
        initViewModel();
        initObservers();
    }

    private void initViews() {
        imageView = findViewById(R.id.img);
        imgBack = findViewById(R.id.imgBack);
        tvTitle = findViewById(R.id.tvTitle);
        addComment = findViewById(R.id.tvAdd);
        etComment = findViewById(R.id.etComment);
        rvComments = findViewById(R.id.rvComments);

        glideInstance.load(data.getImages().get(0).getLink()).into(imageView);
        rvComments.setAdapter(adapter);
        if(data.getTitle() != null){
            tvTitle.setText(data.getTitle());
        }else{
            tvTitle.setText("Uff it's null");
        }

        imgBack.setOnClickListener(v->{
            finish();
        });

        addComment.setOnClickListener(v->{
            if(!TextUtils.isEmpty(etComment.getText())){
                CommentsEntity commentsEntity = new CommentsEntity(data.getId(),etComment.getText().toString());
                viewModel.addComment(commentsEntity);
                adapter.addMoreData(commentsEntity.getComment());
                etComment.setText("");
            }
        });


    }

    private void getExtras() {
        data = (ImageSearchResponse.Data)getIntent().getSerializableExtra(AppConstants.DATA);
    }

    private void initObservers() {
        viewModel.getCommentList().observe(this, strings -> {
            adapter.submitList(strings);
        });
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CommentsViewModel.class);
        viewModel.getComments(data.getId());
    }
}