//起動時に実行されるアクティビティーです。１つの画面に１つのアクティビティーが必要です。
//どのアクティビティーが起動時に実行されるのかはAndroidManifestに記述されています。
package com.spica_travel.taku.gsapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    //アダプタークラスです。
    private MessageRecordsAdapter mAdapter;

    //起動時にOSから実行される関数です。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //メイン画面のレイアウトをセットしています。ListView
        setContentView(R.layout.activity_main);

        //アダプターを作成します。newでクラスをインスタンス化しています。
        mAdapter = new MessageRecordsAdapter(this);

        //ListViewのViewを取得
        //ListView listView = (ListView) findViewById(R.id.mylist);
        //ListViewにアダプターをセット。
        //listView.setAdapter(mAdapter);
        //一覧のデータを作成して表示します。

        //GridViewのViewを取得
        GridView gridView= (GridView) findViewById(R.id.mylist);
        //GridViewにアダプターをセット。
        gridView.setAdapter(mAdapter);
        //GridViewのViewを取得
        fetch();

    }
    //自分で作った関数です。一覧のデータを作成して表示します。
    private void fetch() {
        //jsonデータをサーバーから取得する通信機能です。Volleyの機能です。通信/Users/MOOG/Downloads/MainActivity.javaクラスのインスタンスを作成しているだけです。通信はまだしていません。
        JsonObjectRequest request = new JsonObjectRequest(
                "http://spica-travel.com/json.txt" ,//jsonデータが有るサーバーのURLを指定します。
                null,
                //サーバー通信した結果、成功した時の処理をするクラスを作成しています。１度きりなのでここに書いてる
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //try catchでエラーを処理します。tryが必要かどうかはtryに記述している関数次第です。
                        try {
                            //jsonデータを下記で定義したparse関数を使いデータクラスにセットしています。
                            List<MessageRecord> messageRecords = parse(jsonObject);
                            //データをアダプターにセットしています。
                            mAdapter.setMessageRecords(messageRecords);
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
    private List<MessageRecord> parse(JSONObject json) throws JSONException {
        //空のMessageRecordデータの配列を作成
        ArrayList<MessageRecord> records = new ArrayList<MessageRecord>();
        //jsonデータのmessagesにあるJson配列を取得します。
        JSONArray jsonMessages = json.getJSONArray("messages");
        //配列の数だけ繰り返します。
        for(int i =0; i < jsonMessages.length(); i++) {
            //１つだけ取り出します。
            JSONObject jsonMessage = jsonMessages.getJSONObject(i);
            //jsonの値を取得します。
            String title = jsonMessage.getString("comment");
            String url = jsonMessage.getString("imageUrl");
            //jsonMessageを新しく作ります。
            MessageRecord record = new MessageRecord(url, title);
            //MessageRecordの配列に追加します。
            records.add(record);
        }

        return records;
    }

    //デフォルトで作成されたメニューの関数です。未使用。
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

}
