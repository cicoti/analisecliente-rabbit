@echo off
start "AppSolicitacaoAnaliseCliente" cmd /k "cd /d C:\projetos\ctech\analisecliente\rabbitmq\AppSolicitacaoAnaliseCliente && mvn spring-boot:run"
start "AppVerificacaoCreditoCliente" cmd /k "cd /d C:\projetos\ctech\analisecliente\rabbitmq\AppVerificacaoCreditoCliente && mvn spring-boot:run"
start "AppVerificacaoFichaLimpaCliente" cmd /k "cd /d C:\projetos\ctech\analisecliente\rabbitmq\AppVerificacaoFichaLimpaCliente && mvn spring-boot:run"
start "AppVerificacaoStatusCliente" cmd /k "cd /d C:\projetos\ctech\analisecliente\rabbitmq\AppVerificacaoStatusCliente && mvn spring-boot:run"
start "AppAgregadorAnaliseCliente" cmd /k "cd /d C:\projetos\ctech\analisecliente\rabbitmq\AppAgregadorAnaliseCliente && mvn spring-boot:run"