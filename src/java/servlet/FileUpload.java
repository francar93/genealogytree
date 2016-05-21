/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import utilita.FreeMarker;
import utilita.Message;

/**
 *
 * @author moira
 */
public class FileUpload extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("user_logged", session.getAttribute("user_logged"));

            FreeMarker.process("foto.html", data, response, getServletContext());
        } else {
            response.sendRedirect("login");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        /*
        HttpSession session = request.getSession(false);
        if (session != null) {    
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        
        if(!isMultipart){
            try{
                
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("web/template/img");
        factory.setRepository(repository);
           
        ServletFileUpload upload = new ServletFileUpload(factory);

        List<FileItem> items = upload.parseRequest(request);

        Iterator<FileItem> iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = iter.next();

                if (!item.isFormField()) {
                String fieldName = item.getFieldName();
                File fileName = new File(item.getName());
                
                item.write(fileName);
                
                }else{
                Message message=new Message("pho_slt", true);
                response.sendRedirect("profilo?msg=" + URLEncoder.encode(message.getCode(), "UTF-8"));
                
    }
}
        
 }          catch (FileUploadException ex) {          
                Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
            }          
}else{
        
            
        response.sendRedirect("profilo");
        }
        }else{
        response.sendRedirect("login?msg=" + URLEncoder.encode("log", "UTF-8"));
        } */
        HttpSession session = request.getSession(false);

        if (session != null) {
            Message message = null;

            boolean isMultipart = ServletFileUpload.isMultipartContent(request);

            if (isMultipart) {
                try {
                    DiskFileItemFactory factory = new DiskFileItemFactory();

                    ServletFileUpload upload = new ServletFileUpload(factory);

                    List items = upload.parseRequest(request);
                    Iterator itr = items.iterator();

                    while (itr.hasNext()) {
                        FileItem item = (FileItem) itr.next();

                        if (!item.isFormField()) {
                            File fullFile = new File(item.getName());
                            //String path = session.getServletContext().getContextPath();
                            File savedFile = new File(session.getServletContext().getRealPath("/template/img/profilo/").replace("build\\", "") + File.separator + fullFile.getName());
                            item.write(savedFile);
                            message = new Message("pho_ok", false);

                        }
                    }

                } catch (FileUploadException ex) {
                    message = new Message("srv", true);
                    //response.sendRedirect("profilo?msg=" + URLEncoder.encode(message.getCode(), "UTF-8"));

                } catch (Exception ex) {
                    message = new Message("pho_slt", true);
                    //response.sendRedirect("profilo?msg=" + URLEncoder.encode(message.getCode(), "UTF-8"));
                }

            } else {
                message = new Message("pho_err", true);
            }

            response.sendRedirect("profilo?msg=" + URLEncoder.encode(message.getCode(), "UTF-8"));
        } else {
            response.sendRedirect("login?msg=" + URLEncoder.encode("log", "UTF-8"));
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>          

}
