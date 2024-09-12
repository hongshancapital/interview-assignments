import Application from "./app";
(async () => {
    const application = new Application();
    await application.init();
    application.listen(application.port);
})()