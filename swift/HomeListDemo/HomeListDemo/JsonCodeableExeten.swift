//
//  JsonCodeableExeten.swift
//  HomeListDemo
//
//  Created by yaojinhai on 2022/9/23.
//

import Foundation

//Codable
extension Decodable {
    
    static func convertModel(data: Data?) -> Self? {
        guard let data = data else { return nil }
        var jsonItem: Self? = nil
        do {
            jsonItem = try JSONDecoder().decode(self, from: data)
        } catch  {
#if DEBUG
            fatalError("data convert json error: \(error)")
#endif
        }
        return jsonItem
    }
    static func convertModel(any: Any?) -> Self? {
        convertModel(data: JSONSerialization.data(any: any))
    }
    
    
}

extension JSONDecoder {
    static func jsonDecoder<T: Codable>(_ type: T.Type,from data: Data?) -> T? {
        guard let data = data else {
            return nil;
        }
        let json = JSONDecoder();
        var jsonItem: T? = nil;
        do {
            jsonItem = try json.decode(type, from: data)
        } catch {
#if DEBUG
            fatalError("data convert json error: \(error)")
#endif
        }
        
        return jsonItem;
    }
    static func jsonDecoder<T: Codable>(_ type: T.Type,fromAny data: Any?) -> T? {
        if let data = data as? Data {
            return jsonDecoder(type, from: data);
        }
        if let stringValue = data as? String {
            return jsonDecoder(type, from: stringValue.data(using: .utf8));
        }
        let jsonData = JSONSerialization.data(any: data);
        return jsonDecoder(type, from: jsonData);
    }
}

extension JSONSerialization {
    static func jsonDictionary(data: Data?) -> [String: Any]? {
        jsonObject(data: data) as? [String: Any]
    }

    static func jsonObject(data: Data?) -> Any? {
        guard let data = data else{
            return nil;
        }
        return try? jsonObject(with: data, options: .mutableContainers)
    }
    
    static func data(any: Any?) -> Data? {
        guard let any = any else {
            return nil
        }
        if let strl = any as? String {
            return strl.data(using: .utf8)
        }
        if !isValidJSONObject(any) {
            return nil;
        }
        var dataValue: Data? = nil
        do {
            dataValue = try data(withJSONObject: any, options: .prettyPrinted)
        } catch  {
#if DEBUG
            fatalError("转换失败：\(error)")
#endif
        }
        return dataValue
    }
}

