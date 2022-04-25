package com.ctgu.word2html;

import com.ctgu.util.word2html.Word2Html;
import com.ctgu.util.word2html.bean.DocHtmlDto;
import com.ctgu.util.word2html.config.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

/**
 * @author aoboxia
 * @date 2019/12/20 12:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class word2htmlTest {
    @Test
    public void test() throws IOException {
        System.out.println(ResourceUtils.getURL("classpath:").getPath());

//        Word2Html word2Html = new Word2Html();
//        DocHtmlDto res =  word2Html.doc2Html("C:\\Users\\83778\\Desktop\\1.docx");
//        System.out.println(res.getFilePath());
    }
//    @Test
//    public void uploadTest() throws IOException {
//
//        Word2Html word2Html = new Word2Html();
//        word2Html.doc2Html("/html/1.docx", "test");
//    }
}
