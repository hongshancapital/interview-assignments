//
//  APIRequestProtocol.swift
//  TestSwiftUI
//
//  Created by zhangshouyin on 2023/3/9.
//

import Foundation
import APIService

protocol APIRequestProtocol: APIRequest where Response == BaseResponseModel<DataResponse> {
    associatedtype DataResponse: APIDefaultJSONParsable

    var isMock: Bool { get }
}

extension APIRequestProtocol {
    var isMock: Bool {
        return false
    }

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

    public func intercept(urlRequest: URLRequest) throws -> URLRequest {
        return urlRequest
    }

    public func intercept<U: APIRequest>(request: U, response: APIResponse<Response>, replaceResponseHandler: @escaping APICompletionHandler<Response>) {
        replaceResponseHandler(response)
    }
}
