# Estágio 1: Build da Aplicação com Maven
# Usa uma imagem que já contém o Maven e o JDK 17
FROM maven:3.9-eclipse-temurin-17 AS builder

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia os arquivos de definição do projeto e dependências primeiro
# Só baixa dependências de novo se o pom.xml mudar.
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Baixa as dependências do projeto
RUN mvn dependency:go-offline

# Copia o código-fonte da aplicação
COPY src ./src

# Compila a aplicação e gera o .jar, pulando os testes
RUN mvn package -DskipTests

# Estágio 2: Execução da Aplicação
# Usa uma imagem JRE (Java Runtime Environment) mínima para reduzir o tamanho final
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

# Copia o .jar gerado no estágio de build para o estágio de execução
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta que a aplicação Spring Boot usa por padrão
EXPOSE 8080

# Comando para iniciar a aplicação quando o contêiner for executado
ENTRYPOINT ["java", "-jar", "app.jar"]