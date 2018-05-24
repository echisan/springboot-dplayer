package cn.echisan.springbootdplayer;

import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileTest {

    @Test
    public void fileTest(){
        try {
            File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"blacklist");

            System.out.println(file.length());
            System.out.println(file.getAbsolutePath());
            System.out.println(file.getParent());
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            List<String> blackList = new ArrayList<>();
            String str = "";
            while ((str = br.readLine())!=null){
                blackList.add(str);
            }
            System.out.println(blackList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadFileFormJarTest() throws IOException {
        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"blacklist");
        if (ResourceUtils.isJarFileURL(ResourceUtils.getURL(file.getAbsolutePath()))){
            InputStream is = this.getClass().getResourceAsStream("blacklist");
            Reader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);
            List<String> blackList = new ArrayList<>();
            String str = "";
            while ((str = br.readLine())!=null){
                blackList.add(str);
            }
            System.out.println(blackList);
        }else {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            List<String> blackList = new ArrayList<>();
            String str = "";
            while ((str = br.readLine())!=null){
                blackList.add(str);
            }
            System.out.println(blackList);
        }
    }
}
