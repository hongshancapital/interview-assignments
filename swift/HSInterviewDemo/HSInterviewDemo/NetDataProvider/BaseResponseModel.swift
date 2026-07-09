//
//  BaseResponseModel.swift
//  TestSwiftUI
//
//  Created by zhangshouyin on 2023/3/9.
//

import Foundation
import APIService

public struct BaseResponseModel<T>: APIModelWrapper, APIDefaultJSONParsable where T: APIDefaultJSONParsable {
    public var code: Int
    public var msg: String
    public var data: T?
}

extension BaseResponseModel {
    enum CodingKeys: String, CodingKey {
        case code
        case msg = "desc"
        case data
    }
}
