package gr.odikapoulia.odikapoulia_site;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class EmbedPage extends AppCompatActivity {


    Boolean internetCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
