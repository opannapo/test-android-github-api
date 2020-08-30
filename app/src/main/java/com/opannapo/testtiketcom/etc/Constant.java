package com.opannapo.testtiketcom.etc;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;

import static com.opannapo.testtiketcom.etc.Constant.IntentExtraKey.OWNER_DETIAL;
import static com.opannapo.testtiketcom.etc.Constant.IntentExtraKey.USER_DETIAL;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by napouser on 30,August,2020
 */
public class Constant {
    @Retention(SOURCE)
    @StringDef({
            USER_DETIAL,
            OWNER_DETIAL
    })
    public @interface IntentExtraKey {
        String USER_DETIAL = "USER_DETIAL";
        String OWNER_DETIAL = "OWNER_DETIAL";
    }
}
