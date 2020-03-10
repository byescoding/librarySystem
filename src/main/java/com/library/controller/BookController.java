package com.library.controller;

import com.library.pojo.Book;
import com.library.pojo.Lend;
import com.library.pojo.ReaderCard;
import com.library.service.BookService;
import com.library.service.LendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private LendService lendService;

    //时间显示格式转换
    private Date getDate(String pubstr){
        try {
            SimpleDateFormat df=new SimpleDateFormat("yyyy-mm-dd");
            return df.parse(pubstr);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

//书籍查询页面
    @RequestMapping("/querybook.html")
    public ModelAndView queryBookDo(String searchWord){
        if (bookService.matchBook(searchWord)){
            ArrayList<Book> books =bookService.queryBook(searchWord);
            //把cooks 的数据携带跳转到admin_books 页面
            ModelAndView modelAndView=new ModelAndView("admin_books");
            modelAndView.addObject("books",books);
            return modelAndView;
        }else {
            return new ModelAndView("admin_books","error","没有匹配的书");
        }
    }

    //读者查询

@RequestMapping("/reader_querybook_do.html")
    public ModelAndView readerQueryBookDao(String searchWord){
        if (bookService.matchBook(searchWord)){
            ArrayList<Book> books=bookService.queryBook(searchWord);
            ModelAndView modelAndView=new ModelAndView("reader_books");
            modelAndView.addObject("books",books);
            return modelAndView;
        }else {
            return new  ModelAndView("reader_books","error","没有匹配的书");
        }
}

@RequestMapping("/admin_books.html")
public ModelAndView adminBooks(){
        ArrayList<Book> books=bookService.getAllBooks();
        ModelAndView modelAndView=new ModelAndView("admin_books");
         modelAndView.addObject("books",books);
      return modelAndView;
}


@RequestMapping("/book_add.html")
public ModelAndView addBook(){
        return new ModelAndView("admin_book_add");
}

//添加书本
@RequestMapping("/book_add_do.html")
    public String addBookDo(@RequestParam(value = "pubstr" ) String pubstr , Book book, RedirectAttributes redirectAttributes){
       //转换时间格式
        book.setPub_date(getDate(pubstr));
        if (bookService.addBook(book)){
            redirectAttributes.addFlashAttribute("succ","书本添加成功");
        }else {
            redirectAttributes.addFlashAttribute("error","书本添加失败");
        }
    return "redirect:/admin_books.html";
}

//修改书本信息
@RequestMapping("/updatebook.html")
    public ModelAndView bookEdit(HttpServletRequest request){
        long bookId=Long.parseLong(request.getParameter("bookId"));
        Book book=bookService.getBook(bookId);
        ModelAndView modelAndView=new ModelAndView("admin_book_edit");
        modelAndView.addObject("detail",book);
        return modelAndView;
}

//编辑书本
    @RequestMapping("/book_edit_do.html")
    public String bookEditDo(@RequestParam(value = "pubstr") String pubstr,Book book,RedirectAttributes redirectAttributes){
        book.setPub_date(getDate(pubstr));
        if (bookService.editBook(book)){
            redirectAttributes.addFlashAttribute("succ","图书修改成功");

        }else {
            redirectAttributes.addFlashAttribute("error","图书修改失败");
        }
        return "redirect:/admin_books.html";
    }
//管理员查看书本信息
    @RequestMapping("/admin_book_detail.html")
    public ModelAndView adminBookDetail(HttpServletRequest request) {
        long bookId = Long.parseLong(request.getParameter("bookId"));
        Book book = bookService.getBook(bookId);
        ModelAndView modelAndView = new ModelAndView("admin_book_detail");
        modelAndView.addObject("detail", book);
        return modelAndView;
    }


    //展示书本信息
    @RequestMapping("/reader_book_detail.html")
    public ModelAndView readerBookDetail(HttpServletRequest request){
        long bookId=Long.parseLong(request.getParameter("bookId"));
        Book book=bookService.getBook(bookId);
        ModelAndView modelAndView=new ModelAndView("admin_book_detail");
        modelAndView.addObject("detail",book);
        return modelAndView;

    }

    //加载admin_header.html  视图
    @RequestMapping("/admin_header.html")
    public ModelAndView admin_header(){
        return new ModelAndView(("admin_header"));
    }
    //加载reader_header.html视图
    @RequestMapping("/reader_header.html")
    public ModelAndView reader_header(){
        return new ModelAndView("reader_header");
    }

    //书本信息展示页面
    @RequestMapping("/reader_books.html")
    public ModelAndView readerBooks(HttpServletRequest request){
//        书本信息列表
        ArrayList<Book> books=bookService.getAllBooks();
        ReaderCard readerCard=(ReaderCard)request.getSession().getAttribute("readercard");
//        借出信息列表
        ArrayList<Lend> myAllLendList=lendService.myLendList(readerCard.getReader_id());
        ArrayList<Long> myLendList=new ArrayList<>();
        for(Lend lend:myAllLendList){
            //判断是否已经归还
            if (lend.getBack_date()==null){
                myLendList.add(lend.getBook_id());
            }
        }

        ModelAndView modelAndView=new ModelAndView("reader_books");
        modelAndView.addObject("books",books);
        modelAndView.addObject("myLendList",myLendList);
        return modelAndView;

    }

}
