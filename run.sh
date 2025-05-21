#!/bin/bash

# 1. Criar pasta de build, se não existir
mkdir -p build

# 2. Compilar os arquivos Java
echo "Compilando..."
javac -d build src/app/Main.java src/core/*.java src/utils/*.java

# Verificar se a compilação foi bem-sucedida
if [ $? -eq 0 ]; then
  echo "Compilação concluída com sucesso."
  
  # 3. Executar o programa
  echo "Executando o programa..."
  java -cp build app.Main
else
  echo "Erro na compilação."
fi
