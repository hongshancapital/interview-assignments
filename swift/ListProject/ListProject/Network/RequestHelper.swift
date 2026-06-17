//
//  RequestHelper.swift
//  ListProject
//
//  Created by shencong on 2022/6/12.
//

import Foundation
import Combine


enum RequestHelper {
    static let apiClient = APIClient()
    static let baseUrl = URL(string: "https://mock.mengxuegu.com/mock/62a5a24512c14164246306a9/")!
    static let baseUrlString = "https://mock.mengxuegu.com/mock/62a5a24512c14164246306a9/"
}


enum APIPath: String {
    case resourceList = "example/getList"
}

extension RequestHelper {
    static func requestMock(_ path: APIPath,_ page: Int) -> [ItemModel] {
        var results: [ItemModel] = []
        let pagesCount = 10
        let startIndex = pagesCount*page
        let request = URLRequest(url: baseUrl.appendingPathComponent(path.rawValue))
        let allLists: [ItemModel] = apiClient.requestMock(request)
        
        for (index,element) in allLists.enumerated() {
            if index >= startIndex && index < startIndex+10 {
                results.append(element)
            }
         }
        return results
    }
}

struct RequestWrapper {
    class mockModel: ObservableObject {
        var cancellables = Set<AnyCancellable>()
    }
}

extension RequestWrapper.mockModel {
    func getRequest() {
        let request: NNetworkRequest = NNetworkRequest()
        let rString = RequestHelper.baseUrlString + APIPath.resourceList.rawValue
        request.host = rString
        request.requestMethod = .GET
        NetworkService.shared.fetch(with: request)
            .catch { error -> AnyPublisher<ItemRespondModel, Error> in
            // 如果发生错误，则不会往下走
            Fail(error: error).eraseToAnyPublisher()
        } .sink { completion in
            switch completion {
            case .failure(let error):
                if let error = error as? NetworkError {
                    debugPrint(error.description)
                } else {
                    debugPrint(error.localizedDescription)
                }
            default:
                print(completion)
            }
        } receiveValue: { ItemRespondModel in
            print("成功")
        }
        .store(in: &cancellables)
    }
}
