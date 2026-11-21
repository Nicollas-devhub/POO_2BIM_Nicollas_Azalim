
package br.edu.avaliacao.sacm.console;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import br.edu.avaliacao.sacm.enums.DisponibilidadeMedico;
import br.edu.avaliacao.sacm.enums.Especialidade;
import br.edu.avaliacao.sacm.model.Medico;
import br.edu.avaliacao.sacm.repository.MedicoRepository;

@Component
public class SistemaConsole {

    private final Scanner scanner = new Scanner(System.in);
    private final MedicoRepository medicoRepository;

    public SistemaConsole(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public void exibirMenu() {
        System.out.println("\n===== SACM =====");
        System.out.println("1 - Médicos");
        System.out.println("2 - Pacientes");
        System.out.println("3 - Consultas");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    public int lerOpcao() {
        return Integer.parseInt(scanner.nextLine());
    }

    public void executar() {
        int opcao;

        do {
            exibirMenu();
            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> menuMedicos();
                case 2 -> System.out.println("Menu de Pacientes");
                case 3 -> System.out.println("Menu de Consultas");
                case 0 -> System.out.println("Encerrando o SACM...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private void menuMedicos() {
        int opcao;

        do {
            System.out.println("\n===== MÉDICOS =====");
            System.out.println("1 - Cadastrar médico");
            System.out.println("2 - Listar médicos");
            System.out.println("3 - Buscar médico por ID");
            System.out.println("4 - Atualizar médico");
            System.out.println("5 - Excluir médico");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> cadastrarMedico();
                case 2 ->  listarMedicos();
                case 3 -> buscarMedicoPorId();
                case 4 -> System.out.println("Atualizar médico");
                case 5 -> System.out.println("Excluir médico");
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private void cadastrarMedico() {
        try {
            System.out.println("\n===== CADASTRO DE MÉDICO =====");

            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("CPF: ");
            String cpf = scanner.nextLine();

            System.out.print("Data de nascimento (AAAA-MM-DD): ");
            LocalDate dataNascimento = LocalDate.parse(scanner.nextLine());

            System.out.print("E-mail: ");
            String email = scanner.nextLine();

            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();

            System.out.print("Cidade: ");
            String cidade = scanner.nextLine();

            System.out.print("Estado: ");
            String estado = scanner.nextLine();

            System.out.print("CRM: ");
            String crm = scanner.nextLine();

            System.out.println("\nEspecialidades disponíveis:");
            for (Especialidade especialidade : Especialidade.values()) {
                System.out.println("- " + especialidade);
            }

            System.out.print("Especialidade: ");
            Especialidade especialidade = Especialidade.valueOf(
                scanner.nextLine().toUpperCase()
            );

            System.out.println("\nDisponibilidade:");
            for (DisponibilidadeMedico disponibilidade : DisponibilidadeMedico.values()) {
                System.out.println("- " + disponibilidade);
            }

            System.out.print("Disponibilidade: ");
            DisponibilidadeMedico disponibilidade = DisponibilidadeMedico.valueOf(
                scanner.nextLine().toUpperCase()
            );

            System.out.print("Valor da consulta: ");
            BigDecimal valorConsulta = new BigDecimal(
                scanner.nextLine().replace(",", ".")
            );

            Medico medico = new Medico(
                null,
                nome,
                cpf,
                dataNascimento,
                email,
                telefone,
                cidade,
                estado,
                crm,
                especialidade,
                disponibilidade,
                valorConsulta
            );

            medicoRepository.salvar(medico);

            System.out.println("\nMédico cadastrado com sucesso!");

        } catch (Exception e) {
            System.out.println("\nErro ao cadastrar médico: " + e.getMessage());
        }
    }

    private void listarMedicos() {
    System.out.println("\n===== MÉDICOS CADASTRADOS =====");

    var medicos = medicoRepository.listarTodos();

    if (medicos.isEmpty()) {
        System.out.println("Nenhum médico cadastrado.");
        return;
    }

    for (Medico medico : medicos) {
        System.out.println("------------------------------");
        System.out.println("ID: " + medico.getId());
        System.out.println("Nome: " + medico.getNome());
        System.out.println("CPF: " + medico.getCpf());
        System.out.println("CRM: " + medico.getCrm());
        System.out.println("Especialidade: " + medico.getEspecialidade());
        System.out.println("Disponibilidade: " + medico.getDisponibilidade());
        System.out.println("Valor da consulta: R$ " + medico.getValorConsulta());
    }

    System.out.println("------------------------------");
}

private void buscarMedicoPorId() {
    System.out.println("\n===== BUSCAR MÉDICO POR ID =====");
    System.out.print("Digite o ID do médico: ");

    Long id = Long.parseLong(scanner.nextLine());

    var resultado = medicoRepository.buscarPorId(id);

    if (resultado.isEmpty()) {
        System.out.println("Médico não encontrado.");
        return;
    }

    Medico medico = resultado.get();

    System.out.println("\n===== MÉDICO ENCONTRADO =====");
    System.out.println("ID: " + medico.getId());
    System.out.println("Nome: " + medico.getNome());
    System.out.println("CPF: " + medico.getCpf());
    System.out.println("Data de nascimento: " + medico.getDataNascimento());
    System.out.println("E-mail: " + medico.getEmail());
    System.out.println("Telefone: " + medico.getTelefone());
    System.out.println("Cidade: " + medico.getCidade());
    System.out.println("Estado: " + medico.getEstado());
    System.out.println("CRM: " + medico.getCrm());
    System.out.println("Especialidade: " + medico.getEspecialidade());
    System.out.println("Disponibilidade: " + medico.getDisponibilidade());
    System.out.println("Valor da consulta: R$ " + medico.getValorConsulta());
}
}
