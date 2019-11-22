package haanh.utils;

/**
 *
 * @author HaAnh
 */
public class UrlConstants {
    
    //Servlet
    public static final String SERVLET_LOGIN = "ServletLogin";
    //Admin Servlets
//    public static final String SERVLET_ADMIN_PAGE_INSERT_BOOK = "/pageInsertBook"; --> 404
    public static final String SERVLET_ADMIN_GET_ALL_BOOKS = "getAllBooks";
    public static final String SERVLET_ADMIN_VIEW_BOOK_DETAIL = "viewBookDetail";
    public static final String SERVLET_ADMIN_PAGE_INSERT_BOOK = "pageInsertBook";
    
    //Pages
    public static final String PAGE_LOGIN = "login.jsp";
    public static final String PAGE_BACKGROUND = "background.jsp";
    public static final String PAGE_HOME = "index.jsp";
    //Admin Pages
    public static final String PAGE_ADMIN_BACKGROUND = "/admin-background.jsp";
//    public static final String PAGE_ADMIN_BACKGROUND1 = "admin-background.jsp"; --> 404: vì admin/admin-background.jsp
    // get the last '/' + url 
    public static final String PAGE_ADMIN_HOME = "admin-index.jsp";
//    public static final String PAGE_ADMIN_HOME = "/admin-index.jsp";
//    public static final String PAGE_ADMIN_INSERT_BOOK = "admin-insert-book.jsp"; -> 404
    public static final String PAGE_ADMIN_INSERT_BOOK = "/admin-insert-book.jsp";
    public static final String PAGE_ADMIN_BOOK_DETAIL = "/admin-book-detail.jsp";
    
    //Error Pages
    public static final String PAGE_ERROR = "error.html";
    public static final String PAGE_404 = "page-404.html";
    
    
    //Attributes
    public static final String ATTR_INCLUDED_PAGE = "includedPage";    
    public static final String ATTR_CURRENT_USER = "currentUser";
    public static final String ATTR_PARAMS = "params";
    public static final String ATTR_CATEGORIES = "categories";
    public static final String ATTR_AUTHORS = "authors";
    public static final String ATTR_BOOKS = "books";
    public static final String ATTR_BOOK = "book";
    //Attributes Message and Error
    public static final String ATTR_MESSAGE = "message";
    public static final String ATTR_MESSAGE_BOOK = "messageBook";
    public static final String ATTR_MESSAGE_PHOTO = "messagePhoto";
    public static final String ATTR_ERROR = "error";
}
