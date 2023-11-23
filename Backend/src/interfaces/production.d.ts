
//Document type Object

import { Schema } from "mongoose";

export interface production{
    _id?:Schema.Types.ObjectId,
    title:String,
    alternative_names:Array<String>,
    description:String,
    author:String,
    genres_id:Array<Schema.Types.ObjectId>

}