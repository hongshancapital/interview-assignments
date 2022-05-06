//
//  LoadHomePageRequest.swift
//  InterviewiOSDemo
//
//  Created by 寇云鹏 on 2022/5/7.
//

import Foundation
import Combine

struct LoadHomePageRequest {
    
    static var results: AnyPublisher<[HomePageViewModel], AppError> {
        (1...20)
            .map { _ in LoadHomePageRequest().publisher }
            .zipAll
    }
    
    var publisher: AnyPublisher<HomePageViewModel, AppError> {
        homePagePublisher()
            .map {
                HomePageViewModel(homePageItem: $0)
            }
            .mapError {
                AppError.networkFaild($0)
            }
            .receive(on: DispatchQueue.main)
            .eraseToAnyPublisher()
    }
    
    func homePagePublisher() -> AnyPublisher<HomePageItem, Error> {
        URLSession.shared.dataTaskPublisher(for: URL(string: "https://itunes.apple.com/search?entity=software&limit=50&term=chat")!)
            .map {
                $0.data
            }
            .decode(type: HomePageItem.self, decoder: appDecoder)
            .eraseToAnyPublisher()
    }
}
