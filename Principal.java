import java.time.LocalDate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = inicializarFuncionarios();
        removerFuncionario(funcionarios, "João");
        imprimirFuncionarios(funcionarios);
        aumentarSalario(funcionarios);
        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparFuncionariosPorFuncao(funcionarios);
        imprimirFuncionariosPorFuncao(funcionariosPorFuncao);
        imprimirFuncionariosAniversariantes(funcionarios, 10, 12);
        imprimirFuncionarioMaisVelho(funcionarios);
        imprimirFuncionariosOrdemAlfabetica(funcionarios);
        imprimirTotalSalarios(funcionarios);
        imprimirSalariosMinimo(funcionarios, new BigDecimal("1212.00"));
    }

    private static List<Funcionario> inicializarFuncionarios() {
        return new ArrayList<>(Arrays.asList(
            new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
            new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
            new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
            new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
            new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
            new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
            new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
            new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
            new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
            new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));
    }

    private static void removerFuncionario(List<Funcionario> funcionarios, String nome) {
        funcionarios.removeIf(f -> f.getNome().equals(nome));
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        for (Funcionario f : funcionarios) {
            System.out.println("Nome: " + f.getNome());
            System.out.println("Data de Nascimento: " + f.formatarDataNascimento());
            System.out.println("Salário: R$ " + f.formatarSalario());
            System.out.println("Função: " + f.getFuncao());
            System.out.println();
        }
    }

    private static void aumentarSalario(List<Funcionario> funcionarios) {
        for (Funcionario f : funcionarios) {
            f.aumentarSalario();
        }
    }

    private static Map<String, List<Funcionario>> agruparFuncionariosPorFuncao(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    private static void imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            for (Funcionario f : entry.getValue()) {
                System.out.println("Nome: " + f.getNome());
                System.out.println("Data de Nascimento: " + f.formatarDataNascimento());
                System.out.println("Salário: R$ " + f.formatarSalario());
                System.out.println();
            }
        }
    }

    private static void imprimirFuncionariosAniversariantes(List<Funcionario> funcionarios, int... meses) {
        Set<Integer> mesesSet = Arrays.stream(meses).boxed().collect(Collectors.toSet());
        System.out.println("Aniversariantes nos meses " + Arrays.toString(meses) + ":");
        for (Funcionario f : funcionarios) {
            int mesAniversario = f.getDataNascimento().getMonthValue();
            if (mesesSet.contains(mesAniversario)) {
                System.out.println("Nome: " + f.getNome());
                System.out.println("Data de Nascimento: " + f.formatarDataNascimento());
                System.out.println();
            }
        }
    }

    private static void imprimirFuncionarioMaisVelho(List<Funcionario> funcionarios) {
        Funcionario funcionarioMaisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .get();
        int idadeMaisVelho = LocalDate.now().getYear() - funcionarioMaisVelho.getDataNascimento().getYear();
        System.out.println("Funcionário mais velho:");
        System.out.println("Nome: " + funcionarioMaisVelho.getNome());
        System.out.println("Idade: " + idadeMaisVelho + " anos");
        System.out.println();
    }

    private static void imprimirFuncionariosOrdemAlfabetica(List<Funcionario> funcionarios) {
        List<Funcionario> funcionariosOrdenados = new ArrayList<>(funcionarios);
        funcionariosOrdenados.sort(Comparator.comparing(Funcionario::getNome));
        System.out.println("Funcionários em ordem alfabética:");
        for (Funcionario f : funcionariosOrdenados) {
            System.out.println("Nome: " + f.getNome());
            System.out.println("Data de Nascimento: " + f.formatarDataNascimento());
            System.out.println("Salário: R$ " + f.formatarSalario());
            System.out.println("Função: " + f.getFuncao());
            System.out.println();
        }
    }

    private static void imprimirTotalSalarios(List<Funcionario> funcionarios) {
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total de salários: R$ "
                 + totalSalarios.toPlainString().replace(".", ","));
        System.out.println();
    }
    
    private static void imprimirSalariosMinimo(List<Funcionario> funcionarios, BigDecimal salarioMinimo) {
        System.out.println("Quantidade de saláriosmínimos por funcionários:");
        for (Funcionario f : funcionarios) {
            BigDecimal salariosMinimos = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println("Nome: " + f.getNome());
            System.out.println("Salários mínimos: " + salariosMinimos.toPlainString().replace(".", ","));
            System.out.println();
        }
    }
}
