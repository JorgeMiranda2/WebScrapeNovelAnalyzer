from fastapi import FastAPI, HTTPException
from fastapi.responses import JSONResponse
from Interfaces.UserData import UserData
from Novels_Scraping import Novels_Scraping
from Mangas_Scraping import Mangas_Scraping
from Animes_Scraping import Animes_Scraping

app = FastAPI()

@app.post("/getwork")
async def obtain_work(data: UserData, work_type: str):
    try:
        if work_type == "anime":
            result_class = Animes_Scraping(user=data.username, password=data.password)
        elif work_type == "manga":
            result_class = Mangas_Scraping(user=data.username, password=data.password, idiom=data.Language)
        elif work_type == "novel":
            result_class = Novels_Scraping(user=data.username, password=data.password)
        else:
            raise ValueError("Work type not found")
        
        result = result_class.execute()
        return JSONResponse(content=result, status_code=201)
    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))

def start_application():
    import uvicorn
    uvicorn.run(app, host="localhost", port=8000)

if __name__ == "__main__":
    start_application()
