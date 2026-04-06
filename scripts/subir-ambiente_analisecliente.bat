@echo off
title Subir ambiente Analise Cliente
setlocal

echo ========================================
echo FASE 0 - ENCERRANDO PROCESSOS NAS PORTAS
echo ========================================

for %%p in (8080 8081 8082 8083 8084) do (
  echo Verificando porta %%p...
  for /f "tokens=5" %%a in ('netstat -ano ^| findstr :%%p ^| findstr LISTENING') do (
    echo Matando PID %%a na porta %%p...
    taskkill /F /PID %%a
  )
)

echo.
echo Aguardando 3 segundos para liberar as portas...
timeout /t 3 /nobreak >nul

echo.
echo ========================================
echo FASE 1 - COMPILANDO PROJETOS
echo ========================================

cd /d C:\projetos\ctech\analisecliente\rabbitmq\AppSolicitacaoAnaliseCliente
call mvn clean install -DskipTests
if errorlevel 1 goto erro

cd /d C:\projetos\ctech\analisecliente\rabbitmq\AppVerificacaoCreditoCliente
call mvn clean install -DskipTests
if errorlevel 1 goto erro

cd /d C:\projetos\ctech\analisecliente\rabbitmq\AppVerificacaoFichaLimpaCliente
call mvn clean install -DskipTests
if errorlevel 1 goto erro

cd /d C:\projetos\ctech\analisecliente\rabbitmq\AppVerificacaoStatusCliente
call mvn clean install -DskipTests
if errorlevel 1 goto erro

cd /d C:\projetos\ctech\analisecliente\rabbitmq\AppAgregadorAnaliseCliente
call mvn clean install -DskipTests
if errorlevel 1 goto erro

echo.
echo ========================================
echo FASE 2 - SUBINDO APLICACOES
echo ========================================

start "AppSolicitacaoAnaliseCliente" cmd /k "cd /d C:\projetos\ctech\analisecliente\rabbitmq\AppSolicitacaoAnaliseCliente && mvn spring-boot:run"
start "AppVerificacaoCreditoCliente" cmd /k "cd /d C:\projetos\ctech\analisecliente\rabbitmq\AppVerificacaoCreditoCliente && mvn spring-boot:run"
start "AppVerificacaoFichaLimpaCliente" cmd /k "cd /d C:\projetos\ctech\analisecliente\rabbitmq\AppVerificacaoFichaLimpaCliente && mvn spring-boot:run"
start "AppVerificacaoStatusCliente" cmd /k "cd /d C:\projetos\ctech\analisecliente\rabbitmq\AppVerificacaoStatusCliente && mvn spring-boot:run"
start "AppAgregadorAnaliseCliente" cmd /k "cd /d C:\projetos\ctech\analisecliente\rabbitmq\AppAgregadorAnaliseCliente && mvn spring-boot:run"

echo.
echo Aplicacoes iniciadas.
goto fim

:erro
echo.
echo ERRO na compilacao. Processo interrompido.
pause

:fim
endlocal