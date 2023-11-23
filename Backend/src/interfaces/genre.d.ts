
//Document type Object

import { Schema } from "mongoose";

export interface genre{
    _id?:Schema.Types.ObjectId,
    name:String,
    description:String  
}