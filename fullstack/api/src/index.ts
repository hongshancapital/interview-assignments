import { app } from "./app";
import { connectDb } from "./db";

connectDb().then(() => {
    const port = process.env.port || 3000;
    app.listen(port, () => {
        console.log(`server started at http://localhost:${port}`)
    });
}).catch(err => {
    console.error(err);
})
