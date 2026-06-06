//
//  NetWorkManager.swift
//  SwiftUIDemo
//
//  Created by Eric on 9/16/21.
//

import UIKit
// 定义请求类型
enum RequestType: String {
    case GET = "GET"
    case POST = "POST"
    case PUT = "PUT"
    case UPLOAD = "UPLOAD"
    case DELETE = "DELETE"
}

//网络请求
class NetWorkManager: NSObject {
    //MARK:单例(let是线程安全的)
    static let shared: NetWorkManager = {
        let share = NetWorkManager()
        return share
    }()
}

extension NetWorkManager {
    //发起请求入口
    func request(urlString: String,
                 methodType: RequestType,
                 parameters: [String: Any]?,
                 result: @escaping (_ result: SPCResponseModel) -> ()) {
        //添加服务器地址拼接完整的请求链接
        let requestUrl = SeverUrl + urlString;
        //发起请求
        switch methodType {
        case .POST:
            //暂时只模拟 post 方法
             sendPostRequest(urlString: requestUrl, parameters: parameters, result: result)
            break
        case .GET:
            break
        case .PUT:
            break
        case .UPLOAD:
            break
        case .DELETE:
            break
        }
        
    }
    
    //MARK:POST方法
    private func sendPostRequest(urlString: String,
                                 parameters: [String: Any]?,
                                 result: @escaping (_ result: SPCResponseModel) -> ()){
        //模拟 post 方法
        let model:SPCResponseModel = SPCResponseModel(code: 200,msg: "请求成功",data: "");
        result(model);
    }
}

class SPCResponseModel: NSObject {
    //数据码
    var code: Int = 0
    //是否成功 判断数据返回用
    var success:Bool = false
    //信息
    var msg: String = ""
    //数据
    var data: Any?
    convenience init(code:Int,msg:String,data:Any? = nil){
        self.init()
        self.code = code
        self.msg = msg
        self.data = data
        //200 为请求成功的数据码
        self.success = (code == 200);
    }
}
