import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.chenlb.mmseg4j.ComplexSeg;
import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MMSeg;
import com.chenlb.mmseg4j.Seg;
import com.chenlb.mmseg4j.Word;

public class mmseg {
	protected Dictionary dic;
	
	public mmseg() {
		System.setProperty("mmseg.dic.path", "./src/SegChinese/data");	//這裡可以指定自訂詞庫
		dic = Dictionary.getInstance();
	}

	protected Seg getSeg() {
		return new ComplexSeg(dic);
	}
	
	public String segWords(String txt, String wordSpilt) throws IOException {
		Reader input = new StringReader(txt);
		StringBuilder sb = new StringBuilder();
		Seg seg = getSeg();
		MMSeg mmSeg = new MMSeg(input, seg);
		Word word = null;
		boolean first = true;
		while((word=mmSeg.next())!=null) {
			if(!first) {
				sb.append(wordSpilt);
			}
			String w = word.getString();
			sb.append(w);
			first = false;		
		}
		return sb.toString();
	}
	
	protected void run(String[] args) throws IOException {
		//String txt = "這行文字是要被中文斷詞處理的文章，可以從執行結果看斷詞是否成功 莊圓大師";
		String txt = "研究生命科學好有趣!希望大家都能來參加。";
		
		if(args.length > 0) {
			txt = args[0];
		}
		
		System.out.println(segWords(txt, " | "));
	}

	public static void main(String[] args) throws IOException {		
		new mmseg().run(args);
	}
}
