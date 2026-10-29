package br.com.innovatech.service;

import org.springframework.stereotype.Service;
import br.com.innovatech.dao.TranscricaoDAO;
import br.com.innovatech.model.Transcricao;

import java.util.List;


@Service
public class TranscricaoService {

    private final TranscricaoDAO dao;

    public TranscricaoService(TranscricaoDAO dao) {
        this.dao = dao;
    }

    public List<Transcricao> listarTodos() {
        return dao.listarTodos();
    }

    public Transcricao buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID deve ser maior que zero");
        }

        return dao.buscarPorId(id);
    }

    public Transcricao inserir(Transcricao t) {
        validar(t);

        if (t.getId() <= 0) {
            throw new IllegalArgumentException("O ID deve ser maior que zero");
        }

        if (t.getIdCliente() <= 0) {
            throw new IllegalArgumentException("O ID do cliente deve ser maior que zero");
        }

        if (dao.buscarPorId(t.getId()) != null) {
            throw new IllegalArgumentException("Já existe uma transcrição com esse ID");
        }

        if (!dao.inserir(t)) {
            throw new RuntimeException("Não foi possível cadastrar a transcrição");
        }

        return t;
    }

    public Transcricao atualizar(int id, Transcricao t) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID deve ser maior que zero");
        }

        validar(t);

        if (t.getIdCliente() <= 0) {
            throw new IllegalArgumentException("O ID do cliente deve ser maior que zero");
        }

        t.setId(id);

        if (dao.buscarPorId(id) == null) {
            return null;
        }

        if (!dao.atualizar(t)) {
            throw new RuntimeException("Não foi possível atualizar a transcrição");
        }

        return t;
    }

    public boolean excluir(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID deve ser maior que zero");
        }

        return dao.excluir(id);
    }

    private void validar(Transcricao t) {
        if (t == null) {
            throw new IllegalArgumentException("A transcrição é obrigatória");
        }

        if (t.getTextoBruto() == null || t.getTextoBruto().isBlank()) {
            throw new IllegalArgumentException("O texto da transcrição é obrigatório");
        }
    }
}