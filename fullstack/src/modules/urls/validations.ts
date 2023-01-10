import { celebrate, Segments, Joi } from 'celebrate';
import BizError from '@error/BizError';

export const urlValidation = celebrate({
  [Segments.BODY]: {
    url: Joi.string()
      .uri()
      .error(() => {
        throw new BizError('Invalid url');
      })
      .required(),
  },
});

export const shortValidation = celebrate({
  [Segments.QUERY]: {
    shortUrl: Joi.string()
      .uri()
      .error(() => {
        throw new BizError('Invalid url');
      })
      .required(),
  },
});
