//
//  APIMockService.swift
//  HSDemo
//

import Foundation

struct APIMockService: APIServiceProtocol {
    var result: Result<[ArtWork], APIError>

    func fetchArtWorks(url: URL?, completion: @escaping (Result<[ArtWork], APIError>) -> Void) {
        completion(result)
    }
}
