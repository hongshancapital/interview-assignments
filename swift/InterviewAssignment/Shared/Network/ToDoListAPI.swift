//
//  WebService.swift
//  InterviewAssignment (iOS)
//
//  Created by Vic Zhang on 2021/12/10.
//

import Foundation
import Combine

enum ToDoListAPI {
    internal static func sendRequest(_ urlStr: String) -> AnyPublisher<Data, ToDoListAPIError> {
        guard let url = URL(string: urlStr) else {
            return .empty()
        }

        var request = URLRequest(url: url)
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        request.timeoutInterval = 5
        
        return URLSession.shared.dataTaskPublisher(for: request)
            .mapError({ToDoListAPIError.urlError($0)})
            .flatMap({ data, response -> AnyPublisher<Data, ToDoListAPIError> in
                guard let response = response as? HTTPURLResponse else {
                    return .fail(.invalidHttpResponse)
                }

                guard 200..<300 ~= response.statusCode else {
                    return .fail(.responseError(statusCode: response.statusCode, data: data))
                }
                
                return .just(data)
            })
            .share()
            .eraseToAnyPublisher()
    }
    
    internal static func searchToDoList(_ query: String)  -> AnyPublisher<[ToDoItem], ToDoListAPIError> {
        return sendRequest("https://www.example.com/todolist?q=\(query)")
                .decode(type: [ToDoItem].self, decoder: JSONDecoder())
                .catch {error -> AnyPublisher<[ToDoItem], ToDoListAPIError> in
                    // ignore all error here, just load example data
                    
                    let items: [ToDoItem] = load("example.json")
                    return .just(items.filter{item in
                        guard query.isEmpty else {
                            return false
                        }
                        
                        return !item.name.contains(query)
                    })
                }
                .share()
                .eraseToAnyPublisher()
    }
}
