//
//  ViewModelFactory.swift
//  ListDemo
//
//  Created by kent.sun on 2023/2/2.
//

import Foundation

class ViewModelFactory {
    @MainActor
    func makeMovieListViewModel() -> SearchListViewModel {
        return SearchListViewModel(networking: networking)
    }
    
    private var networking: SearchAppAPI {
        SearchAppAPI()
    }
}
