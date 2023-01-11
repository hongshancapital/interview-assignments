import { Router } from 'express';
import { UrlModel } from '../models/url';

const router = Router();

router.get('/:code', async (req, res) => {
  const { code } = req.params;
  const data = await UrlModel.findOne({ shortenCode: code }).catch(err => {
    res.status(500).json("Server Error")
  });
  if (data) {
    res.redirect(data.initUrl);
  } else {
    res.status(404).json("No Found");
  }
});

export default router;