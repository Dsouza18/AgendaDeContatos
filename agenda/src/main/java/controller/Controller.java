package Controller;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.DAO;
import Model.JavaBeans;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


class ListContatosCommand implements Command {
    private DAO dao;
    public ListContatosCommand(DAO dao, JavaBeans contato) {

        this.dao = dao;
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<JavaBeans> lista = dao.listarContatos();
        request.setAttribute("contatos", lista);
        RequestDispatcher rd = request.getRequestDispatcher("Agenda.jsp");
        rd.forward(request, response);
    }
}

class NovoContatoCommand implements Command {
    private DAO dao;
    private JavaBeans contato;

    public NovoContatoCommand(DAO dao, JavaBeans contato) {
        this.dao = dao;
        this.contato = contato;
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        contato.setNome(request.getParameter("nome"));
        contato.setFone(request.getParameter("fone"));
        contato.setEmail(request.getParameter("email"));
        dao.inserirContato(contato);
        response.sendRedirect("main");
    }
}

class AlterarContatoCommand implements Command {
    private DAO dao;
    private JavaBeans contato;
    public AlterarContatoCommand(DAO dao, JavaBeans contato) {
        this.dao = dao;
        this.contato = contato;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        contato.setNome(request.getParameter("nome"));
        contato.setFone(request.getParameter("fone"));
        contato.setEmail(request.getParameter("email"));
        dao.alterarContato(contato);
        response.sendRedirect("main");
    }
}
class DeletarContatoCommand implements Command {
    private DAO dao;
    private JavaBeans contato;
    public DeletarContatoCommand(DAO dao, JavaBeans contato) {
        this.dao = dao;
        this.contato = contato;
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        contato.setIdcon(request.getParameter("idcon"));
        dao.deletarContato(contato);
        response.sendRedirect("main");
    }
}

class SelecionarContatoCommand implements Command {
    private DAO dao;
    private JavaBeans contato;
    public SelecionarContatoCommand(DAO dao, JavaBeans contato) {
        this.dao = dao;
        this.contato = contato;
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        contato.setIdcon(request.getParameter("idcon"));
        dao.selecionarContato(contato);
        response.sendRedirect("main");
    }
}
@WebServlet(urlPatterns = {"/Controller", "/main", "/insert", "/select", "/update", "/delete", "/report"})
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DAO dao;
    private JavaBeans contato;
    private final Map<String, Command> commandMap = new HashMap<>();

    public Controller() {
        this.dao = DAO.getInstance();  // Obtenha a instância única da DAO
        this.contato = new JavaBeans();

        commandMap.put("/main", new ListContatosCommand(dao, contato));
        commandMap.put("/insert", new NovoContatoCommand(dao, contato));
        commandMap.put("/select", new SelecionarContatoCommand(dao, contato));
        commandMap.put("/update", new AlterarContatoCommand(dao, contato));
        commandMap.put("/delete", new DeletarContatoCommand(dao, contato));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getServletPath();
        System.out.println(acao);
        Command command = commandMap.get(acao);
        if (command != null) {
            command.execute(request, response);
        } else {
            response.sendRedirect("index.html");
        }
    }
}


