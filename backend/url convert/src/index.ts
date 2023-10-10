import app from "./app.js";

const port = 8080;

app.listen(port, () => {
    return console.log(`app is listening at http://localhost:${port}`);
});
