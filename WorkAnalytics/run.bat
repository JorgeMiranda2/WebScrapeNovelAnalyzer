@echo off

rem Instalar los requisitos
python -m pip install --upgrade pip
python -m pip install -r requirements.txt

rem Verificar la instalación de 'flet'
python -c "import flet" 2> nul
if %errorlevel% neq 0 (
    echo Fallo al instalar o verificar los requisitos.
    pause
    exit /b %errorlevel%
)

rem Ejecutar el script principal
python run.py
if %errorlevel% neq 0 (
    echo Fallo al ejecutar el script principal.
    pause
    exit /b %errorlevel%
)

pause
