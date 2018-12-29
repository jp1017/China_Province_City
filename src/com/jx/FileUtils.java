package com.jx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jx on 2018/4/12.
 */

class FileUtils {

    /**
     * ��txt�ļ���ȡ
     */
    public static List<String> readFile(String path) {
        List<String> stringList = new ArrayList<String>();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            String str = "";
            fis = new FileInputStream(path);
            // ���ļ�ϵͳ�е�ĳ���ļ��л�ȡ�ֽ�
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            while ((str = br.readLine()) != null) {
                stringList.add(str);
            }
        } catch (FileNotFoundException e) {
            System.out.println("�Ҳ���ָ���ļ�!");
        } catch (IOException e) {
            System.out.println("��ȡ�ļ�ʧ��!");
        } finally {
            try {
                br.close();
                isr.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringList;
    }

    /**
     * ����.json��ʽ�ļ�
     */
    public static boolean createJsonFile(String jsonString, String fullPath) {
        boolean flag = true;
        try {
            File file = new File(fullPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            // ��ʽ��json�ַ���
            jsonString = JsonFormatTool.formatJson(jsonString);
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }
}
