package com.dtds.common.debug;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.dtds.common.R;
import com.dtds.common.abs.BizCommonBridge;
import com.dtds.common.abs.BizCommonInterface;
import com.dtds.common.util.PreferencesUtil;
import com.dtds.mobileplatform.util.OnNoDoubleClickListener;


/*****************************************
 * @description:【APP调试用】环境环境辅助类
 * @author:lixiaohui
 * @date: 2017/11/16
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class EnvironmentHelper {
    private static final String TAG = "EnvironmentHelper";

    private static final String KEY_ENV_INDEX = "env_index";

    public static int getEnvIndex() {
        return PreferencesUtil.getInt(KEY_ENV_INDEX, -1);
    }

    /*****************************************
     * @description:
     * @author:lixiaohui
     * @date: 2017/11/17
     * @company:深圳动态网络科技有限公司
     *****************************************/
    public static class PickerButton extends AppCompatButton {

        private OnPickListener mOnPickListener;
        private String[] titleStrs;

        public PickerButton(Context context) {
            this(context, null);
        }

        public PickerButton(Context context, AttributeSet attrs) {
            this(context, attrs, R.attr.buttonStyle);
        }

        public PickerButton(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context);
        }

        private void init(Context ctx) {
            titleStrs = ctx.getResources().getStringArray(R.array.urlsStr);
            if (TextUtils.isEmpty(getText())) {
                int env_index = PreferencesUtil.getInt(KEY_ENV_INDEX, -1);
                if (env_index == -1) {
                    setText("环境选择(默认生产环境)");
                } else {
                    setText(titleStrs[env_index]);
                }
            }

            setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    showPickDialog(v.getContext());
                }
            });
            //调试模式才打开
            final BizCommonInterface nativeImplement = BizCommonBridge.findNativeImplement();
            if (nativeImplement.isShowLog()) {
                nativeImplement.configureEnvironment(getContext());
                setVisibility(View.VISIBLE);
                setOnPickListener(new EnvironmentHelper.PickerButton.OnPickListener() {
                    @Override
                    public void onPickChange(EnvironmentHelper.PickerButton pickerButton, int index) {
                        nativeImplement.configureEnvironment(getContext());
                    }
                });
            } else {
                setVisibility(View.GONE);
            }
        }


        /**
         * 显示挑选环境的对话框
         *
         * @param ctx
         */
        public void showPickDialog(Context ctx) {
            int env_index = PreferencesUtil.getInt(KEY_ENV_INDEX, 0);
            new AlertDialog.Builder(ctx).setTitle("环境选择")
                    .setSingleChoiceItems(titleStrs, env_index, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            PreferencesUtil.put(KEY_ENV_INDEX, which);
                            String titleStr = titleStrs[which];
                            setText(titleStr);

                            if (mOnPickListener != null) {
                                mOnPickListener.onPickChange(PickerButton.this, which);
                            }
                        }
                    })
                    .show();
        }

        public OnPickListener getOnPickListener() {
            return mOnPickListener;
        }

        public void setOnPickListener(OnPickListener onPickListener) {
            mOnPickListener = onPickListener;
        }

        public interface OnPickListener {
            void onPickChange(PickerButton pickerButton, int index);
        }

    }

}
