# messaging-sdk

## Visão Geral

O `messaging-sdk` é um componente Java (biblioteca) para integração de serviços de mensageria, como e-mail e WhatsApp, em aplicações Spring Boot. Ele foi projetado para ser utilizado como dependência em outros projetos, fornecendo uma arquitetura flexível, desacoplada e fácil de configurar.

## Arquitetura

O projeto segue uma arquitetura em camadas, separando responsabilidades em:

- **application/**: Casos de uso e orquestração de fluxos de negócio.
- **config/**: Configurações automáticas, propriedades e health checks do componente.
- **domain/**: Interfaces e contratos de negócio (ex: IEmailService, IWhatsappService).
- **infrastructure/**: Implementações concretas dos serviços de mensageria (ex: envio real de e-mail/WhatsApp).

### Componentes principais
- **MensageriaAutoConfiguration**: Configuração automática do Spring Boot para registrar beans do SDK.
- **MensageriaProperties**: Propriedades configuráveis via application.yml para e-mail e WhatsApp.
- **DefaultEmailService / DefaultWhatsappService**: Implementações padrão dos serviços de envio.
- **HealthIndicator**: Exposição do status da mensageria no actuator/health.

## Configuração

Adicione o SDK como dependência no seu projeto Maven:
```xml
<dependency>
  <groupId>com.messaging</groupId>
  <artifactId>messaging-sdk</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

No seu `application.yml` ou `application.properties`, configure as propriedades necessárias:
```yaml
mensageria:
  email:
    from: "seu@email.com"
    host: "smtp.seuprovedor.com"
    port: 587
    username: "usuario"
    password: "senha"
  whatsapp:
    api-url: "https://api.whatsapp.com/send"
    token: "SEU_TOKEN"
```

## Como Utilizar

1. Importe o SDK no seu projeto.
2. Configure as propriedades conforme acima.
3. Injete e utilize os serviços:

```java
@Autowired
private IEmailService emailService;

@Autowired
private IWhatsappService whatsappService;

// Exemplo de uso
emailService.enviarEmail("destino@email.com", "Assunto", "Mensagem");
whatsappService.enviarMensagem("5511999999999", "Mensagem WhatsApp");
```

## Como Testar

O projeto possui testes unitários utilizando Mockito para simular dependências externas, tornando os testes rápidos e confiáveis.

Para rodar os testes:
```bash
mvn clean test
```

Os testes cobrem:
- Envio de mensagens (mockando RestTemplate)
- Health check do actuator (mockando dependências)
- Configuração automática

## Observações
- O projeto **não** possui classe main e não gera um jar executável, apenas biblioteca.
- Para uso em produção, configure corretamente as propriedades de mensageria.
- Para customizações, implemente suas próprias versões das interfaces de domínio e registre como beans Spring.

## Diagrama de Fluxo das Funcionalidades Principais

```text
+-------------------+
|  Projeto Cliente  |
+-------------------+
          |
          v
+-------------------+
| Injeta Interfaces |
| IEmailService     |
| IWhatsappService  |
+-------------------+
          |
          v
+-------------------------------+
| messaging-sdk (AutoConfig)     |
| - MensageriaAutoConfiguration  |
| - MensageriaProperties         |
+-------------------------------+
          |
          v
+-------------------------------+
| Implementações Padrão         |
| - DefaultEmailService         |
| - DefaultWhatsappService      |
+-------------------------------+
          |
          v
+-------------------------------+
| Serviços Externos             |
| (SMTP, API WhatsApp, etc)     |
+-------------------------------+
```

## Sobre o arquivo ManagementContextConfiguration.imports

O arquivo `src/main/resources/META-INF/org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration.imports` existe para registrar configurações customizadas de management/actuator no contexto de gerenciamento do Spring Boot.

**Motivo:**
- Permite que a classe `MensageriaManagementConfiguration` seja automaticamente importada e aplicada apenas ao contexto de management (actuator), sem afetar o contexto principal da aplicação.
- Segue o padrão de auto-configuração do Spring Boot 3+ (e 4), que utiliza arquivos `.imports` para registrar configurações de forma modular e desacoplada.
- Garante que endpoints, health checks ou beans específicos de actuator sejam carregados corretamente quando o actuator está habilitado, sem poluir o contexto principal.

**Quando usar:**
- Sempre que precisar adicionar configurações, beans ou endpoints exclusivos para o actuator/management, utilize esse arquivo para registrar suas classes de configuração.

**Referência:**
- [Spring Boot Docs: ManagementContextConfiguration](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.customizing-management-context)

## Sobre o arquivo spring.factories

O arquivo `src/main/resources/META-INF/spring.factories` é fundamental para que o Spring Boot reconheça e registre automaticamente a configuração do seu componente/biblioteca.

**Motivo:**
- Permite que a classe `MensageriaAutoConfiguration` seja descoberta automaticamente pelo mecanismo de auto-configuração do Spring Boot, sem necessidade de configuração manual pelo usuário do SDK.
- Segue o padrão de auto-configuração do Spring Boot, onde qualquer biblioteca pode registrar beans, propriedades e integrações de forma plugável.
- Garante que, ao importar o SDK como dependência, todas as configurações, beans e integrações sejam ativadas automaticamente, facilitando o uso e reduzindo erros de configuração.

**Quando usar:**
- Sempre que criar um starter/component para Spring Boot que precise registrar configurações, beans ou integrações automaticamente.

**Referência:**
- [Spring Boot Docs: Auto-configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-auto-configuration)

## Sobre a classe MensageriaManagementConfiguration e @ManagementContextConfiguration

A classe `MensageriaManagementConfiguration` foi criada para isolar configurações e beans que devem ser carregados apenas no contexto de management do Spring Boot (ou seja, para endpoints do actuator, health checks, etc.).

**Motivo da existência da classe:**
- Permite registrar beans como o `MensageriaHealthIndicator` apenas no contexto de management, evitando poluir o contexto principal da aplicação.
- Facilita a manutenção e a separação de responsabilidades, deixando claro o que é específico do actuator/management.
- Segue as melhores práticas de modularização e desacoplamento em projetos Spring Boot.

**Sobre o uso de @ManagementContextConfiguration:**
- A anotação `@ManagementContextConfiguration` indica ao Spring Boot que essa configuração deve ser aplicada somente ao contexto de management (actuator), e não ao contexto principal da aplicação.
- Isso garante que beans como health indicators, endpoints customizados ou configurações sensíveis de monitoramento não sejam carregados no contexto principal, melhorando a segurança e a organização do projeto.
- É o padrão recomendado para customizações do actuator a partir do Spring Boot 2.6+.

**Referência:**
- [Spring Boot Docs: ManagementContextConfiguration](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.customizing-management-context)

## Sobre o uso de @ConditionalOnMissingBean e @ConditionalOnProperty

No arquivo de configuração `MensageriaAutoConfiguration`, são utilizados os annotations `@ConditionalOnMissingBean` e `@ConditionalOnProperty` para tornar a auto-configuração do SDK flexível e não invasiva.

**@ConditionalOnMissingBean:**
- Garante que o bean só será criado se não houver outro bean do mesmo tipo já registrado no contexto Spring.
- Permite ao usuário do SDK sobrescrever a implementação padrão (por exemplo, criar seu próprio `IEmailService` ou `IWhatsappService`) sem conflito.
- Segue o padrão de extensibilidade do Spring Boot, facilitando customizações.

**@ConditionalOnProperty:**
- Permite ativar ou desativar a criação do bean via propriedade de configuração (ex: `mensageria.email.enabled=true/false`).
- Dá controle ao usuário para habilitar/desabilitar funcionalidades do SDK sem alterar código, apenas via configuração.
- O parâmetro `matchIfMissing = true` garante que, se a propriedade não estiver definida, o bean será criado por padrão (comportamento opt-out).

Essas anotações tornam o SDK mais seguro para uso em projetos diversos, evitando conflitos e facilitando a configuração e extensão.

## Referências
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Mockito](https://site.mockito.org/)
- [Documentação oficial do projeto](./HELP.md)