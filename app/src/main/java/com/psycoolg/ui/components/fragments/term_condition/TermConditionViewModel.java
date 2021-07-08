package com.psycoolg.ui.components.fragments.term_condition;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TermConditionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TermConditionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}