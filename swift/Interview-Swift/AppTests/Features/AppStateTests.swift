//
//  AppStateTests.swift
//  AppTests
//
//  Created by lizhao on 2022/9/27.
//

import Quick
import Nimble

@testable import App

final class AppStateTests: QuickSpec {
    override func spec() {
        describe("AppState") {

            it("AppState.init") {
                let state = AppState()
                expect(state.appList.loadingList).to(equal(false))
                expect(state.appList.loadingListError).to(beNil())
                expect(state.appList.apps.count).to(equal(0))
                expect(state.appList.canLoadMore).to(beTrue())
                expect(state.appList.loadmoreState).to(equal(.hidden))
                expect(state.appList.page).to(equal(1))
            }
             
            it("AppState.updateFavoriteState: true") {
                let state = MockData.List.mockLoadedFirstPageAppState
                
                state.appList.updateFavoriteState(id: 1, isFavorite: true)
                
                let isFavorite = state.favoriteState(id: 1)
                expect(isFavorite).to(beTrue())
            }
            
            
           it("AppState.updateFavoriteState: false") {
               let state = MockData.List.mockLoadedFirstPageAppState
               
               state.appList.updateFavoriteState(id: 1, isFavorite: false)
               
               let isFavorite = state.favoriteState(id: 1)
               expect(isFavorite).to(beFalse())
           }
        }
    }
}
