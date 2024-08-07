"use client"

import { useState } from "react";
import { anime } from "../Interfaces/IAnime";
import Get from "../API/Get";

export const useManga = () => {

    const [anime, setAnime] = useState<anime[] | null>();     

    const getAnime = async () : Promise<Array<anime> | null>  => {
        let optionalMangas : anime[] | null =  await Get("/public/get-anime") ;
        setAnime(optionalMangas);
        return optionalMangas;
    }

    return {getAnime, anime}

    
}