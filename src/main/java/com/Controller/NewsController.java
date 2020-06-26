package com.Controller;

import com.Entity.News;
import com.Service.NewsService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class NewsController {

    @RequestMapping("/Add")
    protected void Add(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

        request.setCharacterEncoding("utf-8");
        response.sendRedirect("AddNews.jsp");
    }


    @RequestMapping("/AddNews")
    protected void AddNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        News news = new News();
        news.setCategory(request.getParameter("category"));
        news.setTitle(request.getParameter("title"));
        news.setContents(request.getParameter("contents"));
        news.setAuthor(request.getParameter("author"));
       /* String newsDate=request.getParameter("newsdate");
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd"); //加上时间
        Date date= null;
        try {
            date = sDateFormat.parse(newsDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        news.setNewsdate(Date.valueOf(request.getParameter("newsdate")));
        NewsService service = new NewsService();
        service.AddNews(news);
        request.getRequestDispatcher("ShowNewsListServlet").forward(request, response);
    }

    @RequestMapping("/DeleteNews")
    protected void DeleteNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        int newsid=Integer.valueOf( request.getParameter("newsid"));
        NewsService service= new NewsService();
        service.DeleteNews(newsid);
        request.getRequestDispatcher("ShowNewsListServlet").forward(request,response);
    }

    @RequestMapping("/ViewNews")
    protected void ViewNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        response.setContentType(("text/html;charset=UTF-8"));
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("newsid"));
        System.out.println(id);
        NewsService service = new NewsService();
        try
        {
            News news = service.GetNews(id);
            request.setAttribute("news",news);
            request.getRequestDispatcher("ViewNews.jsp").forward(request,response);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    @RequestMapping("/ShowNews")
    protected void ShowNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        NewsService newsService = new NewsService();
        try{
            List<News> listNews  = newsService.QueryNews();
            request.setAttribute("listNews",listNews);
            request.getRequestDispatcher("ShowNewsList.jsp").forward(request,response);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @RequestMapping("/SaveNews")
    protected void SaveNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        News news=new News ();
        news.setNewsid(Integer.valueOf( request.getParameter("newsid")));
        news.setCategory(request.getParameter("category"));
        news.setTitle(request.getParameter("title"));
        news.setAuthor(request.getParameter("author"));
        String newsDate=request.getParameter("newsdate");
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd"); //加上时间
        java.util.Date date= null;
        try {
            date = sDateFormat.parse(newsDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        news.setNewsdate(date);
        news.setContents(request.getParameter("contents"));
        NewsService service=new NewsService();
        System.out.println(service.UpdateNews(news));
        request.getRequestDispatcher("ShowNewsListServlet").forward(request,response);

    }

    @RequestMapping("/EditNews")
    protected void EditNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        request.setCharacterEncoding("UTF-8");
        int newsid=Integer.valueOf( request.getParameter("newsid"));
        System.out.println("newsID:"+newsid);
        NewsService service= new NewsService();
        try {
            News news=service.GetNews(newsid);
            request.setAttribute("news", news);
            request.getRequestDispatcher("EditNews.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
