package com.app.imagesearch.ui.comments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.app.imagesearch.R;
import com.app.imagesearch.constants.AppConstants;
import com.app.imagesearch.data.remote.model.ImageSearchResponse;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


public class CommentsActivity extends AppCompatActivity {


    private CommentsViewModel viewModel;
    ImageSearchResponse.Data data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        getExtras();
        initViewModel();
        initObservers();
    }

    private void getExtras() {
        data = (ImageSearchResponse.Data)getIntent().getSerializableExtra(AppConstants.DATA);

    }

    private void initObservers() {
        viewModel.getCommentList().observe(this, strings -> {

        });
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CommentsViewModel.class);
        viewModel.getComments(1);
    }
}