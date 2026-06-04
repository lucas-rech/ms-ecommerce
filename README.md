# E-Commerce (Microsserviço)

Esse é um projeto cujo intuito é desenvolver um modelo experimental de um e-commerce, separado por microsserviços, que se conversam através de mensageria. O objetivo desse projeto é desenvolver habilidades desde a projeção de um sistema, até o deploy, passando por etapas como System Design, Entity Relationship, configuração de pipeline, com integridade de testes, configurações de DevOps como Kubernetes e Nginx, até o código, propriamente, utilizando de abordagens de Clean Code, Design Patterns etc.

Se trata de um projeto extenso, mas com o intuito de trazer um cenário real de desenvolvimento de um software.


## Como entender o projeto?

Em suma, o projeto está centralizando todos os microsserviços nesse único repositório. Esse é um dos poucos "anti-patterns" que estou adotando, mas apenas com o intuito de facilitar a compreensão geral do sistema de quem estiver visualizando.

Dividimos o projeto em sub-diretórios, da seguinte forma:


```
/auth-servie                # Microsserviço de autenticação de usuários
/customer-service           # Microsserviço de gerenciamento de clientes
/inventory-service          # Microsserviço de estoque e concorrência de baixa
/product-service            # Microsserviço de gerenciamento de produtos
/order-service              # Microsserviço de criação e gestão de pedidos
/payment-service            # Microsserviço de gestão de faturas, pagamentos e notas
/infra                      # Arquivos de configurações de infra (nginx, docker, etc.)
/docs                       # Arquivos de documentação do projeto (diagramas, how-to-do's, etc.)
README.md                   # Descrição geral do projeto
```

Ps: Cada repositório terá seu próprio arquivo README, com instruções para instalar o projeto e rodar.