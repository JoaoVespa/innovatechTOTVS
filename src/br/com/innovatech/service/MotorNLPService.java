package br.com.innovatech.service;

import br.com.innovatech.model.Transcricao;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MotorNLPService {

    // Metodo 1: Avaliação de Churn ponderada por palavras de atrito
    public double calcularRiscoChurn(Transcricao t) {
        if (t.getTextoBruto() == null || t.getTextoBruto().isEmpty()) {
            return 0.0;
        }
        String texto = t.getTextoBruto().toLowerCase();
        double risco = 0.0;

        if (texto.contains("lento") || texto.contains("instavel") || texto.contains("travando")) risco += 25.0;
        if (texto.contains("concorrente") || texto.contains("outra solucao") || texto.contains("migracao")) risco += 35.0;
        if (texto.contains("cancelar") || texto.contains("rescisao") || texto.contains("insatisfeito")) risco += 40.0;

        risco = Math.min(risco, 100.0);
        t.setScoreRiscoChurn(risco);

        if (risco >= 70.0) {
            t.setStatusAlerta("RISCO_CRITICO");
        } else if (risco >= 35.0) {
            t.setStatusAlerta("ATENCAO_CS");
        } else {
            t.setStatusAlerta("CLIENTE_ESTAVEL");
        }
        return risco;
    }

    // Método 2: Identificação de Cross-sell baseada em dores do portfólio TOTVS
    public String mapearOportunidadeTOTVS(Transcricao t) {
        String texto = t.getTextoBruto().toLowerCase();
        String sugestao;

        if (texto.contains("folha") || texto.contains("ponto") || texto.contains("rh")) {
            sugestao = "TOTVS RH (Linha RM)";
        } else if (texto.contains("workflow") || texto.contains("aprovacao") || texto.contains("bpm")) {
            sugestao = "TOTVS Fluig";
        } else if (texto.contains("contabil") || texto.contains("fiscal") || texto.contains("faturamento")) {
            sugestao = "TOTVS Backoffice (Protheus)";
        } else {
            sugestao = "NENHUMA_OPORTUNIDADE";
        }

        t.setProdutoSugerido(sugestao);
        return sugestao;
    }

    // Metodo 3: Extração de valores financeiros e budget via Regex
    public double extrairBudgetNegociacao(Transcricao t) {
        String texto = t.getTextoBruto();
        double valorIdentificado = 0.0;

        Pattern padrao = Pattern.compile("(?i)(?:budget|orcamento|valor)\\D{0,15}(\\d+(?:[.,]\\d{2})?)");
        Matcher matcher = padrao.matcher(texto);

        if (matcher.find()) {
            String valorStr = matcher.group(1).replace(".", "").replace(",", ".");
            try {
                valorIdentificado = Double.parseDouble(valorStr);
            } catch (NumberFormatException e) {
                valorIdentificado = 0.0;
            }
        }
        t.setValorOrcamento(valorIdentificado);
        return valorIdentificado;
    }

    // Metodo 4: Síntese de prioridade para a tomada de decisão do executivo
    public String consolidarDecisaoExecutiva(Transcricao t) {
        calcularRiscoChurn(t);
        mapearOportunidadeTOTVS(t);
        extrairBudgetNegociacao(t);

        StringBuilder resumo = new StringBuilder();
        resumo.append("=== PAINEL ESTRATEGICO INNOVATECH ===\n");
        resumo.append("Cliente ID: ").append(t.getIdCliente()).append("\n");
        resumo.append("Indice de Risco: ").append(t.getScoreRiscoChurn()).append("% (").append(t.getStatusAlerta()).append(")\n");
        resumo.append("Acao Comercial: ").append(t.getProdutoSugerido()).append("\n");
        resumo.append("Budget Mapeado: R$ ").append(t.getValorOrcamento()).append("\n");
        resumo.append("Recomendacao: ");

        if (t.getScoreRiscoChurn() >= 70.0) {
            resumo.append("Acionar Customer Success imediatamente para retencao.");
        } else if (!t.getProdutoSugerido().equals("NENHUMA_OPORTUNIDADE")) {
            resumo.append("Agendar apresentacao tecnica focada em ").append(t.getProdutoSugerido());
        } else {
            resumo.append("Manter cadencia operacional padrao.");
        }
        return resumo.toString();
    }
}