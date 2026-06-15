import app, { initialize } from './app'
import env from './config/env'
const port = env.port || 3000
initialize().then(() => {
  app.listen(port, () => {
    console.log(
      '  App is running at http://localhost:%d in %s mode',
      port,
      app.get('env'),
    )
    console.log('  Press CTRL-C to stop\n')
  })
})
