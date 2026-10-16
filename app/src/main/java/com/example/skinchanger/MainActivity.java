
package com.example.skinchanger;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import android.net.Uri;
import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends Activity {
    private LinearLayout list;
    private EditText search;
    private TextView status;
    private final ArrayList<String> skins = new ArrayList<>();
    private final HashSet<String> favorites = new HashSet<>();
    private String selectedSkin = "";
    private boolean favoritesOnly = false;
    private Uri customImage;

    private int red = Color.rgb(190, 20, 40);

    private GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radius);
        return d;
    }

    private TextView text(String value, int size) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(Color.WHITE);
        return t;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        skins.add("Ruby Knight");
        skins.add("Shadow");
        skins.add("Red Demon");
        skins.add("Crimson");
        skins.add("Dark Ninja");
        skins.add("Scarlet");

        getWindow().setStatusBarColor(Color.rgb(18, 18, 18));
        getWindow().setNavigationBarColor(Color.BLACK);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(18, 20, 18, 16);
        root.setBackgroundColor(Color.rgb(18, 18, 18));
        setContentView(root);

        TextView title = text("SKIN CHANGER", 27);
        title.setTypeface(null, Typeface.BOLD);
        root.addView(title);

        TextView subtitle = text("STAND CHILLOW • SKIN CATALOG", 12);
        subtitle.setTextColor(Color.LTGRAY);
        root.addView(subtitle);

        status = text("Выбери скин из каталога", 14);
        status.setPadding(0, 12, 0, 12);
        root.addView(status);

        search = new EditText(this);
        search.setSingleLine(true);
        search.setTextColor(Color.WHITE);
        search.setHintTextColor(Color.GRAY);
        search.setHint("Поиск скина...");
        search.setPadding(14, 8, 14, 8);
        search.setBackground(shape(Color.rgb(40, 40, 40), 14));
        root.addView(search);

        Button filter = new Button(this);
        filter.setText("☆  ВСЕ СКИНЫ / ИЗБРАННОЕ");
        filter.setTextColor(Color.WHITE);
        filter.setBackground(shape(red, 14));
        root.addView(filter);

        ScrollView scroll = new ScrollView(this);
        list = new LinearLayout(this);
        list.setOrientation(LinearLayout.VERTICAL);
        scroll.addView(list);
        LinearLayout.LayoutParams scrollParams =
                new LinearLayout.LayoutParams(-1, 0, 1);
        scrollParams.topMargin = 8;
        root.addView(scroll, scrollParams);

        filter.setOnClickListener(v -> {
            favoritesOnly = !favoritesOnly;
            filter.setText(favoritesOnly
                    ? "★  ПОКАЗАТЬ ВСЕ СКИНЫ"
                    : "☆  ВСЕ СКИНЫ / ИЗБРАННОЕ");
            renderSkins();
        });

        search.addTextChangedListener(new android.text.TextWatcher() {
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {}
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                renderSkins();
            }
            public void afterTextChanged(
                    android.text.Editable e) {}
        });

        Button gallery = new Button(this);
        gallery.setText("ВЫБРАТЬ ИЗ ГАЛЕРЕИ");
        gallery.setTextColor(Color.WHITE);
        gallery.setBackground(shape(red, 16));
        root.addView(gallery);

        gallery.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, 100);
        });

        renderSkins();
    }

    private void renderSkins() {
        list.removeAllViews();
        String query = search.getText().toString()
                .trim().toLowerCase();

        for (String skin : skins) {
            if (!skin.toLowerCase().contains(query)) continue;
            if (favoritesOnly && !favorites.contains(skin)) continue;
            addSkin(skin);
        }

        if (list.getChildCount() == 0) {
            TextView empty = text("Ничего не найдено", 15);
            empty.setPadding(8, 20, 8, 20);
            list.addView(empty);
        }
    }

    private void addSkin(String name) {
        LinearLayout card = new LinearLayout(this);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(12, 8, 8, 8);
        card.setBackground(shape(Color.rgb(36, 36, 36), 16));

        LinearLayout.LayoutParams cp =
                new LinearLayout.LayoutParams(-1, -2);
        cp.setMargins(0, 6, 0, 6);
        list.addView(card, cp);

        LinearLayout info = new LinearLayout(this);
        info.setOrientation(LinearLayout.VERTICAL);
        card.addView(info, new LinearLayout.LayoutParams(0, -2, 1));

        TextView label = text("◆  " + name, 16);
        info.addView(label);

        TextView sub = text(
                name.equals(selectedSkin) ? "✓ Выбран" : "Нажми, чтобы выбрать",
                11);
        sub.setTextColor(name.equals(selectedSkin)
                ? Color.rgb(255, 90, 100) : Color.LTGRAY);
        info.addView(sub);

        info.setOnClickListener(v -> {
            selectedSkin = name;
            status.setText("Выбран: " + name
                    + " • только в каталоге APK");
            renderSkins();
        });

        Button fav = new Button(this);
        fav.setText(favorites.contains(name) ? "★" : "☆");
        fav.setTextSize(19);
        fav.setTextColor(Color.WHITE);
        card.addView(fav);

        fav.setOnClickListener(v -> {
            if (favorites.contains(name)) {
                favorites.remove(name);
            } else {
                favorites.add(name);
            }
            renderSkins();
        });
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            customImage = data.getData();

            try {
                getContentResolver().takePersistableUriPermission(
                        customImage,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } catch (Exception ignored) {}

            ImageView preview = new ImageView(this);
            preview.setImageURI(customImage);
            preview.setAdjustViewBounds(true);
            preview.setMaxHeight(240);

            new android.app.AlertDialog.Builder(this)
                    .setTitle("Твоё изображение")
                    .setView(preview)
                    .setPositiveButton("ОК", null)
                    .show();
        }
    }
}
