package com.flyzend.baseproject.other;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by 王灿 on 2017/11/10.
 * TextWatcher 一般我们只关注onTextChanged方法
 */

public abstract class OnTextChangedListener implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
