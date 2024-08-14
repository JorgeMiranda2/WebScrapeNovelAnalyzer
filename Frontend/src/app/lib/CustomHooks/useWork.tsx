"use client"

import { useState } from "react";
import { work } from "../Interfaces/IWork";
import Get from "../API/Get";
import { Page } from "../Interfaces/IPage";
import Post from "../API/Post";

export const useWork = () => {

    const workTypes: any = {
        1: "anime",
        2: "manga",
        3: "novel"
    };

    const [works, setWorks] = useState<work[] | null>([]);    
    const [loading, setLoading] = useState<Boolean>(false);
    const [pageInfo, setPageInfo] = useState<Page<work>>(); 


    const getAllMyWorks = async (numberPage:number) : Promise<Array<work>| null> => {
        setLoading(true);
      
        try {
            let optionalWorks: Page<work> | null = await Get(`/api/works/myWorks?numerPage=${numberPage}`);
            if (!optionalWorks) {
                throw new Error("Undefined Works");
            }
            setWorks(optionalWorks.content);
            setPageInfo(optionalWorks);
           
            console.log(optionalWorks.content);
            return optionalWorks.content;
        } catch (error) {
            console.log("Error getting Work covers: " + error);
            return null;
        } finally{
            setLoading(false);
        }
    }
    
    const getAllMyWorksByType = async (numberPage:number, typeId:number) : Promise<Array<work>| null> => {
        setLoading(true);
      
        try {
            let optionalWorks: Page<work> | null = await Get(`/api/works/myWorks/type/${workTypes[typeId]}?numberPage=${numberPage}`);
            if (!optionalWorks) {
                throw new Error("Undefined Works");
            }
            setWorks(optionalWorks.content);
            setPageInfo(optionalWorks);
           
            console.log(optionalWorks.content);
            return optionalWorks.content;
        } catch (error) {
            console.log("Error getting Work covers: " + error);
            return null;
        } finally{
            setLoading(false);
        }
    }

    const getWorksBySearch = async (search : String, numberPage:number) : Promise<Array<work> | null>=> {
        setLoading(true);
        const body = {numberPage:numberPage};
        console.log("number::: " + numberPage);
        try{
            let optionalWorks: Page<work> | null = await Get(`/public/get-works/search/${search}?numberPage=${numberPage}`);
            if (!optionalWorks) {
                throw new Error("Undefined Works");
            }
            setWorks(optionalWorks.content);
            setPageInfo(optionalWorks);
            console.log(optionalWorks);
            return optionalWorks.content;
        } catch (error) {
            console.log("Error getting Work covers: " + error);
            return null;
        } finally{
            setLoading(false);
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
           
            console.log(optionalWorks.content);
            return optionalWorks.content;
        } catch (error) {
            console.log("Error getting Work covers: " + error);
            return null;
        } finally{
            setLoading(false);
        }
    };

    return { getWorks, works, loading, pageInfo, getWorksBySearch };
};