package com.example.bottombar.file_io;

import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class FileIO  {

    JSONObject json_data = null;

    private Context context;
    public FileIO(Context current_context){
        this.context = current_context;
    }



    public void Write(JSONObject json_obj, String file_name){
//        File file = new File("file.txt") ;
//        FileWriter fw = null ;
//        BufferedWriter bufwr = null ;
//
//        String str = "STR" ;
//
//        try {
//            // open file.
//            fw = new FileWriter(file) ;
//            bufwr = new BufferedWriter(fw) ;
//
//            // write data to the file.
//            bufwr.write(str) ;
//
//        } catch (Exception e) {
//            e.printStackTrace() ;
//        }
//
//        // close file.
//        try {
//            if (bufwr != null)
//                bufwr.close() ;
//
//            if (fw != null)
//                fw.close() ;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        File saveDir = context.getFilesDir();
        if(file_name == null) file_name = "test.json";
        String save_file_path = saveDir + File.separator + file_name;

        try {
            Writer output = null;
            File file = new File(save_file_path);
//            File file = new File("storage/sdcard/MyIdea/MyCompositions/" + compoTitle + ".json");
            output = new BufferedWriter(new FileWriter(file));
            output.write(json_obj.toString());
            output.close();

        } catch (Exception e) {
        }
    }

//    @Override
//    protected JSONObject doInBackground(JSONObject... jsonObjects) {
//        json_data = jsonObjects[0];
//        Write(json_data);
//        return json_data;
//    }
}
