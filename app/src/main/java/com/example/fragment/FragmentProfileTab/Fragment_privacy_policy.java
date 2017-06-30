package com.example.fragment.FragmentProfileTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.zotal102.yahwahapp.R;

public class Fragment_privacy_policy extends Fragment {


    ImageView img_bck;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_privacy_policy, container, false);
         img_bck=(ImageView)view.findViewById(R.id.back);

        WebView webView= (WebView)view.findViewById(R.id.webView1);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl("https://eyahweh.com/app/privacy_policy");


        img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new Frag_ProfileOption()).commit();
            }
        });


        return view;

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl("https://eyahweh.com/app/privacy_policy");
            return true;
        }
    }


}








