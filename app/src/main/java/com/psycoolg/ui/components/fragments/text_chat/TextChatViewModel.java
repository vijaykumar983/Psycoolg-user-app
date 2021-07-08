package com.psycoolg.ui.components.fragments.text_chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TextChatViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TextChatViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}