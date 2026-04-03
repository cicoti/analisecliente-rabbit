@echo off
start "AppSolicitacaoAnaliseCliente" cmd /k "cd /d C:\projetos\ctech\analisecliente\rest\AppSolicitacaoAnaliseCliente && mvn spring-boot:run"
start "AppVerificacaoCreditoCliente" cmd /k "cd /d C:\projetos\ctech\analisecliente\rest\AppVerificacaoCreditoCliente && mvn spring-boot:run"
start "AppVerificacaoFichaLimpaCliente" cmd /k "cd /d C:\projetos\ctech\analisecliente\rest\AppVerificacaoFichaLimpaCliente && mvn spring-boot:run"
start "AppVerificacaoStatusCliente" cmd /k "cd /d C:\projetos\ctech\analisecliente\rest\AppVerificacaoStatusCliente && mvn spring-boot:run"
start "AppAgregadorAnaliseCliente" cmd /k "cd /d C:\projetos\ctech\analisecliente\rest\AppAgregadorAnaliseCliente && mvn spring-boot:run"