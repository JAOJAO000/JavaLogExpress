import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 1. CONTRATO DO SISTEMA (Interface ou Classe Abstrata)
abstract class Veiculo {
    private String placa;
    private String modelo;
    protected double capacidadeCargaKg;

    public Veiculo(String placa, String modelo, double capacidadeCargaKg) {
        this.placa = placa;
        this.modelo = modelo;
        this.capacidadeCargaKg = capacityValida(capacidadeCargaKg);
    }

    private double capacityValida(double carga) {
        return carga > 0 ? carga : 100; // Validação simples de encapsulamento
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public double getCapacidadeCargaKg() {
        return capacidadeCargaKg;
    }

    // Método polimórfico que cada tipo de veículo calculará de sua forma
    public abstract double calcularCustoFrete(double distanciaKm);
}

class Van extends Veiculo {

    public Van(String placa, String modelo, double capacidadeCargaKg) {
        super(placa, modelo, capacidadeCargaKg);
    }

    @Override
    public double calcularCustoFrete(double distanciaKm) {
        double frete = distanciaKm * 2.50;

        if (capacidadeCargaKg > 1000) {
            frete += 30;
        }

        return frete;
    }
}

class Caminhao extends Veiculo {

    private int eixos;

    public Caminhao(String placa, String modelo, double capacidadeCargaKg, int eixos) {
        super(placa, modelo, capacidadeCargaKg);
        this.eixos = eixos;
    }

    public int getEixos() {
        return eixos;
    }

    public void setEixos(int eixos) {
        if (eixos > 0) {
            this.eixos = eixos;
        }
    }

    @Override
    public double calcularCustoFrete(double distanciaKm) {
        return distanciaKm * 4.50 * eixos;
    }
}

// 2. CLASSE PRINCIPAL DO SISTEMA
public class JavaLogExpressApp {

    public static void main(String[] args) {

        Scanner leitor = new Scanner(System.in);
        List<Veiculo> frota = new ArrayList<>();

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("\n--- JAVALOG EXPRESS - SISTEMA DE FROTA ---");
            System.out.println("1. Cadastrar Van");
            System.out.println("2. Cadastrar Caminhão");
            System.out.println("3. Listar Frota");
            System.out.println("0. Encerrar");

            System.out.print("Escolha: ");
            opcao = leitor.nextInt();
            leitor.nextLine(); // Limpar buffer do Enter

            switch (opcao) {

                case 1:

                    System.out.print("Placa da Van: ");
                    String pVan = leitor.nextLine();

                    System.out.print("Modelo: ");
                    String mVan = leitor.nextLine();

                    System.out.print("Capacidade (kg): ");
                    double cVan = leitor.nextDouble();
                    leitor.nextLine();

                    Van van = new Van(pVan, mVan, cVan);
                    frota.add(van);

                    // TODO: Instanciar a Van e adicionar na lista 'frota'
                    System.out.println("Van cadastrada com sucesso!");
                    break;

                case 2:

                    System.out.print("Placa do Caminhão: ");
                    String pCam = leitor.nextLine();

                    System.out.print("Modelo: ");
                    String mCam = leitor.nextLine();

                    System.out.print("Capacidade (kg): ");
                    double cCam = leitor.nextDouble();

                    System.out.print("Número de eixos: ");
                    int eixos = leitor.nextInt();
                    leitor.nextLine();

                    Caminhao caminhao = new Caminhao(pCam, mCam, cCam, eixos);
                    frota.add(caminhao);

                    System.out.println("Caminhão cadastrado com sucesso!");
                    break;

                case 3:

                    System.out.println("\n--- RELATÓRIO DA FROTA E SIMULAÇÃO DE FRETE (100 km) ---");

                    if (frota.isEmpty()) {

                        System.out.println("Nenhum veículo cadastrado.");

                    } else {

                        for (Veiculo v : frota) {

                            if (v instanceof Caminhao) {

                                Caminhao c = (Caminhao) v;

                                System.out.println("Veículo: Caminhão | Placa: " + c.getPlaca()
                                        + " | Modelo: " + c.getModelo()
                                        + " | Eixos: " + c.getEixos()
                                        + " | Frete Simulado: R$ " + c.calcularCustoFrete(100));

                            } else {

                                System.out.println("Veículo: Van | Placa: " + v.getPlaca()
                                        + " | Modelo: " + v.getModelo()
                                        + " | Frete Simulado: R$ " + v.calcularCustoFrete(100));
                            }
                        }
                    }

                    break;

                case 0:
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }

        leitor.close();
    }
}