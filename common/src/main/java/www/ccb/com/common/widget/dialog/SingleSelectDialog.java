package www.ccb.com.common.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import www.ccb.com.common.R;
import www.ccb.com.common.base.DefaultBaseAdapter;
import www.ccb.com.common.utils.ViewUtils;

/**
 * 单选弹窗
 *
 */
public class SingleSelectDialog extends AppCompatDialog {

    public SingleSelectDialog(Context context) {
        super(context, R.style.Dialog_Bottom_Translucent);
        setContentView(R.layout.dialog_single_select);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        WindowManager.LayoutParams attr = window.getAttributes();
        attr.width = (ViewUtils.getScreenWidth(getContext()));
        attr.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attr.gravity = Gravity.BOTTOM;
        window.setAttributes(attr);
    }

    public static class Builder {
        SingleSelectDialog dialog;
        private DefaultBaseAdapter ap;

        public Builder(Context context) {
            dialog = new SingleSelectDialog(context);
            dialog.findViewById(R.id.tv_btn_cancel).setOnClickListener(v -> dialog.dismiss());
        }

        public Builder setItems(@ArrayRes int itemsId, final OnClickListener listener) {
            addItems(dialog.getContext().getResources().getTextArray(itemsId), listener);
            return this;
        }

        public Builder setItems(CharSequence[] items, OnClickListener listener) {
            addItems(items, listener);
            return this;
        }
        public Builder setItems(List<CharSequence> items, OnClickListener listener) {
            addItems(items, listener);
            return this;
        }

        private void addItems(CharSequence[] items, OnClickListener listener) {
            ListView listView = dialog.findViewById(R.id.lv_single_select);
            assert listView != null;
            ap = new DefaultBaseAdapter(dialog.getContext(), Arrays.asList(items)) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    convertView = View.inflate(mContext,R.layout.item_dialog_single_select,null);
                    TextView tv = convertView.findViewById(R.id.tv_single_select);
                    tv.setText(items[position]);
                    if (textSize != 0){tv.setTextSize(textSize);}
                    if (textColor != 0){tv.setTextColor(dialog.getContext().getResources().getColor(textColor));}
                    return convertView;
                }

            };
            listView.setAdapter(ap);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                dialog.dismiss();
                if (null != listener) {
                    listener.onClick(dialog, position);
                }
            });
        }
        private void addItems(List<CharSequence> items, OnClickListener listener) {
            ListView listView = dialog.findViewById(R.id.lv_single_select);
            assert listView != null;
            ap = new DefaultBaseAdapter(dialog.getContext(), items) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    convertView = View.inflate(mContext,R.layout.item_dialog_single_select,null);
                    TextView tv = convertView.findViewById(R.id.tv_single_select);
                    tv.setText(items.get(position));
                    if (textSize != 0){tv.setTextSize(textSize);}
                    if (textColor != 0){tv.setTextColor(dialog.getContext().getResources().getColor(textColor));}
                    return convertView;
                }

            };
            listView.setAdapter(ap);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                dialog.dismiss();
                if (null != listener) {
                    listener.onClick(dialog, position);
                }
            });
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            dialog.setOnCancelListener(onCancelListener);
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            dialog.setOnDismissListener(onDismissListener);
            return this;
        }

        private float textSize = 0;
        /**
         * 需要在setItems前设置才有效果
         * @param dp
         * @return
         */
        public Builder setTestSize(float dp) {
            if (null != dialog && dp != 0) {
                textSize = dp;
                return this;
            }
            return null;
        }
        private int textColor = 0;
        /**
         * 需要在setItems前设置才有效果
         * @param color
         * @return
         */
        public Builder setTextColor(@ColorRes int color) {
            if (null != dialog && color != 0) {
                textColor = color;
                return this;
            }
            return null;
        }

        public SingleSelectDialog setHead(CharSequence title) {
            if (null != dialog && !TextUtils.isEmpty(title)) {
                AppCompatTextView textView = dialog.findViewById(R.id.tvTitle);
                dialog.findViewById(R.id.vLine).setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                textView.setText(title);
                return dialog;
            }
            return null;
        }

        public SingleSelectDialog show() {
            if (null != dialog) {
                dialog.show();
                return dialog;
            }
            return null;
        }
    }
}
