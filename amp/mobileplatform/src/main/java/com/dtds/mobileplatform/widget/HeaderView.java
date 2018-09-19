package com.dtds.mobileplatform.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dtds.mobileplatform.R;
import com.dtds.mobileplatform.util.UiHelper;


/**
 * 头布局控件
 * Created by lixiaohui on 2017/4/28.
 */
public class HeaderView extends RelativeLayout {

    private static final String TAG = "HeaderView";

    private TextView mTitleTv;//中间标题文本
    private View mLeftBtn;//左边第一个按钮
    private TextView mLeftBtnTv;//左边第一个按钮的文本
    private ImageView mLeftBtnImv;//左边第一个按钮的图片

    private View mRightBtn;//右边第一个按钮的根布局
    private TextView mRightBtnTv;//右边第一个按钮的文本
    private ImageView mRightBtnImv;//右边第一个按钮的图片

    private View mRight2Btn;//右边第二个按钮的根布局
    private TextView mRight2BtnTv;//右边第二个按钮的文本
    private ImageView mRight2BtnImv;//右边第二个按钮的图片


    public HeaderView(Context context) {
        this(context, null);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewContent();
        readAttributeAndSet(attrs);
    }

    /**
     * 初始化子视图
     */
    private void initViewContent() {
        View viewContent = View.inflate(getContext(), R.layout.amp_header_view, this);
        //中间标题文本
        mTitleTv = (TextView) viewContent.findViewById(R.id.tv_title);
        //左边第一个按钮
        mLeftBtn = viewContent.findViewById(R.id.layout_left);
        mLeftBtnTv = (TextView) viewContent.findViewById(R.id.tv_left);
        mLeftBtnImv = (ImageView) viewContent.findViewById(R.id.imv_left);
        //右边第一个按钮
        mRightBtn = viewContent.findViewById(R.id.layout_right);
        mRightBtnTv = (TextView) viewContent.findViewById(R.id.tv_right);
        mRightBtnImv = (ImageView) viewContent.findViewById(R.id.imv_right);
        //右边第二个按钮
        mRight2Btn = viewContent.findViewById(R.id.layout_right2);
        mRight2BtnTv = (TextView) viewContent.findViewById(R.id.tv_right2);
        mRight2BtnImv = (ImageView) viewContent.findViewById(R.id.imv_right2);
        defaultSet();
    }

    /**
     * 本控件默认设置
     */
    private void defaultSet() {
        //设置左边退出按钮
        setLeftBtnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activityFromView = UiHelper.getActivityFromView(v);
                if (activityFromView != null) {
                    activityFromView.finish();
                }
            }
        });
        //设置标题超出内容后跑马灯效果
        UiHelper.setTextViewMarquee(mTitleTv);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {//如果高度是warp_content模式就指定高度为48dp
            //设置默认布局参数
            int headerHeight = getContext().getResources().getDimensionPixelOffset(R.dimen.amp_header_default_height);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(headerHeight, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     * 读取属性并设置
     */
    private void readAttributeAndSet(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HeaderViewStyle);
        //设置文本
        CharSequence leftText = typedArray.getText(R.styleable.HeaderViewStyle_hvLeftTxt);
        if (leftText != null) {//左边按钮的文本
            setLeftBtnTxt(leftText);
        }
        CharSequence titleText = typedArray.getText(R.styleable.HeaderViewStyle_hvTitleTxt);
        if (titleText != null) {//中间标题的文本
            setTitleTxt(titleText);
        }
        CharSequence rightText = typedArray.getText(R.styleable.HeaderViewStyle_hvRightTxt);
        if (rightText != null) {//右边第一个按钮的文本
            setRightBtnTxt(rightText);
        }
        CharSequence right2Text = typedArray.getText(R.styleable.HeaderViewStyle_hvRight2Txt);
        if (right2Text != null) {
            setRight2BtnTxt(right2Text);
        }
        //设置图片
        int leftImgId = typedArray.getResourceId(R.styleable.HeaderViewStyle_hvLeftImg, 0);
        if (leftImgId != 0) {//有配置图片资源才设置上去
            setLeftBtnImg(leftImgId);
        }
        //是否隐藏左边按钮
        boolean hiddenLeft = typedArray.getBoolean(R.styleable.HeaderViewStyle_hvHiddenLeft, false);
        if (hiddenLeft) {
            hiddenLeftBtn();
        }
        int rightImgId = typedArray.getResourceId(R.styleable.HeaderViewStyle_hvRightImg, 0);
        setRightBtnImg(rightImgId);
        int right2ImgId = typedArray.getResourceId(R.styleable.HeaderViewStyle_hvRight2Img, 0);
        setRight2BtnImg(right2ImgId);

        boolean enableLine = typedArray.getBoolean(R.styleable.HeaderViewStyle_hvBottomLineEnable, true);
        if (enableLine) { //底部添加分割线
            //是否设置了分割线的颜色
            int dividerLineColorResId = typedArray.getColor(R.styleable.HeaderViewStyle_hvBottomLineColor, Color.parseColor("#e4e4e4"));
            View divideView = new View(getContext());
            divideView.setBackgroundColor(dividerLineColorResId);
            LayoutParams divideLp = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
            divideLp.addRule(ALIGN_PARENT_BOTTOM);
            addView(divideView, divideLp);
        }
        //头根布局背景
        Drawable bgDrawable = typedArray.getDrawable(R.styleable.HeaderViewStyle_hvBackground);
        if (bgDrawable != null) {
            if (Build.VERSION.SDK_INT > 16) {
                setBackground(bgDrawable);
            } else {
                setBackgroundDrawable(bgDrawable);
            }
        }
        //初始化文本颜色
        initTextColor(typedArray);
        //初始化文本字号
        initTextSize(typedArray);

        typedArray.recycle();
    }

    private void initTextSize(TypedArray typedArray) {
        float allTxtSize = typedArray.getDimension(R.styleable.HeaderViewStyle_hvTxtSize, 0);
        if (allTxtSize != 0) {
            mLeftBtnTv.setTextSize(allTxtSize);//左
            mTitleTv.setTextSize(allTxtSize);//中
            mRightBtnTv.setTextSize(allTxtSize);//右
            mRight2BtnTv.setTextSize(allTxtSize);//右2
        }
    }

    private void initTextColor(TypedArray typedArray) {
        //头布局所有文本的颜色
        ColorStateList allTxtCsl = typedArray.getColorStateList(R.styleable.HeaderViewStyle_hvTxtColor);
        if (allTxtCsl != null) {
            mLeftBtnTv.setTextColor(allTxtCsl);//左
            mTitleTv.setTextColor(allTxtCsl);//中
            mRightBtnTv.setTextColor(allTxtCsl);//右
            mRight2BtnTv.setTextColor(allTxtCsl);//右2
        }
        //左边文本的颜色
        ColorStateList leftTxtCsl = typedArray.getColorStateList(R.styleable.HeaderViewStyle_hvLeftTxtColor);
        if (leftTxtCsl != null) {
            mLeftBtnTv.setTextColor(leftTxtCsl);//左
        }
        //中间文本的颜色
        ColorStateList titleTxtCsl = typedArray.getColorStateList(R.styleable.HeaderViewStyle_hvTitleTxtColor);
        if (titleTxtCsl != null) {
            mTitleTv.setTextColor(titleTxtCsl);//中
        }
        //右边文本的颜色
        ColorStateList rightTxtCsl = typedArray.getColorStateList(R.styleable.HeaderViewStyle_hvRightTxtColor);
        if (rightTxtCsl != null) {
            mRightBtnTv.setTextColor(rightTxtCsl);//右
        }
        //右边2文本的颜色
        ColorStateList right2TxtCsl = typedArray.getColorStateList(R.styleable.HeaderViewStyle_hvRight2TxtColor);
        if (right2TxtCsl != null) {
            mRight2BtnTv.setTextColor(right2TxtCsl);//右2
        }
    }

    /**
     * 设置标题文本
     *
     * @param resTxtId
     */
    public void setTitleTxt(int resTxtId) {
        setTitleTxt(getResources().getText(resTxtId));
    }

    /**
     * 设置标题文本
     *
     * @param txtStr
     */
    public void setTitleTxt(CharSequence txtStr) {
        mTitleTv.setText(txtStr);
    }

    /**
     * 设置标题颜色
     *
     * @param color
     */
    public void setTitleTxtColor(int color) {
        mTitleTv.setTextColor(color);
    }

    /**
     * 设置标题文本字号大小
     *
     * @param size
     */
    public void setTitleTxtSize(float size) {
        mTitleTv.setTextSize(size);
    }

    /**
     * 设置左边第一个按钮的文本
     *
     * @param stringId
     */
    public void setLeftBtnTxt(int stringId) {
        setLeftBtnTxt(getResources().getText(stringId));
    }

    /**
     * 设置左边第一个按钮的文本
     *
     * @param txtStr
     */
    public void setLeftBtnTxt(CharSequence txtStr) {
        mLeftBtnTv.setText(txtStr);
    }

    /**
     * 设置左边文字颜色
     *
     * @param color
     */
    public void setLeftBtnTxtColor(int color) {
        mLeftBtnTv.setTextColor(color);
    }

    /**
     * 设置左边文本字号大小
     *
     * @param size
     */
    public void setLeftBtnTxtSize(float size) {
        mLeftBtnTv.setTextSize(size);
    }

    /**
     * 设置左边第一个按钮的图片
     *
     * @param imgResId
     */
    public void setLeftBtnImg(int imgResId) {
        if (imgResId > 0) {//合法图片id才设置
            mLeftBtnImv.setImageResource(imgResId);
        } else {//不合法就移除老图片
            mLeftBtnImv.setImageDrawable(null);//the Drawable to set, or null to clear the content
        }
    }


    /**
     * 设置左边第一个按钮的点击事件
     *
     * @param l
     */
    public void setLeftBtnClickListener(OnClickListener l) {
        mLeftBtn.setOnClickListener(l);
    }

    /**
     * 隐藏左边第一个按钮
     */
    public void hiddenLeftBtn() {
        mLeftBtn.setVisibility(View.GONE);
    }

    //--------------------【右边第一个按钮】mRightBtn start--------------------

    /**
     * 右边第一个按钮的图片
     *
     * @param imgResId
     */
    public void setRightBtnImg(int imgResId) {
        if (imgResId > 0) {//合法图片id才设置
            mRightBtnImv.setImageResource(imgResId);
            showRightBtn();
        } else {//不合法就移除老图片
            mRightBtnImv.setImageDrawable(null);//the Drawable to set, or null to clear the content
        }
    }

    /**
     * 右边第一个按钮的文本
     *
     * @param stringId
     */
    public void setRightBtnTxt(int stringId) {
        setRightBtnTxt(getResources().getText(stringId));
    }

    /**
     * 右边第一个按钮的文本
     *
     * @param txtStr
     */
    public void setRightBtnTxt(CharSequence txtStr) {
        mRightBtnTv.setText(txtStr);
        showRightBtn();
    }

    /**
     * 设置右边按钮的颜色
     *
     * @param color
     */
    public void setRightBtnTxtColor(@ColorInt int color) {
        mRightBtnTv.setTextColor(color);
    }

    /**
     * 设置右边文本字号大小
     *
     * @param size
     */
    public void setRightBtnTxtSize(float size) {
        mRightBtnTv.setTextSize(size);
    }

    /**
     * 显示右边第一个按钮
     */
    public void showRightBtn() {
        mRightBtn.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏右边第一个按钮
     */
    public void hiddenRightBtn() {
        mRightBtn.setVisibility(View.GONE);
    }

    /**
     * 设置右边第一个按钮的点击事件
     *
     * @param l
     */
    public void setRightBtnClickListener(OnClickListener l) {
        mRightBtn.setOnClickListener(l);
    }

    /**
     * 设置右边第一个按钮可用性
     *
     * @param enable
     */
    public void setRightBtnEnable(boolean enable) {
        mRightBtn.setEnabled(enable);
        mRightBtnTv.setEnabled(enable);
        mRightBtnImv.setEnabled(enable);
    }
    //--------------------【右边第一个按钮】mRightBtn end--------------------


    //--------------------【右边第二个按钮】mRight2Btn start--------------------

    /**
     * 右边第二个按钮的图片
     *
     * @param imgResId
     */
    public void setRight2BtnImg(int imgResId) {
        if (imgResId > 0) {//合法图片id才设置
            mRight2BtnImv.setImageResource(imgResId);
            showRight2Btn();
        } else {//不合法就移除老图片
            mRight2BtnImv.setImageDrawable(null);//the Drawable to set, or null to clear the content
        }
    }

    /**
     * 右边第二个按钮的文本
     *
     * @param stringId
     */
    public void setRight2BtnTxt(int stringId) {
        setRight2BtnTxt(getResources().getText(stringId));
    }

    /**
     * 右边第二个按钮的文本
     *
     * @param txtStr
     */
    public void setRight2BtnTxt(CharSequence txtStr) {
        mRight2BtnTv.setText(txtStr);
        showRight2Btn();
    }

    /**
     * 设置右边2按钮的颜色
     *
     * @param color
     */
    public void setRight2BtnTxtColor(@ColorInt int color) {
        mRight2BtnTv.setTextColor(color);
    }

    /**
     * 设置右边2文本字号大小
     *
     * @param size
     */
    public void setRight2BtnTxtSize(float size) {
        mRight2BtnTv.setTextSize(size);
    }

    /**
     * 显示右边第二个按钮
     */
    public void showRight2Btn() {
        mRight2Btn.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏右边第二个按钮
     */
    public void hiddenRight2Btn() {
        mRight2Btn.setVisibility(View.GONE);
    }

    /**
     * 设置右边第二个按钮的点击事件
     *
     * @param l
     */
    public void setRight2BtnClickListener(OnClickListener l) {
        mRight2Btn.setOnClickListener(l);
    }

    /**
     * 设置右边第二个按钮可用性
     *
     * @param enable
     */
    public void setRight2BtnEnable(boolean enable) {
        mRight2Btn.setEnabled(enable);
    }
    //--------------------【右边第二个按钮】mRight2Btn end--------------------

}
