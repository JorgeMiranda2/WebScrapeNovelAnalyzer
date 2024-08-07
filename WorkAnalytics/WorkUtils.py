
def convert_genres_to_list(genres:str) -> list[str]:
   return [x.strip() for x in genres.split(",")]


def conver_rating_to_float(rating:str) -> float:
    rate:list[str] = [ x.strip() for x in rating.split("/")]
    result:float = float(rate[0]) / float(rate[1])
    return round(result, 2)