package service;

import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import dao.MatriculaDAO;
import model.Matricula;
import spark.Request;
import spark.Response;

public class MatriculaService {

    private MatriculaDAO matriculaDAO = new MatriculaDAO();
    private String form;
    private final int FORM_INSERT = 1;
    private final int FORM_DETAIL = 2;
    private final int FORM_UPDATE = 3;
    private final int FORM_ORDERBY_MATRICULA = 1;
    private final int FORM_ORDERBY_NOME = 2;
    private final int FORM_ORDERBY_IDADE = 3;

    public MatriculaService() {
        makeForm();
    }

    public void makeForm() {
        makeForm(FORM_INSERT, new Matricula(), FORM_ORDERBY_NOME);
    }

    public void makeForm(int orderBy) {
        makeForm(FORM_INSERT, new Matricula(), orderBy);
    }

    public void makeForm(int tipo, Matricula matricula, int orderBy) {
        String nomeArquivo = "form.html";
        form = "";
        try {
            Scanner entrada = new Scanner(new File(nomeArquivo));
            while (entrada.hasNext()) {
                form += (entrada.nextLine() + "\n");
            }
            entrada.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String umaMatricula = "";
        if (tipo != FORM_INSERT) {
            umaMatricula += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umaMatricula += "\t\t<tr>";
            umaMatricula += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/matricula/list/1\">Nova Matricula</a></b></font></td>";
            umaMatricula += "\t\t</tr>";
            umaMatricula += "\t</table>";
            umaMatricula += "\t<br>";
        }

        if (tipo == FORM_INSERT || tipo == FORM_UPDATE) {
            String action = "/matricula/";
            String name, descricao, buttonLabel;
            if (tipo == FORM_INSERT) {
                action += "insert";
                name = "Inserir Matricula";
                descricao = "leite, pão, ...";
                buttonLabel = "Inserir";
            } else {
                action += "update/" + matricula.getMatricula();
                name = "Atualizar Matricula (ID " + matricula.getMatricula() + ")";
                descricao = matricula.getNome();
                buttonLabel = "Atualizar";
            }
            umaMatricula += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
            umaMatricula += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umaMatricula += "\t\t<tr>";
            umaMatricula += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
            umaMatricula += "\t\t</tr>";
            umaMatricula += "\t\t<tr>";
            umaMatricula += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
            umaMatricula += "\t\t</tr>";
            umaMatricula += "\t\t<tr>";
            umaMatricula += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\"" + matricula.getNome() + "\"></td>";
            umaMatricula += "\t\t\t<td>Idade: <input class=\"input--register\" type=\"text\" name=\"idade\" value=\"" + matricula.getIdade() + "\"></td>";
            umaMatricula += "\t\t\t<td>Curso: <input class=\"input--register\" type=\"text\" name=\"curso\" value=\"" + matricula.getCurso() + "\"></td>";
            umaMatricula += "\t\t</tr>";
            umaMatricula += "\t\t<tr>";
            //umaMatricula += "\t\t\t<td>&nbsp;Data de matrícula: <input class=\"input--register\" type=\"text\" name=\"dataMatricula\" value=\"" + matricula.getDataMatricula().toString() + "\"></td>";
            //umaMatricula += "\t\t\t<td>Data de validade: <input class=\"input--register\" type=\"text\" name=\"dataValidade\" value=\"" + matricula.getDataValidade().toString() + "\"></td>";
            umaMatricula += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\"" + buttonLabel + "\" class=\"input--main__style input--button\"></td>";
            umaMatricula += "\t\t</tr>";
            umaMatricula += "\t</table>";
            umaMatricula += "\t</form>";
        } else if (tipo == FORM_DETAIL) {
            umaMatricula += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umaMatricula += "\t\t<tr>";
            umaMatricula += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Matricula (ID " + matricula.getMatricula() + ")</b></font></td>";
            umaMatricula += "\t\t</tr>";
            umaMatricula += "\t\t<tr>";
            umaMatricula += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
            umaMatricula += "\t\t</tr>";
            umaMatricula += "\t\t<tr>";
            umaMatricula += "\t\t\t<td>&nbsp;Nome: " + matricula.getNome() + "</td>";
            umaMatricula += "\t\t\t<td>Idade: " + matricula.getIdade() + "</td>";
            umaMatricula += "\t\t\t<td>Curso: " + matricula.getCurso() + "</td>";
            umaMatricula += "\t\t</tr>";
            umaMatricula += "\t\t<tr>";
            //umaMatricula += "\t\t\t<td>&nbsp;Data de matrícula: " + matricula.getDataMatricula().toString() + "</td>";
            //umaMatricula += "\t\t\t<td>Data de validade: " + matricula.getDataValidade().toString() + "</td>";
            umaMatricula += "\t\t\t<td>&nbsp;</td>";
            umaMatricula += "\t\t</tr>";
            umaMatricula += "\t</table>";
        } else {
            System.out.println("ERRO! Tipo não identificado " + tipo);
        }
        form = form.replaceFirst("<UM-MATRICULA>", umaMatricula);

        String lista = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
        lista += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Matriculas</b></font></td></tr>\n" +
                "\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
                "\n<tr>\n" +
                "\t<td><a href=\"/matricula/list/" + FORM_ORDERBY_MATRICULA + "\"><b>ID</b></a></td>\n" +
                "\t<td><a href=\"/matricula/list/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
                "\t<td><a href=\"/matricula/list/" + FORM_ORDERBY_IDADE + "\"><b>Idade</b></a></td>\n" +
                "\t<td><a href=\"/matricula/list/" + FORM_ORDERBY_IDADE + "\"><b>Curso</b></a></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
                "</tr>\n";

        List<Matricula> matriculas;
        if (orderBy == FORM_ORDERBY_MATRICULA) {
            matriculas = matriculaDAO.getOrderByMatricula();
        } else if (orderBy == FORM_ORDERBY_NOME) {
            matriculas = matriculaDAO.getOrderByNome();
        } else if (orderBy == FORM_ORDERBY_IDADE) {
            matriculas = matriculaDAO.getOrderByIdade();
        } else {
            matriculas = matriculaDAO.get();
        }

        int i = 0;
        String bgcolor = "";
        for (Matricula m : matriculas) {
            bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
            lista += "\n<tr bgcolor=\"" + bgcolor + "\">\n" +
                    "\t<td>" + m.getMatricula() + "</td>\n" +
                    "\t<td>" + m.getNome() + "</td>\n" +
                    "\t<td>" + m.getIdade() + "</td>\n" +
                    "\t<td>" + m.getCurso() + "</td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"/matricula/" + m.getMatricula() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"/matricula/update/" + m.getMatricula() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteMatricula('" + m.getMatricula() + "', '" + m.getNome() + "', '" + m.getIdade() + "', '" + m.getCurso() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "</tr>\n";
        }
        lista += "</table>";
        form = form.replaceFirst("<LISTAR-MATRICULA>", lista);
    }

    public Object insert(Request request, Response response) {
        String nome = request.queryParams("nome");
        int idade = Integer.parseInt(request.queryParams("idade"));
        String curso = request.queryParams("curso");

        String resp = "";

        Matricula matricula = new Matricula(nome, idade, curso, -1);

        if (matriculaDAO.insert(matricula)) {
            resp = "Matricula (" + nome + ") inserida!";
            response.status(201); // 201 Created
        } else {
            resp = "Matricula (" + nome + ") não inserida!";
            response.status(404); // 404 Not found
        }

        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

    public Object get(Request request, Response response) {
        int matricula = Integer.parseInt(request.params(":matricula"));
        Matricula matriculaz = matriculaDAO.get(matricula);

        if (matriculaz != null) {
            response.status(200); // success
            makeForm(FORM_DETAIL, matriculaz, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Matricula " + matricula + " não encontrada.";
            makeForm();
            form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
        }

        return form;
    }

    public Object getToUpdate(Request request, Response response) {
        int id = Integer.parseInt(request.params(":matricula"));
        Matricula matricula = matriculaDAO.get(id);

        if (matricula != null) {
            response.status(200); // success
            makeForm(FORM_UPDATE, matricula, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Matricula " + id + " não encontrada.";
            makeForm();
            form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
        }

        return form;
    }

    public Object getAll(Request request, Response response) {
        int orderBy = Integer.parseInt(request.params(":orderby"));
        makeForm(orderBy);
        response.header("Content-Type", "text/html");
        response.header("Content-Encoding", "UTF-8");
        return form;
    }

    public Object update(Request request, Response response) {
        int matricula = Integer.parseInt(request.params(":matricula"));
        Matricula matriculaz = matriculaDAO.get(matricula);
        String resp = "";

        if (matriculaz != null) {
            matriculaz.setNome(request.queryParams("nome"));
            matriculaz.setIdade(Integer.parseInt(request.queryParams("idade")));
            matriculaz.setCurso(request.queryParams("curso"));           
            matriculaDAO.update(matriculaz);
            response.status(200); // success
            resp = "Matricula (ID " + matriculaz.getMatricula() + ") atualizada!";
        } else {
            response.status(404); // 404 Not found
            resp = "Matricula (ID \" + matricula.getId() + \") não encontrada!";
        }
        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

    public Object delete(Request request, Response response) {
        int matriculaId = Integer.parseInt(request.params(":matricula"));
        Matricula matriculaz = matriculaDAO.get(matriculaId);
        String resp = "";

        if (matriculaz != null) {
            matriculaDAO.delete(matriculaz.getMatricula());
            response.status(200); // success
            resp = "Matricula (" + matriculaId + ") excluída!";
        } else {
            response.status(404); // 404 Not found
            resp = "Matricula (" + matriculaId + ") não encontrada!";
        }
        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

}