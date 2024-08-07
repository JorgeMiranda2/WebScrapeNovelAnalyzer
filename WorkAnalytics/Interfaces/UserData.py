from pydantic import BaseModel
from typing import Optional

class UserData(BaseModel):
    username:str
    password:str
    Language: Optional[int] = 1

