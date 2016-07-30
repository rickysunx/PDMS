
var IE6;
IE6=true;

//弹出类似msn登陆的通知框的代码，在有新的消息到达的时候弹出通知
if(IE6){
	var oPopup = window.createPopup();
	var popTop=30;
	var iTimeout;
	var iMoveSpeed		= 50;	//间隔的毫秒数，越小越快
	var iTableWidth		= 200;	//内容表格的宽度
	var iTableHeight	= 130;	//内容表格的高度
	var iTaskBarHeight	= 25;	//任务栏的高度设置，弹出框会从这个位置开始出现
	var stayTime		= 300000;//弹出窗口以后停留的时间长度
	var bPopupHide		= false;//用于控制隐藏的变量
}

	function closePop(){
		bPopupHide=true;
		oPopup.hide();
		clearTimeout(iTimeout);
		return false;
	}

	function popmsg(msgstr)
	{
		
		bPopupHide = false;
		var oDiv;
		
		var divs = oPopup.document.getElementsByTagName("div");
		if(divs.length>0) {
			oDiv = divs[0];
		} else {
			oDiv=oPopup.document.createElement("div");
		}
		
		oDiv.style.backgroundColor="#ffffff";
		oDiv.style.border="1px solid #6A9924";
		/*
		oDiv.innerHTML = "<table width='"+ (iTableWidth-2) +"' height='"+ (iTableHeight-2) +"' border='0' cellpadding='0' cellspacing='0' style='font-size:12px;'>" +
						"<tr><td height=25 align=center><b>新问题提示</b></td></tr>" +
						"<tr><td align='left' valign='top'>" +
						msgstr+"</td></tr><tr><td align=center  style='cursor: hand;' onclick='parent.closePop();'><font color='#333333'>关闭</font></td></tr></table>";
		*/
		var myhtml="";
		myhtml+="<table style='font-size: 12px;' width='"+(iTableWidth-2)+"' height='"+(iTableHeight-2)+"' border=\"0\" cellpadding=\"2\" cellspacing=\"2\">  ";
		myhtml+="  <tr>                                                                                         ";
		myhtml+="	<td height=\"20\" width='150' bgcolor=\"#E6F4D0\">最新通知</td> ";
		myhtml+="	<td height=\"20\" width='50' bgcolor=\"#E6F4D0\" align='right'><span style='cursor: hand;' onclick='parent.closePop();'>关闭</span></td> ";
		myhtml+="  </tr>                                                                                        ";
		myhtml+="  <tr>";
		myhtml+="	<td colspan='2' valign='top'>";
		myhtml+=msgstr;
		myhtml+="	</td>";
		myhtml+="  </tr>";
		
		myhtml+="</table>";
		
		oDiv.innerHTML = myhtml;
		
		
		/*oDiv.oncontextmenu = function(){
			bPopupHide=true;
			oPopup.hide();
			clearTimeout(iTimeout);
			return false;
		}*/
		oPopup.document.body.insertBefore(oDiv);
		popshow();
	}
	
	function popshow(iStay)
	{
		try {
			if(bPopupHide){
				return false;
			}
			var bFullDisplay=popTop-iTaskBarHeight>=iTableHeight;
			if (bFullDisplay && arguments.length>0){
				oPopup.show(screen.width-iTableWidth-20, screen.height-popTop, iTableWidth, iTableHeight);
				if(iStay>0)iTimeout=setTimeout("popshow("+ (iStay-iMoveSpeed) + ");",iMoveSpeed)
				else pophide();
			}
			else if(bFullDisplay){
				oPopup.show(screen.width-iTableWidth-20, screen.height-popTop, iTableWidth, iTableHeight);
				iTimeout=setTimeout("popshow(" + stayTime +");",iMoveSpeed);
			}
			else{
				oPopup.show(screen.width-iTableWidth-20, screen.height-popTop, iTableWidth, popTop-iTaskBarHeight);
				popTop+=10;
				iTimeout=setTimeout("popshow();",iMoveSpeed);
			}
		} catch (e) {
		}
		
	}
	function pophide(){
		if(bPopupHide){
			return false;
		}
		var bNullDisplay	= popTop<=30;
		if (bNullDisplay){
			oPopup.hide();
		}
		else{
			oPopup.show(screen.width-iTableWidth-20, screen.height-popTop, iTableWidth, popTop-iTaskBarHeight);
			popTop-=10;
			iTimeout=setTimeout("pophide();",iMoveSpeed);
		}
	}
	