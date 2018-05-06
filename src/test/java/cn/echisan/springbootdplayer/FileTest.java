package cn.echisan.springbootdplayer;

import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileTest {

    @Test
    public void fileTest(){
        try {
            File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"blacklist");
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
}
