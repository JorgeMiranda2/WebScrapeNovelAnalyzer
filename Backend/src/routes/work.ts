import {Router} from "express";

import { WorkController } from "../controllers/WorkController";

const router = Router();


const {getWorks, getWork, postWork, deleteWork, putWork} = WorkController;

router.route("/")
.get(getWorks)
.post(postWork)

router.route("/:id")
.get(getWork)
.delete(deleteWork)
.put(putWork)

export default router;