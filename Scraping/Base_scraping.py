import json
from selenium.webdriver.support.ui import WebDriverWait
import undetected_chromedriver as uc 
from abc import ABC, abstractmethod
import json
import tempfile
import os
import pickle
# Open the JSON file and load the data into a Python variable
with open('Config/config.json', 'r') as json_file:
    json_data = json.load(json_file)

print(json_data["user_agent"])

# Creating the interface class for the websites classes
class Base_Scraping(ABC):
    @abstractmethod
    def __init__(self,user, password, website_type):
        
        self.user = user
        self.password = password
        try:
            self.cookies_file = tempfile.gettempdir()+ json_data["websites"][f"{website_type}"]["cookies_file"]
            print(self.cookies_file)
            self.website_name = json_data["websites"][f"{website_type}"]["website_name"]
            self.url_library = json_data["websites"][f"{website_type}"]["url_library"]
            self.work_type = json_data["websites"][f"{website_type}"]["work_type"]
            self.agent = json_data["user_agent"]
            self.timeout = json_data["flow_control"]["timeout_seconds"]
            self.request_intervals = json_data["flow_control"]["request_interval_seconds"]
            self.info = None
        except KeyError as e:
            # Handle Exceptions and showing error params
            print(f"Error: The key {e} is not in the json file.")

    
    # Method to connect to the website
    def connect(self):
        try:
            print("Connecting...")
            self.driver = uc.Chrome(headless=False, log_level=3)
            self.driver.get(self.url_library)
            self.wait = WebDriverWait(self.driver, 30)
            print("Connected")
        except KeyError as e:
            print(f"Getting an error while connecting: {e}")
    
    def check_cookies(self):
        print("Checking cookies")
        if os.path.isfile(self.cookies_file):
            cookies = pickle.load(open(self.cookies_file, "rb"))
            self.driver.get(f"{self.url_library}/robots.txt")
            for cookie in cookies:
                try:
                    self.driver.add_cookie(cookie)
                except KeyError as e:
                    print(f"error when retrieve cookies: {e}")
            print("Checked")
            return True 
        else:
            print("not Checked")
            return False
                  
                  
                  
    def go_to_page(self):
        self.driver.get(self.url_library)                 
            
    def save_cookies(self):
        pickle.dump(self.driver.get_cookies(), open(self.cookies_file, "wb"))
        
    @abstractmethod
    def do_web_scraping(self):
        pass
    
    # Method to get the work tittle
    @abstractmethod
    def obtain_tittle(self):
        pass
    # Method to retrieve the work languaje origin   
    @abstractmethod
    def obtain_languaje(self):
        pass
    # method to retrieve the author's name of the work
    @abstractmethod
    def obtain_author(self):
        pass
    
    # Method to do the diferents operations from the specific work and get the scraping data
    @abstractmethod
    def obtain_work_data(self):
        pass
    
    # Method to do the diferents operations and get the total page scraping data
    @abstractmethod
    def execute(self):
        pass
    
    # Method to login in the page
    @abstractmethod
    def login(self):
        pass
    
    @abstractmethod
    def login_verifier(self):
        pass
    
    @abstractmethod
    def enter_to_list_page(self):
        pass
    
    @abstractmethod
    def enter_to_login_page(self):
        pass
    