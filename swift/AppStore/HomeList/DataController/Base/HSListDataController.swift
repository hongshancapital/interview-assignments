//
//  HSListDataController.swift
//  AppStore
//
//  Created by huyanling on 2022/5/12.
//

import Combine
import Foundation

class HSListDataController<Model: AnyObject>: ObservableObject, HSListDataControllerProtocol {
    typealias Item = Model

    @Published var hasMore: Bool = false
    @Published var items: [Model] = [Model]()
    var cursor: String?
    var isRquest: Bool = false
    var mockJson: String?
    enum State {
        case noDeter
        case complete
        case empty
        case error
    }

    @Published var loadState: State = .noDeter

    func requestEndPoint() -> APIService.Endpoint {
        return .search
    }

    func requestParma() -> [String: String] {
        return [String: String]()
    }

    func parseDataItems(from responseData: [String: Any], isRefresh: Bool) -> [Model] {
        return [Model]()
    }

    func loadData(more: Bool) {
        if more && !hasMore {
            return
        }
        if isRquest {
            return
        }
        isRquest = true
        let successBlock: (_ response: [String: Any]) -> Void = { [weak self] response in
            guard let self = self else { return }
            if more {
                let moreItems = self.parseDataItems(from: response, isRefresh: false)
                self.items = self.items + moreItems
            } else {
                self.items = self.parseDataItems(from: response, isRefresh: true)
            }
            if self.items.count == 0 {
                self.loadState = .empty
            } else {
                self.loadState = .complete
            }
        }
        let failureBlock: (_ error: Error) -> Void = { [weak self] error in
            guard let self = self else { return }
            print("\(error.localizedDescription)")
            self.hasMore = false
            if self.items.count == 0 {
                self.loadState = .error
            } else {
                self.loadState = .complete
            }
        }

        #if DEBUG
            if let path = Bundle.main.path(forResource: mockJson ?? "", ofType: nil),
               let json = try? JSONSerialization.jsonObject(with: NSData(contentsOfFile: path) as Data) as? [String: Any] {
                successBlock(json)
                isRquest = false
                return
            }
        #endif
        APIService.shared.GET(endpoint: requestEndPoint(), params: requestParma()) { (result: Result<Dictionary<String, Any>, APIService.APIError>) in
            self.isRquest = false
            switch result {
            case let .success(appList):
                successBlock(appList)
            case let .failure(error):
                failureBlock(error)
            }
        }
    }
}
