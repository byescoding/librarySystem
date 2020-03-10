package com.library.controller;

import com.library.pojo.ReaderCard;
import com.library.service.BookService;
import com.library.service.LendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LendController {
    @Autowired
    private LendService lendService;
    @Autowired
    private BookService bookService;

    //删除图书
    @RequestMapping("/deletebook.html")
    public String deleteBook(HttpServletRequest request, RedirectAttributes redirectAttributes){
        long bookId=Long.parseLong(request.getParameter("bookId"));
        if (bookService.deleteBook(bookId)){
            redirectAttributes.addFlashAttribute("succ","书本删除成功");

        }else {
            redirectAttributes.addFlashAttribute("error","书本删除失败");
        }
        return "redirect:/admin_books.html";
    }

    //展示借出书本记录列表
    @RequestMapping("/lendlist.html")
    public ModelAndView lendlist(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView("admin_lend_list");
        modelAndView.addObject("list",lendService.lendList());
        return modelAndView;
    }

    //我的借还记录
    @RequestMapping("/mylend.html")
    public ModelAndView mylend(HttpServletRequest request){
        ReaderCard readerCard=(ReaderCard)request.getSession().getAttribute("readercard");
        ModelAndView modelAndView=new ModelAndView("reader_lend_list");
        modelAndView.addObject("list",lendService.myLendList(readerCard.getReader_id()));
        return  modelAndView;
    }

    //删除借书记录
    @RequestMapping("/deletelend.html")
    public String deletelend(HttpServletRequest request,RedirectAttributes redirectAttributes){
        long serNum=Long.parseLong(request.getParameter("serNum"));
        if (lendService.deleteLend(serNum)>0){
            redirectAttributes.addFlashAttribute("succ","删除成功");

        }else {
            redirectAttributes.addFlashAttribute("error","删除失败");
        }
        return "redirect:/lendlist.html";
    }

    //借阅书本
    @RequestMapping("/lendbook.html")
    public String booklend(HttpServletRequest request,RedirectAttributes redirectAttributes){
        long bookId=Long.parseLong(request.getParameter("bookId"));
        long readerId=((ReaderCard)request.getSession().getAttribute("readercard")).getReader_id();
        if (lendService.lendBook(bookId,readerId)){
            redirectAttributes.addFlashAttribute("succ","借阅成功");

        }else {
            redirectAttributes.addFlashAttribute("error","借阅失败");
        }
        return "redirect:/reader_books.html";
    }
//归还书本
    @RequestMapping("/returnbook.html")
    public String bookReturn(HttpServletRequest request,RedirectAttributes redirectAttributes){
        long bookId=Long.parseLong(request.getParameter("bookId"));
         long readerId=((ReaderCard)request.getSession().getAttribute("readercard")).getReader_id();
         if (lendService.returnBook(bookId,readerId)){
             redirectAttributes.addFlashAttribute("succ","图书归还成功");

         }else {
             redirectAttributes.addFlashAttribute("error","图书归还失败");
         }
         return "redirect:/reader_books.html";
    }
}
