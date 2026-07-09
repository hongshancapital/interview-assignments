//
//  TestHelpers.swift
//  DemoTests
//
//  Created by 葬花桥 on 2023/3/16.
//

import XCTest
import Combine

extension NSError {
    static var test: NSError {
        return NSError(domain: "test", code: 0, userInfo: [NSLocalizedDescriptionKey: "Unit Test error"])
    }
}

extension Publisher {
    func sinkToResult(_ result: @escaping (Result<Output, Failure>) -> Void) -> AnyCancellable {
        sink(receiveCompletion: { completion in
            switch completion {
            case let .failure(error):
                result(.failure(error))
            default: break
            }
        }, receiveValue: { value in
            result(.success(value))
        })
    }
}

extension Result where Success: Equatable {
    func assertSuccess(value: Success, file: StaticString = #file, line: UInt = #line) {
        switch self {
        case let .success(resultValue):
            XCTAssertEqual(resultValue, value, file: file, line: line)
        case let .failure(error):
            XCTFail("Unexpected error: \(error)", file: file, line: line)
        }
    }
}

extension Result {
    func assertFailure(_ message: String? = nil, file: StaticString = #file, line: UInt = #line) {
        switch self {
        case let .success(value):
            XCTFail("Unexpected success: \(value)", file: file, line: line)
        case let .failure(error):
            if let message = message {
                XCTAssertEqual(error.localizedDescription, message, file: file, line: line)
            }
        }
    }
}

extension Result {
    var isSuccess: Bool {
        switch self {
        case .success: return true
        case .failure: return false
        }
    }
}

enum MockError: Swift.Error {
    case valueNotSet
    case codeDataModel
    case timeout
}

public func XCTAssertThrowsError<T>(_ expression: @escaping () async throws -> T, _ message: @autoclosure () -> String = "", file: StaticString = #filePath, line: UInt = #line, _ errorHandler: (_ error: Error) -> Void = { _ in }) async {
    var error: Error?
    do {
        _ = try await expression()
    } catch let e {
        error = e
//        XCTFail("Async error thrown: \(error)", file: file, line: line)
    }
    
    if let error = error {
        
        func thorw() throws {
            throw error
        }
        
        XCTAssertThrowsError(try thorw(), message(), file: file, line: line, errorHandler)
    }
}

public extension Publisher {
    var sequence: CombineAsyncThrowingPublsiher<Self> {
        CombineAsyncThrowingPublsiher(self)
    }
    
    func timeoutSequence<S>(_ interval: S.SchedulerTimeType.Stride, scheduler: S) -> CombineAsyncThrowingPublsiher<Publishers.Timeout<Publishers.SetFailureType<Self, Error>, S>> where S : Scheduler {
        let p = self.setFailureType(to: Error.self)
        .timeout(interval, scheduler: scheduler) {
            MockError.timeout
        }
        
        return CombineAsyncThrowingPublsiher(p)
    }
}

public struct CombineAsyncPublsiher<P>: AsyncSequence, AsyncIteratorProtocol where P: Publisher, P.Failure == Never {
    public typealias Element = P.Output
    public typealias AsyncIterator = CombineAsyncPublsiher<P>
    
    public func makeAsyncIterator() -> Self {
        return self
    }
    
    private let stream: AsyncStream<P.Output>
    private var iterator: AsyncStream<P.Output>.Iterator
    private var cancellable: AnyCancellable?
    
    public init(_ upstream: P, bufferingPolicy limit: AsyncStream<Element>.Continuation.BufferingPolicy = .unbounded) {
        var subscription: AnyCancellable?
        stream = AsyncStream<P.Output>(P.Output.self, bufferingPolicy: limit) { continuation in
            subscription = upstream
                .handleEvents(
                    receiveCancel: {
                        continuation.finish()
                    }
                )
                .sink(receiveValue: { value in
                    continuation.yield(value)
                })
        }
        cancellable = subscription
        iterator = stream.makeAsyncIterator()
    }
    
    public mutating func cancel() {
        cancellable?.cancel()
        cancellable = nil
    }
    
    public mutating func next() async -> P.Output? {
        await iterator.next()
    }
}

public extension Publisher where Self.Failure == Never {
    var sequence: CombineAsyncPublsiher<Self> {
        CombineAsyncPublsiher(self)
    }
}


public struct CombineAsyncThrowingPublsiher<P: Publisher>: AsyncSequence, AsyncIteratorProtocol {
    public typealias Element = P.Output
    public typealias AsyncIterator = CombineAsyncThrowingPublsiher<P>
    
    public func makeAsyncIterator() -> Self {
        return self
    }
    
    private let stream: AsyncThrowingStream<P.Output, Error>
    private var iterator: AsyncThrowingStream<P.Output, Error>.AsyncIterator // = stream.makeAsyncIterator()
    private var cancellable: AnyCancellable?
    
    public init(_ upstream: P, bufferingPolicy limit: AsyncThrowingStream<Element, Error>.Continuation.BufferingPolicy = .unbounded) {
        var subscription: AnyCancellable?
        
        stream = AsyncThrowingStream<P.Output, Error>(P.Output.self, bufferingPolicy: limit) { continuation in
            subscription = upstream
                .handleEvents(
                    receiveCancel: {
                        continuation.finish(throwing: nil)
                    }
                )
                .sink(receiveCompletion: { completion in
                    switch completion {
                    case .failure(let error):
                        continuation.finish(throwing: error)
                    case .finished: continuation.finish(throwing: nil)
                    }
                }, receiveValue: { value in
                    continuation.yield(value)
                })
        }
        cancellable = subscription
        iterator = stream.makeAsyncIterator()
    }
    
    public mutating func cancel() {
        cancellable?.cancel()
        cancellable = nil
    }
    
    public mutating func next() async throws -> P.Output? {
        try await iterator.next()
    }
}
