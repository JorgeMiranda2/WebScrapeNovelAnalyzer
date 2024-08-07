"use client"

import { useState } from "react"
import Get from "../API/Get"
import { IdentificationType } from "../Interfaces/IdentificationType";
function useIdentificationType(){

    const IDENTIFICATIONTYPES_ROUTE = "/public/identificationtypes";

    const [identificationTypes, setIdentificationTypes] = useState<IdentificationType[] | null>([]);

const getIdentificationTypes = async (): Promise<IdentificationType[] | null> => {
    try {
      const identificationTypes: IdentificationType[] | null = await Get(IDENTIFICATIONTYPES_ROUTE);
      setIdentificationTypes(identificationTypes);
      return identificationTypes;
    } catch (error) {
      console.error("Error fetching identification types:", error);
      return null;
    }
  };

    return {getIdentificationTypes, identificationTypes}
}

export default useIdentificationType;