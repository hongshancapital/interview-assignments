import express from 'express';
import HttpResponseError from "./HttpResponseError";

const ErrorHandler: express.ErrorRequestHandler = (err, _req, res, _next) => {
  if (err instanceof HttpResponseError) {
    res.status(err.statusCode).send({
      message: err.message
    });
  } else {
    res.status(500).send({
      message: err.message
    });
  }
}

export default ErrorHandler;