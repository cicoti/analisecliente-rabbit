@echo off
for %%p in (8080 8081 8082 8083 8084) do (
  for /f "tokens=5" %%a in ('netstat -ano ^| findstr :%%p ^| findstr LISTENING') do (
    taskkill /F /PID %%a
  )
)