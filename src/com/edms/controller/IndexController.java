package com.edms.controller;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.service.spi.InjectService;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.edms.dwr.XmppChatClass;
import com.edms.model.LoginModel;


@Controller
public class IndexController {
	
	@Autowired private XmppChatClass xmppChatClass;
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String getIndex(ModelMap map){
		System.out.println("in login..........");
		map.addAttribute("loginUser", new LoginModel());
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String performLogin(@ModelAttribute(value="loginUser")LoginModel loginUser, ModelMap map){
		System.out.println("in login..........");
		System.out.println("userid="+loginUser.getUserid());
		System.out.println("password="+loginUser.getPassword());
		xmppChatClass.createConnection(true,"mail.speedymx.com",5280, "/http-bind", "speedymx.com");
		xmppChatClass.getRoster();
		xmppChatClass.performLogin(loginUser.getUserid(), loginUser.getPassword());
		//xmppChatService.connectAndLogin(true,"mail.speedymx.com",5280, "/http-bind", "speedymx.com", loginUser.getUserid(), loginUser.getPassword());
		return "redirect:/userChat";
	}
	
	@RequestMapping(value="/userChat", method=RequestMethod.GET)
	public String getChat(ModelMap map){
		System.out.println("in userChat..........");
		return "userChat";
	}
	
	@RequestMapping(value = "/sendChatMessage", method = RequestMethod.GET)
    public @ResponseBody String check(@RequestParam Map<String,String> requestParams)
    {   
		String message=requestParams.get("message");
		String buddyJID=requestParams.get("buddyJID");
		xmppChatClass.sendMessage(message, buddyJID);
		return "success";
    }
	
	/*@RequestMapping(value="/login", method=RequestMethod.POST)
	public String performLogin(@ModelAttribute(value="loginUser")LoginModel loginUser, ModelMap map, HttpSession session, HttpServletRequest request){
		System.out.println("in login..........");
		System.out.println("userid="+loginUser.getUserid());
		System.out.println("password="+loginUser.getPassword());
		session.setAttribute("jid", loginUser.getUserid());
		//UUID uid=UUID.randomUUID();
		Random rand=new Random();
		int uid=rand.nextInt(100);
		session.setAttribute("sid", uid);
		session.setAttribute("rid", uid);
		return "redirect:/converseChat";
	}
	
	@RequestMapping(value="/converseChat", method=RequestMethod.GET)
	public String getChat(ModelMap map){
		System.out.println("in converseChat..........");
		return "converseChat";
	}*/

}