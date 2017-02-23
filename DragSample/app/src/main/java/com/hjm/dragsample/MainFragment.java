package com.hjm.dragsample;

import android.content.ClipData;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by h_shiozawa on 2017/02/22.
 */

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        final ImageView target = (ImageView) view.findViewById(R.id.target);
        target.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startDrag(target);
                return true;
            }
        });

        return view;
    }

    private void startDrag(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.startDragAndDrop(
                    createClipData(GOTO_OFFICE),
                    new View.DragShadowBuilder(view),
                    null,
                    View.DRAG_FLAG_GLOBAL);
        }
    }

    private static ClipData createClipData(String text) {
        ClipData.Item item = new ClipData.Item(text);
        return new ClipData(text, new String[]{}, item);
    }

    private static final String GOTO_OFFICE = "goto_office";

}

