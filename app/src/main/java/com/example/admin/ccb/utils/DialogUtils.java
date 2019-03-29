package com.example.admin.ccb.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.admin.ccb.R;
import com.example.admin.ccb.view.SendCodeTextView;
import com.jungly.gridpasswordview.GridPasswordView;

import www.ccb.com.common.utils.UiUtils;

public class DialogUtils {

    private static Dialog dialog;
    private static PopupWindow popupWindow;
    private static Dialog progressDialog;

    public static boolean popWindowIsShowing() {
        if (popupWindow != null) {
            return popupWindow.isShowing();
        } else {
            return false;
        }
    }


//    private static ExplosionField mExplosionField;

    public static void showHomePop(final Context context, View viewRoot, String imageUrl,
                                   View.OnClickListener ivIconClickListener,
                                   View.OnClickListener ivDeleteClickListener,
                                   boolean isCanceledOnTouchOutside) {
        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_home_pop, null);
        ImageView ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
        ImageView ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
        ivIcon.setOnClickListener(ivIconClickListener);
        GlideImageUtils.displayNoPlaceholder(context, imageUrl, ivIcon);
        ivDelete.setOnClickListener(ivDeleteClickListener);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(isCanceledOnTouchOutside);
//        popupWindow.setAnimationStyle(R.style.showPopupAnimation);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        setBackgroundAlpha(context, 0.5f);//设置屏幕透明度
        popupWindow.showAtLocation(viewRoot, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(context, 1.0f);
            }
        });
    }

    private static void setBackgroundAlpha(Context context, float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) context).getWindow().setAttributes(lp);
    }

    public static void dissmissPop() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    public static void showHomeDialog(Context context, String imageUrl, View.OnClickListener ivIconClickListener, View.OnClickListener ivDeleteClickListener, boolean isCanceledOnTouchOutside) {
        dialog = new Dialog(context, R.style.alert_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_home_pop, null);
        ImageView ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
        ImageView ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
        ivIcon.setOnClickListener(ivIconClickListener);
        GlideImageUtils.displayNoPlaceholder(context, imageUrl, ivIcon);
        ivDelete.setOnClickListener(ivDeleteClickListener);
        dialog.setContentView(view);
        dialog.setCancelable(isCanceledOnTouchOutside);
        dialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        dialog.show();
//        if (onExplosionFieldListener!=null){
//            mExplosionField = ExplosionField.attach2Window(((Activity)context));
//            addListener(((Activity)context).findViewById(R.id.rootView),onExplosionFieldListener);
//        }
    }

//    private static void addListener(View root,final ExplosionField.OnExplosionFieldListener onExplosionFieldListener) {
//        if (root instanceof ViewGroup) {
//            ViewGroup parent = (ViewGroup) root;
//            for (int i = 0; i < parent.getChildCount(); i++) {
//                addListener(parent.getChildAt(i),onExplosionFieldListener);
//            }
//        } else {
//            root.setClickable(true);
//            root.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mExplosionField.explode(v);
//                    mExplosionField.setOnExplosionFieldListener(onExplosionFieldListener);
//                    v.setOnClickListener(null);
//                }
//            });
//
//        }
//    }

    /**
     * @param context
     * @param title
     * @param content
     * @param right
     * @param rightOnClickListener
     * @param isCancelable
     */

    public static void showDialogForIosStyle(Context context,
                                             String title,
                                             String content,
                                             String right, View.OnClickListener rightOnClickListener,
                                             boolean isCancelable) {
        showDialogForIosStyle(context,
                title,
                content,
                null, null,
                right, rightOnClickListener,
                isCancelable);
    }

    /**
     * @param context
     * @param title
     * @param content
     * @param left
     * @param leftOnClickListener
     * @param right
     * @param rightOnClickListener
     * @param isCancelable
     */
    public static void showDialogForIosStyle(Context context,
                                             String title,
                                             String content,
                                             String left, View.OnClickListener leftOnClickListener,
                                             String right, View.OnClickListener rightOnClickListener,
                                             boolean isCancelable) {
        showDialogForIosStyle(context,
                title, R.color.color_030303,
                content, R.color.color_030303,
                left, leftOnClickListener, R.color.color_0076ff,
                right, rightOnClickListener, R.color.color_0076ff,
                isCancelable);
    }

    /**
     * @param context              上下问
     * @param title                弹框头
     * @param titleColorId         弹窗头字体颜色
     * @param content              弹窗内容
     * @param contentColorId       弹窗内容字体颜色
     * @param left                 左侧文字
     * @param leftOnClickListener
     * @param leftColorId
     * @param right
     * @param rightOnClickListener
     * @param rightColorId
     * @param isCancelable
     */

    public static void showDialogForIosStyle(Context context,
                                             String title, int titleColorId,
                                             String content, int contentColorId,
                                             String left, View.OnClickListener leftOnClickListener, int leftColorId,
                                             String right, View.OnClickListener rightOnClickListener, int rightColorId,
                                             boolean isCancelable) {
        dialog = new Dialog(context, R.style.alert_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_ios_style, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        TextView tvContent = (TextView) view.findViewById(R.id.tvContent);
        View hLine = view.findViewById(R.id.hLine);
        LinearLayout llBottom = (LinearLayout) view.findViewById(R.id.llBottom);
        TextView tvLeft = (TextView) view.findViewById(R.id.tvLeft);
        View vLine = view.findViewById(R.id.vLine);
        TextView tvRight = (TextView) view.findViewById(R.id.tvRight);
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            if (titleColorId != 0) {
                tvTitle.setTextColor(context.getResources().getColor(titleColorId));
            }
            tvTitle.setText(title);
        }
        if (TextUtils.isEmpty(content)) {
            tvContent.setVisibility(View.GONE);
        } else {
            tvContent.setVisibility(View.VISIBLE);
            if (contentColorId != 0) {
                tvContent.setTextColor(context.getResources().getColor(contentColorId));
            }
            tvContent.setText(Html.fromHtml(content));
        }
        if (TextUtils.isEmpty(left)) {
            tvLeft.setVisibility(View.GONE);
        } else {
            tvLeft.setVisibility(View.VISIBLE);
            if (leftColorId != 0) {
                tvLeft.setTextColor(context.getResources().getColor(leftColorId));
            }
            tvLeft.setText(left);
            if (leftOnClickListener != null) {
                tvLeft.setOnClickListener(leftOnClickListener);
            } else {
                tvLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dissmissDialog();
                    }
                });
            }
        }
        if (TextUtils.isEmpty(right)) {
            tvRight.setVisibility(View.GONE);
        } else {
            tvRight.setVisibility(View.VISIBLE);
            if (rightColorId != 0) {
                tvRight.setTextColor(context.getResources().getColor(rightColorId));
            }
            tvRight.setText(right);
            if (rightOnClickListener != null) {
                tvRight.setOnClickListener(rightOnClickListener);
            } else {
                tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dissmissDialog();
                    }
                });
            }
        }
        if (!TextUtils.isEmpty(left) && !TextUtils.isEmpty(right)) {
            tvLeft.setBackgroundResource(R.drawable.selector_pressed_blradius12);
            tvRight.setBackgroundResource(R.drawable.selector_pressed_brradius12);
            hLine.setVisibility(View.VISIBLE);
            llBottom.setVisibility(View.VISIBLE);
            vLine.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(left) && TextUtils.isEmpty(right)) {
            tvLeft.setBackgroundResource(R.drawable.selector_pressed_blbrradius12);
//            tvRight.setBackgroundResource(R.drawable.selector_pressed_brradius12);
            hLine.setVisibility(View.VISIBLE);
            llBottom.setVisibility(View.VISIBLE);
            vLine.setVisibility(View.GONE);
        } else if (TextUtils.isEmpty(left) && !TextUtils.isEmpty(right)) {
            tvRight.setBackgroundResource(R.drawable.selector_pressed_blbrradius12);
//            tvLeft.setBackgroundResource(R.drawable.selector_pressed_blradius12);
            hLine.setVisibility(View.VISIBLE);
            llBottom.setVisibility(View.VISIBLE);
            vLine.setVisibility(View.GONE);
        } else {
            hLine.setVisibility(View.GONE);
            llBottom.setVisibility(View.GONE);
            vLine.setVisibility(View.GONE);
        }
        dialog.setContentView(view);
        dialog.setCancelable(isCancelable);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void showPayDialogForIosStyle(Context context,
                                                String title,
                                                String explain,
                                                String money,
                                                GridPasswordView.OnPasswordChangedListener onPasswordChangedListener,
                                                View.OnClickListener closeClickListener,
                                                boolean isCancelable) {
        showPayDialogForIosStyle(context,
                title, R.color.color_292929,
                explain, R.color.color_292929,
                money, R.color.color_292929,
                onPasswordChangedListener, closeClickListener,
                isCancelable,
                false);
    }

    public static void showPayDialogForIosStyle(Context context,
                                                String title, int titleColorId,
                                                String explain, int explainColorId,
                                                String money, int moneyColorId,
                                                GridPasswordView.OnPasswordChangedListener onPasswordChangedListener,
                                                View.OnClickListener closeClickListener,
                                                boolean isCancelable,
                                                boolean isCanceledOnTouchOutside) {
        dialog = new Dialog(context, R.style.alert_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_pay_ios_style, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        ImageView ivClose = (ImageView) view.findViewById(R.id.ivClose);
        View line = view.findViewById(R.id.line);
        TextView tvExplain = (TextView) view.findViewById(R.id.tvExplain);
        TextView tvMoney = (TextView) view.findViewById(R.id.tvMoney);
        GridPasswordView gridPasswordView = (GridPasswordView) view.findViewById(R.id.gridPasswordView);
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            if (titleColorId != 0) {
                tvTitle.setTextColor(context.getResources().getColor(titleColorId));
            }
            tvTitle.setText(title);
        }
        if (TextUtils.isEmpty(explain)) {
            tvExplain.setVisibility(View.GONE);
        } else {
            tvExplain.setVisibility(View.VISIBLE);
            if (explainColorId != 0) {
                tvExplain.setTextColor(context.getResources().getColor(explainColorId));
            }
            tvExplain.setText(Html.fromHtml(explain));
        }
        if (TextUtils.isEmpty(money)) {
            tvMoney.setVisibility(View.GONE);
        } else {
            tvMoney.setVisibility(View.VISIBLE);
            if (moneyColorId != 0) {
                tvMoney.setTextColor(context.getResources().getColor(moneyColorId));
            }
            tvMoney.setText("¥" + money);
        }
        ivClose.setOnClickListener(closeClickListener);
        gridPasswordView.setOnPasswordChangedListener(onPasswordChangedListener);
        dialog.setContentView(view);
        dialog.setCancelable(isCancelable);
        dialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        dialog.show();
    }

    public static void dissmissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public static void showBottomDialogForIosStyle(Context context,
                                                   String titleText,
                                                   String[] itemTextArr,
                                                   View.OnClickListener[] itemOnClickListenerArr,
                                                   boolean isCancelable) {
        int[] itemTextColorArr = new int[itemTextArr.length];
        int[] itemTextSizeArr = new int[itemTextArr.length];
        for (int i = 0; i < itemTextArr.length; i++) {
            itemTextColorArr[i] = R.color.color_0076ff;
            itemTextSizeArr[i] = 15;
        }
        showBottomDialogForIosStyle(context,
                titleText, R.color.color_292929, 12, 34,
                itemTextArr, itemTextColorArr, itemTextSizeArr, itemOnClickListenerArr, 44,
                isCancelable);

    }

    public static void showBottomDialogForIosStyle(Context context,
                                                   String titleText,
                                                   int titleTextColor,
                                                   int titleTextSize,
                                                   int titleHeight,
                                                   String[] itemTextArr,
                                                   int[] itemTextColorArr,
                                                   int[] itemTextSizeArr,
                                                   View.OnClickListener[] itemOnClickListenerArr,
                                                   int itemHeight,
                                                   boolean isCancelable) {

        dialog = new Dialog(context, R.style.alert_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_ios_bottom_style, null);
        LinearLayout llContiner = (LinearLayout) view.findViewById(R.id.llContiner);
        if (!TextUtils.isEmpty(titleText)) {
            TextView titleView = new TextView(context);
            titleView.setHeight(titleHeight == 0 ? UiUtils.dp2px(34) : UiUtils.dp2px(titleHeight));
            titleView.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            titleView.setGravity(Gravity.CENTER);
            titleView.setText(titleText);
            if (titleTextColor != 0) {
                titleView.setTextColor(context.getResources().getColor(titleTextColor));
            } else {
                titleView.setTextColor(context.getResources().getColor(R.color.color_292929));
            }
            if (titleTextSize != 0) {
                titleView.setTextSize(titleTextSize);
            } else {
                titleView.setTextSize(12);
            }
            llContiner.addView(titleView);
            TextView line = new TextView(context);
            line.setBackgroundColor(context.getResources().getColor(R.color.color_e0e0e0));
            line.setHeight(1);
            line.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            llContiner.addView(line);
        }
        if (itemTextArr != null && itemTextArr.length > 0) {
            for (int i = 0; i < itemTextArr.length; i++) {
                TextView itemView = new TextView(context);
                itemView.setHeight(itemHeight == 0 ? UiUtils.dp2px(44) : UiUtils.dp2px(itemHeight));
                itemView.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                itemView.setGravity(Gravity.CENTER);
                itemView.setText(itemTextArr[i]);
                if (itemTextColorArr != null && i < itemTextColorArr.length && itemTextColorArr[i] != 0) {
                    itemView.setTextColor(context.getResources().getColor(itemTextColorArr[i]));
                } else {
                    itemView.setTextColor(context.getResources().getColor(R.color.color_0076ff));
                }
                if (itemTextSizeArr != null && i < itemTextSizeArr.length && itemTextSizeArr[i] != 0) {
                    itemView.setTextSize(itemTextSizeArr[i]);
                } else {
                    itemView.setTextSize(15);
                }

                llContiner.addView(itemView);
                if (i != itemTextArr.length - 1) {
                    TextView line = new TextView(context);
                    line.setBackgroundColor(context.getResources().getColor(R.color.color_e0e0e0));
                    line.setHeight(1);
                    line.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                    llContiner.addView(line);
                }
                if (i == 0) {
                    if (TextUtils.isEmpty(titleText)) {
                        if (itemTextArr.length == 1) {
                            itemView.setBackgroundResource(R.drawable.selector_pressed_radius12);
                        } else {
                            itemView.setBackgroundResource(R.drawable.selector_pressed_tltrradius12);
                        }
                    } else {
                        if (itemTextArr.length == 1) {
                            itemView.setBackgroundResource(R.drawable.selector_pressed_blbrradius12);
                        } else {
                            itemView.setBackgroundResource(R.drawable.selector_pressed);
                        }
                    }
                } else if (i == itemTextArr.length - 1) {
                    itemView.setBackgroundResource(R.drawable.selector_pressed_blbrradius12);
                } else {
                    itemView.setBackgroundResource(R.drawable.selector_pressed);
                }
                itemView.setClickable(true);
                if (itemOnClickListenerArr != null && i < itemOnClickListenerArr.length && itemOnClickListenerArr[i] != null) {
                    itemView.setOnClickListener(itemOnClickListenerArr[i]);
                }
            }
        }
        dialog.setContentView(view);
        dialog.setCancelable(isCancelable);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(UiUtils.dp2px(10), UiUtils.dp2px(10), UiUtils.dp2px(10), UiUtils.dp2px(10));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    public static void showSecurityCodeDialogForIosStyle(Context context,
                                                         String title,
                                                         String content,
                                                         TextWatcher securityCodeTextWatcher,
                                                         View.OnClickListener sendSecurityCodeOnClickListener,
                                                         String left, View.OnClickListener leftOnClickListener,
                                                         String right, View.OnClickListener rightOnClickListener,
                                                         boolean isCancelable) {
        showSecurityCodeDialogForIosStyle(context, title, 0,
                content, 0,
                securityCodeTextWatcher,
                sendSecurityCodeOnClickListener,
                left, leftOnClickListener, 0,
                right, rightOnClickListener, 0,
                isCancelable);
    }

    public static void showSecurityCodeDialogForIosStyle(Context context,
                                                         String title, int titleColorId,
                                                         String content, int contentColorId,
                                                         TextWatcher securityCodeTextWatcher,
                                                         View.OnClickListener sendSecurityCodeOnClickListener,
                                                         String left, View.OnClickListener leftOnClickListener, int leftColorId,
                                                         String right, View.OnClickListener rightOnClickListener, int rightColorId,
                                                         boolean isCancelable) {
        dialog = new Dialog(context, R.style.alert_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_ios_securitycode_style, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        TextView tvContent = (TextView) view.findViewById(R.id.tvContent);
        EditText etSecurityCode = (EditText) view.findViewById(R.id.etSecurityCode);
        SendCodeTextView tvSendSecurityCode = (SendCodeTextView) view.findViewById(R.id.tvSendSecurityCode);
        View hLine = view.findViewById(R.id.hLine);
        LinearLayout llBottom = (LinearLayout) view.findViewById(R.id.llBottom);
        TextView tvLeft = (TextView) view.findViewById(R.id.tvLeft);
        View vLine = view.findViewById(R.id.vLine);
        TextView tvRight = (TextView) view.findViewById(R.id.tvRight);
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            if (titleColorId != 0) {
                tvTitle.setTextColor(context.getResources().getColor(titleColorId));
            }
            tvTitle.setText(title);
        }
        if (TextUtils.isEmpty(content)) {
            tvContent.setVisibility(View.GONE);
        } else {
            tvContent.setVisibility(View.VISIBLE);
            if (contentColorId != 0) {
                tvContent.setTextColor(context.getResources().getColor(contentColorId));
            }
            tvContent.setText(Html.fromHtml(content));
        }
        if (sendSecurityCodeOnClickListener != null) {
            tvSendSecurityCode.setOnClickListener(sendSecurityCodeOnClickListener);
        }
        if (securityCodeTextWatcher != null) {
            etSecurityCode.addTextChangedListener(securityCodeTextWatcher);
        }
        if (TextUtils.isEmpty(left)) {
            tvLeft.setVisibility(View.GONE);
        } else {
            tvLeft.setVisibility(View.VISIBLE);
            if (leftColorId != 0) {
                tvLeft.setTextColor(context.getResources().getColor(leftColorId));
            }
            tvLeft.setText(left);
            if (leftOnClickListener != null) {
                tvLeft.setOnClickListener(leftOnClickListener);
            }
        }
        if (TextUtils.isEmpty(right)) {
            tvRight.setVisibility(View.GONE);
        } else {
            tvRight.setVisibility(View.VISIBLE);
            if (rightColorId != 0) {
                tvRight.setTextColor(context.getResources().getColor(rightColorId));
            }
            tvRight.setText(right);
            if (rightOnClickListener != null) {
                tvRight.setOnClickListener(rightOnClickListener);
            }
        }
        if (!TextUtils.isEmpty(left) && !TextUtils.isEmpty(right)) {
            tvLeft.setBackgroundResource(R.drawable.selector_pressed_blradius12);
            tvRight.setBackgroundResource(R.drawable.selector_pressed_brradius12);
            hLine.setVisibility(View.VISIBLE);
            llBottom.setVisibility(View.VISIBLE);
            vLine.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(left) && TextUtils.isEmpty(right)) {
            tvLeft.setBackgroundResource(R.drawable.selector_pressed_blbrradius12);
//            tvRight.setBackgroundResource(R.drawable.selector_pressed_brradius12);
            hLine.setVisibility(View.VISIBLE);
            llBottom.setVisibility(View.VISIBLE);
            vLine.setVisibility(View.GONE);
        } else if (TextUtils.isEmpty(left) && !TextUtils.isEmpty(right)) {
            tvRight.setBackgroundResource(R.drawable.selector_pressed_blbrradius12);
//            tvLeft.setBackgroundResource(R.drawable.selector_pressed_blradius12);
            hLine.setVisibility(View.VISIBLE);
            llBottom.setVisibility(View.VISIBLE);
            vLine.setVisibility(View.GONE);
        } else {
            hLine.setVisibility(View.GONE);
            llBottom.setVisibility(View.GONE);
            vLine.setVisibility(View.GONE);
        }
        dialog.setContentView(view);
        dialog.setCancelable(isCancelable);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void showProgressDialogForIosStyle(Context context,
                                                     String content,
                                                     boolean isCancelable) {

        progressDialog = new Dialog(context, R.style.alert_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_ios_progress_style, null);
        TextView tvProgressContent = (TextView) view.findViewById(R.id.tvProgressContent);
        if (!TextUtils.isEmpty(content)) {
            tvProgressContent.setText(content);
        }
        progressDialog.setContentView(view);
        progressDialog.setCancelable(isCancelable);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public static void dissmissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}
