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
	
	String[] QUESITONTYPE = {"�����", "ѡ����", "�ж���", "�ʴ���", "������"};
	Boolean bHasAnswer = false;
	String subject = "ѡ����";
		
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
        // document.getElementById('PageOfficeCtrl1').Document.Application ΢��office VBA����ĸ�Application����
        operateStr += "var obj = document.getElementById('PageOfficeCtrl1').Document.Application;\n";
        operateStr += "obj.Selection.EndKey(6);\n"; // ��λ��굽�ĵ�ĩβ

		//������Ŀ����
		operateStr += "obj.Selection.TypeParagraph();"; //��������
		operateStr += "obj.Selection.Font.Bold=true;\n";
		
		for(int listIndex=0; listIndex<qsCode.size(); listIndex++) { 
			//Get the Question
			Iterator<Question> itr = qs.iterator();
			while(itr.hasNext()){
				Question nextObj = (Question)itr.next();
				if(nextObj.getCode()==qsCode.get(listIndex)){
					String type = nextObj.getQuestionTypes();
					System.out.println(nextObj.getCode());
					System.out.println("����:" + type);
					String titleID = nextObj.getName();
					String score = qsScores[scoreIndex];
					scoreIndex++;
					System.out.println("��:" + score);
					//Type is different
					if(type.compareToIgnoreCase(preType)!=0){
						preType = type;
						 operateStr += "obj.Selection.Range.Text = '" + QUESITONTYPE[Integer.parseInt(type)-1] + "';\n"; //���� 
					     operateStr += "obj.Selection.Font.Bold=false;\n";
					     // ����������������ƶ����λ��
					     operateStr += "obj.Selection.EndKey(5,1);\n";
					     operateStr += "obj.Selection.MoveRight(1,1);\n";
					     
					     
					     pNum = 1;
					}
					
					 operateStr += "obj.Selection.TypeParagraph();"; //��������
		             operateStr += "obj.Selection.Range.Text = '" + pNum + ".(" + score + "��)';\n"; // �����������
		             // ����������������ƶ����λ��
		             operateStr += "obj.Selection.EndKey(5,1);\n";
		             operateStr += "obj.Selection.MoveRight(1,1);\n";
		             
		             //The title exist?
		             Stream s = (Stream)heSession.load(Stream.class, Integer.parseInt(titleID.trim()));
		             if(s.getQuestionTitle()!=null){
		             	// ����ָ�����⵽�ĵ���
		             	operateStr += "document.getElementById('PageOfficeCtrl1').InsertDocumentFromURL('Openstream?id=" + titleID + "&Action=title');\n";
		             }else{
		            	 operateStr += "obj.Selection.TypeParagraph();"; //��������
		             }
		             System.out.println("Stream ID:" + titleID);
		             pNum++;
		             
		             break;
					
				}
			}
			
			
			
		}
		
		if(bHasAnswer){
			
			operateStr += "obj.Selection.TypeParagraph();"; //��������     
			operateStr += "obj.Selection.TypeParagraph();"; //��������     
			operateStr += "obj.Selection.Range.Text = '��';\n"; //���� 
	     	operateStr += "obj.Selection.Font.Bold=false;\n";
	     	// ����������������ƶ����λ��
	     	operateStr += "obj.Selection.EndKey(5,1);\n";
	     	operateStr += "obj.Selection.MoveRight(1,1);\n";
	     	operateStr += "obj.Selection.TypeParagraph();"; //��������     
	     	
			
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
						System.out.println("����:" + type);
						String answerID = nextObj.getAnswer();
						String score = qsScores[scoreIndex];
						scoreIndex++;
						System.out.println("��:" + score);
						//Type is different
						if(type.compareToIgnoreCase(preType)!=0){
							preType = type;
							 operateStr += "obj.Selection.Range.Text = '" + QUESITONTYPE[Integer.parseInt(type)-1] + "';\n"; //���� 
						     operateStr += "obj.Selection.Font.Bold=false;\n";
						     // ����������������ƶ����λ��
						     operateStr += "obj.Selection.EndKey(5,1);\n";
						     operateStr += "obj.Selection.MoveRight(1,1);\n";
						     
						     
						     pNum = 1;
						}
						
						 operateStr += "obj.Selection.TypeParagraph();"; //��������
			             operateStr += "obj.Selection.Range.Text = '" + pNum + ".';\n"; // �����������
			             // ����������������ƶ����λ��
			             operateStr += "obj.Selection.EndKey(5,1);\n";
			             operateStr += "obj.Selection.MoveRight(1,1);\n";
			            
			             //The answer exist?
			             Stream s = (Stream)heSession.load(Stream.class, Integer.parseInt(answerID.trim()));
			             if(s.getQuestionAnswer()!=null){
			             	// ����ָ�����⵽�ĵ���
			             	operateStr += "document.getElementById('PageOfficeCtrl1').InsertDocumentFromURL('Openstream?id=" + answerID + "&Action=answer');\n";
			             }else{
			            	 operateStr += "obj.Selection.TypeParagraph();"; //��������
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
	     // document.getElementById('PageOfficeCtrl1').Document.Application ΢��office VBA����ĸ�Application����
	     operateStr += "var obj = document.getElementById('PageOfficeCtrl1').Document.Application;\n";
	     operateStr += "obj.Selection.EndKey(6);\n"; // ��λ��굽�ĵ�ĩβ
		 operateStr += "\n}\n";
	}
	}
      //******************************׿��PageOffice�����ʹ��*******************************
	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	poCtrl1.setServerPage(request.getContextPath()+"/poserver.do"); //���б���
	//���ز˵���
	//poCtrl1.setMenubar(false);
	poCtrl1.setCustomToolbar(false);
	poCtrl1.setCaption("�����Ծ�");
	

	//���ñ���ҳ��
	String id = "";
	if(wordPaperID==null) wordPaperID="";
	
	//��Word�ļ�
	//poCtrl1.webOpen("template/template1.doc", OpenModeType.docNormalEdit, "����");
	if(bExistWordPaper){
		poCtrl1.webOpen("OpenWordPaperStream?id=" + wordPaperID, OpenModeType.docNormalEdit, "����Ա");
	}else{
		poCtrl1.setJsFunction_AfterDocumentOpened("Create()");
		//�½�word
		poCtrl1.webCreateNew("������", DocumentVersion.Word2003);
	}
	poCtrl1.setSaveFilePage("SavePaper?ID=" + wordPaperID );
	poCtrl1.setJsFunction_AfterDocumentSaved("saveCheck");
	poCtrl1.setTagId("PageOfficeCtrl1"); //���б���	


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>��Word�ĵ��ж�̬���� �Ծ�</title>

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
     			alert('����ɹ���');	
     		} else {
     			alert('����ʧ�ܣ�');
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
