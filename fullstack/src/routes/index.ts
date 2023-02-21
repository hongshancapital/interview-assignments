import epxress from 'express';
const router = epxress.Router();
import { UrlModel } from "../models/url";

router.get('/:code', async (req, res, next) => {
  try {
    const urlCode = req.params.code;
    const url = await UrlModel.findOne({ urlCode });
    if (url) {
      res.redirect(url.longUrl);
    } else {
      res.status(404).json("No url found");
    }
  } catch (error) {
    res.status(500).json("Server error");
  }
});

export default router;