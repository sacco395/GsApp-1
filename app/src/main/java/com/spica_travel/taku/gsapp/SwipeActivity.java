package com.spica_travel.taku.gsapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class SwipeActivity extends ActionBarActivity {

    //アダプタークラスです。
    private FavoriteRecordsAdapter fAdapter;

    //起動時にOSから実行される関数です。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //メイン画面のレイアウトをセットしています。ListView
        setContentView(R.layout.activity_swipe);

        //アダプターを作成します。newでクラスをインスタンス化しています。
        fAdapter = new FavoriteRecordsAdapter(this);

        //ListViewのViewを取得
        ListView listView = (ListView) findViewById(R.id.mylist2);
        //ListViewにアダプターをセット。
        listView.setAdapter(fAdapter);
        //一覧のデータを作成して表示します。
        fetch();

    }
    //自分で作った関数です。一覧のデータを作成して表示します。
    private void fetch() {
        //jsonデータをサーバーから取得する通信機能です。Volleyの機能です。通信クラスのインスタンスを作成しているだけです。通信はまだしていません。
        JsonObjectRequest request = new JsonObjectRequest(
                "http://spica-travel.com/json3.txt" ,//jsonデータが有るサーバーのURLを指定します。
                null,
                //サーバー通信した結果、成功した時の処理をするクラスを作成しています。
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //try catchでエラーを処理します。tryが必要かどうかはtryに記述している関数次第です。
                        try {
                            //jsonデータを下記で定義したparse関数を使いデータクラスにセットしています。
                            List<FavoriteRecord> favoriteRecords = parse(jsonObject);
                            //データをアダプターにセットしています。
                            fAdapter.setFavoriteRecords(favoriteRecords);
                        }
                        catch(JSONException e) {
                            //トーストを表示
                            Toast.makeText(getApplicationContext(), "Unable to parse data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                //通信結果、エラーの時の処理クラスを作成。
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //トーストを表示
                        Toast.makeText(getApplicationContext(), "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        //作成した通信クラスをキュー、待ち行列にいれて適当なタイミングで通信します。
        //VolleyApplicationはnewしていません。これはAndroidManifestで記載しているので起動時に自動的にnewされています。
        VolleyApplication.getInstance().getRequestQueue().add(request);
    }
    //サーバにあるjsonデータをMessageRecordに変換します。
    private List<FavoriteRecord> parse(JSONObject json) throws JSONException {
        //空のMessageRecordデータの配列を作成
        ArrayList<FavoriteRecord> records = new ArrayList<FavoriteRecord>();
        //jsonデータのmessagesにあるJson配列を取得します。
        JSONArray jsonFavorites = json.getJSONArray("favorites");
        //配列の数だけ繰り返します。
        for(int i =0; i < jsonFavorites.length(); i++) {
            //１つだけ取り出します。
            JSONObject jsonFavorite = jsonFavorites.getJSONObject(i);
            //jsonの値を取得します。
            String comment = jsonFavorite.getString("comment");
            String title = jsonFavorite.getString("title");
            String author = jsonFavorite.getString("author");
            String url = jsonFavorite.getString("imageUrl");
            //jsonMessageを新しく作ります。
            FavoriteRecord record = new FavoriteRecord(url, title , author, comment);
            //MessageRecordの配列に追加します。
            records.add(record);
        }

        return records;
    }

    //デフォルトで作成されたメニューの関数です。未使用。
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_swipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnToFirst:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

}
