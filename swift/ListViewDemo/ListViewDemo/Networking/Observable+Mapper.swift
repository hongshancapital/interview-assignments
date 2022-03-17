//
//  Observable+Mapper.swift
//  ListViewDemo
//
//  Created by HL on 2022/3/16.
//

import UIKit
import RxSwift
import Moya
import ObjectMapper

extension Observable {

    func mapModels<T: Mappable>(type: T.Type) -> Observable<[T]> {
        return self.map { response in
            do {
                let dic = try self.handleBaseResponse(response: response)
                guard let results = dic["results"] as? [[String: Any]] else {
                    throw AppError.requestError(code: -1, message: "数据解析错误")
                }
                return [T].init(JSONArray: results)
            } catch let error {
                throw error
            }
        }
    }

    private func handleBaseResponse(response: Element) throws -> [String: Any] {
        guard let dic = response as? [String: Any] else {
            throw AppError.requestError(code: -1, message: "数据解析错误")
        }
        return dic
    }
}

enum AppError: Error {
    case requestError(code: Int, message: String?)
}
