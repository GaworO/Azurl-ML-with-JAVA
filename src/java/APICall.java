
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;

/**
 *
 * @author Abhishek Karan
 */
public class APICall extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet APICall</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1 align='center'>Bankruptcy Prediction</h1>");

            String industrial_risk = request.getParameter("Industrial_Risk");
            String management_risk = request.getParameter("Management_Risk");
            String financial_flexibility = request.getParameter("Financial_Flexibility");
            String credibility = request.getParameter("Credibility");
            String operating_risk = request.getParameter("Operating_Risk");
            String competiteveness = request.getParameter("Competiteveness");

            out.println("<div align='center'>");
            out.println("Industrial Risk: " + industrial_risk + "<br/><br/>");
            out.println("Management Risk:" + management_risk + "<br/><br/>");
            out.println("Financial Flexibility: " + financial_flexibility + "<br/><br/>");
            out.println("Credibility: " + credibility + "<br/><br/>");
            out.println("Operating Risk: " + operating_risk + "<br/><br/>");

            AzureML_API aapi = new AzureML_API(industrial_risk, management_risk, financial_flexibility, credibility, operating_risk, competiteveness);
            String result = aapi.getData();
            double ans = Double.parseDouble(result);
            ans = Math.round(Math.abs(ans));

            if (ans == 0.0) {
                result = "Healthy Company";
            } else {
                result = "Bankrupt Company";
            }

            out.println("<h4 align='center'>RESULT: " + result + "</h4>");
            out.print("<a href='index.html'>GO BACK</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(APICall.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(APICall.class.getName()).log(Level.SEVERE, null, ex);
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
