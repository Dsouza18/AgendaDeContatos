package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update", "/delete", "/report" })
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
		} else if (action.equals("/update")) {
			editarContato(request, response);
		} else if (action.equals("/delete")) {
			removerContato(request, response);
		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);
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

	// Editar contato
	// Novo contato
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Recebimento do Id do contato que será editado
		String idcon = request.getParameter("idcon");
		// setar a variável JavaBeans
		contato.setIdcon(idcon);
		// Executar o método selecionar contato(DAO)
		dao.selecionarContato(contato);
		// Setar os atributos do formulário com o conteudo JavBeans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		// Encaminhar ao documento Editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("Editar.jsp");
		rd.forward(request, response);
	}

	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Setar as variáveis JavaBeans
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// executar o método alterarContato
		dao.alterarContato(contato);
		// Redirecionar para o documento Agenda.jsp com os contatos atualizados
		response.sendRedirect("main");
	}

	// Remover um contato
	protected void removerContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idcon = request.getParameter("idcon");
		// Teste de recebimento
		System.out.println(idcon);
		// setar as variaveis JavaBeans
		contato.setIdcon(idcon);
		// Executar o método deletar contato
		dao.deletarContato(contato);
		// Redirecionar para o documento Agenda.jsp com os contatos atualizados
		response.sendRedirect("main");
	}
	
	//Gerar Relatório em PDF
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Document documento = new Document();
		try {
			//tipo de conteúdo
			response.setContentType("application/pdf");
			//nome do documento
		response.addHeader("Content-Disposition", "inline; filename="+"contatos.pdf");
		//criar o documento
		PdfWriter.getInstance(documento, response.getOutputStream());
		//abrir o documento para gerar o conteúdo
		documento.open();
		documento.add(new Paragraph("Lista de contatos:"));
		documento.add(new Paragraph(" "));
		//criar uma tabela
		PdfPTable tabela = new PdfPTable(3);
		PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
		PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
		PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
		tabela.addCell(col1);
		tabela.addCell(col2);
		tabela.addCell(col3);
		//popular a tabela com os contatos 
		
		ArrayList<JavaBeans> lista = dao.listarContatos();
		for (int i = 0; i < lista.size(); i++) {
			tabela.addCell(lista.get(i).getNome());
			tabela.addCell(lista.get(i).getFone());
			tabela.addCell(lista.get(i).getEmail());
		}
		documento.add(tabela);
		documento.close();
		} catch (Exception e) {
		System.out.println(e);
		documento.close();
		}
	}
}
