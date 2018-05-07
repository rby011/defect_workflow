package ghook.listener.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <PRE>
 * 1. TOKEN ����  : ����, ����
 * 2. REPOSITORY ���� : �߰� ����
 * 3. ������ ���� Servlet Context �� ����
 * 4. servlet context ���� ���� �� ���Ͽ� ����
 * </PRE>
 */
@WebServlet("/RepositoryConfig")
public class RepositoryConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RepositoryConfig() {
		super();
	}

	public void init() {

	}
	
	
}
