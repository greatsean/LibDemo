package com.dtds.mobileplatform.widget.ev;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dtds.mobileplatform.R;

/*****************************************
 * @description:无数据、无网络、其他异常显示的空视图
 * @author:lixiaohui
 * @date: 2018/8/10
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class SimpleEmptyView extends RelativeLayout implements IEmptyView, View.OnClickListener {

    protected ImageView mEvDescImv;
    protected TextView mEvDescMainTxt;
    protected TextView mEvDescSubTxt;
    protected Button mEvLoadBtn;
    private View mReplaceView;

    private String mDescNoDataTxt;
    private String mDescLoadErrorTxt;
    private Drawable mDescNoDataImg;
    private Drawable mDescLoadErrorImg;

    private EmptyActionListener mEmptyActionListener;

    public SimpleEmptyView(Context context) {
        this(context, null);
    }

    public SimpleEmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        View.inflate(context, getLayoutId(), this);
        mEvDescImv = (ImageView) findViewById(R.id.ev_desc_imv);
        mEvDescMainTxt = (TextView) findViewById(R.id.ev_desc_main_txt);
        mEvDescSubTxt = (TextView) findViewById(R.id.ev_desc_sub_txt);
        mEvLoadBtn = (Button) findViewById(R.id.ev_load_btn);
        mEvLoadBtn.setOnClickListener(this);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleEmptyViewStyle);
            mDescNoDataTxt = typedArray.getString(R.styleable.SimpleEmptyViewStyle_sevDescNoDataTxt);
            mDescLoadErrorTxt = typedArray.getString(R.styleable.SimpleEmptyViewStyle_sevDescLoadErrorTxt);
            mDescNoDataImg = typedArray.getDrawable(R.styleable.SimpleEmptyViewStyle_sevDescNoDataImg);
            mDescLoadErrorImg = typedArray.getDrawable(R.styleable.SimpleEmptyViewStyle_sevDescLoadErrorImg);
            typedArray.recycle();
        }
        hiddenEmptyView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.ev_simple_empty_view;
    }

    @Override
    public void setEmptyReason(int emptyReasonCode) {
        String reasonStr = null;
        if (emptyReasonCode == EMPTY_REASON_CODE_NO_DATA) {
            if (TextUtils.isEmpty(mDescNoDataTxt)) {
                reasonStr = "无数据.";
            } else {
                reasonStr = mDescNoDataTxt;
            }
        } else if (emptyReasonCode == EMPTY_REASON_CODE_LOAD_ERROR) {
            if (TextUtils.isEmpty(mDescLoadErrorTxt)) {
                reasonStr = "加载失败，请稍后再试.";
            } else {
                reasonStr = mDescLoadErrorTxt;
            }
        } else {
            if (TextUtils.isEmpty(mDescLoadErrorTxt)) {
                reasonStr = "系统繁忙，请稍后再试.";
            } else {
                reasonStr = mDescLoadErrorTxt;
            }
//            reasonStr = "啊哦，网络不太顺畅哦~";
        }
        setEmptyReason(emptyReasonCode, reasonStr);
    }

    @Override
    public void setEmptyReason(int emptyReasonCode, String reasonMainStr) {
        setEmptyReason(emptyReasonCode, reasonMainStr, null);
    }

    @Override
    public void setEmptyReason(int emptyReasonCode, String reasonMainStr, String reasonSubStr) {
        mEvDescMainTxt.setText(reasonMainStr);
        mEvDescSubTxt.setText(reasonSubStr);
        if (emptyReasonCode == EMPTY_REASON_CODE_NO_DATA) {
            mEvDescImv.setImageDrawable(mDescNoDataImg);
            mEvLoadBtn.setVisibility(INVISIBLE);
        } else if (emptyReasonCode == EMPTY_REASON_CODE_LOAD_ERROR) {
            mEvDescImv.setImageDrawable(mDescLoadErrorImg);
            mEvLoadBtn.setVisibility(VISIBLE);
        } else {
            mEvDescImv.setImageDrawable(mDescLoadErrorImg);
            mEvLoadBtn.setVisibility(VISIBLE);
        }
    }


    @Override
    public void showEmptyView() {
        setVisibility(VISIBLE);
        if (mReplaceView != null) {
            mReplaceView.setVisibility(GONE);
        }
    }

    @Override
    public void hiddenEmptyView() {
        setVisibility(GONE);
        if (mReplaceView != null) {
            mReplaceView.setVisibility(VISIBLE);
        }
    }

    @Override
    public void setEmptyActionListener(EmptyActionListener listener) {
        this.mEmptyActionListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ev_load_btn) {
            if (mEmptyActionListener != null) {
                mEmptyActionListener.onEmptyViewRetry(getTag());
            }
        }
    }

    /**
     * 设置替换view
     *
     * @param replaceView
     */
    public void setReplaceView(View replaceView) {
        this.mReplaceView = replaceView;
    }

}
