//
//  DataService.swift
//  SwiftHomeWork
//
//  Created by apple on 2022/4/12.
//

import Foundation

protocol AppService {
    func refresh() async throws -> [Entity]
    func loadMore() async throws -> [Entity]
}

enum Err: Error, LocalizedError {

    case invalidURL(url: String)

    case networkError(response: URLResponse)

    var errorDescription: String? {
        switch self {
        case .invalidURL(let url):
            return "transform string to URL failed \(url)"
        case .networkError(let response):
            return "network failed: \(response)"
        }

    }

    var failureReason: String? {
        switch self {
        case .invalidURL(let url):
            return "invalid url \(url)"
        case .networkError(let response):
            return "network failed: \(response)"
        }

    }

    var recoverySuggestion: String? {
        switch self {
        case .invalidURL:
            return "give a valid url"
        case .networkError:
            return "check your network and try again"
        }

    }

    var helpAnchor: String? {
        switch self {
        case .invalidURL:
            return "give a valid url"
        case .networkError:
            return "check your network and try again"
        }

    }
}

class MockNetService: AppService {

    static let url = "https://itunes.apple.com/search?entity=software&limit=50&term=chat"

    var mockData = [Entity]()

    let pageSize = 20

    func getMockData() async throws -> [Entity] {
        if mockData.isEmpty {
            mockData = try await getData().results
        } else {
            try await Task.sleep(nanoseconds: 2_000_000_000)// mock the api request time
        }
        return mockData
    }

    func getData() async throws -> Response {
        guard let url = URL.init(string: Self.url) else {
            throw Err.invalidURL(url: Self.url)
        }
        let (data, response) = try await URLSession.shared.data(for: URLRequest.init(url: url), delegate: nil)
        if let httpResponse = response as? HTTPURLResponse, !(200...299).contains(httpResponse.statusCode) {
            throw Err.networkError(response: response)
        }
        return try JSONDecoder().decode(Response.self, from: data)
    }

    func refresh() async throws -> [Entity] {
        let data = try await getMockData()
        return Array(data.prefix(self.pageSize))
    }

    func loadMore() async throws -> [Entity] {
        let data = try await getMockData()
        return Array(data.prefix(self.pageSize*2))
    }
}
