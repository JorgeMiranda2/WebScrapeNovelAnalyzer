"use client"

import { useState } from "react";
import { work } from "../Interfaces/IWork";
import Get from "../API/Get";
import { Page } from "../Interfaces/IPage";

export const useWork = () => {

    const workTypes: any = {
        1: "anime",
        2: "manga",
        3: "novel"
    };

    const [works, setWorks] = useState<work[] | null>([]);    
    const [loading, setLoading] = useState<Boolean>(false);
    const [pageInfo, setPageInfo] = useState<Page<work>>(); 

    const getWorksBySearch = async (search : String) : Promise<Array<work> | null>=> {
        try{
           // let optionalWorks: Page<work> | null = await Get(`/public/get-works/${workTypes[typeId]}?numberPage=${numberPage}`);
            return null;
        } catch (error) {
            return null;
        }
    }

    const getWorks = async (typeId: number, numberPage: number = 0): Promise<Array<work> | null> => {
        setLoading(true);
        if (!workTypes[typeId]) {
            console.error('Invalid work type ID');
            return null;
        }
        try {
            console.log(numberPage);
            let optionalWorks: Page<work> | null = await Get(`/public/get-works/${workTypes[typeId]}?numberPage=${numberPage}`);
            if (!optionalWorks) {
                throw new Error("Undefined Works");
            }
            setWorks(optionalWorks.content);
            setPageInfo(optionalWorks);
            setLoading(false);
            console.log(optionalWorks.content);
            return optionalWorks.content;
        } catch (error) {
            console.log("Error getting Work covers: " + error);
            return null;
        }
    };

    return { getWorks, works, loading, pageInfo, getWorksBySearch };
};