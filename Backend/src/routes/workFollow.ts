import {Router} from "express";

import { WorkFollowController } from "../controllers/WorkFollowController";

const router = Router();


const {getWorksFollow, getWorkFollow, postWorkFollow, deleteWorkFollow, putWorkFollow} = WorkFollowController;

router.route("/")
.get(getWorksFollow)
.post(postWorkFollow)

router.route("/:id")
.get(getWorkFollow)
.delete(deleteWorkFollow)
.put(putWorkFollow)

export default router;