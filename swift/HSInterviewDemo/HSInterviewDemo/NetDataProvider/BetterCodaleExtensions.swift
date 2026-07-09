//
//  BetterCodableExtensions.swift
//  APIService_Example
//
//  Created by CoderStar on 2022/5/6.
//  Copyright © 2022 CocoaPods. All rights reserved.
//

import Foundation
import BetterCodable

public typealias DefaultEmptyArray<T> = DefaultCodable<DefaultEmptyArrayStrategy<T>> where T: Decodable

public typealias DefaultEmptyDictionary<K, V> = DefaultCodable<DefaultEmptyDictionaryStrategy<K, V>> where K: Decodable & Hashable, V: Decodable

public typealias DefaultFalse = DefaultCodable<DefaultFalseStrategy>

public typealias DefaultTrue = DefaultCodable<DefaultTrueStrategy>

/// Double
public struct DefaultDoubleZeroStrategy: DefaultCodableStrategy {
    public static var defaultValue: Double { 0.0 }
}

public typealias DefaultDoubleZero = DefaultCodable<DefaultDoubleZeroStrategy>

/// Float
public struct DefaultFloatZeroStrategy: DefaultCodableStrategy {
    public static var defaultValue: Float { 0.0 }
}

public typealias DefaultFloatZero = DefaultCodable<DefaultFloatZeroStrategy>

/// Int
public struct DefaultIntZeroStrategy: DefaultCodableStrategy {
    public static var defaultValue: Int { 0 }
}

public typealias DefaultIntZero = DefaultCodable<DefaultIntZeroStrategy>

/// 空字符串
public struct DefaultEmptyStringStrategy: DefaultCodableStrategy {
    public static var defaultValue: String { "" }
}

public typealias DefaultEmptyString = DefaultCodable<DefaultEmptyStringStrategy>
