import app from "../app"
import BaseConfig from "../config"
import boots from "../boots"
const PORT = BaseConfig.PORT || 3777

boots()
console.log("环境启动成功")
app.listen(PORT, () => {
  console.log(`启动成功 on port ${PORT}`)
})
