package br.com.coffeeandit.transaction.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(value = "AlteracaoSituacaoDTO", description = "Objeto de transporte para  alteração de uma situacao de transação")
public class AlteracaoSituacaoDTO {

    @ApiModelProperty(value = "Situação da Transação", required = false)
    private SituacaoEnum situacao;
}
