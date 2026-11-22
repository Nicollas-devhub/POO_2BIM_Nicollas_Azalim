
package br.edu.avaliacao.sacm.console;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import org.springframework.stereotype.Component;
import br.edu.avaliacao.sacm.repository.PacienteRepository;
import br.edu.avaliacao.sacm.model.Paciente;
import br.edu.avaliacao.sacm.enums.DisponibilidadeMedico;
import br.edu.avaliacao.sacm.enums.Especialidade;
import br.edu.avaliacao.sacm.model.Medico;
import br.edu.avaliacao.sacm.repository.MedicoRepository;
import java.time.LocalDateTime;
import br.edu.avaliacao.sacm.model.Consulta;
import br.edu.avaliacao.sacm.service.ConsultaService;
import br.edu.avaliacao.sacm.repository.ConsultaRepository;

@Component
public class SistemaConsole {

   private final Scanner scanner = new Scanner(System.in);
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final ConsultaService consultaService;
    private final ConsultaRepository consultaRepository;

    public SistemaConsole(
            MedicoRepository medicoRepository,
            PacienteRepository pacienteRepository,
            ConsultaService consultaService,
            ConsultaRepository consultaRepository) {

        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.consultaService = consultaService;
        this.consultaRepository = consultaRepository;
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
                case 2 -> menuPacientes();
                case 3 -> menuConsultas();
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
                case 4 -> atualizarMedico();
                case 5 -> excluirMedico();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private void menuPacientes() {
        int opcao;

    do {
        System.out.println("\n===== PACIENTES =====");
        System.out.println("1 - Cadastrar paciente");
        System.out.println("2 - Listar pacientes");
        System.out.println("3 - Buscar paciente por ID");
        System.out.println("4 - Atualizar paciente");
        System.out.println("5 - Excluir paciente");
        System.out.println("0 - Voltar");
        System.out.print("Escolha uma opção: ");

        opcao = lerOpcao();

        switch (opcao) {
            case 1 -> cadastrarPaciente();
            case 2 -> listarPacientes();
            case 3 -> buscarPacientePorId();
            case 4 -> atualizarPaciente();
            case 5 -> excluirPaciente();
            case 0 -> System.out.println("Voltando...");
            default -> System.out.println("Opção inválida.");
        }

    } while (opcao != 0);
}

private void cadastrarPaciente() {
    try {
        System.out.println("\n===== CADASTRO DE PACIENTE =====");

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

        System.out.print("Número do prontuário: ");
        String numeroProntuario = scanner.nextLine();

        System.out.print("Convênio: ");
        String convenio = scanner.nextLine();

        System.out.print("Contato de emergência: ");
        String contatoEmergencia = scanner.nextLine();

        System.out.print("Telefone de emergência: ");
        String telefoneEmergencia = scanner.nextLine();

        Paciente paciente = new Paciente(
            null,
            nome,
            cpf,
            dataNascimento,
            email,
            telefone,
            cidade,
            estado,
            numeroProntuario,
            convenio,
            contatoEmergencia,
            telefoneEmergencia
        );

        pacienteRepository.salvar(paciente);

        System.out.println("\nPaciente cadastrado com sucesso!");

    } catch (Exception e) {
        System.out.println("\nErro ao cadastrar paciente: " + e.getMessage());
    }
}

private void listarPacientes() {
    System.out.println("\n===== PACIENTES CADASTRADOS =====");

    var pacientes = pacienteRepository.listarTodos();

    if (pacientes.isEmpty()) {
        System.out.println("Nenhum paciente cadastrado.");
        return;
    }

    for (Paciente paciente : pacientes) {
        System.out.println("------------------------------");
        System.out.println("ID: " + paciente.getId());
        System.out.println("Nome: " + paciente.getNome());
        System.out.println("CPF: " + paciente.getCpf());
        System.out.println("Prontuário: " + paciente.getNumeroProntuario());
        System.out.println("Convênio: " + paciente.getConvenio());
    }

    System.out.println("------------------------------");
}

private void buscarPacientePorId() {
    System.out.println("\n===== BUSCAR PACIENTE POR ID =====");
    System.out.print("Digite o ID do paciente: ");

    Long id = Long.parseLong(scanner.nextLine());

    var resultado = pacienteRepository.buscarPorId(id);

    if (resultado.isEmpty()) {
        System.out.println("Paciente não encontrado.");
        return;
    }

    Paciente paciente = resultado.get();

    System.out.println("\n===== PACIENTE ENCONTRADO =====");
    System.out.println("ID: " + paciente.getId());
    System.out.println("Nome: " + paciente.getNome());
    System.out.println("CPF: " + paciente.getCpf());
    System.out.println("Data de nascimento: " + paciente.getDataNascimento());
    System.out.println("E-mail: " + paciente.getEmail());
    System.out.println("Telefone: " + paciente.getTelefone());
    System.out.println("Cidade: " + paciente.getCidade());
    System.out.println("Estado: " + paciente.getEstado());
    System.out.println("Prontuário: " + paciente.getNumeroProntuario());
    System.out.println("Convênio: " + paciente.getConvenio());
    System.out.println("Contato de emergência: " + paciente.getContatoEmergencia());
    System.out.println("Telefone de emergência: " + paciente.getTelefoneEmergencia());
}

private void atualizarPaciente() {
    try {
        System.out.println("\n===== ATUALIZAR PACIENTE =====");
        System.out.print("Digite o ID do paciente: ");

        Long id = Long.parseLong(scanner.nextLine());

        var resultado = pacienteRepository.buscarPorId(id);

        if (resultado.isEmpty()) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        Paciente antigo = resultado.get();

        System.out.println("Paciente encontrado: " + antigo.getNome());

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();

        System.out.print("Novo CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Data de nascimento (AAAA-MM-DD): ");
        LocalDate dataNascimento = LocalDate.parse(scanner.nextLine());

        System.out.print("Novo e-mail: ");
        String email = scanner.nextLine();

        System.out.print("Novo telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Nova cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Novo estado: ");
        String estado = scanner.nextLine();

        System.out.print("Novo número de prontuário: ");
        String numeroProntuario = scanner.nextLine();

        System.out.print("Novo convênio: ");
        String convenio = scanner.nextLine();

        System.out.print("Novo contato de emergência: ");
        String contatoEmergencia = scanner.nextLine();

        System.out.print("Novo telefone de emergência: ");
        String telefoneEmergencia = scanner.nextLine();

        Paciente paciente = new Paciente(
            id, nome, cpf, dataNascimento, email, telefone,
            cidade, estado, numeroProntuario, convenio,
            contatoEmergencia, telefoneEmergencia
        );

        pacienteRepository.atualizar(paciente);

        System.out.println("Paciente atualizado com sucesso!");

    } catch (Exception e) {
        System.out.println("Erro ao atualizar paciente: " + e.getMessage());
    }
}

private void excluirPaciente() {
    try {
        System.out.println("\n===== EXCLUIR PACIENTE =====");
        System.out.print("Digite o ID do paciente: ");

        Long id = Long.parseLong(scanner.nextLine());

        var resultado = pacienteRepository.buscarPorId(id);

        if (resultado.isEmpty()) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        Paciente paciente = resultado.get();

        System.out.println("Paciente encontrado: " + paciente.getNome());
        System.out.print("Deseja realmente excluir? (S/N): ");

        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            pacienteRepository.deletar(id);
            System.out.println("Paciente excluído com sucesso!");
        } else {
            System.out.println("Exclusão cancelada.");
        }

    } catch (Exception e) {
        System.out.println("Erro ao excluir paciente: " + e.getMessage());
    }
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

    private void atualizarMedico() {
    try {
        System.out.println("\n===== ATUALIZAR MÉDICO =====");
        System.out.print("Digite o ID do médico: ");

        Long id = Long.parseLong(scanner.nextLine());

        var resultado = medicoRepository.buscarPorId(id);

        if (resultado.isEmpty()) {
            System.out.println("Médico não encontrado.");
            return;
        }

        Medico antigo = resultado.get();

        System.out.println("Médico encontrado: " + antigo.getNome());

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();

        System.out.print("Novo CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Data de nascimento (AAAA-MM-DD): ");
        LocalDate dataNascimento = LocalDate.parse(scanner.nextLine());

        System.out.print("Novo e-mail: ");
        String email = scanner.nextLine();

        System.out.print("Novo telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Nova cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Novo estado: ");
        String estado = scanner.nextLine();

        System.out.print("Novo CRM: ");
        String crm = scanner.nextLine();

        System.out.println("Especialidades:");
        for (Especialidade e : Especialidade.values()) {
            System.out.println("- " + e);
        }

        System.out.print("Nova especialidade: ");
        Especialidade especialidade = Especialidade.valueOf(
            scanner.nextLine().toUpperCase()
        );

        System.out.println("Disponibilidade:");
        for (DisponibilidadeMedico d : DisponibilidadeMedico.values()) {
            System.out.println("- " + d);
        }

        System.out.print("Nova disponibilidade: ");
        DisponibilidadeMedico disponibilidade = DisponibilidadeMedico.valueOf(
            scanner.nextLine().toUpperCase()
        );

        System.out.print("Novo valor da consulta: ");
        BigDecimal valorConsulta = new BigDecimal(
            scanner.nextLine().replace(",", ".")
        );

        Medico medico = new Medico(
            id, nome, cpf, dataNascimento, email, telefone,
            cidade, estado, crm, especialidade, disponibilidade, valorConsulta
        );

        medicoRepository.atualizar(medico);

        System.out.println("Médico atualizado com sucesso!");

    } catch (Exception e) {
        System.out.println("Erro ao atualizar médico: " + e.getMessage());
    }
     }


     private void excluirMedico() {
    try {
        System.out.println("\n===== EXCLUIR MÉDICO =====");
        System.out.print("Digite o ID do médico: ");

        Long id = Long.parseLong(scanner.nextLine());

        var resultado = medicoRepository.buscarPorId(id);

        if (resultado.isEmpty()) {
            System.out.println("Médico não encontrado.");
            return;
        }

        Medico medico = resultado.get();

        System.out.println("Médico encontrado: " + medico.getNome());
        System.out.print("Deseja realmente excluir? (S/N): ");

        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            medicoRepository.deletar(id);
            System.out.println("Médico excluído com sucesso!");
        } else {
            System.out.println("Exclusão cancelada.");
        }

    } catch (Exception e) {
        System.out.println("Erro ao excluir médico: " + e.getMessage());
    }
   }

   private void menuConsultas() {
    int opcao;

    do {
        System.out.println("\n===== CONSULTAS =====");
        System.out.println("1 - Agendar consulta");
        System.out.println("2 - Listar consultas");
        System.out.println("3 - Buscar consulta por ID");
        System.out.println("4 - Confirmar consulta");
        System.out.println("5 - Cancelar consulta");
        System.out.println("6 - Finalizar consulta");
        System.out.println("0 - Voltar");
        System.out.print("Escolha uma opção: ");

        opcao = lerOpcao();

        switch (opcao) {
            case 1 -> agendarConsulta();
            case 2 -> listarConsultas();
            case 3 -> buscarConsulta();
            case 4 -> confirmarConsulta();
            case 5 -> cancelarConsulta();
            case 6 -> finalizarConsulta();
            case 0 -> System.out.println("Voltando ao menu principal...");
            default -> System.out.println("Opção inválida.");
        }

    } while (opcao != 0);
   }

   private void listarConsultas() {
    var consultas = consultaRepository.listarTodos();

    if (consultas.isEmpty()) {
        System.out.println("\nNenhuma consulta cadastrada.");
        return;
    }

    System.out.println("\n===== LISTA DE CONSULTAS =====");

    for (Consulta consulta : consultas) {
        System.out.println("----------------------------");
        System.out.println("ID: " + consulta.getId());
        System.out.println("Médico: " + consulta.getMedico().getNome());
        System.out.println("Paciente: " + consulta.getPaciente().getNome());
        System.out.println("Data/Hora: " + consulta.getDataHora());
        System.out.println("Motivo: " + consulta.getMotivo());
        System.out.println("Status: " + consulta.getStatus());
    }

    System.out.println("----------------------------");
   }

   private void buscarConsulta() {
    System.out.print("Digite o ID da consulta: ");
    Long id = scanner.nextLong();
    scanner.nextLine();

    var consulta = consultaRepository.buscarPorId(id);

    if (consulta.isEmpty()) {
        System.out.println("Consulta não encontrada.");
        return;
    }

    Consulta c = consulta.get();

    System.out.println("\n===== CONSULTA ENCONTRADA =====");
    System.out.println("ID: " + c.getId());
    System.out.println("Médico: " + c.getMedico().getNome());
    System.out.println("Paciente: " + c.getPaciente().getNome());
    System.out.println("Data/Hora: " + c.getDataHora());
    System.out.println("Motivo: " + c.getMotivo());
    System.out.println("Status: " + c.getStatus());
   }



   private void agendarConsulta() {
    System.out.println("\n===== AGENDAR CONSULTA =====");

    System.out.print("ID do médico: ");
    Long idMedico = scanner.nextLong();
    scanner.nextLine();

    var medico = medicoRepository.buscarPorId(idMedico);

    if (medico.isEmpty()) {
        System.out.println("Médico não encontrado.");
        return;
    }

    System.out.print("ID do paciente: ");
    Long idPaciente = scanner.nextLong();
    scanner.nextLine();

    var paciente = pacienteRepository.buscarPorId(idPaciente);

    if (paciente.isEmpty()) {
        System.out.println("Paciente não encontrado.");
        return;
    }

    System.out.print("Data e hora (AAAA-MM-DD HH:MM): ");
    String dataHoraTexto = scanner.nextLine();

    LocalDateTime dataHora = LocalDateTime.parse(
        dataHoraTexto.replace(" ", "T")
    );

    System.out.print("Motivo da consulta: ");
    String motivo = scanner.nextLine();

   Consulta consulta = new Consulta(
    null,
    medico.get(),
    paciente.get(),
    dataHora,
    motivo,
    null
);

    consultaService.agendar(consulta);

    System.out.println("Consulta agendada com sucesso!");
   }

   private void confirmarConsulta() {
    System.out.println("\n===== CONFIRMAR CONSULTA =====");

    System.out.print("Digite o ID da consulta: ");
    Long id = scanner.nextLong();
    scanner.nextLine();

    consultaService.confirmar(id);

    System.out.println("Consulta confirmada com sucesso!");
   }


   private void cancelarConsulta() {
    System.out.println("\n===== CANCELAR CONSULTA =====");

    System.out.print("Digite o ID da consulta: ");
    Long id = scanner.nextLong();
    scanner.nextLine();

    consultaService.cancelar(id);

    System.out.println("Consulta cancelada com sucesso!");
}

private void finalizarConsulta() {
    System.out.println("\n===== FINALIZAR CONSULTA =====");

    System.out.print("Digite o ID da consulta: ");
    Long id = scanner.nextLong();
    scanner.nextLine();

    consultaService.finalizar(id);

    System.out.println("Consulta finalizada com sucesso!");
}

}
