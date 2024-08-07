import subprocess
import os

def install_requirements():
    print("Directorio actual:", os.getcwd())
    try:
        # Leer el archivo requirements.txt
        with open('requirements.txt') as f:
            requirements = f.read().splitlines()
        
        # Instalar los módulos uno por uno
        for requirement in requirements:
            subprocess.check_call(['pip', 'install', requirement])
        
        print('Todos los módulos se han instalado correctamente.')
    
    except Exception as e:
        print('Error al instalar los módulos:', e)

if __name__ == "__main__":
    install_requirements()
