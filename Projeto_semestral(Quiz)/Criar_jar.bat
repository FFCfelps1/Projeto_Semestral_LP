@echo off
REM Automação para rodar o Projeto Semestral (Quiz)

REM Verifica se o .jar pode ser executado diretamente
echo Tentando rodar o .jar diretamente...
java -jar Projeto_semestral(Quiz).jar
if %ERRORLEVEL% EQU 0 (
    echo O .jar foi executado com sucesso!
    exit /b
)

REM Verifica se todas as classes estão no .jar
echo Verificando o conteúdo do .jar...
jar tf Projeto_semestral(Quiz).jar > jar_contents.txt
findstr /c:"QuizApp.class" jar_contents.txt >nul
if %ERRORLEVEL% NEQ 0 (
    echo As classes não estão no .jar. Recriando o .jar...
    cd bin
    jar cfe "../Projeto_semestral(Quiz).jar" QuizApp *
    cd ..
)

REM Cria um .jar "fat" contendo o MySQL Connector
echo Criando um .jar "fat" com o MySQL Connector...
mkdir temp
cd temp
jar xf "../Projeto_semestral(Quiz).jar"
jar xf ..\lib\mysql-connector-j-8.4.0.jar
jar cfe ../Projeto_semestral_fat.jar QuizApp *
cd ..
rmdir /s /q temp

REM Tenta rodar o .jar "fat"
echo Tentando rodar o .jar "fat"...
java -jar Projeto_semestral_fat.jar
if %ERRORLEVEL% EQU 0 (
    echo O .jar "fat" foi executado com sucesso!
    exit /b
)

echo Não foi possível executar o .jar. Verifique os passos manualmente.
exit /b 1