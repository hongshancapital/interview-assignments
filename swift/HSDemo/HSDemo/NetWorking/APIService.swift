//
//  APIService.swift
//  SwitUI实战
//

import Foundation

struct APIService: APIServiceProtocol {
    func fetchArtWorks(url: URL?, completion: @escaping (Result<[ArtWork], APIError>) -> Void) {
        guard let url = url else {
            let error = APIError.badURL
            completion(Result.failure(error))
            return
        }

        let task = URLSession.shared.dataTask(with: url) { data, response, error in
            if let error = error as? URLError {
                completion(Result.failure(APIError.url(error)))
            } else if let response = response as? HTTPURLResponse,!(200 ... 299).contains(response.statusCode) {
                completion(Result.failure(APIError.badResponse(statusCode: response.statusCode)))
            } else if let data = data {
                let decoder = JSONDecoder()
                do {
                    let listData = try decoder.decode(ArtWorlList.self, from: data) as ArtWorlList
                    completion(Result.success(listData.results))
                } catch {
                    completion(Result.failure(APIError.parsing(error as? DecodingError)))
                }
            }
        }

        task.resume()
    }
}
