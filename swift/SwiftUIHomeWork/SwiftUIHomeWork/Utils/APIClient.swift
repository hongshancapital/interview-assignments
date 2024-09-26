//
//  APIClient.swift
//  SwiftUIHomeWork
//
//  Created by Yu jun on 2022/6/25.
//

import UIKit

class APIClient: NSObject {
    static let shareClient: APIClient = APIClient()
    let urlSession = URLSession(configuration: URLSessionConfiguration.default, delegate: HWURLSessionDelegate(), delegateQueue: OperationQueue.main)
    ///获取App的分页数据
    func getAppMessage(page: String, pageNumber: String, completionHandler: @escaping ([AnyHashable: Any]?, Error?) -> Void) {
        let request = NSMutableURLRequest(url: URL(string: "https://www.baid.com/getAppMessage")!)
        let parameters = ["page": page, "pageNumber": pageNumber] as [String: Any]
        var jsonData: Data?
        do {
            jsonData = try JSONSerialization.data(withJSONObject: parameters, options: JSONSerialization.WritingOptions.prettyPrinted)
        } catch {
            print("转换json失败:\(error)")
        }
        request.httpBody = jsonData
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.timeoutInterval = 30
        self.urlSession.hw_performDataTask(with: request as URLRequest) { modellist, response, error in
            if error != nil {
                let ns_error = error! as NSError
                print("获取第\(page)页数据失败失败，原因是：\(String(describing: ns_error.userInfo["msg"]))")
            }
            completionHandler(modellist, error)
        }
    }
    ///点赞反馈给总数据，刷新数据
    func selectedFavoriteToAll(model: AppMessageModel, completionHandler: @escaping (Error?) -> Void){
        let request = NSMutableURLRequest(url: URL(string: "https://www.baid.com/selectedFavorite")!)
        let parameters = ["id": model.id, "isFavorite": model.isFavorite ? "Y" : "N"] as [String: Any]
        var jsonData: Data?
        do {
            jsonData = try JSONSerialization.data(withJSONObject: parameters, options: JSONSerialization.WritingOptions.prettyPrinted)
        } catch {
            print("转换json失败:\(error)")
        }
        request.httpBody = jsonData
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.timeoutInterval = 30
        self.urlSession.hw_performDataTask(with: request as URLRequest) { modellist, response, error in
            if error == nil {
                print("点赞成功")
            } else {
                let ns_error = error! as NSError
                print("点赞失败，原因是：\(String(describing: ns_error.userInfo["msg"]))")
            }
            completionHandler(error)
        }
    }

}
class HWURLSessionDelegate: NSObject, URLSessionDelegate {
    
}
