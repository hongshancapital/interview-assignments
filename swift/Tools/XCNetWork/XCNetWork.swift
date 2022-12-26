//
//  XCNetWork.swift
//  XCWorks
//
//  Created by 张兴程 on 2022/12/26.
//

import UIKit
import AFNetworking

/// 请求类型
enum RequestType : String {
    case GET = "GET"
    case POST = "POST"
}

class XCNetWork: AFHTTPSessionManager {
    static let shareInstance : XCNetWork = {
        let xcNetWork = XCNetWork()
        xcNetWork.responseSerializer.acceptableContentTypes?.insert("text/html")
        xcNetWork.responseSerializer.acceptableContentTypes?.insert("text/plain")
        return xcNetWork
    }()
    
    func request(methodType:RequestType, urlString:String, parameters:[NSString:Any], finished: @escaping (_ result:Any?,_ error:Error?)->()){
        let successCallBack = { (task : URLSessionDataTask, result:Any) in
            finished(result, nil)
        }
        
        let failureCallBack = { (task:URLSessionTask?, error:Error) in
            finished(nil, error)
        }
        
        if methodType == .GET {
            get(urlString, parameters: parameters, headers: nil, progress: nil, success: successCallBack, failure: failureCallBack)
        }else{
            post(urlString, parameters: parameters, headers: nil, progress: nil, success: successCallBack, failure: failureCallBack)
        }
    }
}
