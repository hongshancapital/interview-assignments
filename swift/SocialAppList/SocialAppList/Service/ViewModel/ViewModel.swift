//
//  ViewModel.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Combine

private var actionKey = "actionKey"

protocol ViewModel: ObservableObject, HasStorage {
    associatedtype Action
        
    var action: PassthroughSubject<Action, Never> { get }
    
    func transform(action: AnyPublisher<Action, Never>) -> AnyPublisher<Action, Never>
    
    func mutate(action: Action)
}

extension ViewModel {
    func transform(action: AnyPublisher<Action, Never>) -> AnyPublisher<Action, Never> { return action }
    
    func mutate(action: Action) {}
}

extension ViewModel {
    var action: PassthroughSubject<Action, Never> {
        return _action
    }
    
    private var _action: PassthroughSubject<Action, Never> {
        if let object: PassthroughSubject<Action, Never> = associatedObject(forKey: &actionKey) {
            return object
        } else {
            let object = PassthroughSubject<Action, Never>()
            setAssociatedObject(object, forKey: &actionKey)
            transform(action: object.eraseToAnyPublisher())
                .sink(onValue: { [weak self] action in
                    guard let self = self else { return }
                    self.mutate(action: action)
                }).store(in: &storage)
            return object
        }
    }
}

extension ViewModel {
    func send(_ action: Action) {
        self.action.send(action)
    }
}
