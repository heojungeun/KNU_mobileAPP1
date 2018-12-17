package com.knu_mobileapp1_team2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class StoreFragment extends Fragment {
    SharedPreferences sp;

    int totalPoints;
    int usedPoints;

    TextView tvwStorePoints;

    ListView lvwStoreList;

    ArrayList<StoreButton> listButtons;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        sp = getContext().getSharedPreferences("com.knu_mobileapp1_team2.pref", Activity.MODE_PRIVATE);

        totalPoints = sp.getInt("saved_points", 0);
        usedPoints = sp.getInt("used_points", 0);

        tvwStorePoints = view.findViewById(R.id.tvwStorePoints);
        lvwStoreList = view.findViewById(R.id.lvwStoreList);

        listButtons = new ArrayList<>();
        listButtons.add(new StoreButton(getString(R.string.store_item_1), 500, R.drawable.partner_avocado));
        listButtons.add(new StoreButton(getString(R.string.store_item_2), 700, R.drawable.partner_carrot));
        listButtons.add(new StoreButton(getString(R.string.store_item_3), 900, R.drawable.partner_apple));
        listButtons.add(new StoreButton(getString(R.string.store_item_4), 1100, R.drawable.partner_tomato));
        listButtons.add(new StoreButton(getString(R.string.store_item_5), 1300, R.drawable.partner_pumpkin));

        StoreButtonAdapter sba = new StoreButtonAdapter(getContext(), listButtons);

        lvwStoreList.setAdapter(sba);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        tvwStorePoints.setText(String.format(getString(R.string.store_points), totalPoints - usedPoints));


    }

    private class StoreButton {
        String name;
        int price;
        int imgres;

        public StoreButton(String name, int price, int imgres) {
            this.name = name;
            this.price = price;
            this.imgres = imgres;
        }
    }

    void purchaseItem(int i) {

    }

    private class StoreButtonAdapter extends BaseAdapter {
        Context ctx;
        ArrayList<StoreButton> buttons;

        public StoreButtonAdapter(Context context, ArrayList<StoreButton> buttons) {
            this.ctx = context;
            this.buttons = buttons;
        }

        @Override
        public int getCount() {
            return buttons.size();
        }

        @Override
        public StoreButton getItem(int i) {
            return buttons.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View v = getLayoutInflater().inflate(R.layout.list_store_item, null);

            ImageView ivwStoreItemPic = v.findViewById(R.id.ivwStoreItemPic);
            TextView tvwStoreItemName = v.findViewById(R.id.tvwStoreItemName);
            TextView tvwStoreItemPrice = v.findViewById(R.id.tvwStoreItemPrice);

            ivwStoreItemPic.setImageResource(buttons.get(i).imgres);
            tvwStoreItemName.setText(buttons.get(i).name);
            tvwStoreItemPrice.setText(String.format(getString(R.string.store_pricetag), buttons.get(i).price));

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    purchaseItem(i);
                }
            });

            return v;
        }
    }
}
