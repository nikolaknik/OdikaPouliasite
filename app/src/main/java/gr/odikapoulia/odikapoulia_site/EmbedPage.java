package gr.odikapoulia.odikapoulia_site;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

public class EmbedPage extends AppCompatActivity {

    public Boolean internetCheck;
    public MediaPlayer mediaPlayer;
    private Context mContext;
    private Activity mActivity;
    private PopupWindow mPopupWindow;

    @Override
    public void onBackPressed() {

        // Get the application context
        mContext = getApplicationContext();

        // Get the activity
        mActivity = EmbedPage.this;
        // Initialize a new instance of LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.popup_exit,null);

        // Initialize a new instance of popup window
        boolean focusable = true;
        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                focusable
        );

        // Set an elevation value for popup window
        // Call requires API level 21
        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }

        // Get a reference for the custom view close button
        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
            }
        });

        mediaPlayer = MediaPlayer.create(this, R.raw.nomusic);
        mediaPlayer.start();

        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER,0,0);

        Button buttonExit = (Button) customView.findViewById(R.id.exit);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getIntent().setAction("");
                startActivity(intent);
                finish();
                System.exit(0);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);

        InternetCheck myIncheck = new InternetCheck();

        if(myIncheck.isNetworkAvailable(getApplicationContext())){

            internetCheck = true;

            System.out.println(internetCheck);

            setContentView(R.layout.embed_page);

            WebView webview = (WebView) findViewById(R.id.webview);

            webview.getSettings().setJavaScriptEnabled(true);

            final Activity activity = this;

            webview.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    // Activities and WebViews measure progress with different scales.
                    // The progress meter will automatically disappear when we reach 100%
                    activity.setProgress(progress * 1000);
                }
            });
            webview.setWebViewClient(new WebViewClient() {
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                }
            });

            webview.getSettings().setJavaScriptEnabled(true);


            webview.loadUrl("https://odikapoulia.gr/");

        }else{

            internetCheck = false;

            System.out.println(internetCheck);

            setContentView(R.layout.no_internet_layout);

            Button button_retry = (Button)findViewById(R.id.button_retry);
            button_retry.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), EmbedPage.class);

                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getIntent().setAction("");
                startActivity(intent);

                                              }
            });

            Button buttonExit = (Button)findViewById(R.id.button_exit);
            buttonExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getIntent().setAction("");
                    startActivity(intent);
                    finish();
                    System.exit(0);

                }
            });


        }



        // WebView myWebView = (WebView) findViewById(R.id.webview);
      //  myWebView.loadUrl("https://odikapoulia.gr/");
    }
}
