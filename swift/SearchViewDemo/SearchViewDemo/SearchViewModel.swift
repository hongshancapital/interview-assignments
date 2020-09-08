//
//  SearchViewModel.swift
//  SearchDemo
//
//  Created by Mason Sun on 2020/9/8.
//

import Foundation

class SearchViewModel: ObservableObject {
    @Published var isSearching: Bool = false
    @Published var result: [Category] = []

    // MARK: Intents

    func cancel() {
        result = []
        isSearching = false
    }

    func search(with text: String) {
        isSearching = true
        Request.getMockData { result in
            DispatchQueue.main.async {
                switch result {
                case .success(let categories): self.result = categories.filter { $0.brand == text }
                case .failure(let error): print(error.localizedDescription)
                }
            }
        }
    }
}

