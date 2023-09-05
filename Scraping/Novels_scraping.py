import json
import sys
from Base_scraping import Base_Scraping
from selenium import webdriver
from selenium.webdriver.common.by import By  
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as ec
from selenium.webdriver.common.keys import Keys  
import undetected_chromedriver as uc 
from bs4 import BeautifulSoup
import time
from selenium.common.exceptions import StaleElementReferenceException
import threading
# Open the JSON file and load the data into a Python variable
with open('Config/config.json', 'r') as json_file:
    json_data = json.load(json_file)
    
class Novels_Scraping(Base_Scraping):
    def __init__(self, user, password): 
        website_type = "web_novel_site"
        super().__init__(user, password, website_type)
   
    
        # Obtaining scraped elements:
             
    # get the work title
    def obtain_title(self):
        element_text = self.soup.find("div",class_="seriestitlenu").text.strip()
        print(element_text)
        return element_text
    
    def obtain_work_year(self):
        element_text = self.soup.find("div",id="edityear").text.strip()
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
        element_text = self.soup.find("span",class_="uvotes").text.strip().split(",")[0][1:].translate(str.maketrans('', '', ' '))
        print(element_text)
        return element_text
    
    def obtain_genres(self):
        separator = ", "
        elements_text = self.soup.find("div",id="seriesgenre").findAll("a",class_="genre")
        
        elements = [element.text.strip() for element in elements_text] 
        print(separator.join(elements))
        return separator.join(elements)
    
    
    # Method to do the diferents operations from the specific work and get the scraping data
    def obtain_work_data(self):
        pass
    
    def obtain_tags(self):
        elements = []
        separator = ", "
        elements_text = self.soup.find("div",id="showtags").findAll("a",id="etagme")
        elements = [element.text.strip() for element in elements_text] 
        return separator.join(elements)
    
    def obtain_image(self):
        element = self.soup.find("div",class_="wpb_row").find("img").get("src")
        print(element)
        return element
    
    
    def test_service(self):
        self.connect()
        self.driver.get("https://www.novelupdates.com/series/i-became-a-flashing-genius-at-the-magic-academy/")
        soup = BeautifulSoup(self.driver.page_source, "html.parser")
        self.soup = soup
        self.browse_section_lists()
        
        
        
        
    # Method to do the diferents operations and get the total page scraping data
    def execute(self):
        self.connect()
        self.login()
        self.go_to_list()
        self.browse_sections_list()
        
    
    

    def browse_sections_list(self):
        try:
            self.soup = BeautifulSoup(self.driver.page_source, "html.parser") 
            elements_works = self.wait.until(ec.presence_of_all_elements_located((By.XPATH, "//div[@id='cssmenu']/ul[not(@class)]/li/a")))
            elements_works = [work.get_attribute("href") for work in elements_works]
            work_links = elements_works[1:]
            print(work_links)
            print("sections charged")
            print(work_links)
            
            for i, link in enumerate(work_links):
                print(len(work_links))
                try:
                    self.driver.get(link)
                    self.browse_work_pages()
                    print("alcanza")
                    # Actualizar la lista de elementos después de volver atrás
                    elements_works = self.wait.until(ec.presence_of_all_elements_located((By.XPATH, "//div[@id='cssmenu']/ul[not(@class)]/li/a")))
                    elements_works = [work.get_attribute("href") for work in elements_works]
                    work_links = elements_works[1:]
                    print("nuevo arr: " + str(len(work_links)))
                    
                    
                except StaleElementReferenceException:
                    # Manejar la excepción StaleElementReferenceException y continuar con el siguiente enlace
                    print(f"Elemento estalecido en el enlace {i}, continuando con el siguiente.")
                    continue
                
        except Exception as e:
            print(f"Error en browse_sections_list: {e}")

# Resto de tu código

                
    def browse_work_pages(self):
        try:
            # Obtener todas las URL de los trabajos
            elements_works = self.wait.until(ec.presence_of_all_elements_located((By.XPATH, "//td[@class='title_shorten']/a")))
            work_links = [work.get_attribute("href") for work in elements_works]
            print("elements charged")
            
            # Obtain every work and do the web scraping
            for link in work_links:
                self.driver.get(link)  # Volver a la página del trabajo
                self.do_web_scraping()
                self.driver.back()  # Regresar a la lista de trabajos

        except KeyError as e:
            print("Something went wrong when going through works pages")
            
            
    def do_web_scraping(self):
        try: 
            self.soup = BeautifulSoup(self.driver.page_source, "html.parser")
            self.obtain_title()
            self.obtain_author()
            self.obtain_languaje()
            self.obtain_image()
            self.obtain_tags()
            self.obtain_genres()
            self.obtain_work_data()
        except Exception as e:
            print(f"An error has occurred during web scraping: {e}")
    
    
    
    # Method to login in the page
    def login(self,time_max=10):
        print("1")
        self.check_cookies()
        print("2")
        self.go_to_page()
        print("3")
        login = self.login_verifier()
        print("4")
        if (not login):
            while time_max > 0:
                try:    
                    e = self.wait.until(ec.element_to_be_clickable((By.XPATH, "//a[text()='Login']")))
                    e.click()
                    time.sleep(1)   
                    e = self.wait.until(ec.element_to_be_clickable((By.ID, "user_login")))
                    e.send_keys(self.user)
                    time.sleep(1)   
                    e = self.wait.until(ec.element_to_be_clickable((By.ID, "user_pass")))
                    e.send_keys(self.password)
                    time.sleep(1)   
                    e = self.wait.until(ec.element_to_be_clickable((By.ID, "rememberme")))
                    if (not e.is_selected()):
                        e.click()
                    time.sleep(1)   
                    e = self.wait.until(ec.element_to_be_clickable((By.CSS_SELECTOR, "input[name='wp-submit']")))
                    e.click() 
                    self.save_cookies()
                    break
                except KeyError as e:
                    print(f"error trying to logued: {e}")
                time_max-=1
                    
    
    # Method to validate if cookies exist and is logued
    def login_verifier(self):
        try:
            e = self.wait.until(ec.element_to_be_clickable((By.XPATH, "//span[@id='menu_right_item']")))
            #e.click() 
            return True  
        except:
            return False    
              
        
        
        
        
    def enter_to_list_page(self):
        pass
    
    def enter_to_login_page(self):
        pass
        
        
novela = Novels_Scraping("prueba","prueba")
novela.execute()

