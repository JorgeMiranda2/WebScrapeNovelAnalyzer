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
# Open the JSON file and load the data into a Python variable
with open('Config/config.json', 'r') as json_file:
    json_data = json.load(json_file)
    
class Novels_Scraping(Base_Scraping):
    def __init__(self, user, password): 
        website_type = "web_novel_site"
        super().__init__(user, password, website_type)
   

    # Method to get the work tittle
 
    def obtain_tittle(self):
        pass
    # Method to retrieve the work languaje origin   

    def obtain_languaje(self):
        pass
    # method to retrieve the author's name of the work

    def obtain_author(self):
        pass
    
    # Method to do the diferents operations from the specific work and get the scraping data

    def obtain_work_data(self):
        pass
    
    
    
    # Method to do the diferents operations and get the total page scraping data
    def execute(self):
        self.connect()
        self.login()
        self.go_to_list()
        self.browse_work_pages()
        
    def go_to_list(self):
        print("trying to go to the list page")
        e = self.wait.until(ec.element_to_be_clickable((By.XPATH, "//a[text()='Reading List']")))
        e.click()
        time.sleep(1)   
        
    def browse_work_pages(self):
        try:
            # Obtener todas las URL de los trabajos
            elements_works = self.wait.until(ec.presence_of_all_elements_located((By.XPATH, "//td[@class='title_shorten']/a")))
            work_links = [work.get_attribute("href") for work in elements_works]
        
            
            # Ahora que estás en las páginas de trabajo individuales, realiza el web scraping en un bucle separado
            for link in work_links:
                self.driver.get(link)  # Volver a la página del trabajo
                self.do_web_scraping()
                self.go_to_list()  # Regresar a la lista de trabajos

        except KeyError as e:
            print("Something went wrong when going through works pages")
            
            
    def do_web_scraping(self):
        time.sleep(1)
    # Method to login in the page

    def login(self,time_max=15):
        print("1")
        self.check_cookies()
        print("2")
        self.go_to_page()
        print("3")
        time.sleep(2)
        print("4")
        login = self.login_verifier()
        print("5")
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
                    print("intentando")
                    self.save_cookies()
                    break
                except KeyError as e:
                    print(f"error trying to logued: {e}")
                time_max-=1
    time.sleep(10)            
    
    # Method to validate if cookies exist and is logued
    def login_verifier(self):
        try:
            e = self.driver.find_element(By.ID, "menu_right_item")
            e.click() 
            return True  
        except:
            return False    
              
        
        
        
        
    def enter_to_list_page(self):
        pass
    
    def enter_to_login_page(self):
        pass
        
        
novela = Novels_Scraping("Fineslow","jemc1410")
novela.execute()

