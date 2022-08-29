//
//  AppListDefaultValue.swift
//  AppList
//
//  Created by 大洋 on 2022/8/21.
//

import Foundation

protocol AppListDefaultValue {
    associatedtype Value: Codable
    static var defaultValue: Value { get }
}

@propertyWrapper
struct AppListDefault<T: AppListDefaultValue> {
    var wrappedValue: T.Value
}

extension AppListDefault: Codable {
    init(from decoder: Decoder) throws {
        let container = try decoder.singleValueContainer()
        wrappedValue = (try? container.decode(T.Value.self)) ?? T.defaultValue
    }
    
    func encode(to encoder: Encoder) throws {
        var container = encoder.singleValueContainer()
        try container.encode(wrappedValue)
    }
}


protocol IDemoDefaultValue {
    associatedtype Value: Codable
    static var defaultValue: Value { get }
}


extension KeyedDecodingContainer {
    func decode<T>(_ type: AppListDefault<T>.Type, forKey key: KeyedDecodingContainer<K>.Key) throws -> AppListDefault<T> where T: AppListDefaultValue {
        try decodeIfPresent(type, forKey: key) ?? AppListDefault(wrappedValue: T.defaultValue)
    }
}

extension KeyedEncodingContainer {
    mutating func encode<T>(_ value: AppListDefault<T>, forKey key: KeyedEncodingContainer<K>.Key) throws where T: AppListDefaultValue {
        try encodeIfPresent(value.wrappedValue, forKey: key)
    }
}

extension String: AppListDefaultValue {
    static var defaultValue: String = ""
}

extension Int: AppListDefaultValue {
    static var defaultValue: Int = 0
}

extension Bool {
    enum False: AppListDefaultValue {
        static var defaultValue: Bool = false
    }
    enum True: AppListDefaultValue {
        static var defaultValue: Bool = true
    }
}

extension Array: AppListDefaultValue where Element: Codable {
    static var defaultValue: [Element] { [] }
}

extension URL: AppListDefaultValue {
    static var defaultValue: URL? = URL(string: "")
}
