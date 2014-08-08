<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.wordwriter.*,
	com.zhuozhengsoft.pageoffice.*,com.questionbase.question.*,com.questionbase.hbm.*,org.hibernate.*,com.questionbase.hbm.factory.*,java.sql.*;"
	pageEncoding="gb2312"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
		//For debug
	Enumeration enu=request.getParameterNames();  
    System.out.println("Compose.jsp    parameter");
	while(enu.hasMoreElements()){  
		String paraName=(String)enu.nextElement();  
		System.out.println(paraName+": "+request.getParameter(paraName));  
	}  
	
	String[] QUESITONTYPE = {"填空题", "选择题", "判断题", "问答题", "计算题"};
	Boolean bHasAnswer = false;
	String subject = "选择题";
		
	Session heSession = QuestionBaseFactory.GetInstance()
			.getSessionFactory().openSession();
	
	boolean bExistWordPaper = false;
	String wordPaperID = request.getParameter("wordPaperId");//"";
	if(wordPaperID==null) wordPaperID = "";
	String strQuestionList = "";
	String strScoreList = "";
	String strPaperCode = request.getParameter("shijuan");
	System.out.println("shijuan is " + strPaperCode);
	if(strPaperCode==null) strPaperCode = "";
	//strPaperCode = "48";
	boolean bExistByParent = false;
	if(wordPaperID.trim().length()!=0){
		WordPaper s = (WordPaper)heSession.load(WordPaper.class, Integer.parseInt(wordPaperID.trim()));
		Blob wordContent = null;
		
		//Get content from DB
		wordContent = s.getQuestionPaper();
		if(wordContent!=null){
			bExistByParent = true;
			bExistWordPaper = true;
		}
	}
	
	
	if(!bExistByParent){
		if(strPaperCode.trim().length()==0){
			strQuestionList = request.getParameter("ti");
			if(strQuestionList==null) strQuestionList = "";
			strScoreList = request.getParameter("fen");
			if(strScoreList==null) strScoreList = "";
		}else{
			//Get information from DB
		
			//Get wordPaper ID
			Blob wordContent = null;
			TestPaperInfo tpi = (TestPaperInfo)heSession.load(TestPaperInfo.class, Integer.parseInt(strPaperCode.trim()));
			if(tpi!=null){
				wordPaperID = tpi.getWordPaperId();
				//Get content from DB
				if(wordPaperID!=null && wordPaperID.trim().length()!=0){
					WordPaper w = (WordPaper)heSession.load(WordPaper.class, Integer.parseInt(wordPaperID.trim()));
					wordContent = w.getQuestionPaper();
				}
			}
			if(wordContent!=null){
				bExistWordPaper = true;
			}
			else{			
				List<TestPaperDetailInfo> paperList = (List<TestPaperDetailInfo>)heSession.createQuery("from TestPaperDetailInfo where PaperCode=" + strPaperCode + "order by sequence").list();
				for(int index = 0; index < paperList.size(); index++)
				{
					int questionCode = paperList.get(index).getQuestionCode();
					int questionScore = paperList.get(index).getScore();
					System.out.println("question code: " + questionCode + "  score: " + questionScore);
					strQuestionList += "," + questionCode;
					strScoreList += "," + questionScore;
				}
			}
		}
	}
		
	String operateStr="";
	if(!bExistWordPaper){//need to compose by ti and fen
	if((strQuestionList.trim().length()>0)&&(strScoreList.trim().length()>0)){
		String[] qsStr = strQuestionList.split(",");
		
		
		String[] qsScores = strScoreList.split(",");
		int scoreIndex = 0;
		
		System.out.println(request.getParameter("showAnswer"));
		
		//Display answer?
		if(request.getParameter("showAnswer").compareToIgnoreCase("true")==0){
			bHasAnswer = true;
		}
		
		ArrayList<Integer> qsCode = new ArrayList<Integer>();
		
		
		for(String q : qsStr){
			try{
				qsCode.add(Integer.parseInt(q));
			}catch(Exception e){
			}
		}
		
		
		
		List<Question> qs = QuestionManager.GetInstance().CreateQuestionByQuestionCode(qsCode);
		
		//Iterator<Question> itr = qs.iterator();
		
		String preType = "";
		int pNum = 1;
		
        operateStr += "function Create(){\n";
        //Disable "save"
        // operateStr += "document.getElementById('PageOfficeCtrl1').SetEnableFileCommand(3, false);\n";
        // document.getElementById('PageOfficeCtrl1').Document.Application 微软office VBA对象的根Application对象
        operateStr += "var obj = document.getElementById('PageOfficeCtrl1').Document.Application;\n";
        operateStr += "obj.Selection.EndKey(6);\n"; // 定位光标到文档末尾

		//加入题目类型
		operateStr += "obj.Selection.TypeParagraph();"; //用来换行
		operateStr += "obj.Selection.Font.Bold=true;\n";
		
		for(int listIndex=0; listIndex<qsCode.size(); listIndex++) { 
			//Get the Question
			Iterator<Question> itr = qs.iterator();
			while(itr.hasNext()){
				Question nextObj = (Question)itr.next();
				if(nextObj.getCode()==qsCode.get(listIndex)){
					String type = nextObj.getQuestionTypes();
					System.out.println(nextObj.getCode());
					System.out.println("题型:" + type);
					String titleID = nextObj.getName();
					String score = qsScores[scoreIndex];
					scoreIndex++;
					System.out.println("分:" + score);
					//Type is different
					if(type.compareToIgnoreCase(preType)!=0){
						preType = type;
						 operateStr += "obj.Selection.Range.Text = '" + QUESITONTYPE[Integer.parseInt(type)-1] + "';\n"; //题型 
					     operateStr += "obj.Selection.Font.Bold=false;\n";
					     // 下面两句代码用来移动光标位置
					     operateStr += "obj.Selection.EndKey(5,1);\n";
					     operateStr += "obj.Selection.MoveRight(1,1);\n";
					     
					     
					     pNum = 1;
					}
					
					 operateStr += "obj.Selection.TypeParagraph();"; //用来换行
		             operateStr += "obj.Selection.Range.Text = '" + pNum + ".(" + score + "分)';\n"; // 用来生成题号
		             // 下面两句代码用来移动光标位置
		             operateStr += "obj.Selection.EndKey(5,1);\n";
		             operateStr += "obj.Selection.MoveRight(1,1);\n";
		             
		             //The title exist?
		             Stream s = (Stream)heSession.load(Stream.class, Integer.parseInt(titleID.trim()));
		             if(s.getQuestionTitle()!=null){
		             	// 插入指定的题到文档中
		             	operateStr += "document.getElementById('PageOfficeCtrl1').InsertDocumentFromURL('Openstream?id=" + titleID + "&Action=title');\n";
		             }else{
		            	 operateStr += "obj.Selection.TypeParagraph();"; //用来换行
		             }
		             System.out.println("Stream ID:" + titleID);
		             pNum++;
		             
		             break;
					
				}
			}
			
			
			
		}
		
		if(bHasAnswer){
			
			operateStr += "obj.Selection.TypeParagraph();"; //用来换行     
			operateStr += "obj.Selection.TypeParagraph();"; //用来换行     
			operateStr += "obj.Selection.Range.Text = '答案';\n"; //题型 
	     	operateStr += "obj.Selection.Font.Bold=false;\n";
	     	// 下面两句代码用来移动光标位置
	     	operateStr += "obj.Selection.EndKey(5,1);\n";
	     	operateStr += "obj.Selection.MoveRight(1,1);\n";
	     	operateStr += "obj.Selection.TypeParagraph();"; //用来换行     
	     	
			
			preType = "";
			pNum = 1;
			scoreIndex = 0;
			for(int listIndex=0; listIndex<qsCode.size(); listIndex++) { 
				//Get the Question
				Iterator<Question> itr = qs.iterator();
				while(itr.hasNext()){
					Question nextObj = (Question)itr.next();
					if(nextObj.getCode()==qsCode.get(listIndex)){
						String type = nextObj.getQuestionTypes();
						System.out.println("题型:" + type);
						String answerID = nextObj.getAnswer();
						String score = qsScores[scoreIndex];
						scoreIndex++;
						System.out.println("分:" + score);
						//Type is different
						if(type.compareToIgnoreCase(preType)!=0){
							preType = type;
							 operateStr += "obj.Selection.Range.Text = '" + QUESITONTYPE[Integer.parseInt(type)-1] + "';\n"; //题型 
						     operateStr += "obj.Selection.Font.Bold=false;\n";
						     // 下面两句代码用来移动光标位置
						     operateStr += "obj.Selection.EndKey(5,1);\n";
						     operateStr += "obj.Selection.MoveRight(1,1);\n";
						     
						     
						     pNum = 1;
						}
						
						 operateStr += "obj.Selection.TypeParagraph();"; //用来换行
			             operateStr += "obj.Selection.Range.Text = '" + pNum + ".';\n"; // 用来生成题号
			             // 下面两句代码用来移动光标位置
			             operateStr += "obj.Selection.EndKey(5,1);\n";
			             operateStr += "obj.Selection.MoveRight(1,1);\n";
			            
			             //The answer exist?
			             Stream s = (Stream)heSession.load(Stream.class, Integer.parseInt(answerID.trim()));
			             if(s.getQuestionAnswer()!=null){
			             	// 插入指定的题到文档中
			             	operateStr += "document.getElementById('PageOfficeCtrl1').InsertDocumentFromURL('Openstream?id=" + answerID + "&Action=answer');\n";
			             }else{
			            	 operateStr += "obj.Selection.TypeParagraph();"; //用来换行
			             }
			             System.out.println("Stream ID(answer):" + answerID);
			             pNum++;
			             
			             break;
						
					}
				}
				
				
				
			}
       
		}
       
        operateStr += "\n}\n";
	}else{
		 
		 //Creat an empty document
		 operateStr += "function Create(){\n";
	     //Disable "save"
	     operateStr += "document.getElementById('PageOfficeCtrl1').SetEnableFileCommand(3, false);\n";
	     // document.getElementById('PageOfficeCtrl1').Document.Application 微软office VBA对象的根Application对象
	     operateStr += "var obj = document.getElementById('PageOfficeCtrl1').Document.Application;\n";
	     operateStr += "obj.Selection.EndKey(6);\n"; // 定位光标到文档末尾
		 operateStr += "\n}\n";
	}
	}
      //******************************卓正PageOffice组件的使用*******************************
	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	poCtrl1.setServerPage(request.getContextPath()+"/poserver.do"); //此行必须
	//隐藏菜单栏
	//poCtrl1.setMenubar(false);
	poCtrl1.setCustomToolbar(false);
	poCtrl1.setCaption("生成试卷");
	

	//设置保存页面
	String id = "";
	if(wordPaperID==null) wordPaperID="";
	
	//打开Word文件
	//poCtrl1.webOpen("template/template1.doc", OpenModeType.docNormalEdit, "张三");
	if(bExistWordPaper){
		poCtrl1.webOpen("OpenWordPaperStream?id=" + wordPaperID, OpenModeType.docNormalEdit, "管理员");
	}else{
		poCtrl1.setJsFunction_AfterDocumentOpened("Create()");
		//新建word
		poCtrl1.webCreateNew("张佚名", DocumentVersion.Word2003);
	}
	poCtrl1.setSaveFilePage("SavePaper?ID=" + wordPaperID );
	poCtrl1.setJsFunction_AfterDocumentSaved("saveCheck");
	poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须	


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>在Word文档中动态生成 试卷</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>
<script type="text/javascript">
		var p = (window.parent.parent);
		var nameHidden = p.document.getElementById("wordPaperId");
		
         <%=operateStr %>
         
         function saveCheck(){
        		
     		var customResult = document.getElementById("PageOfficeCtrl1").CustomSaveResult;
     		
             var arr = customResult.split("_");

     		var result = arr[0];
     		var id = arr[1];
     		if (result == "ok") {
     			document.getElementById("Hidden1").value= ' ' + id;
     			//--------------------------------------------------------------------------------------------------------------------------------
     			//to parent page
     			nameHidden.value = id;
     			alert('保存成功！');	
     		} else {
     			alert('保存失败！');
     		}
     		
     	}
    </script>
<body>
  <input id="Hidden1" name="neworsave" type="hidden" value="" />
	<div style="width: auto; height: 700px;">
		<po:PageOfficeCtrl id="PageOfficeCtrl1"></po:PageOfficeCtrl>
	</div>
</body>
</html>
