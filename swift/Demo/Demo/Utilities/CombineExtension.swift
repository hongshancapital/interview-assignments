//
//  CombineExtension.swift
//  Demo
//
//  Created by 葬花桥 on 2023/3/15.
//

import Foundation
#if canImport(Combine)
import Combine

@available(OSX 10.15, iOS 13.0, tvOS 13.0, watchOS 6.0, *)
public extension Publisher {
    func weakSinkOn<T: AnyObject>(_ object: T, receiveCompletion: @escaping (T, Subscribers.Completion<Failure>) -> Void, receiveValue: @escaping ((T, Output) -> Void)) -> AnyCancellable {
        sink { [weak object] in
            guard let object = object else {
                return
            }
            receiveCompletion(object, $0)
        } receiveValue: { [weak object] in
            guard let object = object else {
                return
            }
            receiveValue(object, $0)
        }
    }
}

#endif
