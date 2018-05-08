package team.antelope.fg.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import team.antelope.fg.entity.Person;
import team.antelope.fg.entity.User;
import team.antelope.fg.util.Log4jUtil;

/**
 * 
 * @author ���Ĳ�
 * @time:2018��4��12�� ����9:19:21
 * @Description:TODO ����Ȩ�޹�����
 */
public class AccessFilter implements Filter {

	private FilterConfig filterConfig = null;
	private String excludedPage;
	private String[] excludedPages;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// ��ȡ��ʼ�����ö���
		this.filterConfig = filterConfig;
		excludedPage = filterConfig.getInitParameter("excludedPages");
		if (excludedPage != null && excludedPage.length() > 0) {
			excludedPages = excludedPage.split(",");
		}
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		// 1.ǿת
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		// �����ʾ���� ����֤�û�����URL �Ƿ����������·��
        boolean flag = true;	//��Ҫ����
        for (String page:excludedPages) {
            if (request.getServletPath().equals(page)){
                flag = false;	//����Ҫ����
            }
        }
        Log4jUtil.info("doFilter");

		// 2.�߼�
        // ��֤�û���¼
        if (flag){
        	Log4jUtil.info("��Ҫ��֤");
        	Person person = (Person) request.getSession().getAttribute("person");
        	// ���û�е���ϵͳ������תҳ��
    		if (person == null) {
    			Log4jUtil.info("personΪnull");
    			response.setStatus(401);// ����Ҫ��������֤�� ������Ҫ��¼����ҳ�����������ܷ��ش���Ӧ
    			// δ���룬��ת������ҳ����е���
    			request.getRequestDispatcher("/login.jsp").forward(request, response);
    			// û�е��벻��Ҫ����ִ����һ��filter
    			return;
    		}
        }
		// 3.����, ���������߲��ù��˵ģ�����Լ���
		chain.doFilter(request, response);
	}

}