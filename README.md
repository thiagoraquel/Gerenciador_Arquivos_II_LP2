# Gerenciador de arquivos 

## 📖 Descrição
Este projeto é uma aplicação em Java que gerencia arquivos PDF por meio de uma interface de linha de comando. Ele permite que o usuário crie, edite, busque e exclua entradas de três tipos principais: **Livro**, **Nota de Aula** e **Slide**. Cada entrada contém metadados informados pelo usuário, e os arquivos PDF são organizados em uma estrutura de diretórios por autor.

A aplicação segue o paradigma da programação orientada a objetos e é construída com uma **estrutura de Game Loop**, permitindo que o usuário interaja continuamente com o sistema até escolher sair. Os dados da biblioteca ativa são persistidos em um arquivo `.txt` na pasta `data/`.

## ✨ Funcionalidades
- Criação de entradas (livro, nota de aula, slide)
- Organização por autor e tipo
- Persistência via arquivo de texto
- Estrutura de diretórios configurável
- Game Loop interativo
- Suporte à alternância entre bibliotecas
- Menu em terminal com navegação simples

## 📁 Estrutura de Pastas
```
pdf-library-manager/
├── src/                # Código-fonte em Java
│   ├── app/            # Ponto de entrada e loop principal
│   ├── core/           # Lógica de negócio (biblioteca, entradas, persistência)
│   ├── menu/           # Menu textual interativo
│   └── utils/          # Funções auxiliares (como cópia de arquivos)
├── data/               # Arquivo config.txt com caminho da biblioteca ativa
├── build/              # Arquivos compilados .class
├── README.md           # Este arquivo
├── run.sh              # Script para compilar e rodar
└── .gitignore          # Arquivos a serem ignorados no Git
```

## ⚙️ Como Compilar e Executar

### Pré-requisitos
- Java 11 ou superior instalado
- Terminal com acesso a comandos `javac` e `java`

### 1. Compilar
```bash
javac -d build src/**/*.java
```

### 2. Executar
```bash
java -cp build app.Main
```

### 3. Alternativamente, use o script:
```bash
./run.sh
```

```

## 👥 Autores
- Marcos Antônio Fontes Leite
- [Adicione o nome dos colegas do grupo aqui]


---
> Projeto acadêmico desenvolvido como parte da disciplina DIM0116 - Linguagem de Programação II (2025.1)
