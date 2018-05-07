package ghook.listener.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <PRE>
 * 1. TOKEN 설정  : 생성, 편집
 * 2. REPOSITORY 설정 : 추가 삭제
 * 3. 각각에 대해 Servlet Context 에 저장
 * 4. servlet context 설정 사항 을 파일에 저장
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
