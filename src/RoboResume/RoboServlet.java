package RoboResume;
import java.sql.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class RoboServlet
 */
@WebServlet("/RoboServlet")
public class RoboServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public RoboServlet() {
        super();
        
    }

	
	/*protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}*/

    //, email =?, eduAll = ?, workAll = ?" + " skillAll = ?
	 		
        
	
	@SuppressWarnings("null")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
		 Connection con = null;
	     Statement stmt = null;
	     PreparedStatement pstmt = null;
	     ResultSet rs = null;
	     
	     String name, email,  degree, university, gradD, company, role, tenure, duty1, duty2, skill, ratings;
	     String Fullname, Email, Edu, Exp, Skil;
	     String eduAll = "";
	     String workAll = "";
	     String skillAll = "";
	     HttpSession session = request.getSession();
	     
	     String nextURL = "/Resume.jsp";
	     String[] edu_Achievement;
	     edu_Achievement = new String[10];
	     int i = 1;
	     String[] work_Experience;
	     work_Experience = new String[10];
	     int i1 = 1;
	     String[] skills;
	     skills = new String[20];
	     int i2 = 1;
	     
 try{
	 
			 Class.forName("com.mysql.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://localhost/Resumebuilder?user=root&password=password");
		     
			 
			 //Insert name
			 name = request.getParameter("name");
			
			 //Insert email
			 email = request.getParameter("email");
			 if (!(email.contains("@")&& email.contains("."))){
				  email = "Invalid email. Try again"; 
			 }
		     //Insert Education
			 degree = request.getParameter("degree");
    		 university = request.getParameter("university");
    		 gradD = request.getParameter("gradD");
    		 edu_Achievement[i] = degree +"\n"+university+", "+gradD+"\n";
    		 eduAll = edu_Achievement[i];
			 
		     while (i< 11)
		     {
		    	 String choice = request.getParameter("group1");
		    	 if(choice.equals("Yes"))
		    	 {
		    		 degree = request.getParameter("degree");
		    		 university = request.getParameter("university");
		    		 gradD = request.getParameter("gradD");
		    		 edu_Achievement[i] = degree +"\n"+university+", "+gradD+"\n";
		    		 eduAll = edu_Achievement[i];
		    		 i++;
		    	 }
		    	 else
		    		 if(choice.equals("No"))
		    		 {
		    			 i++;
		    			 break;	
		    		 }
			
		     }
		     //Insert Work Experience
		     company = request.getParameter("degree");
    		 role = request.getParameter("role");
    		 tenure = request.getParameter("tenure");
    		 duty1 = request.getParameter("duty1");
    		 duty2 = request.getParameter("duty2");
    		 work_Experience[i1] = role+"\n"+company+", "+tenure+"\n"+"   Duty-1: "+duty1+"\n"+"   Duty-2: "+duty2 +"\n";
    		 workAll = work_Experience[i1];
		     while (i1<11)
		     {
		    	 String choice1 = request.getParameter("group2");
		    	 if (choice1.equals("yes"))
		    	 {
		    		 company = request.getParameter("degree");
		    		 role = request.getParameter("role");
		    		 tenure = request.getParameter("tenure");
		    		 duty1 = request.getParameter("duty1");
		    		 duty2 = request.getParameter("duty2");
		    		 work_Experience[i1] = role+"\n"+company+", "+tenure+"\n"+"   Duty-1: "+duty1+"\n"+"   Duty-2: "+duty2 +"\n";
		    		 workAll = work_Experience[i1];
		    		 i1++;
		    	 }
		    	 else
		    		 if(choice1.equals("no")) 
		    		 {
		    			 i1++;
		    			 break;
		    		 }
		     }
		     //Insert skills
		     skill = request.getParameter("skill");
    		 ratings = request.getParameter("rating");
    		 skills[i2] = skill+"\n"+ ratings+"\n";
    		 skillAll = skills[i2];
		     while(i2<21)
		     {
		    	 String choice2 = request.getParameter("group3");
		    	 if(choice2.equals("Yeah"))
		    	 {
		    		 skill = request.getParameter("skill");
		    		 ratings = request.getParameter("rating");
		    		 skills[i2] = skill+"\n"+ ratings+"\n";
		    		 skillAll = skills[i2];
		    		 i2++;
		    		 
		    	 }
		    	 else 
		    		 if(choice2.equals("Nah"))
		    		 {
		    			 i2++;
		    			 break;
		    		 }
		     }
		     
		     
		   //Insert into the database
			 pstmt = con.prepareStatement("insert into Resume(name, email, eduAll, workAll, skillAll) values(?,?,?,?,?)");
			 pstmt.setString(1, name);
			 pstmt.setString(2, email);
			 pstmt.setString(3, eduAll);
			 pstmt.setString(4, workAll);
			 pstmt.setString(5, skillAll);
			 pstmt.executeUpdate();
			 
			 //Output into the Web
			 pstmt = con.prepareStatement("select * from Resume where name = ?");
			 pstmt.setString(1, name);
			 rs = pstmt.executeQuery();
			 rs.next();
			 
			 Fullname = rs.getString("name");
			 Email = rs.getString("email");
			 Edu = rs.getString("eduAll");
			 Exp = rs.getString("workAll");
			 Skil = rs.getString("skillAll");
			 
			 
			 session.setAttribute("column1", Fullname);
			 session.setAttribute("column2", Email);
			 session.setAttribute("column3", Edu);
			 session.setAttribute("column4", Exp);
			 session.setAttribute("column5", Skil);
			 
			 
			 
			 
			 
			 
			 
			 getServletContext().getRequestDispatcher(nextURL).forward(request, response);
			 
			 
		//doGet(request, response);
	
		     }
			 
	  
	  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	  finally
	  {
		  
	  }
 }
}
	

