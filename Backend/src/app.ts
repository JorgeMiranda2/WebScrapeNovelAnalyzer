import express from "express";
import cors from "cors";
import routerUser from "./routes/user.ts";
import routerState from "./routes/state.ts";
import routerTag from "./routes/tag.ts";
import routerGenre from "./routes/genre.ts";
import routerWork from "./routes/work.ts";
import routerWorkFollow from "./routes/workFollow.ts";
import routerProduction from "./routes/production.ts";
const app = express();

//configuration

app.set("port", process.env.PORT  || 4000);

//middlewares
app.use(cors());
app.use(express.json());



//routes
app.use('/api/user', routerUser); 
app.use('/api/state', routerState); 
app.use('/api/tag', routerTag); 
app.use('/api/production', routerProduction); 
app.use('/api/genre', routerGenre); 
app.use('/api/work', routerWork); 
app.use('/api/workfollow', routerWorkFollow); 



export default app;