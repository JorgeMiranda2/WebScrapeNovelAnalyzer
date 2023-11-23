import {Router} from "express";

import { userController } from "../controllers/UserController";

const router = Router();


const {getUsers, getUser, postUser, deleteUser, putUser} = userController;

router.route("/")
.get(getUsers)
.post(postUser)

router.route("/:id")
.get(getUser)
.delete(deleteUser)
.put(putUser)

export default router;