import {Router} from "express";

import { TagController } from "../controllers/TagController";

const router = Router();


const {getTags, getTag, postTag, deleteTag, putTag} = TagController;

router.route("/")
.get(getTags)
.post(postTag)

router.route("/:id")
.get(getTag)
.delete(deleteTag)
.put(putTag)

export default router;