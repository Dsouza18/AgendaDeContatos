package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			listarContato(request, response);
		} else {
			response.sendRedirect("index.html");
		}
	}
	// Listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Criando um objeto que irá receber os dados JavaBeans
		ArrayList<JavaBeans> lista = dao.listarContatos();
		// Encaminhar a lista para o documento agenda.jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("Agenda.jsp");
		rd.forward(request, response);
	}
	// Novo contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Setar as variáveis JavaBeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// invocar metodo inserirContato
		dao.inserirContato(contato);
		// Redirecionamento para o documento Agenda.jsp
		response.sendRedirect("main");
	}
	//Editar contato
	// Novo contato
		protected void listarContato(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			//Recebimento do Id do contato que será editado
			String idcon = request.getParameter("idcon");
			//setar a variável JavaBeans
			contato.setIdcon(idcon);
			//Executar o método selecionar contato(DAO)
			dao.selecionarContato(contato);
			//Setar os atributos do formulário com o conteudo JavBeans
			request.setAttribute("idcon", contato.getIdcon());
			request.setAttribute("nome", contato.getNome());
			request.setAttribute("fone", contato.getFone());
			request.setAttribute("email", contato.getEmail());
			//Encaminhar ao documento Editar.jsp
			RequestDispatcher rd = request.getRequestDispatcher("Editar.jsp");
			rd.forward(request, response);
		}
}
