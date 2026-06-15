//
//  HttpRequestUtils.swift
//  LoginDemo
//
//  Created by 高丽军 on 2021/9/9.
//

import Foundation
import Combine

let createPath = "/create/account"
let loginPath = "/login"

var accountInfo:Dictionary<String,AccountModel> = NSDictionary() as! Dictionary<String, AccountModel>

struct HttpRequest {

    func createAccount(nickName:String, password:String) -> HttpResponse {
        var dic:Dictionary<String,String> = Dictionary()
        dic["nickName"] = nickName
        dic["password"] = password
        return HttpManager().startRequest(requestPath: createPath, parmars: dic)
    }
    
    func login(nickName:String, password:String) -> HttpResponse {
        var dic:Dictionary<String,String> = Dictionary()
        dic["nickName"] = nickName
        dic["password"] = password
        return HttpManager().startRequest(requestPath: loginPath, parmars: dic)
    }
}

struct HttpManager {
    
    func startRequest(requestPath:String,parmars:Dictionary<String, String>) -> HttpResponse {
        
        var response = HttpResponse()
        
        switch requestPath {
        case createPath:
            let name = parmars["nickName"]!
            
            for keyName in accountInfo.keys {
                if (keyName == name) {
                    response.requestCode = 201 //
                    response.errorMessage = "该用户已经存在"
                    response.data = nil
                    return response
                }
            }
            
            let account = AccountModel.init(nickName: name, password: parmars["password"]!)
            accountInfo[name] = account
            response.requestCode = 200
            response.data = nil
            break;
        case loginPath:
            let name = parmars["nickName"]!
            
            for keyName in accountInfo.keys {
                if (keyName == name) {
                    
                    let password = parmars["password"]!
                    let accountInfoTmp = accountInfo[keyName]
                    if password == accountInfoTmp?.password {
                        response.requestCode = 200 //
                        response.data = nil
                        return response
                    }
                }
            }
            
            response.requestCode = 202 //
            response.errorMessage = "用户名或者密码错误"
            response.data = nil
            
            break;
        default:
            response.requestCode = 300 //
            response.errorMessage = "没有这个方法"
            response.data = nil
            break;
        }
        
        return response
    }
    
}

struct HttpResponse {
    var requestCode:Int?
    var error:Error?
    var errorMessage:String?
    var data:Dictionary<String,String>?
}


struct AccountModel {
    var nickName:String
    var password:String
    
    init(nickName:String,password:String) {
        self.nickName = nickName
        self.password = password
    }
}
