//
//  DownloadTaskPublisher.swift
//  Network
//
//  Created by shencong on 2022/6/10.
//

import Foundation
import Combine

public struct DownloadResponse {
    public var fileURL: URL
    public var response: URLResponse
}

extension URLSession {
    func dataTaskProgressPublisher(for request: URLRequest, progress: Progress? = nil) -> AnyPublisher<NDataResponse, Error> {
        return Deferred<AnyPublisher<NDataResponse, Error>> {
            let subject = PassthroughSubject<NDataResponse, Error>()
            let task = self.dataTask(with: request) { data, response, error in
                if let data = data, let response = response {
                    subject.send(NDataResponse(data: data, response: response))
                    subject.send(completion: .finished)
                } else if let error = error {
                    subject.send(completion: .failure(error))
                }
            }
            progress?.addChild(task.progress, withPendingUnitCount: 1)
            task.resume()
            return subject
                .handleEvents(receiveCancel: task.cancel)
                .eraseToAnyPublisher()
        }
        .eraseToAnyPublisher()
    }

    func dataTaskProgressPublisher(for url: URL, progress: Progress? = nil) -> AnyPublisher<NDataResponse, Error> {
        return dataTaskProgressPublisher(for: URLRequest(url: url), progress: progress)
    }

    func downloadTaskPublisher(for url: URL, progress: Progress? = nil) ->  AnyPublisher<DownloadResponse, Error> {
        let request = URLRequest(url: url)
        return downloadTaskPublisher(with: request, progress: progress)
    }

    func downloadTaskPublisher(with request: URLRequest, progress: Progress? = nil) -> AnyPublisher<DownloadResponse, Error> {
        return Deferred<AnyPublisher<DownloadResponse, Error>> {
            let subject = PassthroughSubject<DownloadResponse, Error>()
            let task = self.downloadTask(with: request) { url, response, error in
                guard error == nil else {
                    subject.send(completion: .failure(error!))
                    return
                }

                guard let response = response else {
                    subject.send(completion: .failure(URLError(.badServerResponse)))
                    return
                }

                guard let url = url else {
                    subject.send(completion: .failure(URLError(.fileDoesNotExist)))
                    return
                }

                do {
                    guard let cacheDir = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask).first else { return }
                    let fileUrl = cacheDir.appendingPathComponent(UUID().uuidString)
                    try FileManager.default.moveItem(atPath: url.path, toPath: fileUrl.path)
                    subject.send(DownloadResponse(fileURL: fileUrl, response: response))
                    subject.send(completion: .finished)
                } catch {
                    subject.send(completion: .failure(error))
                    return
                }
            }
            progress?.addChild(task.progress, withPendingUnitCount: 1)
            task.resume()
            return subject
                .handleEvents(receiveCancel: task.cancel)
                .eraseToAnyPublisher()
        }
        .eraseToAnyPublisher()
    }
}
