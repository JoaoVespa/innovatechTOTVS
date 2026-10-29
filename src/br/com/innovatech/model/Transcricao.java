package br.com.innovatech.model;

public class Transcricao {
    private int id;
    private int idCliente;
    private String textoBruto;
    private double scoreRiscoChurn;
    private double valorOrcamento;
    private String produtoSugerido;
    private String statusAlerta;

    // Construtor vazio
    public Transcricao() {}

    // Construtor com os dados iniciais que vêm da reunião
    public Transcricao(int id, int idCliente, String textoBruto) {
        this.id = id;
        this.idCliente = idCliente;
        this.textoBruto = textoBruto;
        this.statusAlerta = "ANALISE_PENDENTE";
    }

    // --- GETTERS E SETTERS ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getTextoBruto() { return textoBruto; }
    public void setTextoBruto(String textoBruto) { this.textoBruto = textoBruto; }

    public double getScoreRiscoChurn() { return scoreRiscoChurn; }
    public void setScoreRiscoChurn(double scoreRiscoChurn) { this.scoreRiscoChurn = scoreRiscoChurn; }

    public double getValorOrcamento() { return valorOrcamento; }
    public void setValorOrcamento(double valorOrcamento) { this.valorOrcamento = valorOrcamento; }

    public String getProdutoSugerido() { return produtoSugerido; }
    public void setProdutoSugerido(String produtoSugerido) { this.produtoSugerido = produtoSugerido; }

    public String getStatusAlerta() { return statusAlerta; }
    public void setStatusAlerta(String statusAlerta) { this.statusAlerta = statusAlerta; }
}
