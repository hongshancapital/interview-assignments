//
//  NetworkMonitor.swift
//  SwiftUIAssignments
//
//  Created by zcj on 2023/6/5.
//

import Foundation
import Network
import Combine

class NetworkMonitor {
    static let shared = NetworkMonitor()

    /// status for current network
    var status: Status = .requiresConnection

    // MARK: - private
    private let monitor: NWPathMonitor
    private lazy var subject = CurrentValueSubject<Status, Never>(.connected)

    // MARK: - system
    private init() {
        monitor = NWPathMonitor()
        monitor.pathUpdateHandler = { [weak self] path in
            guard let `self` = self else { return }
            DispatchQueue.main.async {
                switch path.status {
                case .satisfied:
                    self.status = .connected
                    self.subject.send(.connected)
                case .unsatisfied:
                    self.status = .unConnected
                    self.subject.send(.unConnected)
                case .requiresConnection:
                    self.status = .requiresConnection
                    self.subject.send(.requiresConnection)
                @unknown default:
                    fatalError()
                }
            }
        }

        let queue = DispatchQueue(label: "NetworkMonitor")
        monitor.start(queue: queue)
    }
}

extension NetworkMonitor {

    enum Status {
        /// connnected network
        case connected

        /// unConnnected network
        case unConnected

        /// does not currently have a usable route, but a connection attempt will trigger network attachment.
        case requiresConnection
    }
}


extension NetworkMonitor {

    var publisher: AnyPublisher<Status, Never> {
        subject.eraseToAnyPublisher()
    }
}
