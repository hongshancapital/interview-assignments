//
//  UploadTaskPublisher.swift.swift
//  Network
//
//  Created by shencong on 2022/6/10.
//

import Foundation
import Combine

extension URLSession {
    func dataTaskProgressPublisher(for request: URLRequest, from data: Data, progress: Progress? = nil) -> AnyPublisher<NDataResponse, Error> {
        return Deferred<AnyPublisher<NDataResponse, Error>> {
            let subject = PassthroughSubject<NDataResponse, Error>()
            let task = self.uploadTask(with: request, from: data) { data, response, error in
                if let data = data, let response = response {
                    debugPrint(" ==> " + (String.init(data: data, encoding: String.Encoding.utf8) ?? ""))
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
}
