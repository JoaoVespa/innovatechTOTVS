package br.com.innovatech.teste;

import br.com.innovatech.model.Transcricao;
import br.com.innovatech.service.MotorNLPService;

public class TestePrincipal {

    public static void main(String[] args) {

        MotorNLPService motor = new MotorNLPService();

        System.out.println("INICIANDO TESTES DO MOTOR NLP - INNOVATECH\n");

        // Cenario 1: Cliente insatisfeito e oportunidade de RH
        Transcricao call1 = new Transcricao(1, 1001,
                "O sistema atual esta muito lento e travando bastante. "
                        + "O pessoal do RH esta insatisfeito com o fechamento da folha. "
                        + "Se nao melhorar, vamos ter que cancelar e buscar um concorrente. "
                        + "Porem, temos um budget de 45000 aprovado para melhorias.");

        System.out.println("Cenario 1: Analisando transcricao...");
        System.out.println(motor.consolidarDecisaoExecutiva(call1));

        System.out.println("\n--------------------------------------------------");

        // Cenario 2: Cliente satisfeito e oportunidade Fluig
        Transcricao call2 = new Transcricao(2, 1002,
                "Estamos gostando muito do ERP atual. "
                        + "O proximo passo para o ano que vem e melhorar nosso workflow de compras "
                        + "e organizar o fluxo de aprovacao das diretorias "
                        + "com um orcamento de 15000,50.");

        System.out.println("Cenario 2: Analisando transcricao...");
        System.out.println(motor.consolidarDecisaoExecutiva(call2));

        System.out.println("\nTestes de analise concluidos.");
    }
}
