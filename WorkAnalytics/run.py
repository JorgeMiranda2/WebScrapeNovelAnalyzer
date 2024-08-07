import flet as ft
from Install_Requirements import install_requirements
from Novels_Scraping import Novels_Scraping
from Mangas_Scraping import Mangas_Scraping
from Animes_Scraping import Animes_Scraping
import json

def main(page: ft.Page):
    
    page.window_height = 480
    page.window_width = 640
    page.window_resizable = False
    page.padding = 0
    page.vertical_alignment = ft.MainAxisAlignment.CENTER
    page.horizontal_alignment = ft.CrossAxisAlignment.CENTER
    type_number = 0

    def get_info(name, password):
        save_path()
        global type_number

        options={1: Novels_Scraping,
                2:Mangas_Scraping,
                3:Animes_Scraping}
        
        print(f"type: ", type_number, " name: ", name, " password: ", password)
        scrap = options[type_number](name,password)
        scrap.execute() 
        
    # Open directory dialog
    def get_directory_result(e: ft.FilePickerResultEvent):
        directory_path.value = e.path if e.path else "Cancelled!"
        directory_path.update()

    get_directory_dialog = ft.FilePicker(on_result=get_directory_result)
    directory_path = ft.Text()

    def obtain_result(ex):
        name = name_input.value
        password = password_input.value
        get_info(name, password)
       
    def save_path():
        # Abrir el archivo JSON y cargar los datos
        with open('Config/config.json', 'r') as f:
            data = json.load(f)

        # Cambiar el valor de la clave 'clave'
        data['path'] = directory_path.value

        # Guardar los cambios en el archivo JSON
        with open('Config/config.json', 'w') as f:
            json.dump(data, f, indent=4)



    # hide all dialogs in overlay
    page.overlay.extend([get_directory_dialog])

    # Definir las imágenes y contenedores

    descripcion = ft.Container(
        content=ft.Text(
            "Select the page from which to get the information:",
            color=ft.colors.BLACK,
            weight=ft.FontWeight.NORMAL,
            size=18,
            italic=True
        ),
        margin=ft.margin.only(bottom=20),
        width=540,
    )

    def change_to_page2(num):
        global type_number
        type_number = num
        print(f"type_number: " , type_number)
        page.remove(pagina1)
        page.add(pagina2)

    def change_to_page1(e):
        page.remove(pagina2)
        page.add(pagina1)


    directory = ft.Container(
    #border=ft.border.all(color=ft.colors.BLACK, width=1), 
    margin = ft.margin.symmetric(horizontal=30),
    content=ft.Row(
        [
            ft.ElevatedButton(
                "Open directory",
                icon=ft.icons.FOLDER_OPEN,
                on_click=lambda _: get_directory_dialog.get_directory_path(),
                disabled=page.web,
            ),
            directory_path,
        ]
    )
)
    
    name_input = ft.TextField(label="Your name", icon=ft.icons.ACCOUNT_CIRCLE)
        
                              
    password_input = ft.TextField(label="Your password", icon=ft.icons.PASSWORD, password=True, can_reveal_password=True)

    submit_button = ft.ElevatedButton(
            adaptive=True,  # a CupertinoButton will be rendered when running on apple-platform
            content=ft.Row(
                [
                    ft.Icon(name="send"),
                    ft.Text("Get", size=16,weight=ft.FontWeight.BOLD ),
                ],
                tight=True,
            ),
            on_click=obtain_result
        )
    
    cancel_button = ft.ElevatedButton(
            adaptive=True,  # a CupertinoButton will be rendered when running on apple-platform
            content=ft.Row(
                [
                    ft.Icon(name="arrow_back"),
                    ft.Text("Back", size=16,weight=ft.FontWeight.BOLD ),
                ],
                tight=True,
            ),
            on_click=change_to_page1
        )
    
    form_title = ft.Container(
        content=ft.Text(
            "Log in with the account information from the selected page:",
            color=ft.colors.BLACK,
            weight=ft.FontWeight.NORMAL,
            size=18,
            italic=True
        ),
        width=540,
    )

    submit_component = ft.Container(ft.Row(
        controls=[cancel_button,submit_button],
        alignment=ft.MainAxisAlignment.END)
      
        )


    formulario = ft.Container(
        # border=ft.border.all(color=ft.colors.BLACK, width=1), 
        content = ft.Column(
        spacing=12,
        controls=[form_title,name_input, password_input, submit_component],
    ),
        margin = ft.margin.symmetric(horizontal=60),
    )

    categorias = ft.Container(
        ft.Column(
            horizontal_alignment=ft.CrossAxisAlignment.CENTER,
            spacing=16,
            controls=[
                ft.Container(
                    width=500,
                    height=40,
                    content=ft.ElevatedButton(
                        content=ft.Container(
                            content=ft.Text("TuMangaOnline", color=ft.colors.BLUE, text_align=ft.TextAlign.CENTER,
                                             weight=ft.FontWeight.BOLD, size=24),

                        ),
                        on_click=lambda _:change_to_page2(2)  # Cambia a la página 2 cuando se hace clic en este botón
                    ),

                ),
                ft.Container(
                    width=500,
                    height=40,
                    content=ft.ElevatedButton(
                        bgcolor="#2c3e50",
                        content=ft.Container(
                            content=ft.Text("NovelUpdates", color=ft.colors.WHITE, text_align=ft.TextAlign.CENTER,
                                             weight=ft.FontWeight.BOLD, size=24),

                        ),
                    on_click=lambda _:change_to_page2(1) 
                    ),
                ),
              
                ft.Container(
                    width=500,
                    height=40,
                    content=ft.ElevatedButton(
                        bgcolor=ft.colors.BLUE,
                        content=ft.Container(
                            content=ft.Text("MyAnimeList", color=ft.colors.WHITE, text_align=ft.TextAlign.CENTER,
                                             weight=ft.FontWeight.BOLD, size=24),

                        ),
                        on_click=lambda _:change_to_page2(3)              
                        ),

                )

            ]
        ),
        margin=ft.margin.only(bottom=60),
        width=540,
        adaptive=True,

    )

    creditos = ft.Container(

        content=ft.Text(
            "Created by @JorgeMiranda2.",

            color="#000000",
            text_align=ft.TextAlign.JUSTIFY,
            weight=ft.FontWeight.BOLD,
            size=12


        ),
        width=640,


    )

    titulo = ft.Container(
        alignment=ft.alignment.center,
        margin=ft.margin.only(top=20),

        width=540,
        height=70,
        content=ft.Stack(
            [
                ft.Text(
                    spans=[
                        ft.TextSpan(
                            "Get your list!",
                            ft.TextStyle(
                                size=40,
                                weight=ft.FontWeight.BOLD,
                                foreground=ft.Paint(
                                    color="#273469",
                                    stroke_width=6,
                                    stroke_join=ft.StrokeJoin.ROUND,
                                    style=ft.PaintingStyle.STROKE,

                                ),
                            ),
                        ),
                    ],
                ),
                ft.Text(
                    spans=[
                        ft.TextSpan(
                            "Get your list!",
                            ft.TextStyle(
                                size=40,
                                weight=ft.FontWeight.BOLD,
                                color="#ffffff",
                            ),
                        ),
                    ],
                ),
            ]
        )

    )

    titulo2 = ft.Container(
        alignment=ft.alignment.center,
        margin=ft.margin.only(top=20),

        width=540,
        height=70,
        content=ft.Stack(
            [
                ft.Text(
                    spans=[
                        ft.TextSpan(
                            "Select account",
                            ft.TextStyle(
                                size=40,
                                weight=ft.FontWeight.BOLD,
                                foreground=ft.Paint(
                                    color="#273469",
                                    stroke_width=6,
                                    stroke_join=ft.StrokeJoin.ROUND,
                                    style=ft.PaintingStyle.STROKE,

                                ),
                            ),
                        ),
                    ],
                ),
                ft.Text(
                    spans=[
                        ft.TextSpan(
                            "Select account",
                            ft.TextStyle(
                                size=40,
                                weight=ft.FontWeight.BOLD,
                                color="#ffffff",
                            ),
                        ),
                    ],
                ),
            ]
        )

    )




    # Contenedor principal para la página 1
    global pagina1
    pagina1 = ft.Container(
        ft.Column(
            spacing=0,
            controls=[titulo, descripcion, categorias, creditos],
            alignment=ft.MainAxisAlignment.SPACE_BETWEEN,
            horizontal_alignment=ft.CrossAxisAlignment.CENTER,
        ),
        width=640,
        height=441,
        bgcolor="#6F7D8C",
        alignment=ft.alignment.top_center  # Centrar verticalmente
    )

    # Contenedor principal para la página 2
    global pagina2
    pagina2 = ft.Container(
        ft.Column(
            spacing=0,
            controls=[titulo2, directory, formulario, creditos ],
            alignment=ft.MainAxisAlignment.SPACE_BETWEEN,
            horizontal_alignment=ft.CrossAxisAlignment.CENTER,
        ),
        width=640,
        height=441,
        bgcolor="#6F7D8C",
        alignment=ft.alignment.top_center  # Centrar verticalmente
    )

    # Agregar la página inicial a la aplicación
    page.add(pagina1)

ft.app(target=main)
