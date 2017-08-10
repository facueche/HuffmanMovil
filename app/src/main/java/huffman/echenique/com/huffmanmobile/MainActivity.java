package huffman.echenique.com.huffmanmobile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import huffman.*;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

    private List<String> items = null;
    private boolean code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle b = this.getIntent().getExtras();
        getFiles(new File("/").listFiles());
        this.code = b.getBoolean("code");
        System.out.println(this.code);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        int selectedRow = (int)id;
        if(selectedRow == 0){
            getFiles(new File("/").listFiles());
        }else{
            File file = new File(items.get(selectedRow));
            if(file.isDirectory()){
                getFiles(file.listFiles());
            }else{
                System.out.println(file.getAbsolutePath());
                String extension = file.getPath();
                if("txt".equals(extension.substring(extension.length()-3, extension.length())) || "txt".equals(extension.substring(extension.length()-7, extension.length()-4)))
                    new HuffmanTree(file.getPath(), code, "utf-8");
                else
                    new HuffmanTree(file.getPath(), code);

                new AlertDialog.Builder(this).setTitle("Hecho").setNeutralButton("OK", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int button){
                                //do nothing
                            }
                        })
                        .show();

            }
        }
    }
    private void getFiles(File[] files){
        items = new ArrayList<String>();
        items.add(getString(R.string.goto_root));
        for(File file : files){
            items.add(file.getPath());
        }
        ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,R.layout.mytextview, items);
        setListAdapter(fileList);
    }

    public void LetsCode(View v){

    }
}