package com.appdora.bootstrap;

import com.appdora.domain.*;
import com.appdora.repository.*;
import com.appdora.service.ClienteService;
import com.appdora.service.dto.*;
import com.appdora.service.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class
DoraBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CheckoutRepository checkoutRepository;
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UserRepository userRepository;
    private final ClienteService clienteService;
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final ClienteMapper clienteMapper;
    private final CategoriaMapper categoriaMapper;
    private final ProdutoMapper produtoMapper;
    private final CheckoutMapper checkoutMapper;

    public DoraBootstrap(CheckoutRepository checkoutRepository, ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository, UserRepository userRepository, ClienteService clienteService, TagRepository tagRepository, TagMapper tagMapper, ClienteMapper clienteMapper, CategoriaMapper categoriaMapper, ProdutoMapper produtoMapper, CheckoutMapper checkoutMapper) {
        this.checkoutRepository = checkoutRepository;
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.userRepository = userRepository;
        this.clienteService = clienteService;
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
        this.clienteMapper = clienteMapper;
        this.categoriaMapper = categoriaMapper;
        this.produtoMapper = produtoMapper;
        this.checkoutMapper = checkoutMapper;
    }

    List<Categoria> categorias = new ArrayList<>();
    List<Tag> tags = new ArrayList<>();
    List<Produto> produtos = new ArrayList<>();
    List<Cliente> clientes = new ArrayList<>();
    //List<Checkout> checkouts = new ArrayList<>();

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //log.debug("Loading Bootstrap Data");
        loadTag();
        loadCliente();
        loadCategoria();
        loadProduto();
        //loadCheckout();
    }

    private void loadCheckout(){
        List<CheckoutDTO> checkoutDTOS = new ArrayList<>();
        Set<ProdutoDTO> listProduto = new LinkedHashSet<ProdutoDTO>(produtoMapper.toDto(this.produtos));

        List<Checkout> checkouts = checkoutMapper.toEntity(checkoutDTOS);
        checkoutRepository.save(checkouts);
        //this.checkouts = checkouts;
    }

    private void loadProduto(){
        List<ProdutoDTO> produtoDTOS = new ArrayList<>();

        List<Produto> produtos = produtoMapper.toEntity(produtoDTOS);
        produtoRepository.save(produtos);
        this.produtos = produtos;
    }

    private void loadCategoria(){
        List<CategoriaDTO> categoriaDTOS = new ArrayList<>();
        categoriaDTOS.add(new CategoriaDTO("Saia"));
        categoriaDTOS.add(new CategoriaDTO("Macacao"));
        categoriaDTOS.add(new CategoriaDTO("Óculos"));
        List<Categoria> categorias = categoriaMapper.toEntity(categoriaDTOS);
        categoriaRepository.save(categorias);
        this.categorias = categorias;
    }

    private void loadCliente(){
        List<ClienteDTO> clienteDTOS = new ArrayList<>();
        clienteDTOS.add( saveCliente("Gustavo Caraciolo", "gustavocaraciolo@gmail.com", "932289111"));
        clienteDTOS.add( saveCliente ("Guilherme Caraciolo", "gcaraciolo@gmail.com", "932289111"));
        clienteDTOS.add( saveCliente ("Gabriella Caraciolo", "gabriella_cavalcante@hotmail.com", "932289111"));
        clienteDTOS.add( saveCliente ("Miguel Caraciolo", "miguel@gmail.com", "932289111"));
        List<Cliente> clientes = clienteMapper.toEntity(clienteDTOS);
        this.clientes = clientes;
    }

    private ClienteDTO saveCliente(String name, String email, String telefone) {
        User user = clienteService.saveUser(new ClienteDTO(name, email, telefone, this.tags.get(0).getId(), null));
        ClienteDTO save = clienteService.save(new ClienteDTO(name, email, telefone, this.tags.get(0).getId(),user.getId()));
        return save;
    }

    private void loadTag(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        tagDTOS.add(new TagDTO("Elos"));
        tagDTOS.add(new TagDTO("Lança perfime"));
        tagDTOS.add(new TagDTO("Chapeu"));
        tagDTOS.add(new TagDTO("Maquiagem"));
        List<Tag> tags = tagMapper.toEntity(tagDTOS);
        tagRepository.save(tags);
        this.tags = tags;
    }
}
