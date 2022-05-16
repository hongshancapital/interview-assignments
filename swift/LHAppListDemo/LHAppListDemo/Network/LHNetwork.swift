//
//  LHNetwork.swift
//  LHAppListDemo
//
//  Created by 刘志华 on 2022/5/14.
//

import Moya
import UIKit

// 请求成功回调
typealias LHRequestModelsSuccessCallback<T: Decodable> = ((T?, LHErrorResp?) -> Void)

let JSONParseErroor = -99

/// 网络请求错误的回调
typealias LHErrorCallback = ((LHErrorResp) -> Void)

class LHErrorResp {
    var msg: String = ""

    var code: Int = -111
}

private let endpointClosure = { (target: TargetType) -> Endpoint in
    let url = target.baseURL.absoluteString + "/" + target.path
    var task = target.task

    var endpoint = Endpoint(
        url: url,
        sampleResponseClosure: { .networkResponse(200, target.sampleData) },
        method: target.method,
        task: task,
        httpHeaderFields: target.headers
    )

    if let apiTarget = target as? MultiTarget,
       let target = apiTarget.target as? LHAppAPI
    {
        switch target {
        case .searchApp:
            return endpoint
        }
    }

    return endpoint
}

private let requestClosure = { (endpoint: Endpoint, done: MoyaProvider.RequestResultClosure) in
    do {
        var request = try endpoint.urlRequest()
        // 设置请求时长
        request.timeoutInterval = 30
        // 打印请求参数
        if let requestData = request.httpBody {
            print("rquest url：\(request.url!)" + "\n" + "\(request.httpMethod ?? "")" + "发送参数" + "\(String(data: request.httpBody!, encoding: String.Encoding.utf8) ?? "")")
        } else {
            print("rquest url：\(request.url!)" + "\(String(describing: request.httpMethod))")
        }

        if let header = request.allHTTPHeaderFields {
            print("请求header内容\(header)")
        }

        done(.success(request))
    } catch {
        done(.failure(MoyaError.underlying(error, nil)))
    }
}

private let networkPlugin = NetworkActivityPlugin.init { changeType, _ in
    print("networkPlugin \(changeType)")
    switch changeType {
    case .began:
        print("begin请求网络")
    case .ended:
        print("end网络请求")
    }
}

private let decoder = JSONDecoder()

private let Provider = MoyaProvider<MultiTarget>(endpointClosure: endpointClosure, requestClosure: requestClosure, plugins: [networkPlugin], trackInflights: false)

private func request<T: Decodable>(_ target: TargetType, modelType _: T.Type, successCallback: @escaping LHRequestModelsSuccessCallback<T>, failureCallback: LHErrorCallback? = nil) {
    Provider.request(MultiTarget(target)) { result in
        switch result {
        case let .success(response):
            do {
                let jsonData = try decoder.decode(T.self, from: response.data)
                print("返回结果是：\(jsonData)")
                successCallback(jsonData, nil)

            } catch {
                let error = NSError(domain: "JSON解析失败", code: JSONParseErroor, userInfo: nil)
                handleError(error, failure: failureCallback)
            }
        case let .failure(error as NSError):
            handleError(error, failure: failureCallback)
        }
    }
}

/// 处理报错
private func handleError(_ err: NSError, failure: LHErrorCallback?) {
    let model = LHErrorResp()
    model.msg = err.localizedDescription
    model.code = err.code
    print("发生错误：\(model.code)--\(model.msg)")
    failure?(model)
}

/// 发送请求
func request<T: Decodable>(_ target: TargetType, modelType: T.Type) async -> (resp: T?, err: LHErrorResp?) {
    await withCheckedContinuation { continuation in
        request(target, modelType: modelType) { resp, err in
            continuation.resume(returning: (resp, err))
        } failureCallback: { err in
            continuation.resume(returning: (nil, err))
        }
    }
}
