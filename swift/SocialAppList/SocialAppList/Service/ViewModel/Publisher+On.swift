//
//  Publisher+On.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Combine

@available(macOS 10.15, iOS 13.0, tvOS 13.0, watchOS 6.0, *)
extension Publisher {
    func sink(onValue: ((Self.Output) -> Void)? = nil,
              onError: ((Error) -> Void)? = nil,
              onCompleted: (() -> Void)? = nil) -> AnyCancellable
    {
        return self.sink { completion in
            switch completion {
            case .finished:
                onCompleted?()
            case let .failure(error):
                onError?(error)
            }
        } receiveValue: { value in
            onValue?(value)
        }
    }
}
