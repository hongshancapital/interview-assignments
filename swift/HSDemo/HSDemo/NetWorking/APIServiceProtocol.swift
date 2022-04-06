//
//  APIServiceProtocol.swift
//  HSDemo
//

import Foundation

protocol APIServiceProtocol {
    func fetchArtWorks(url: URL?, completion: @escaping (Result<[ArtWork], APIError>) -> Void)
}
