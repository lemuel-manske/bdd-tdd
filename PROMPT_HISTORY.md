# PROMPT 1

Estou replicando o sistema responsável por emitir certidões negativas de débitos da prefeitura de Blumenau
utilizando TDD. Quero que você crie 5 cenários de teste BDD que seguem a estrutura Gherkin,
onde: 2 cenários são "felizes", 2 são "tristes/exceções" e um último cenário é _abuse case_, onde
tentamos levar o sistema no limite para explorar alguma vulnerabilidade (este último tem foco em segurança).
Você deve ler o artigo feito pelo Dan North sobre BDD disponível em https://dannorth.net/blog/introducing-bdd/
para garantir que seguimos **exatamente** o que o autor propõe sobre como escrever *BDD-ish*. As funcionalidades
serão: emitir uma declaração, validar uma certidão já emitida (para garantir que a mesma não foi adulterada).
Você deve emitir um arquivo markdown com todos os cenários de teste em texto simples, cada cenário
separado por uma linha em branco. Foque em clareza e evite introduzir ambiguidades ou conceitos não claros.
Você deve atuar como alguém que conhece o domínio de emissão de certidões negativas de débitos da prefeitura
de Blumenau e que está familiarizado com TDD e BDD.

# RESPOSTA 1

```md
# Cenários BDD — Certidão Negativa de Débitos (CND) da Prefeitura de Blumenau

Os cenários abaixo seguem a estrutura proposta por Dan North em "Introducing BDD": uma história
(`Como... quero... para...`) que expressa o valor do comportamento, e cenários de aceite no
formato `Dado/Quando/Então`, usando `E` para encadear premissas e desfechos de forma natural.

## Funcionalidade: Emissão e validação de certidão negativa de débitos

Como contribuinte da Prefeitura de Blumenau,
quero emitir uma certidão negativa de débitos municipais,
para comprovar que não possuo pendências tributárias junto ao município.

Como servidor da prefeitura,
quero validar a autenticidade de uma certidão negativa de débitos,
para garantir que o documento apresentado não foi adulterado.

### Cenário: Contribuinte sem débitos obtém sua certidão negativa

Dado que o contribuinte possui cadastro ativo na prefeitura
E o contribuinte não possui débitos municipais pendentes de IPTU, ISS ou taxas
Quando o contribuinte solicita a emissão da certidão negativa de débitos
Então o sistema emite a certidão negativa de débitos
E o sistema atribui à certidão um número de protocolo único
E o sistema assina digitalmente a certidão
E o sistema registra a certidão para futura consulta de autenticidade

### Cenário: Certidão emitida pelo sistema é reconhecida como autêntica

Dado que uma certidão negativa de débitos foi emitida pelo sistema para um contribuinte
E o conteúdo dessa certidão não foi alterado desde a emissão
Quando o interessado valida a autenticidade da certidão informando o número do protocolo
Então o sistema confirma que a certidão é autêntica
E o sistema apresenta os dados originais da certidão emitida
E o sistema informa a situação de "sem débitos" para o contribuinte

### Cenário: Contribuinte com débitos pendentes não obtém certidão negativa

Dado que o contribuinte possui ao menos um débito municipal pendente de IPTU, ISS ou taxas
Quando o contribuinte solicita a emissão da certidão negativa de débitos
Então o sistema nega a emissão da certidão negativa
E o sistema informa a existência de débitos pendentes
E o sistema não emite número de protocolo nem assinatura digital

### Cenário: Certidão adulterada é rejeitada na validação

Dado que uma certidão negativa de débitos foi emitida pelo sistema para um contribuinte
E o conteúdo dessa certidão foi alterado após a emissão (por exemplo, o nome do contribuinte ou o número do documento)
Quando o interessado valida a autenticidade da certidão adulterada
Então o sistema rejeita a certidão como não autêntica
E o sistema informa que o documento não confere com o registro original

### Cenário de abuso: Documento forjado com protocolo reutilizado é rejeitado e a tentativa é registrada

Dado que um atacante possui uma certidão negativa de débitos válida emitida para o contribuinte A
E o atacante fabrica um documento para o contribuinte B reutilizando o número de protocolo e a assinatura digital da certidão de A
Quando o atacante submete o documento fabricado para validação
Então o sistema rejeita o documento como não autêntico
E o sistema não revela dados de nenhuma certidão válida
E o sistema registra a tentativa de fraude com os dados do solicitante para auditoria
```

# PROMPT 2

Atue como um Engenheiro de Testes Sênior especializado em JUnit e Segurança
e implemente os cenários de teste em BDD disponibilizados utilizando JUnit 5.
Seu trabalho é somente criar os testes e os *stubs* necessários para que o código
compile. Ao final, os testes devem ser executados e devem **FALHAR** pelo
motivo certo, ou seja, a implementação dos métodos ainda não foi feita.
**NÃO** implemente a lógica dos métodos, apenas crie os testes e os stubs.
Codifique tudo em inglês.

# PROMPT 3

Implemente a lógica **mínima** necessária para que os testes JUnit 5 criados anteriormente passem.
A lógica deve ser suficiente para que cada teste seja aprovado, mas não deve incluir funcionalidades
adicionais ou melhorias.O objetivo é garantir que os testes passem sem introduzir complexidade desnecessária.
Ao final, garanta que os testes executem e passem com sucesso. Você **NÃO** deve alterar os testes JUnit 5
criados anteriormente, apenas implementar a lógica mínima necessária para que eles passem.
Codifique tudo em inglês.

