from Novels_Scraping import Novels_Scraping
def main():
    
    print("\033[1m\033[91m Programa para obtener datos de tus obras\033[0m")
    print("""\033[1m\033[92m Las opciones a considerar son: \n \033[0m
          \033[96m 1. Novelas - Novel Updates \n
          \033[96m 2. Mangas - TuMangaOnline\n
          \033[96m 3. Animes - MyAnimeList\n \033[0m""")
    
    while True:
        x = input("\033[1m\033[92mPorfavor digitar el numero al cual desea acceder: \033[0m ")
        try:
            x = int(x)  # Intenta convertir la entrada a un entero
            if x in (1, 2, 3):
                break
            else:
                print("\033[1m\033[91mError: El número ingresado no es 1, 2 ni 3. Intente nuevamente.\033[0m")
        except ValueError:
            print("\033[1m\033[91mError: Por favor, ingrese un número válido (1, 2 o 3).\033[0m")
            
            
    options={1: Novels_Scraping,
             2:Novels_Scraping,
             3:Novels_Scraping}
            
    user = input("\033[1m .Porfavor digite el nombre de usuario para acceder a la pagina:\n\033[0m ")
    password = input("\033[1m .Porfavor digite la contraseña de la pagina:\n\033[0m ")

    scrap = options[x](user,password)
    scrap.execute() 
    
    


if (__name__ == "__main__"):
    main()