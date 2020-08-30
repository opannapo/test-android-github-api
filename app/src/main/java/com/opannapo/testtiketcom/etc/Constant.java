package com.opannapo.testtiketcom.etc;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

import static com.opannapo.testtiketcom.etc.Constant.ErrorType.EMPTY_RESULT;
import static com.opannapo.testtiketcom.etc.Constant.ErrorType.ERROR_LOAD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by napouser on 30,August,2020
 */
public class Constant {
    @Retention(SOURCE)
    @IntDef({
            EMPTY_RESULT,
            ERROR_LOAD
    })
    public @interface ErrorType {
        int EMPTY_RESULT = 1;
        int ERROR_LOAD = 2;
    }
}
