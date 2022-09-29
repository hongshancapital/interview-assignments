//
//  AppRequestsTargetTests.swift
//  AppTests
//
//  Created by lizhao on 2022/9/27.
//

import Quick
import Nimble
import GCore
import Combine
import Moya

@testable import App

final class AppRequestsTargetTests: QuickSpec {
    override func spec() {
        describe("AppListRequest") {
            var request: AppListRequest!
            var networkProvider: MoyaProvider<AppRequestsTarget>!
            var cancelabel: AnyCancellable?
            
            beforeEach {
                networkProvider = NetworkProvider.build(AppRequestsTarget.self)
                request = AppListRequest(page: 1)
            }
            
            it("send request") {
                waitUntil(timeout: .seconds(10)) { done in
                    cancelabel = request
                        .sendRequest(dependence: networkProvider)
                        .sink { _ in
                            
                        } receiveValue: { value in
                            expect(value.count).to(equal(20))
                            done()
                        }
                }
            }
            
            afterEach {
                cancelabel = nil
            }
        }
        
        describe("FavoriteAppRequest isFavorited: false") {
            var request: FavoriteAppRequest!
            var networkProvider: MoyaProvider<AppRequestsTarget>!
            var cancelabel: AnyCancellable?
            
            beforeEach {
                networkProvider = MoyaProvider<AppRequestsTarget>(stubClosure: MoyaProvider.delayedStub(0.2))
                request = FavoriteAppRequest(id: 1, isFavorited: true)
            }
            
            it("send request") {
                waitUntil(timeout: .seconds(10)) { done in
                    cancelabel = request
                        .sendRequest(dependence: networkProvider)
                        .sink { _ in
                            print("finshed")
                        } receiveValue: { value in
                            expect(value.id).to(equal(1))
                            expect(value.isFavorited).to(beFalse())
                            done()
                        }
                }
            }

            afterEach {
                cancelabel = nil
            }
        }
        
        describe("FavoriteAppRequest isFavorited: false") {
            var request: FavoriteAppRequest!
            var networkProvider: MoyaProvider<AppRequestsTarget>!
            var cancelabel: AnyCancellable?
            
            beforeEach {
                networkProvider = MoyaProvider<AppRequestsTarget>(stubClosure: MoyaProvider.delayedStub(0.2))
                request = FavoriteAppRequest(id: 1, isFavorited: false)
            }
            
            it("send request") {
                waitUntil(timeout: .seconds(10)) { done in
                    cancelabel = request
                        .sendRequest(dependence: networkProvider)
                        .sink { _ in
                            
                        } receiveValue: { value in
                            expect(value.id).to(equal(1))
                            expect(value.isFavorited).to(beTrue())
                            done()
                        }
                }
            }

            afterEach {
                cancelabel = nil
            }
        }
    }
}
