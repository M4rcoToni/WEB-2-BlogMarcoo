package com.example.web2.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Path;

import java.nio.file.Paths;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.web2.repository.RepositoryPostagens;
import com.example.web2.model.Postagens;

@SpringBootApplication
@Controller
public class ControllerPostagens {
  @Autowired
  RepositoryPostagens repositoryPostagens;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public ModelAndView inicio() {
    ModelAndView mv = new ModelAndView("index");

    List<Postagens> postagens = repositoryPostagens.findFirstByOrderByIdDesc();
    mv.addObject("postagens", postagens);
    return mv;
  }

  @RequestMapping(value = "/postagens", method = RequestMethod.GET)
  public ModelAndView getPostagens() {
    ModelAndView mv = new ModelAndView("postagens");
    List<Postagens> postagens = repositoryPostagens.findAll();
    mv.addObject("postagens", postagens);
    return mv;
  }

  @RequestMapping(value = "/postagens/delete/{id}", method = RequestMethod.GET)
  public String deletePostagens(@PathVariable("id") int id, RedirectAttributes attributes) {
    repositoryPostagens.deleteById(id);
    attributes.addFlashAttribute("mensagem", "Deletado com sucesso");
    return "redirect:/postagens";
  }

  @RequestMapping(value = "/postagens/{id}", method = RequestMethod.GET)
  public ModelAndView getPostagem(@PathVariable("id") int id) {
    ModelAndView mv = new ModelAndView("postagem");
    Optional<Postagens> postagem = repositoryPostagens.findById(id);
    mv.addObject("id", postagem.get().getId());
    mv.addObject("autor", postagem.get().getAutor());
    mv.addObject("titulo", postagem.get().getTitulo());
    mv.addObject("conteudo", postagem.get().getConteudo());
    mv.addObject("imagem", postagem.get().getImagem());
    mv.addObject("data", postagem.get().getData());

    return mv;
  }

  @RequestMapping(value = "/postagens/alterar/{id}", method = RequestMethod.GET)
  public ModelAndView alterar(@PathVariable("id") int id) {
    ModelAndView mv = new ModelAndView("alterar");
    Optional<Postagens> postagem = repositoryPostagens.findById(id);
    mv.addObject("id", postagem.get().getId());
    mv.addObject("autor", postagem.get().getAutor());
    mv.addObject("titulo", postagem.get().getTitulo());
    mv.addObject("tipo", postagem.get().getTipo());
    mv.addObject("conteudo", postagem.get().getConteudo());

    return mv;
  }

  @RequestMapping(value = "/postagens/alterar/{id}", method = RequestMethod.POST)
  public String alterarPostagem(Postagens postagem) {
    Postagens postagemExistente = repositoryPostagens.findById(postagem.getId()).orElse(null);
    postagemExistente.setAutor(postagem.getAutor());
    postagemExistente.setTitulo(postagem.getTitulo());
    postagemExistente.setTipo(postagem.getTipo());
    postagemExistente.setConteudo(postagem.getConteudo());
    repositoryPostagens.save(postagemExistente);
    return "redirect:/postagens";
  }

  @RequestMapping(value = "/save", method = RequestMethod.GET)
  public String save() {
    return "formulario";
  }

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String savePost(@Valid Postagens postagem, BindingResult result, RedirectAttributes attributes,
      @RequestParam("file") MultipartFile imagem) {
    if (result.hasErrors()) {
      attributes.addFlashAttribute("mensagem", "Verifique os campos!");
      return "redirect:/save";

    }
    postagem.setData(LocalDate.now());
    try {
      if (!imagem.isEmpty()) {
        byte[] bytes = imagem.getBytes();
        String nomeImagem = LocalDate.now() + imagem.getOriginalFilename();
        Path caminho = Paths.get("./src/main/resources/static/img/" + nomeImagem);
        Files.write(caminho, bytes);
        postagem.setImagem(nomeImagem);
      }
    } catch (IOException e) {
      System.out.println("ERRO NA IMAGEM" + e);
    }
    repositoryPostagens.save(postagem);
    return "redirect:/postagens";
  }

  @RequestMapping(value = "/img/{imagem}", method = RequestMethod.GET)
  @ResponseBody
  public byte[] getImagem(@PathVariable("imagem") String imagem) throws IOException {
    File imagemArquivo = new File("./src/main/resources/static/img/" + imagem);
    if (imagem != null || imagem.trim().length() > 0) {
      return Files.readAllBytes(imagemArquivo.toPath());
    }
    return null;

  }

  @RequestMapping(value = "/pesquisar", method = RequestMethod.POST)
  public ModelAndView getPostagensByTituloLike(@RequestParam("pesquisar") String buscar) throws IOException {
    ModelAndView mv = new ModelAndView("postagens");
    List<Postagens> postagem = repositoryPostagens.findPostagensByTituloLike(buscar);
    mv.addObject("postagens", postagem);
    return mv;
  }

  @RequestMapping(value = "/postagens/tipo/{tipo}", method = RequestMethod.GET)
  public ModelAndView getPostagemByTipo(@PathVariable("tipo") int tip) {
    ModelAndView mv = new ModelAndView("postagens");
    List<Postagens> postagens = repositoryPostagens.findPostagensByTipo(tip);

    mv.addObject("postagens", postagens);
    return mv;
  }

}
