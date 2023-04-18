import { readFileSync, writeFileSync } from "fs"
import { join } from "path"

// 因为后续代码编译成js后会构建到build目录，这里的id_data放到project目录下
const data_file_name:string = "../id_data"

var global_id = 0

function WriteGlobalIdTofile() {
    writeFileSync(join(__dirname, data_file_name), global_id.toString()) 
}

function ReadGlobalIdFromFile() {
    const contents:Buffer = readFileSync(join(__dirname, data_file_name))
    global_id = parseInt(contents.toString())
}

// init global id from datafile
(function () {
    ReadGlobalIdFromFile()
})()

/*
 * 1.一个很朴素的id generator，有点傻：）;
 * 2.功能仅限于能用，生产环境一般会有专门的微服务模块，并且保证放号的唯一性，后续需要处理的冲突就越少;
 * 3. 2023.01.21 对这里进行了一点加强，global_id改变之后会持久化到文件中，每次模块初始化都会去文件读取最新的global_id，这样即使项目多次
 *    启动，产生的global_id也不会重复，实际生产环境可以通过db来持久化，这里为了保持简洁使用文件持久化;
 */
export function GetUniqID(): number {
    const id:number = global_id++
    WriteGlobalIdTofile()
    console.log("INFO: id", id)
    return id
}