package kr.co.itcen.mysite.action.board;

import kr.co.itcen.web.mvc.Action;
import kr.co.itcen.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("writeform".equals(actionName)) {
			action=new WriteFormAction();
		}else if("write".equals(actionName)) {
			action = new WriteAction();
		}else if("viewform".equals(actionName)) {
			action= new ViewFormAction();
		}else if("modifyform".equals(actionName)){
			action = new ModifyFormAction();
		}else if("modify".equals(actionName)){
			action = new ModifyAction();
		}else if("delete".equals(actionName)){
			action= new DeleteAction();
		}else if("request".equals(actionName)){
			action=new RequestFormAction();
		}else {
			//default(list)
			action = new ListAction();
		}
		return action;
	}

}
