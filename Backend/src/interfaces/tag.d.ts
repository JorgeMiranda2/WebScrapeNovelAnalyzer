
//Document type Object

import { Schema } from "mongoose";

export interface tag{
    _id?:Schema.Types.ObjectId,
    name:String,
    description:String
   
}