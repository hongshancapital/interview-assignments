//
//  APIRequest.swift
//  TestSwiftUI
//
//  Created by zhangshouyin on 2023/3/9.
//

import Foundation
import APIService

class MKAPIRequest<DataResponse: APIDefaultJSONParsable>: APIRequest {
    typealias Response = BaseResponseModel<DataResponse>

    var baseURL: URL {
        if isMock {
            return NetworkConstants.baseMockURL
        }
        switch NetworkConstants.env {
        case .prod:
            return NetworkConstants.baseProdURL
        case .dev:
            return NetworkConstants.baseDevURL
        }
    }

    var path: String {
        return ""
    }

    var method: APIRequestMethod { .get }

    var parameters: [String: Any]? {
        return nil
    }

    var headers: APIRequestHeaders? {
        return nil
    }

    var taskType: APIRequestTaskType {
        return .request
    }

    var encoding: APIParameterEncoding {
        return APIURLEncoding.default
    }

    public var cache: APICache? {
        return nil
    }

    public var cacheShouldWriteHandler: ((APIResponse<Response>) -> Bool)? {
        return nil
    }

    public var cacheFilterParameters: [String] {
        return []
    }

    var isMock: Bool {
        return true
    }

    /// 验证码
    private var neteaseValidate = ""

    func intercept(parameters: [String: Any]?) -> [String: Any]? {
        if !neteaseValidate.isEmpty {
            var resultParameters = parameters ?? [:]
            resultParameters["neteaseValidate"] = neteaseValidate
            return resultParameters
        } else {
            return parameters
        }
    }

    func intercept<U: APIRequest>(request: U, response: APIResponse<Response>, replaceResponseHandler: @escaping APICompletionHandler<Response>) {
        if response.result.value?.code == 1001, let zsyAPIRequest = request as? MKAPIRequest {
            zsyAPIRequest.neteaseValidate = "123"
            APIService.sendRequest(zsyAPIRequest, completionHandler: replaceResponseHandler)
        } else {
            replaceResponseHandler(response)
        }
    }
}

