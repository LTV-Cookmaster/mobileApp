package com.sorcierstechnologiques.cookmaster.ui.fidelity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FidelityViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FidelityViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is fidelity fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}