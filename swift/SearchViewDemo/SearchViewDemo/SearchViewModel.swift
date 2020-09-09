//
//  SearchViewModel.swift
//  SearchDemo
//
//  Created by Mason Sun on 2020/9/8.
//

import Foundation

class SearchViewModel: ObservableObject {
    @Published var text: String = "" { didSet { search() } }
    @Published var result: [Category] = []
    @Published var isSearching: Bool = false

    // MARK: Accessors

    var isEmptyResult: Bool {
        isSearching && result.isEmpty && !text.isEmpty
    }

    // MARK: Intents

    func cancel() {
        text = ""
        result = []
        isSearching = false
    }

    func search(completion: ((Result<[Category], Error>) -> Void)? = nil) {
        isSearching = true
        Request.getMockData { result in
            DispatchQueue.main.async {
                switch result {
                case .success(let categories): self.result = categories.filter { $0.brand == self.text }
                case .failure(let error): print(error.localizedDescription)
                }
                completion?(result)
            }
        }
    }
}

