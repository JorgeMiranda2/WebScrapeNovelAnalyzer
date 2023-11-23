import dotenv from "dotenv";
dotenv.config();
import app from "./app.ts";
import "./database.ts";




const port:number = app.get("port")

const server = app.listen(port);
    console.log(`getting port `,port);
    

export default server ;
