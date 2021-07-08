package com.psycoolg.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

import com.psycoolg.BuildConfig;
import com.psycoolg.R;
import com.psycoolg.pojo.CategoryData;


public class Utility {

    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(ContextCompat.getDrawable(view.getContext(), R.drawable.placeholder))
                .into(view);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void audioCall(Activity activity,String number)
    {
        activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number)));
    }

    public static void setGradientColor(Activity mActivity, AppCompatButton btnButton) {
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.RIGHT_LEFT,
                new int[]{ContextCompat.getColor(mActivity, R.color.color_004EB4),
                        ContextCompat.getColor(mActivity, R.color.color_01377D)
                });

        gradientDrawable.setCornerRadius(15f);
        btnButton.setBackground(gradientDrawable);
    }
    public static void showSnackBarMsgError(Activity activity, String message) {
        View parentLayout = activity.findViewById(android.R.id.content);
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        finish();
                    }
                })
                .setActionTextColor(activity.getResources().getColor(R.color.colorRed))
                .show();
    }

    public static void warningSnackBar(Context context, View v, String msg) {
        Snackbar snackbar = Snackbar.make(v, msg, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_01377D));
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static boolean isDeviceOnline(Context context) {
        boolean isDeviceOnLine = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            isDeviceOnLine = netInfo != null && netInfo.isConnected();
        }
        return isDeviceOnLine;

    }
    public static boolean isValidEmail(String email) {
        return Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches();
    }
    public static void showToastMessageError(Activity activity, String message) {
        Toast toast = new Toast(activity);
        toast.setDuration(Toast.LENGTH_LONG);
        View toastView = LayoutInflater.from(activity).inflate(R.layout.custom_toast, null);
        toastView.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_rounded_dark_blue));
        CheckedTextView txtMessage = toastView.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        toast.setView(toastView);
        toast.show();

    }

    public static String toTitleCase(String str) {
        if (str == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }
    public static String getFormatChangeDate(String ourDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(ourDate);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());  //set local time zone
            ourDate = dateFormatter.format(value);
        } catch (Exception e) {
            ourDate = "";
        }
        return ourDate;
    }

    public static String getChangeDate(String ourDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(ourDate);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM, yyyy hh:mm aa"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());  //set local time zone
            ourDate = dateFormatter.format(value);
        } catch (Exception e) {
            ourDate = "";
        }
        return ourDate;
    }

    public static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    public static File createAppDir(Activity mActivity, File mFileTemp, String TEMP_PHOTO_FILE_NAME) {
        String root = Environment.getExternalStorageDirectory().toString();
        new File(root + "/" + mActivity.getString(R.string.app_name) + "/temp").mkdirs();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(root + "/" + mActivity.getString(R.string.app_name) + "/temp/", TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(mActivity.getFilesDir(), TEMP_PHOTO_FILE_NAME);
        }
        return mFileTemp;
    }
    public static Bitmap decodeUriToBitmap(Context mContext, Uri sendUri) {
        Bitmap getBitmap = null;
        try {
            InputStream image_stream;
            try {
                image_stream = mContext.getContentResolver().openInputStream(sendUri);
                getBitmap = BitmapFactory.decodeStream(image_stream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBitmap;
    }

    public static void sendEmail(Activity activity, String email) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String[] recipients = {email};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.setType("text/html");
        intent.setPackage("com.google.android.gm");
        activity.startActivity(Intent.createChooser(intent, "Send mail"));
    }

    @SuppressLint("DefaultLocale")
    public static String fmt(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }

    public static void shareApp(Activity activity)
    {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Psycoolg");
            String shareMessage= "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            activity.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }
}
