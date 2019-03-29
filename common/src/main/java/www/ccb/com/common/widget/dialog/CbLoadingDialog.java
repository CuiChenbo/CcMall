package www.ccb.com.common.widget.dialog;

/**
 * Created by ChenboCui on 2018/6/4.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import www.ccb.com.common.R;

/**
 * 加载提醒对话框
 */
public class CbLoadingDialog extends ProgressDialog
{

    private TextView tvMessage;
    private CharSequence message;
    private int progressColor = -1;

    public CbLoadingDialog(Context context)
    {
        super(context,R.style.CbLoadingDialog);
    }

    public CbLoadingDialog(Context context, int theme)
    {
        super(context, R.style.CbLoadingDialog);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        init(getContext());
    }
    private void init(Context context)
    {
        setCancelable(true);
        setCanceledOnTouchOutside(false);//设置不可取消，点击其他区域不能取消

        setContentView(R.layout.c_progress_dialog);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        tvMessage = (TextView) findViewById(R.id.progress_dialog_message);
        tvMessage.setText(message);
        if (TextUtils.isEmpty(message)){tvMessage.setVisibility(View.GONE);}
    }

    public void setMessage(CharSequence message) {
        this.message = message;
        if (tvMessage != null)        tvMessage.setText(message);

    }

    @Override
    public void show()
    {
        super.show();
    }

    public void Cancelable(boolean b){
        setCancelable(b);
    }
}
