package app;

import java.util.List;
import java.util.Scanner;

import dao.MatriculaDAO;
import model.Matricula;

public class Aplicacao {

    public static void main(String[] args) throws Exception {
        MatriculaDAO matriculaDAO = new MatriculaDAO();
        Scanner scanner = new Scanner(System.in);

        int opcao;
        do {
            System.out.println("\n\n==== Menu ====");
            System.out.println("1) Inserir");
            System.out.println("2) Listar");
            System.out.println("3) Atualizar");
            System.out.println("4) Excluir");
            System.out.println("5) Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("\n==== Inserir usuário ====");
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Idade: ");
                    int idade = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Curso: ");
                    String curso = scanner.nextLine();
                    System.out.print("Matrícula: ");
                    int matriculaNumero = scanner.nextInt();

                    Matricula novaMatricula = new Matricula(nome, idade, curso, matriculaNumero);
                    if (matriculaDAO.insert(novaMatricula)) {
                        System.out.println("Inserção com sucesso -> " + novaMatricula.toString());
                    }
                    break;

                case 2:
                    System.out.println("\n==== Listar usuários ====");
                    List<Matricula> matriculas = matriculaDAO.get();
                    for (Matricula m : matriculas) {
                        System.out.println(m.toString());
                    }
                    break;

                case 3:
                    System.out.println("\n==== Atualizar usuário ====");
                    System.out.print("Digite o código do usuário a ser atualizado: ");
                    String codigoAtualizar = scanner.nextLine();
                    Matricula matriculaAtualizar = matriculaDAO.get(codigoAtualizar);
                    

                    if (matriculaAtualizar != null) {
                        System.out.print("Novo curso: ");
                        String novoCurso = scanner.nextLine();
                        matriculaAtualizar.setCurso(novoCurso);

                        matriculaDAO.update(matriculaAtualizar);
                        System.out.println("Atualização realizada com sucesso.");
                    } else {
                        System.out.println("Usuário não encontrado.");
                    }
                    break;

                case 4:
                    System.out.println("\n==== Excluir usuário ====");
                    System.out.print("Digite o código do usuário a ser excluído: ");
                    String codigoExcluir = scanner.nextLine();
                    Matricula matriculaExcluir = matriculaDAO.get(codigoExcluir);

                    if (matriculaExcluir != null) {
                    	matriculaDAO.delete(Integer.parseInt(codigoExcluir));
                        System.out.println("Exclusão realizada com sucesso.");
                    } else {
                        System.out.println("Usuário não encontrado.");
                    }
                    break;

                case 5:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 5);

        scanner.close();
    }
}