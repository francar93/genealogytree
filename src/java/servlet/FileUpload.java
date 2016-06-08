package servlet;

import classi.utente;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
            String filename =("template/img/profilo/");
            data.put("foto", filename);

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
                            
                            //String path = session.getServletContext().getContextPath();
                            utente user_logged = (utente)session.getAttribute("user_logged");
                            String id = user_logged.getId();
                            
                            File savedFile = new File(session.getServletContext().getRealPath("/template/img/profilo/").replace("build\\", "") + File.separator + id + ".jpg");
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
        return "Servlet per il caricamento delle foto";
    }          

}
