import {Router} from "express";

import { GenreController } from "../controllers/GenreController";

const router = Router();


const {getGenres, getGenre, postGenre, deleteGenre, putGenre} = GenreController;

router.route("/")
.get(getGenres)
.post(postGenre)

router.route("/:id")
.get(getGenre)
.delete(deleteGenre)
.put(putGenre)

export default router;