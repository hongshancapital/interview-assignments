//
//  STBaseModel.swift
//  STBaseProject
//
//  Created by stack on 2018/3/14.
//  Copyright © 2018 ST. All rights reserved.
//

import UIKit

open class STBaseModel: NSObject {
    
    deinit {
        print("🌈 -> \(self) 🌈 ----> 🌈 dealloc")
    }
    
    open override func value(forUndefinedKey key: String) -> Any? {
        print("⚠️ ⚠️ Key = \(key) isUndefinedKey ⚠️ ⚠️")
        return nil
    }

    open override class func setValue(_ value: Any?, forUndefinedKey key: String) {
        print("⚠️ ⚠️ Key = \(key) isUndefinedKey ⚠️ ⚠️")
    }

    open override func setValue(_ value: Any?, forUndefinedKey key: String) {
        print("⚠️ ⚠️ Key = \(key) isUndefinedKey ⚠️ ⚠️")
    }
    
    open override class func resolveInstanceMethod(_ sel: Selector!) -> Bool {
        if let aMethod = class_getInstanceMethod(self, NSSelectorFromString("unrecognizedSelectorSentToInstance")) {
            class_addMethod(self, sel, method_getImplementation(aMethod), method_getTypeEncoding(aMethod))
            return true
        }
        return super.resolveInstanceMethod(sel)
    }
    
    open override class func resolveClassMethod(_ sel: Selector!) -> Bool {
        if let aMethod = class_getClassMethod(self, NSSelectorFromString("unrecognizedSelectorSentToClass")) {
            class_addMethod(self, sel, method_getImplementation(aMethod), method_getTypeEncoding(aMethod))
            return true
        }
        return super.resolveInstanceMethod(sel)
    }
    
    private func unrecognizedSelectorSentToInstance() {
        print("unrecognized selector sent to Instance");
    }
    
    private class func unrecognizedSelectorSentToClass() {
        print("unrecognized selector sent to class");
    }
}

public extension STBaseModel {
    /// json转model
    func st_jsonToModel<T>(_ type: T.Type, value: Any) -> T? where T : Decodable {
        if let newValue = value as? String {
            if let data = newValue.data(using: .utf8) {
                do {
                    let dict = try JSONSerialization.jsonObject(with: data, options: .mutableContainers)
                    let data = try JSONSerialization.data(withJSONObject: dict)
                    let decoder = JSONDecoder()
                    return try decoder.decode(type, from: data)
                } catch {
                    return nil
                }
            }
        }
        return nil
    }
    
    static func jsonToModel<T>(_ type: T.Type, value: Any) -> T? where T : Decodable {
        return STBaseModel().st_jsonToModel(type, value: value)
    }
    
    /// model转json
    func st_modelToJson<T>(value: T) -> String where T : Encodable {
        if let jsonData = try? JSONEncoder().encode(value) {
            if let jsonString = String.init(data: jsonData, encoding: String.Encoding.utf8) {
                return jsonString
            }
        }
        return ""
    }
    
    static func modelToJson<T>(value: T) -> String where T : Encodable {
        return STBaseModel().st_modelToJson(value: value)
    }
    
    /// array转json
    func st_arrayToJson<T>(value: T) -> String where T : Encodable {
        var jsonStr = ""
        if JSONSerialization.isValidJSONObject(value) {
            do {
                let data = try JSONSerialization.data(withJSONObject: value, options: .prettyPrinted)
                if let str = String.init(data: data, encoding: .utf8) {
                    jsonStr = str
                }
            } catch {
                
            }
        }
        return jsonStr
    }
    
    static func arrayToJson<T>(value: T) -> String where T : Encodable {
        return STBaseModel().st_arrayToJson(value: value)
    }
}
