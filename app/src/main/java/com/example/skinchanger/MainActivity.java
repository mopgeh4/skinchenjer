
package com.example.skinchanger;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.*;
import android.graphics.drawable.GradientDrawable;

public class MainActivity extends Activity {
    private LinearLayout list;
    private final String[] skins = {
        "Ruby Knight", "Shadow", "Red Demon",
        "Crimson", "Dark Ninja", "Scarlet"
    };

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
        getWindow().setStatusBarColor(Color.rgb(18, 18, 18));
        getWindow().setNavigationBarColor(Color.BLACK);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(20, 24, 20, 20);
        root.setBackgroundColor(Color.rgb(18, 18, 18));
        setContentView(root);

        TextView title = text("SKIN CHANGER", 27);
        title.setTypeface(null, Typeface.BOLD);
        root.addView(title);

        TextView subtitle = text("STAND CHILLOW • DEMO", 13);
        subtitle.setTextColor(Color.LTGRAY);
        root.addView(subtitle);

        TextView note = text(
            "Демо-каталог. Смена скина внутри игры " +
            "не подключена.", 14);
        note.setPadding(0, 18, 0, 18);
        root.addView(note);

        ScrollView scroll = new ScrollView(this);
        list = new LinearLayout(this);
        list.setOrientation(LinearLayout.VERTICAL);
        scroll.addView(list);
        root.addView(scroll, new LinearLayout.LayoutParams(
            -1, 0, 1));

        for (String skin : skins) {
            addSkin(skin);
        }

        Button gallery = new Button(this);
        gallery.setText("ВЫБРАТЬ ИЗ ГАЛЕРЕИ");
        gallery.setTextColor(Color.WHITE);
        gallery.setBackground(shape(Color.rgb(190, 20, 40), 18));
        root.addView(gallery);
        gallery.setOnClickListener(v -> {
            Toast.makeText(this,
                "Импорт изображений добавим следующим шагом",
                Toast.LENGTH_SHORT).show();
        });
    }

    private void addSkin(String name) {
        LinearLayout card = new LinearLayout(this);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(14, 12, 14, 12);
        card.setBackground(shape(Color.rgb(36, 36, 36), 18));

        LinearLayout.LayoutParams cp =
            new LinearLayout.LayoutParams(-1, -2);
        cp.setMargins(0, 8, 0, 8);
        list.addView(card, cp);

        TextView label = text("◆  " + name, 17);
        card.addView(label, new LinearLayout.LayoutParams(0, -2, 1));

        Button fav = new Button(this);
        fav.setText("☆");
        fav.setTextSize(20);
        card.addView(fav);

        fav.setOnClickListener(v -> {
            boolean selected = fav.getText().equals("★");
            fav.setText(selected ? "☆" : "★");
            Toast.makeText(this,
                selected ? "Убрано из избранного" : "В избранном",
                Toast.LENGTH_SHORT).show();
        });
    }
}
