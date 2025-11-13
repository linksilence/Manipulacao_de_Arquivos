import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class Principal {


    private String nomeDoArquivo;


    public Principal(String nomeArquivo) {
        this.nomeDoArquivo = nomeArquivo;
    }


    public void inserirDados(String registro) {
        File fArquivo = null;
        FileWriter fwArquivo = null;
        BufferedWriter bw = null;
        
        try {
            fArquivo = new File(this.nomeDoArquivo);
            

            fwArquivo = new FileWriter(fArquivo, true);
            bw = new BufferedWriter(fwArquivo);
            
            // escreve o registro no arquivo e pula uma linha com o \n
            bw.write(registro + "\n");
            System.out.println("Registro adicionado com sucesso...");
            
        } catch (Exception e) {
            System.err.println("Erro ao inserir linhas no arquivo: " + fArquivo);
            
        } finally {
            // Garantir fechamento dos recursos
            try {
                if (bw != null) bw.close();
                if (fwArquivo != null) fwArquivo.close();
            } catch (Exception e) {
                // ignorar erro no fechamento
            }
        }
    }

    public void listarDados() {
        Scanner lendoArquivo = null;
        File arquivo = null;
        try {
            arquivo = new File(this.nomeDoArquivo);
            lendoArquivo = new Scanner(arquivo);


            while (lendoArquivo.hasNextLine()) {
                this.processandoLinha(lendoArquivo.nextLine());
            }

        } catch (FileNotFoundException e) {
            // tratando quando o arquivo nao existe
            System.err.println("Erro: arquivo nao existe. " + arquivo);

        } finally {

            try {
                if (lendoArquivo != null) {
                    lendoArquivo.close();
                }
            } catch (Exception e) {

            }
        }
    }

    // Exe 2.1 - faz o tratamento das exceções
    private void processandoLinha(String linha) {
        // toda linha do arquivo segue o formato: nome:telefone

        if (linha != null && !linha.trim().isEmpty()) {
            try {
                // separando os campos através do delimitador ':'
                String[] campos = linha.split(":");

                // VERIFICAÇÃO CRÍTICA - Previne ArrayIndexOutOfBoundsException
                if (campos.length >= 2) {
                    // USAR printf COM FORMATAÇÃO CONFORME PDF
                    System.out.printf("Nome: %-30sFone: %s\n", campos[0].trim(), campos[1].trim());
                } else {
                    System.err.println("[ERRO] Linha com formato inválido no arquivo: " + linha);
                }

            } catch (Exception e) {
                System.err.println("[ERRO INESPERADO] Falha ao processar linha: " + linha);
            }
        }
    }

    // exe 2.2
    public void buscarDados(String nomeBusca) {
        Scanner lendoArquivo = null;
        File arquivo = null;
        boolean encontrado = false;

        try {
            arquivo = new File(this.nomeDoArquivo);
            lendoArquivo = new Scanner(arquivo);

            System.out.println("\n..:: Resultados da Busca ::..");
            System.out.println("Procurando por: " + nomeBusca);
            System.out.println("--------------------------------------\n");

            while (lendoArquivo.hasNextLine()) {
                String linha = lendoArquivo.nextLine();

                if (linha != null && !linha.trim().isEmpty()) {
                    try {
                        String[] campos = linha.split(":");

                        // VERIFICAÇÃO CRÍTICA
                        if (campos.length >= 2) {
                            String nomeArquivo = campos[0].trim();

                            // Comparação com equalsIgnoreCase para ignorar maiúsculas/minúsculas
                            if (nomeArquivo.equalsIgnoreCase(nomeBusca)) {
                                // Usa printf com formatação (EXERCÍCIO 2.1)
                                System.out.printf("Nome: %-30sFone: %s\n", campos[0].trim(), campos[1].trim());
                                encontrado = true;
                            }
                        }

                    } catch (Exception e) {
                        System.err.println("[ERRO INESPERADO] Falha ao processar linha: " + linha);
                    }
                }
            }

            if (!encontrado) {
                System.out.println("[INFO] Nenhum contato encontrado com o nome: " + nomeBusca);
            }

            System.out.println("--------------------------------------\n");

        } catch (FileNotFoundException e) {
            System.err.println("Erro: arquivo nao existe. " + arquivo);

        } finally {
            // Fechamento seguro do Scanner
            try {
                if (lendoArquivo != null) {
                    lendoArquivo.close();
                }
            } catch (Exception e) {
                // ignorar erro no fechamento
            }
        }
    }

    public void menu() {
        // passando para o objeto da classe Scanner o dispositivo de entrada padrão
        // que é o teclado
        Scanner teclado = new Scanner(System.in);
        int op = 0;

        do {
            System.out.println("..:: Trabalhando com Arquivos Texto ::..");
            System.out.println("1 - Inserir linha");
            System.out.println("2 - Listar todo arquivo");
            System.out.println("3 - Sair");
            System.out.println("4 - Buscar por Nome");
            System.out.print("Entre com uma opcao: ");
            op = teclado.nextInt();

            switch (op) {
                case 1:
                    teclado.nextLine();
                    String nome;
                    String telefone;
                    System.out.println("Entre com os dados:");
                    System.out.print("Nome: ");
                    nome = teclado.nextLine();
                    System.out.print("Fone: ");
                    telefone = teclado.nextLine();
                    this.inserirDados(nome + ":" + telefone);
                    break;

                case 2:
                    this.listarDados();
                    break;

                case 3:
                    System.out.println("Saindo...");
                    break;

                case 4:
                    teclado.nextLine();
                    System.out.print("Digite o nome para buscar: ");
                    String nomeBusca = teclado.nextLine();
                    this.buscarDados(nomeBusca);
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (op != 3);

        teclado.close();
    }

    public static void main(String[] args) {
        Principal p = new Principal("C:\\Users\\danie\\IdeaProjects\\Manipulacao_de_Arquivos\\src\\uso_pra_chorar.txt");
        p.menu();
    }
}