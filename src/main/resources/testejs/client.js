const BASEURL = "http://api.jardafood.local:8080";

function consultar() {
  $.ajax({
    url: BASEURL + "/formas-pagamento",
    type: "get",

    success: function (response) {
      preencherTabela(response);
    },
  });
}

function cadastrar() {
  var formaPagamentoJson = JSON.stringify({
    descricao: $("#campo-descricao").val(),
  });

  console.log(formaPagamentoJson);

  $.ajax({
    url: BASEURL + "/formas-pagamento",
    type: "post",
    data: formaPagamentoJson,
    contentType: "application/json",

    success: function (response) {
      alert("Forma de pagamento adicionada!");
      consultar();
    },

    error: function (error) {
      if (error.status == 400) {
        var problem = JSON.parse(error.responseText);
        alert(problem.userMessage);
      } else {
        alert("Erro ao cadastrar forma de pagamento!");
      }
    },
  });
}

function excluir(formaPagamento) {
  $.ajax({
    url: BASEURL + "/formas-pagamento/" + formaPagamento.id,
    type: "delete",

    success: function (response) {
      consultar();
    },
    error: function (error) {
      if (error.status >= 400 && error.status <= 499) {
        var problem = JSON.parse(error.responseText);
        alert(problem.userMessage);
      } else {
        alert("Erro ao cadastrar forma de pagamento!");
      }
    },
  });
}

function preencherTabela(formasPagamento) {
  $("#tabela tbody tr").remove();

  $.each(formasPagamento, function (i, formaPagamento) {
    var linha = $("<tr>");

    var linkAcao = $("<a href='#'>")
      .text("Excluir")
      .click(function (event) {
        event.preventDefault();
        excluir(formaPagamento);
      });

    linha.append(
      $("<td>").text(formaPagamento.id),
      $("<td>").text(formaPagamento.descricao),
      $("<td>").append(linkAcao)
    );

    linha.appendTo("#tabela");
  });
}

$("#btn-consultar").click(consultar);
$("#btn-cadastrar").click(cadastrar);

consultar();

// function consultar() {
//   $.ajax({
//     url: BASEURL + "/formas-pagamento",
//     type: "get",
//     success: function (response) {
//       preencherTabela(response);
//     },
//   });
// }

// function salvar() {
//   var body = {
//     descricao: $("#campo-descricao").val(),
//   };

//   $.ajax({
//     url: BASEURL + "/formas-pagamento",
//     type: "post",
//     data: JSON.stringify(body),
//     contentType: "application/json",
//     success: function (response) {
//       consultar();
//       $("#campo-descricao").val("");
//     },
//     error: function (error) {
//       if (error.status === 400) {
//         var problem = JSON.parse(error.responseText);
//         alert(problem.userMessage);
//       }
//     },
//   });
// }

// function excluir(formaPagamento) {
//   alert(
//     "Implemente aqui para excluir forma de pagamento de ID " + formaPagamento.id
//   );
// }

// function preencherTabela(formasPagamento) {
//   $("#tabela tbody tr").remove();

//   $.each(formasPagamento, function (i, formaPagamento) {
//     var linha = $("<tr>");

//     linha.append(
//       $("<td>").text(formaPagamento.id),
//       $("<td>").text(formaPagamento.descricao)
//     );

//     linha.appendTo("#tabela");
//   });
// }

// $("#btn-cadastrar").click(salvar);
// $("#btn-consultar").click(consultar);

// consultar();

// function consultarRestaurantes() {
//   $.ajax({
//     url: BASEURL + "/restaurantes",
//     type: "get",

//     success: function (response) {
//       $("#conteudo").text(JSON.stringify(response));
//     },
//   });
// }

// function fecharRestaurante() {
//   $.ajax({
//     url: BASEURL + "/restaurantes/1/fechamento",
//     type: "put",

//     success: function (response) {
//       alert("restaurante foi fechado");
//     },
//   });
// }

// $("#botao").click(fecharRestaurante);
