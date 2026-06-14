# Manual do Programador: Gestão de Contactos

**Programação Orientada a Objetos (2025/2026)**

> Aluno 1: **Diego Laya**, nº **2025154378**
> Aluno 2: **Luis Junqueira**, nº **2025168125**

---

## 1. Introdução

Aplicação **Java Consola** para gestão de uma agenda de contactos. Permite
listar, acrescentar (garantindo a unicidade da informação), remover (total ou
parcialmente), encontrar e produzir estatísticas. A informação é **lida de um
ficheiro no arranque** e **gravada antes de o programa terminar**, garantindo que
cada utilização trabalha com a informação mais atual.

Dois tipos de contactos:

- **Pessoais**: nome e lista de entradas de contacto.
- **Profissionais**: o anterior e o nome da empresa.

A distinção é feita pela `Identificacao`: empresa vazia indica contacto pessoal;
empresa preenchida indica contacto profissional.

## 2. Compilar e executar

```bash
javac -encoding UTF-8 -d out src/trabfinal/*.java
java -cp out trabfinal.TrabFinal
```

O programa cria/atualiza o ficheiro de dados **`contactos.txt`** na pasta onde é
executado (definido em `TrabFinal.FICHEIRO_DADOS`).

## 3. Arquitetura e relações entre classes

```
TrabFinal ---> Menu ---> GestorContactos  <>--1..*  Contacto  <*>--1  Identificacao
                |                                       |
                +---> GestorFicheiros                   <>--1..*  EntradaContacto ---> TipoContacto (enum)
```

(`<>` agregação, `<*>` composição.) Diagrama de classes completo em
**`src/Diagram Classes.drawio`** (abrir com [draw.io](https://app.diagrams.net)).

**Separação de responsabilidades (camadas):**

| Camada | Classe(s) | Responsabilidade |
|--------|-----------|------------------|
| Interação | `Menu`, `TrabFinal` | Ler opções do utilizador e mostrar resultados |
| Modelo / negócio | `GestorContactos`, `Contacto`, `Identificacao`, `EntradaContacto`, `TipoContacto` | Guardar e manipular dados; regras de unicidade |
| Persistência | `GestorFicheiros` | Ler/gravar a agenda e exportar listagens |

As classes do modelo **não conhecem** `Scanner` nem `System.out`, e a manipulação
de ficheiros está toda em `GestorFicheiros`. Assim é possível trocar a interface
(ex.: GUI) ou o formato do ficheiro sem alterar o modelo.

## 4. Classes

### 4.1 `TrabFinal` (principal)
Carrega a agenda, arranca o `Menu`, que no fim grava a informação.
- **Atributos:** `FICHEIRO_DADOS: String` (estático).
- **Métodos:** `main(String[]): void`.

### 4.2 `Menu`
Toda a interação com o utilizador.
- **Atributos:** `gestor: GestorContactos`, `scanner: Scanner`, `ficheiroDados: String`, `runNumber: int`.
- **Métodos principais:** `menuInicial(): void` (ciclo), `mostrarListaContactos()`,
  `acrescentarContacto()`, `removerContacto()`, `encontrarContactos()`,
  `mostrarEstatisticas()`. Apoio: `carregarContactos()` (lê do ficheiro no arranque),
  `perguntarEscreverFicheiro(String)` (exporta listagem), `lerTipoContacto()`.

### 4.3 `GestorContactos`
Lógica de negócio sobre a lista de contactos.
- **Atributos:** `listaContactos: ArrayList<Contacto>`.
- **Métodos principais:**
  - `acrescentarContacto(Contacto): void`, `removerContacto(int): void`, `getContacto(int): Contacto`.
  - `encontrarInformacao(String): ArrayList<Integer>`: índices dos contactos que satisfazem o critério.
  - `encontrarContactosRepetidos(Contacto): Contacto`: deteta identificação duplicada.
  - `encontrarInformacaoRepetidaContactos(Contacto): ArrayList<Contacto>`: deteta informação repetida.
  - `estatisticas(): String`: contagem por tipo (usa `HashMap<TipoContacto,Integer>`).

### 4.4 `GestorFicheiros` (manipulação de ficheiros)
Classe utilitária (só métodos `static`).
- **Métodos principais:**
  - `carregar(String): ArrayList<Contacto>`: lê o ficheiro de dados (lista vazia se não existir).
  - `guardar(ArrayList<Contacto>, String): boolean`: grava toda a agenda.
  - `exportarTexto(String, String): boolean`: grava uma listagem num ficheiro de texto à escolha.

**Formato do ficheiro de dados** (uma linha por registo, campos separados por TAB,
codificação **UTF-8**):

```
C<TAB>nome<TAB>empresa      (início de um contacto)
E<TAB>TIPO<TAB>valor        (entrada; TIPO = TELEFONE|TELEMOVEL|MAIL)
```

### 4.5 `Contacto`
Uma `Identificacao` e a lista das suas `EntradaContacto`.
- **Atributos:** `identificacao: Identificacao`, `entradas: ArrayList<EntradaContacto>`.
- **Métodos principais:** `addEntradaContacto(EntradaContacto)`,
  `removerEntradaContacto(EntradaContacto): boolean`, `temInformacao(EntradaContacto): boolean`,
  `temAlgumaInformacaoIgual(Contacto): boolean`, `acrescentarInformacao(Contacto)`,
  `procurarInformacaoParcial(String): ArrayList<EntradaContacto>`.

### 4.6 `Identificacao`
- **Atributos:** `nome: String`, `empresa: String` (ambos `final`; `nome` validado não-vazio).
- **Métodos principais:** `getNome()`, `getEmpresa()`,
  `procurarIdentificacaoParcial(String): boolean`, `equals()`/`hashCode()`.

### 4.7 `EntradaContacto`
- **Atributos:** `tipo: TipoContacto`, `valor: String` (validados no construtor).
- **Métodos principais:** `getTipo()`, `getValor()`, `temMesmoValor(EntradaContacto): boolean`,
  `procurarEntradaParcial(String): boolean`, `equals()`/`hashCode()`.

### 4.8 `TipoContacto` (enum)
Valores: `TELEFONE`, `TELEMOVEL`, `MAIL`.
- **Métodos:** `fromInt(int): TipoContacto` (opção 1..3 para o tipo), `toString()` (apresentação com acentos).

## 5. Regras de unicidade (Acrescentar)

1. **Identificação igual** (nome+empresa) a um existente: acrescentar a info ao
   existente, ou desistir.
2. **Informação igual** (telefone/telemóvel/mail) noutros contactos: acrescentar
   a info ao existente, acrescentar o novo, ou desistir.
3. **Informação nova**: o contacto é adicionado.

Dentro do mesmo contacto, `temInformacao`/`acrescentarInformacao` evitam valores repetidos.

## 6. Como estender

- **Novo tipo de contacto** (ex.: `FAX`): acrescentar ao enum `TipoContacto` e
  tratar em `fromInt()`/`toString()`. Estatísticas e persistência adaptam-se.
- **Validação de dados** (ex.: telemóvel = 9 dígitos): acrescentar em
  `EntradaContacto` ou no `Menu`, usando `String.matches` (expressões regulares).
- **Outro formato de ficheiro** (CSV/JSON): alterar apenas `GestorFicheiros`.
- **Importar de um ficheiro à escolha** (funcionalidade futura): acrescentar uma
  opção no menu que permita ao utilizador indicar/escolher o ficheiro de onde
  importar contactos para a agenda, em vez de usar apenas o `contactos.txt` fixo.
  Reutiliza `GestorFicheiros.carregar(nome)`: basta pedir o nome do ficheiro e
  juntar os contactos lidos à agenda, aplicando as mesmas verificações de unicidade
  da opção "Acrescentar Contacto".
