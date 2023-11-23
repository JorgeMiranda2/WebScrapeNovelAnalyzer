import {Schema, model} from "mongoose";

const COL_NAME = "genre"

const genreSchema = new Schema({

  name:{
    type:String,
    required:true,
    unique:true
  },
  description:{
    type:String,
    maxlength:300,
  }
}, { timestamps: true })

const genreModel = model(COL_NAME, genreSchema);

export {genreModel, COL_NAME};