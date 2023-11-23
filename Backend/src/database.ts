import mongoose from "mongoose";

const URI:string = process.env.NODE_ENV == "test" ? process.env.MONGODB_URI_TEST || "mongodb://localhost/dbtest" :  process.env.MONGODB_URI || "mongodb://localhost/dbtest";
mongoose.connect(URI);

const connection = mongoose.connection;

connection.once("open", ()=>{
console.log("database connected: ", URI)
})

