//
//  APIRequest.swift
//  SwiftUIHomeWork
//
//  Created by Yu jun on 2022/6/25.
//

import Foundation
var allAppMessageArray: [AppMessageModel] = []
extension URLSession {
    static let hw_errorDomain = "HomeWorkAPIErrorDomain"
    ///网络请求mock数据
    func hw_performDataTask(with request: URLRequest,
                            completionHandler: @escaping ([AnyHashable: Any]?, URLResponse?, Error?) -> Void) {
        DispatchQueue.global().async {
            sleep(3)
            DispatchQueue.main.async {
                if allAppMessageArray.count == 0 {
                    load(fileName: "1.json") { modelList, response, error in
                        if error != nil {
                            completionHandler(nil, response, error)
                            return
                        } else {
                            for item in modelList! {
                                let id = item["trackId"] as! Int
                                let trackName = item["trackName"] as? String ?? ""
                                let artworkUrl60 = item["artworkUrl60"] as? String ?? ""
                                let description = item["description"] as? String ?? ""
                                let messageModel = AppMessageModel(id: id, trackName: trackName, artworkUrl60: artworkUrl60, description: description)
                                allAppMessageArray.append(messageModel)
                            }
                        }
                    }
                }
                guard let bodyData = request.httpBody else {
                    let error: Error =
                    NSError(domain: URLSession.hw_errorDomain, code: 10004, userInfo: ["msg": "参数为空"])
                    completionHandler(nil, nil, error)
                    return
                }
                var bodyDictionary: [AnyHashable: Any]?
                do {
                    bodyDictionary = try JSONSerialization.jsonObject(with: bodyData) as? [AnyHashable: Any]
                } catch {
                    let error: Error =
                    NSError(domain: URLSession.hw_errorDomain, code: 10005, userInfo: ["msg": "参数数据转换失败"])
                    completionHandler(nil, nil, error)
                    return
                }
                guard let urlStr = request.url?.absoluteString,
                urlStr.hasPrefix("https://") else {
                    let error: Error =
                    NSError(domain: URLSession.hw_errorDomain, code: 10005, userInfo: ["msg": "请求地址有误"])
                    completionHandler(nil, nil, error)
                    return
                }
                if urlStr.hasSuffix("getAppMessage") {
                    guard let pageStr = bodyDictionary?["page"] as? String,
                    let page = Int(pageStr),
                    page > 0 else {
                        let error: Error =
                        NSError(domain: URLSession.hw_errorDomain, code: 10006, userInfo: ["msg": "page传参出错"])
                        completionHandler(nil, nil, error)
                        return
                    }
                    guard let pageNumberStr = bodyDictionary?["pageNumber"] as? String,
                    let pageNumber = Int(pageNumberStr),
                    pageNumber > 0 else {
                        let error: Error =
                        NSError(domain: URLSession.hw_errorDomain, code: 10006, userInfo: ["msg": "pageNumber传参出错"])
                        completionHandler(nil, nil, error)
                        return
                    }
                    
                    if (page - 1) * pageNumber >= allAppMessageArray.count {
                        completionHandler(["count": allAppMessageArray.count, "showAppList": []], nil, nil)
                    } else {
                        var showAppList: [AppMessageModel] = []
                        if page * pageNumber > allAppMessageArray.count {
                            showAppList = Array(allAppMessageArray[((page - 1) * pageNumber)..<allAppMessageArray.count]) as! [AppMessageModel]
                        } else {
                            showAppList = Array(allAppMessageArray[((page - 1) * pageNumber)..<(page * pageNumber)]) as! [AppMessageModel]
                        }
                        let responseDict: [AnyHashable: Any] = ["count": allAppMessageArray.count, "showAppList": showAppList]
                        completionHandler(responseDict, nil, nil)
                    }
                } else if urlStr.hasSuffix("selectedFavorite") {
                    guard let id = bodyDictionary?["id"] as? Int else {
                        let error: Error =
                        NSError(domain: URLSession.hw_errorDomain, code: 10006, userInfo: ["msg": "id传参出错"])
                        completionHandler(nil, nil, error)
                        return
                    }
                    guard let isFavorite = bodyDictionary?["isFavorite"] as? String else {
                        let error: Error =
                        NSError(domain: URLSession.hw_errorDomain, code: 10006, userInfo: ["msg": "id传参出错"])
                        completionHandler(nil, nil, error)
                        return
                    }
                    guard let appindex = allAppMessageArray.firstIndex(where: {
                        $0.id == id
                    }) else {
                        let error: Error =
                        NSError(domain: URLSession.hw_errorDomain, code: 10008, userInfo: ["msg": "查不到该条数据"])
                        completionHandler(nil, nil, error)
                        return
                    }
                    allAppMessageArray[appindex].isFavorite = (isFavorite == "Y") ? true : false
                    completionHandler(["count": allAppMessageArray.count, "showApplist": []], nil, nil)
                } else {
                    let error: Error =
                    NSError(domain: URLSession.hw_errorDomain, code: 10009, userInfo: ["msg": "找不到该服务"])
                    completionHandler(nil, nil, error)
                    return
                }
                
            }
            func load(fileName: String, completionHandler: @escaping ([[AnyHashable: Any]]?, URLResponse?, Error?) -> Void) {
                var data: Data = Data()
                guard let file = Bundle.main.url(forResource: "1.json", withExtension: nil)
                    else {
                    let error: Error =
                    NSError(domain: URLSession.hw_errorDomain, code: 10000, userInfo: ["msg": "找不到数据"])
                    completionHandler(nil, nil, error)
                    return
                }
                do {
                    data = try Data(contentsOf: file)
                } catch {
                    let error: Error =
                    NSError(domain: URLSession.hw_errorDomain, code: 10001, userInfo: ["msg": "数据加载失败"])
                    completionHandler(nil, nil, error)
                    return
                }
                var jsonDictionary: [AnyHashable: Any]?
                do {
                    jsonDictionary = try JSONSerialization.jsonObject(with: data) as? [AnyHashable: Any]
                } catch {
                    let error: Error =
                    NSError(domain: URLSession.hw_errorDomain, code: 10002, userInfo: ["msg": "数据转换失败"])
                    completionHandler(nil, nil, error)
                    return
                }
                var messageArray: [[AnyHashable: Any]] = []
                if let resultsArray = jsonDictionary?["results"] as? [[AnyHashable: Any]] {
                    for item in resultsArray {
                        guard item["trackId"] is Int else {
                            continue
                        }
                        messageArray.append(item)
                    }
                }
                if messageArray.count > 0 {
                    completionHandler(messageArray, nil, nil)
                } else {
                    let error: Error =
                    NSError(domain: URLSession.hw_errorDomain, code: 10003, userInfo: ["msg": "数据获取失败"])
                    completionHandler(nil, nil, error)
                }
            }
        }
    }
}



