package src.service;

import src.dao.UsuarioDAO;
import src.model.Usuario;

import java.util.List;

public class UsuarioService {
    private UsuarioDAO usuarioDAO;

    // Construtor com injeção de dependência
    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    // Construtor padrão para uso normal
    public UsuarioService() {
        this(new UsuarioDAO());
    }

    public boolean autenticar(String email, String senha) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("O email não pode ser vazio.");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser vazia.");
        }

        try {
            Usuario usuario = usuarioDAO.buscarPorEmailSenha(email, senha);
            return usuario != null;
        } catch (Exception e) {
            System.err.println("Erro ao autenticar usuário: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean cadastrar(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("O usuário não pode ser nulo.");
        }

        try {
            usuarioDAO.cadastrarUsuario(usuario);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Usuario> listarTodos() {
        try {
            return usuarioDAO.listarTodos();
        } catch (Exception e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}