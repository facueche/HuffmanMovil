package huffman.echenique.com.huffmanmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

/**
 * Created by Daniel on 24/06/2015.
 */
public class Main extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void LetsCode(View v){
        Bundle bundle = new Bundle();
        System.out.println(v.getId() == R.id.code);
        System.out.println(v.getId() == R.id.decode);
        if(v.getId() == R.id.code){
            bundle.putBoolean("code",true);
        }
        if(v.getId() == R.id.decode){
            bundle.putBoolean("code",false);
        }
        StarAnotherActivity(bundle,MainActivity.class);
    }

    private void StarAnotherActivity(Bundle bundle, Class c){
        Intent i = new Intent(this, c);
        i.putExtras(bundle);
        startActivity(i);
    }
}
