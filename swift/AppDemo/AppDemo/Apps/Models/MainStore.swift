//
//  MainStore.swift
//  AppDemo
//
//  Created by jaly on 2022/11/29.
//

import Foundation

protocol MainState {
    init()
}

protocol MainReduce {
    associatedtype S: MainState
    associatedtype A: MainAction
    
    init()
    func reduce(state: S, action: A) async -> S
}

protocol MainAction {
    
}

class MainStore<S: MainState, R: MainReduce>: ObservableObject {
    
    @Published
    private(set) var state: S = .init()
    
    private var reducer: R = .init()
    
    
    func dispatch<A>(action: A) async where R.S == S, R.A == A {
        let newState = await reducer.reduce(state: state, action: action)
        await MainActor.run {
            self.state = newState
        }
    }
}
