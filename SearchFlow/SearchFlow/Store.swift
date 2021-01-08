//
//  Store.swift
//  SearchBar-SwiftUI
//
//  Created by evan on 2021/1/6.
//

import Combine
import Foundation

typealias SearchStore = Store<SearchState, SearchAction, SearchSever>
typealias Reducer<State, Action, Server> = (inout State, Action, Server) -> AnyPublisher<Action, Never>?

final class Store<State, Action, Server> : ObservableObject {
    @Published var state: State
    
    private let server: Server
    private let reducer: Reducer<State, Action, Server>
    private var effectCancellables: Set<AnyCancellable> = []

    init(initialState: State, server: Server, reducer: @escaping Reducer<State, Action, Server>) {
        self.state = initialState
        self.server = server
        self.reducer = reducer
    }
    
    func send(_ action: Action) {
        guard let value = reducer(&state, action, server) else { return }
        value
            .receive(on: DispatchQueue.main)
            .sink(receiveValue: send)
            .store(in: &effectCancellables)
    }
}
