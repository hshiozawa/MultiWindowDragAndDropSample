package com.hjm.dropsample;

import android.app.Dialog;
import android.content.ClipDescription;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by h_shiozawa on 2017/02/22.
 */

public class MainFragment extends Fragment {

    private final static String TAG = MainFragment.class.getSimpleName();

    private static final String GOTO_OFFICE = "goto_office";

    private static final
    @ColorInt
    int ALPHA_BLUE = alpha(0x33, Color.BLUE);

    private static final
    @ColorInt
    int ALPHA_RED = alpha(0x33, Color.RED);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        final ImageView company = (ImageView) view.findViewById(R.id.company);
        company.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                Log.d(TAG, "onDrag(): view=" + v + ",event=" + event);
                return handleApplyDrag(company, event);
            }
        });
        return view;
    }

    private boolean handleApplyDrag(ImageView view, DragEvent event) {
        // ApplyDragEvent かどうかを確認
        if (!validateApplyDragEvent(event)) {
            return false;
        }

        int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                changeColorFilter(view, ALPHA_BLUE);
                return true;
            case DragEvent.ACTION_DRAG_ENTERED:
                changeColorFilter(view, ALPHA_RED);
                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                changeColorFilter(view, ALPHA_BLUE);
                return true;
            case DragEvent.ACTION_DROP:
                new MyDialogFragment().show(getChildFragmentManager(), null);
                clearColorFilter(view);
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                clearColorFilter(view);
                return true;
            default:
                Log.e(TAG, "unknown drag event action. event=" + event);
        }
        return false;
    }

    private static boolean validateApplyDragEvent(@NonNull DragEvent event) {
        ClipDescription description = event.getClipDescription();
        if (description == null) {
            return false;
        }

        return GOTO_OFFICE.equals(description.getLabel());
    }

    private static void changeColorFilter(ImageView view, @ColorInt int color) {
        view.setColorFilter(color);
        view.invalidate();
    }

    private static void clearColorFilter(ImageView view) {
        view.clearColorFilter();
        view.invalidate();
    }

    private static
    @ColorInt
    int alpha(int alpha, @ColorInt int color) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    public static class MyDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            @StringRes int id = enableTrain() ?
                    R.string.goto_office : R.string.cant_goto_office;
            return new AlertDialog.Builder(getActivity())
                    .setMessage(id)
                    .create();
        }

        private static boolean enableTrain() {
            return System.currentTimeMillis() % 5 != 0;
        }

        @Override
        public void onPause() {
            super.onPause();
            dismiss();
        }
    }

}

