import json
from Convert_To_Csv import Convert_To_Csv 
from Base_Scraping import Base_Scraping
from selenium.webdriver.common.by import By  
from selenium.webdriver.support import expected_conditions as ec
from selenium.webdriver.common.keys import Keys  
import undetected_chromedriver as uc 
from bs4 import BeautifulSoup
from selenium.common.exceptions import TimeoutException
import time
from selenium.common.exceptions import StaleElementReferenceException


# Open the JSON file and load the data into a Python variable
with open('Config/config.json', 'r') as json_file:
    json_data = json.load(json_file)
    
class Mangas_Scraping(Base_Scraping):
    def __init__(self, user, password, idiom): 
        website_type = "web_manga_site"
        self.idiom = idiom
        super().__init__(user, password, website_type)
   
    
        # Obtaining scraped elements:
             
    # get the work title
    def obtain_title(self):
        element_text = self.soup.find("h1", class_="element-title my-2")
        element_text = ''.join(element_text.find_all(text=True, recursive=False)).strip()
        print(element_text)
        return element_text
    
    def obtain_demography(self):
        element_text = self.soup.find("div",class_="element-image my-2").find("div",class_="demography").text.strip()
        print(element_text)
        return element_text
    
    def obtain_work_year(self):
        element_text = self.soup.find("h1", class_="element-title my-2").find("small", class_="text-muted").text
        element_text = element_text.translate(str.maketrans('', '', '()')).strip()
        print(element_text)
        return element_text
            
    #  retrieve the work languaje origin   
    def obtain_languaje(self):
        element_text = self.soup.find("div",id="showlang").find("a").text.strip()
        print(element_text)
        return element_text
   
    # retrieve the author's name of the work
    def obtain_author(self):
        element_text = self.soup.find("a",id="authtag").text.strip()
        print(element_text)
        return element_text
        
        
    def obtain_rating(self):
        max_rating = 10
        element_text = self.soup.find("div",class_="element-image my-2").find("div",class_="score").find("a").text.strip()
        element_text += "/" + str(max_rating)
        print(element_text)
        return element_text
    
    def obtain_genres(self):
        try:
            separator = ", "
            elements_text = self.soup.find("div",class_="col-12 col-md-9 element-header-content-text").findAll("a",class_="badge badge-primary py-2 px-4 mx-1 my-2")
            
            elements = [element.text.strip() for element in elements_text] 
            print(separator.join(elements))
            return separator.join(elements)
        except:
            print("error trying to get genres")
            return None
    
    
    # Method to do the diferents operations from the specific work and get the scraping data
    def obtain_year(self):
        try:
            element_text = self.soup.find("h1", class_="element-title my-2").find("small", class_="text-muted").text
            element_text = element_text.translate(str.maketrans('', '', '()')).strip()
            print(element_text)
            return element_text
        except:
            return None
    
    def obtain_tags(self):
        elements = []
        separator = ", "
        elements_text = self.soup.find("div",id="showtags").findAll("a",id="etagme")
        elements = [element.text.strip() for element in elements_text] 
        return separator.join(elements)
    
    def obtain_image(self):
        try:
            element = self.soup.find("div",class_="element-image my-2").find("img").get("src").strip()
            print(element)
            return element
        except:
            print("error trying to get image")
            return None
    
    
    def test_service(self):
        self.connect()
        self.driver.get("https://www.novelupdates.com/series/i-became-a-flashing-genius-at-the-magic-academy/")
        soup = BeautifulSoup(self.driver.page_source, "html.parser")
        self.soup = soup
        self.browse_sections_list()
        
        
    def browse_part_list(self, list_name):
        
        while True:
            try:
                self.browse_work_pages(list_name)
                # Obtener todos los elementos <a> dentro del <nav> con class="flex justify-between"
                #elements = self.wait.until(ec.presence_of_all_elements_located((By.XPATH, "//nav[@class='flex justify-between']//a")))
                # Iterar a través de los elementos y buscar el que tiene rel="next"
                next_button = self.wait.until(ec.presence_of_element_located((By.XPATH, "//nav[@class='flex justify-between']//a[@rel='next']")))
                next_button.click()
                time.sleep(1)
            except Exception as e:
                print(f"Ocurrió un error o se alcanzó el final de la paginación:  {e}"  )
                break

        
    # Method to do the diferents operations and get the total page scraping data
    def execute(self):
        try:
            self.connect()
            self.login()
            self.change_idiom_page()
            self.go_to_list()
            self.browse_sections_list()
            print(self.data)
            Convert_To_Csv(self.data, "Mangas.csv")
        except Exception as e:
            print(f"An error has ocurred in the process: {e}" )
        self.driver.close()
    
    def change_idiom_page(self):
        print("changing idiom")
        time.sleep(1)
        try:
            e = self.wait.until(ec.element_to_be_clickable((By.XPATH, "//a[@id='navbarDropdownLanguage']")))
            e.click()
            e = self.wait.until(ec.presence_of_all_elements_located((By.XPATH, "//div[@class='dropdown-menu dropdown-menu-right show']/a/span")))
            e[int(self.idiom)-1].click()
            
        except TimeoutException:
            print("not found element at the estimated time")

    def browse_sections_list(self):
        print("Searching for sections ")
        try:
            time.sleep(1)
            self.soup = BeautifulSoup(self.driver.page_source, "html.parser") 
            elements_works = self.wait.until(ec.presence_of_all_elements_located((By.XPATH, "//div[@class='d-flex element-header-bar-elements flex-row flex-wrap align-items-center']/div/a")))
            list_names = [element.text.strip() for element in elements_works]
            work_links = [work.get_attribute("href") for work in elements_works]
            
            print("sections charged")
          
            
            for i, (link,list_name) in enumerate(zip(work_links,list_names)):
                print(len(work_links))
                try:
                    try:
                        self.driver.get(link)
                    except TimeoutException as e:
                        print("Timeout, next item  !!!")
                    print(list_name)
                    list_name = list_name.split("\n")
                    print(list_name)
                    list_name = list_name[1]
                    #self.browse_work_pages()
                    self.browse_part_list(list_name)
                    print("alcanza")
                    # Actualizar la lista de elementos después de volver atrás
                    elements_works = self.wait.until(ec.presence_of_all_elements_located((By.XPATH, "//div[@class='d-flex element-header-bar-elements flex-row flex-wrap align-items-center']/div/a")))
                    elements_works = [work.get_attribute("href") for work in elements_works]
                    
                except StaleElementReferenceException:
                    # Manejar la excepción StaleElementReferenceException y continuar con el siguiente enlace
                    print(f"element not working in {i}, passing to the next.")
                    continue
                time.sleep(1)
        except Exception as e:
            print(f"Error en browse_sections_list: {e}")

# Resto de tu código

                
    def browse_work_pages(self, list_name):
        try:
            print("Trying to get elements from every work in the list")
            # Obtener todas las URL de los trabajos
            elements_works = self.wait.until(ec.presence_of_all_elements_located((By.XPATH, "//div[@class='row']//div[@class='element  proyect-item col-6 col-sm-6 col-md-4 col-lg-3 mt-2  ']/a")))
            work_links = [work.get_attribute("href") for work in elements_works]
            print(work_links)
            # Obtain every work and do the web scraping
            for link in work_links:
                try:
                    self.driver.get(link)  # Volver a la página del trabajo
                except TimeoutException as e:
                    print("Page load Timeout Occured ... moving to next item !!!")
                    time.sleep(1)
                    
                self.do_web_scraping(list_name)
                time.sleep(4)
                try:
                    self.driver.back()
                except TimeoutException as e:
                    print("Page load Timeout Occured ... moving to next item !!!")
                    time.sleep(2)
       
        

        except KeyError as e:
            print("Something went wrong when going through works pages")
            
            
    def do_web_scraping(self, list_name):
        try: 
            self.soup = BeautifulSoup(self.driver.page_source, "html.parser")
            self.data.append({
            "Title":self.obtain_title(),
            #"Author":self.obtain_author(),
            "Rating":self.obtain_rating(),
            #"Languaje":self.obtain_languaje(),
            "Image":self.obtain_image(),
            "Demography":self.obtain_demography(),
            "Genres":self.obtain_genres(),
            "Year":self.obtain_year(),
            "List":list_name
            })
        except Exception as e:
            print(f"An error has occurred during web scraping: {e}")
    
    
    
    # Method to login in the page
    def login(self,time_max=10):
        print("trying to login in the page")
        self.check_cookies()
        self.go_to_page()
        time.sleep(1)
        login = self.login_verifier()
        print("4")
        if (not login):
            while time_max > 0:
                try:    
                    self.driver.get("https://lectortmo.com/login")
                    time.sleep(2)   
                    e = self.wait.until(ec.element_to_be_clickable((By.ID, "email")))
                    e.send_keys(self.user)
                    time.sleep(2)   
                    e = self.wait.until(ec.element_to_be_clickable((By.ID, "password")))
                    e.send_keys(self.password)
                    time.sleep(2)   
                    e = self.wait.until(ec.element_to_be_clickable((By.CLASS_NAME, "custom-control-label")))
                    if (not e.is_selected()):
                        e.click()
                    time.sleep(1)   
                    e = self.wait.until(ec.element_to_be_clickable((By.CSS_SELECTOR, "button[class='btn btn-block btn-lg btn-primary']")))
                    e.click() 
                    self.save_cookies()
                    break
                except KeyError as e:
                    print(f"error trying to logued: {e}")
                time_max-=1
                    
    
    # Method to validate if cookies exist and is logued
    def login_verifier(self):
        try:
            e = self.wait.until(ec.element_to_be_clickable((By.XPATH, "//a[@id='navbarDropdownMenuLink']")))
            #e.click() 
            return True  
        except:
            return False    
              
        
        
        
        
    def enter_to_list_page(self):
        pass
    
    def enter_to_login_page(self):
        pass
        
        

