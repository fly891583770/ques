package com.questionbase.word;

import java.io.*;

import com.jacob.com.*;
import com.jacob.activeX.*;

import java.util.ArrayList;

public class WordToHtml {
	public void changeFiles(ArrayList<String> wordfilelist, String savepath){
		for(int index=0; index< wordfilelist.size(); index++){
			change(wordfilelist.get(index), savepath);
		}
		
	}
	//灏嗘寚瀹氱洰褰曚笅闈㈢殑鎸囧畾doc鏂囦欢杞寲涓篐TML骞跺瓨鍌ㄥ湪savepaths鐩綍涓�
	private  void change(String filepath, String savepath) { 
	File f = new File(filepath);
	  String filename = f.getName();
	  String filetype = filename.substring((filename.length() - 3), filename.length());
	// 鍙栧緱鏂囦欢绫诲瀷  if (filetype.equals("doc")) {
	// 鍒ゆ柇鏄惁涓篸oc鏂囦欢  
	System.out.println("褰撳墠姝ｅ湪杞崲......");
	   // 鎵撳嵃褰撳墠鐩綍璺緞  
	System.out.println(filepath);
	   ActiveXComponent app = new ActiveXComponent("Word.Application");
	// 鍚姩word  
	String docpath = filepath;
	   String htmlpath = savepath + filename.substring(0, (filename.length() - 4));
	   String inFile = docpath;
	   // 瑕佽浆鎹㈢殑word鏂囦欢   
	   String tpFile = htmlpath;
	   // HTML鏂囦欢   
	   
	   boolean flag = false;
	try {   
	   app.setProperty("Visible", new Variant(false));
	// 璁剧疆word涓嶅彲瑙�  
	Object docs = app.getProperty("Documents").toDispatch();
	    Object doc = Dispatch.invoke((Dispatch)docs,"Open",Dispatch.Method,new Object[] { inFile, new Variant(false),new Variant(true) }, new int[1]).toDispatch();
	// 鎵撳紑word鏂囦欢    
	/*     
	* new Variant(10)绛涢�杩囩殑缃戦〉     
	* new Variant(9) 鍗曚釜鏂囦欢缃戦〉    
	 * new Variant(8) 鍙﹀瓨涓虹綉椤�    
	 * new Variant(7) 鍙﹀瓨涓簍xt鏍煎紡     
	 * new Variant(6) 鍙﹀瓨涓簉tf鏍煎紡     
	 * new Variant(0) 鍙﹀瓨涓篸oc鏍煎紡     
	 */   
	 Dispatch.invoke((Dispatch)doc, "SaveAs", Dispatch.Method, new Object[] {tpFile, new Variant(10) }, new int[1]);
	// 浣滀负html鏍煎紡淇濆瓨鍒颁复鏃舵枃浠�  
	    Variant fl = new Variant(false);
	    Dispatch.call((Dispatch)doc, "Close", fl);
	    flag = true;
	   } catch (Exception e) {    
	   
	   e.printStackTrace();
	   } finally {    
	   
	   app.invoke("Quit", new Variant[] {});
	   }
	   
	   System.out.println("Word to html: OK!");
	  } 
	  
	  
	
}
