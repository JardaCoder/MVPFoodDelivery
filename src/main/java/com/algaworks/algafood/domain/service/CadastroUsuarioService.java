package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	private static final String MSG_SENHA_NAO_CORRESPONDE = "A senha informada não corresponde com a senha atual";
	private static final String MSG_USUARIO_EM_USO = "Usuario de código %d não pode ser removido por que está em uso.";
	private static final String MSG_EMAIL_EM_USO = "O e-mail %s já é utilizado por outro usuário.";

	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(String.format(MSG_EMAIL_EM_USO, usuario.getEmail()));
		}
		
		if(usuario.isNew())
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void excluir (Long usuarioId) {
		try {
			usuarioRepository.deleteById(usuarioId);
			usuarioRepository.flush();
			
		}catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(usuarioId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(
					MSG_USUARIO_EM_USO, usuarioId));
		}
		
	}
	
	public Usuario buscarOuFalhar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}

	@Transactional
	public void alterarSenha(Long usuarioId, String novaSenha, String senhaAtual) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		
		if(!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
			throw new NegocioException(MSG_SENHA_NAO_CORRESPONDE);
		}
		usuario.setSenha(passwordEncoder.encode(novaSenha));
	}
	
	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		
		usuario.removerGrupo(grupo);
	}
	
	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		
		usuario.adicionarGrupo(grupo);
	}
	

}
