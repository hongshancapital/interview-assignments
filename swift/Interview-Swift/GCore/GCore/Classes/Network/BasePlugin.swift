//
//  BasePlugin.swift
//  GCore
//
//  Created by lizhao on 2022/9/20.
//

import Moya
import Result

// MARK: - ErrorHandlerPlugin
public protocol ErrorHUGType {
    func show(error: Error)
}

public protocol ErrorHandlerTargetType {
    var canShowErrorHUG: Bool { get }
    func canDisplayHUGForError(_ error: Error) -> Bool
}


public struct ErrorHandlerPlugin: PluginType {
    let errorHUG: ErrorHUGType
    
    public init(errorHUG: ErrorHUGType) {
        self.errorHUG = errorHUG
    }
    
    private func displayErrorIfEnable(error: Error, target: TargetType) {
        guard let errorHandlerTarget = target as? ErrorHandlerTargetType,
              errorHandlerTarget.canShowErrorHUG,
              errorHandlerTarget.canDisplayHUGForError(error) else {
            return
        }
        errorHUG.show(error: error)
    }

    
    private func serverErrorFromResponse(_ response: Response) -> Error? {
        if let _ = try? JSONSerialization.jsonObject(with: response.data, options: .allowFragments) {
            // TODO: 获取响应体的信息打包到 NSError
            var userInfo: [String: Any] = [NSLocalizedDescriptionKey: "error: xxxx"]
            userInfo["Body"] = "xxx"
            userInfo["HTTP"] = "xxx"
            userInfo["Header"] = "xxx"
            return NSError(domain: "\(NetworkConfiguration.baseURL.absoluteString).httpError",
                code: response.statusCode,
                userInfo: userInfo)
        } else {
            return nil
        }
    }
    
    private func internalErrorFromError(_ error: Error) -> Error? {
        let code = (error as NSError).code
        switch code {
        case NSURLErrorNotConnectedToInternet:
            let userInfo = [NSLocalizedDescriptionKey: "网络不可用,请检查网络后重试."]
            let interError = NSError(domain: "\(NetworkConfiguration.baseURL.absoluteString).internalError",
                code: code,
                userInfo: userInfo)
            return interError
        case NSURLErrorTimedOut:
            let userInfo = [NSLocalizedDescriptionKey: "请求超时,请检查网络后重试."]
            let interError = NSError(domain: "\(NetworkConfiguration.baseURL.absoluteString).internalError",
                code: code,
                userInfo: userInfo)
            return interError
        default:
            return nil
        }
    }
    
    public func process(_ result: Result<Response, MoyaError>, target: TargetType) -> Result<Response, MoyaError> {

        switch result {
        case .failure(let error):
            switch error {
            case .underlying(let interError, let response):
                if let error = internalErrorFromError(interError) {
                    self.displayErrorIfEnable(error: error, target: target)
                    return Result<Response, MoyaError>(error: MoyaError.underlying(error, response))
                } else if let res = response,
                          let serverError = serverErrorFromResponse(res) {
                    self.displayErrorIfEnable(error: serverError, target: target)
                    return Result<Response, MoyaError>(error: MoyaError.underlying(serverError, res))
                } else {
                    if (interError as NSError).code != NSURLErrorCancelled {
                        self.displayErrorIfEnable(error: interError, target: target)
                    }
                }
            case .parameterEncoding(let interError):
                self.displayErrorIfEnable(error: interError, target: target)
            case .objectMapping(let interError, _):
                self.displayErrorIfEnable(error: interError, target: target)
            default:
                self.displayErrorIfEnable(error: error, target: target)
            }
        case .success(let response):
            ConsoleLog.debug("<HTTP> response", context: "\(response.request?.httpMethod?.uppercased() ?? "") \(response.request?.url?.absoluteString ?? "") | \(response.statusCode)")
        }
        return result
    }
}
extension Moya.Response {
    public func formatDecodeError(_ error: Error) -> Error {
        var userInfo = (error as NSError).userInfo
        userInfo["Response"] = self
        userInfo["Body"] = String(data: self.data, encoding: String.Encoding.utf8) ?? ""
        return NSError(domain: (error as NSError).domain, code: (error as NSError).code, userInfo: userInfo)
    }
}
