
"use client"

import { useState } from "react";
import { Manga } from "../Interfaces/IManga";
import Get from "../API/Get";

export const useManga = () => {

    const [mangas, setMangas] = useState<Manga[] | null>();     

    const getMangas = async () : Promise<Array<Manga> | null>  => {
        let optionalMangas : Manga[] | null =  await Get("/public/get-mangas") ;
        setMangas(optionalMangas);
        return optionalMangas;
    }

    return {getMangas, mangas}


}