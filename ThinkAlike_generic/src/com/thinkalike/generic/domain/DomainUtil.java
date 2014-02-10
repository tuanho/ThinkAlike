package com.thinkalike.generic.domain;

import com.thinkalike.generic.viewmodel.control.INodeView;
import com.thinkalike.generic.viewmodel.control.UINode;

/**
 * @author Timothy
 *
 */
public class DomainUtil{
	//-- Constants and Enums -----------------------------------
	public static final String TAG = DomainUtil.class.getSimpleName();
	
	//-- Inner Classes and Structures --------------------------
	//-- Delegates and Events ----------------------------------
	//-- Instance and Shared Fields ----------------------------
	private static DomainUtil _this;
	
	//-- Properties --------------------------------------------
	
	//-- Constructors ------------------------------------------
	private DomainUtil() {
	}

	//-- Destructors -------------------------------------------
	//-- Base Class Overrides ----------------------------------
	//-- Public and internal Methods ---------------------------
	public static DomainUtil getInstance() {
		if(_this == null)
			_this = new DomainUtil();
		return _this;
	}
	
	//Node/Element ---
	//public static void dumpNode(String tag, Node baseNode, int level){}
	//public static UINode getUINode(Node node){}
	public static INodeView getNodeView(UINode uiNode){
		return (uiNode==null)? null : uiNode.getView();
	}

	//IView ---
	//public static void linkView(Node parentNode, Node childNode){}
	//public static void linkView(Node parentNode, Node childNode, int index){}
	//public static void unlinkView(Node parentNode, Node childNode){}
	
	//-- Private and Protected Methods -------------------------
	//-- Event Handlers ----------------------------------------
}