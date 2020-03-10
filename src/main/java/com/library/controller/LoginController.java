package com.library.controller;

import com.library.pojo.Admin;
import com.library.pojo.ReaderCard;
import com.library.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
public class LoginController {

    private LoginService loginService;
    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    //如果url路径是/ 或者是 /login.html 的话返回登录页面
    @RequestMapping(value = {"/", "/login.html"})
    public String toLogin(HttpServletRequest request) {
        request.getSession().invalidate();
        return "index";
    }

    /**
     * @param request
     * @return 如果跳出该页面的话重定向返回登录页面 把session 清除
     */
    //退出登录
    @RequestMapping("/logout.html")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login.html";
    }

    //负责处理loginCheck.html请求
    //请求参数会根据参数名称默认契约自动绑定到相应方法的入参中
    @RequestMapping(value = "/api/loginCheck", method = RequestMethod.POST)
    public @ResponseBody
    Object loginCheck(HttpServletRequest request) {
        //表单获取数据
        long id = Long.parseLong(request.getParameter("id"));
        String password = request.getParameter("passwd");
        //开始判断是否有该读者或管理者的匹配信息
     boolean isReader = loginService.hasMatchReader(id, password);
        boolean isAdmin = loginService.hasMatchAdmin(id, password);

        HashMap<String, String> res = new HashMap<>();
        if (isAdmin) {
            Admin admin = new Admin();
            admin.setAdmin_id(id);
            admin.setPassword(password);
            String username = loginService.getAdminUsername(id);
            admin.setUsername(username);
            request.getSession().setAttribute("admin", admin);
            res.put("stateCode", "1");
            res.put("msg", "管理员登录成功");

        }else if(isReader){
            ReaderCard readerCard =loginService.findReaderCardById(id);
            request.getSession().setAttribute("readercard",readerCard);
            res.put("stateCode","2");
            res.put("msg","读者登录成功");
        }
        else {
            res.put("stateCode", "0");
            res.put("msg", "账号或密码错误");
        }

        return res;
    }
    //使用modelandview方法返回指定页面
    @RequestMapping("/admin_main.html")
    public ModelAndView toAdminMain(HttpServletResponse response){

        return new ModelAndView("admin_main");
    }
    @RequestMapping("/reader_main.html")
    public ModelAndView toReaderMain(HttpServletResponse response) {
        return new ModelAndView("reader_main");
    }

    @RequestMapping("/admin_repasswd.html")
    public ModelAndView reAdminPasswd() {
        return new ModelAndView("admin_repasswd");
    }

    //修改管理密码
    @RequestMapping("/admin_repasswd_do")
    public String reAdminPasswdDo(HttpServletRequest request, String oldPasswd, String newPasswd, RedirectAttributes redirectAttributes){
        Admin admin=(Admin) request.getSession().getAttribute("admin");
        long id=admin.getAdmin_id();
        String password=loginService.getAdminPassword(id);
        if (password.equals(oldPasswd)){
            if (loginService.adminRestPassword(id,newPasswd)){
                redirectAttributes.addFlashAttribute("succ","密码修改成功");
                return "redirect:/admin_repasswd.html";
            }else {
                redirectAttributes.addFlashAttribute("error","密码修改失败");
                return "redirect:/admin_repasswd.html";
            }
        }else {
            redirectAttributes.addFlashAttribute("error","旧密码错误");
            return  "redirect:/admin_repasswd.html";
        }

    }

    @RequestMapping("/reader_repasswd.html")
    public ModelAndView reReaderPasswd() {
        return new ModelAndView("reader_repasswd");
    }


    //修改读者密码
    @RequestMapping("/reader_repasswd_do")
    public String reReaderPasswdDo(HttpServletRequest request, String oldPasswd, String newPasswd, RedirectAttributes redirectAttributes){
        ReaderCard readerCard =(ReaderCard) request.getSession().getAttribute("readercard");
        long id=readerCard.getReader_id();
        String password=loginService.getReaderPassword(id);
        if (password.equals(oldPasswd)){
            if (loginService.readerRePassword(id,newPasswd)){
                redirectAttributes.addFlashAttribute("succ","密码修改成功");
                return "redirect:/reader_repasswd.html";
            }else {
                redirectAttributes.addFlashAttribute("error","密码修改失败");
                return "redirect:/reader_repasswd.html";
            }
        }else {
            redirectAttributes.addFlashAttribute("error","旧密码错误");
            return  "redirect:/reader_repasswd.html";
        }

    }


    //配置404错误页面  *
    @RequestMapping("*")
    public String nofind(){
        return "404";
    }
}